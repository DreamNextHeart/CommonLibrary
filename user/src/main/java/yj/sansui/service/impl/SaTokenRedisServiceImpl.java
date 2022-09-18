package yj.sansui.service.impl;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.util.SaFoxUtil;
import org.springframework.stereotype.Service;
import yj.sansui.version2.RedisUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sansui
 */
@Service
public class SaTokenRedisServiceImpl implements SaTokenDao {

    /**
     * 数据集合
     */
    public Map<String, Object> dataMap = new ConcurrentHashMap<String, Object>();

    /**
     * 过期时间集合 (单位: 毫秒) , 记录所有key的到期时间 [注意不是剩余存活时间]
     */
    public Map<String, Long> expireMap = new ConcurrentHashMap<String, Long>();

    /**
     * 构造函数
     */
    public SaTokenRedisServiceImpl() {
        initRefreshThread();
    }

    // ------------------------ String 读写操作

    /**
     * 获取Value，如无返空
     *
     * @param key 键名称
     * @return value
     */
    @Override
    public String get(String key) {
        return RedisUtil.getString(key);
    }

    /**
     * 写入Value，并设定存活时间 (单位: 秒)
     *
     * @param key     键名称
     * @param value   值
     * @param timeout 过期时间(值大于0时限时存储，值=-1时永久存储，值=0或小于-2时不存储)
     */
    @Override
    public void set(String key, String value, long timeout) {
        if(timeout == 0 || timeout <= SaTokenDao.NOT_VALUE_EXPIRE)  {
            return;
        }
        //判断是否永不过期
        if(timeout == SaTokenDao.NEVER_EXPIRE) {
            RedisUtil.setString(key,value);
        } else {
            RedisUtil.setStringTime(key, value, timeout);
        }
    }

    /**
     * 更新Value (过期时间不变)
     *
     * @param key   键名称
     * @param value 值
     */
    @Override
    public void update(String key, String value) {
        long expire = getTimeout(key);
        // -2 = 无此键
        if(expire == SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        this.set(key, value, expire);
    }

    /**
     * 删除Value
     *
     * @param key 键名称
     */
    @Override
    public void delete(String key) {
        RedisUtil.deleteString(key);
    }

    /**
     * 获取Value的剩余存活时间 (单位: 秒)
     *
     * @param key 指定key
     * @return 这个key的剩余存活时间
     */
    @Override
    public long getTimeout(String key) {
        return RedisUtil.getStringTimeout(key);
    }

    /**
     * 修改Value的剩余存活时间 (单位: 秒)
     *
     * @param key     指定key
     * @param timeout 过期时间
     */
    @Override
    public void updateTimeout(String key, long timeout) {

    }

    /**
     * 获取Object，如无返空
     *
     * @param key 键名称
     * @return object
     */
    @Override
    public Object getObject(String key) {
        clearKeyByTimeout(key);
        return RedisUtil.getObject(key);
    }

    /**
     * 写入Object，并设定存活时间 (单位: 秒)
     *
     * @param key     键名称
     * @param object  值
     * @param timeout 存活时间 (值大于0时限时存储，值=-1时永久存储，值=0或小于-2时不存储)
     */
    @Override
    public void setObject(String key, Object object, long timeout) {
        if(timeout == 0 || timeout <= SaTokenDao.NOT_VALUE_EXPIRE)  {
            return;
        }
        //判断是否永不过期
        if(timeout == SaTokenDao.NEVER_EXPIRE) {
            RedisUtil.setObject(key,object);
        } else {
            RedisUtil.setObjectTime(key, object, timeout);
        }
    }

    /**
     * 更新Object (过期时间不变)
     *
     * @param key    键名称
     * @param object 值
     */
    @Override
    public void updateObject(String key, Object object) {
        long expire = getObjectTimeout(key);
        // -2 = 无此键
        if(expire == SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        this.setObject(key, object, expire);
    }

    /**
     * 删除Object
     *
     * @param key 键名称
     */
    @Override
    public void deleteObject(String key) {
        RedisUtil.deleteObject(key);
    }

    /**
     * 获取Object的剩余存活时间 (单位: 秒)
     *
     * @param key 指定key
     * @return 这个key的剩余存活时间
     */
    @Override
    public long getObjectTimeout(String key) {
        return RedisUtil.getObjectTimeout(key);
    }

    /**
     * 修改Object的剩余存活时间 (单位: 秒)
     *
     * @param key     指定key
     * @param timeout 过期时间
     */
    @Override
    public void updateObjectTimeout(String key, long timeout) {
        // 判断是否想要设置为永久
        if(timeout == SaTokenDao.NEVER_EXPIRE) {
            long expire = getObjectTimeout(key);
            if(expire == SaTokenDao.NEVER_EXPIRE) {
                // 如果其已经被设置为永久，则不作任何处理
            } else {
                // 如果尚未被设置为永久，那么再次set一次
                this.setObject(key, this.getObject(key), timeout);
            }
        }
    }

    // ------------------------ 过期时间相关操作

    /**
     * 如果指定key已经过期，则立即清除它
     * @param key 指定key
     */
    void clearKeyByTimeout(String key) {
        Long expirationTime = expireMap.get(key);
        // 清除条件：如果不为空 && 不是[永不过期] && 已经超过过期时间
        if(expirationTime != null && expirationTime != SaTokenDao.NEVER_EXPIRE && expirationTime < System.currentTimeMillis()) {
            dataMap.remove(key);
            expireMap.remove(key);
        }
    }

    /**
     * 获取指定key的剩余存活时间 (单位：秒)
     */
    long getKeyTimeout(String key) {
        // 先检查是否已经过期
        clearKeyByTimeout(key);
        // 获取过期时间
        Long expire = expireMap.get(key);
        // 如果根本没有这个值
        if(expire == null) {
            return SaTokenDao.NOT_VALUE_EXPIRE;
        }
        // 如果被标注为永不过期
        if(expire == SaTokenDao.NEVER_EXPIRE) {
            return SaTokenDao.NEVER_EXPIRE;
        }
        // ---- 计算剩余时间并返回
        long timeout = (expire - System.currentTimeMillis()) / 1000;
        // 小于零时，视为不存在
        if(timeout < 0) {
            dataMap.remove(key);
            expireMap.remove(key);
            return SaTokenDao.NOT_VALUE_EXPIRE;
        }
        return timeout;
    }

    // --------------------- 定时清理过期数据

    /**
     * 执行数据清理的线程
     */
    public Thread refreshThread;

    /**
     * 是否继续执行数据清理的线程标记
     */
    public volatile boolean refreshFlag;

    /**
     * 清理所有已经过期的key
     */
    public void refreshDataMap() {
        Iterator<String> keys = expireMap.keySet().iterator();
        while (keys.hasNext()) {
            clearKeyByTimeout(keys.next());
        }
    }

    /**
     * 初始化定时任务
     */
    public void initRefreshThread() {
        // 如果配置了<=0的值，则不启动定时清理
        if(SaManager.getConfig().getDataRefreshPeriod() <= 0) {
            return;
        }
        // 启动定时刷新
        this.refreshFlag = true;
        this.refreshThread = new Thread(() -> {
            for (;;) {
                try {
                    try {
                        // 如果已经被标记为结束
                        if(refreshFlag == false) {
                            return;
                        }
                        // 执行清理
                        refreshDataMap();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // 休眠N秒
                    int dataRefreshPeriod = SaManager.getConfig().getDataRefreshPeriod();
                    if(dataRefreshPeriod <= 0) {
                        dataRefreshPeriod = 1;
                    }
                    Thread.sleep(dataRefreshPeriod * 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.refreshThread.start();
    }

    /**
     * 结束定时任务
     */
    public void endRefreshThread() {
        this.refreshFlag = false;
    }

    //------------------------------会话管理
    /**
     * 搜索数据
     *
     * @param prefix   前缀
     * @param keyword  关键字
     * @param start    开始处索引 (-1代表查询所有)
     * @param size     获取数量
     * @param sortType 排序类型（true=正序，false=反序）
     * @return 查询到的数据集合
     */
    @Override
    public List<String> searchData(String prefix, String keyword, int start, int size, boolean sortType) {
        return SaFoxUtil.searchList(expireMap.keySet(), prefix, keyword, start, size, sortType);
    }
}
