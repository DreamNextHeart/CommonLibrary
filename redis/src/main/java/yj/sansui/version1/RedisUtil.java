package yj.sansui.version1;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * RedisUtil,redis工具类，内涵各种操作redis服务器的操作方法
 *
 * @author Sansui
 */
@Component("RedisUtil1")
public class RedisUtil {

    private static RedisTemplate<String, Object> redisTemplate;

    /**
     * setRedisTemplate，注入Bean——RedisTemplate
     * @param redisTemplate RedisTemplate<String, Object>
     */
    @Resource
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        RedisUtil.redisTemplate = redisTemplate;
    }

    /**
     * del 删除缓存
     * @param key String（可多个参数）
     */
    public static void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(
                        (Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * expire，指定缓存失效时间
     * @param key String
     * @param time long
     * @return true/false
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * getExpire，根据key获取过期时间
     * @param key String
     * @return redisTemplate.getExpire(key,TimeUnit.SECONDS);
     */
    public long getExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    /**
     * hasKey，判断key是否存在
     * @param key String
     * @return true/false
     */
    public static boolean hasKey(String key){
        try {
            return redisTemplate.hasKey(key);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }


    /**
     * getByKey，根据key获取value
     * @param key String
     * @return null/value
     */
    public static Object getByKey(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }


    /**
     * setKeyValue，往redis存入key和value的值
     * @param key String
     * @param value Object
     * @return ture/false
     */
    public static boolean setKeyValue(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }



    /**
     * setKeyValueTime，设置key，value和对应过期时间
     * @param key
     * @param value
     * @param time
     * @return true/false
     */
    public static boolean setKeyValueTime(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue()
                        .set(key, value, time, TimeUnit.SECONDS);
            } else {
                setKeyValue(key, value);
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }



    /**
     * incr，递增
     * 从key开始，递增因子是delta
     * @param key
     * @param delta
     * @return Exception/value
     */
    public long incr(String key,long delta){
        if(delta<0){
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key,delta);
    }

    /**
     * decr，递减
     * 从key开始，递减因子是delta
     * @param key
     * @param delta
     * @return Exception/value
     */
    public long decr(String key, long delta){
        if(delta<0){
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key,-delta);
    }



    /**
     * hashGetKeyValue，hash获取键值对
     * @param key
     * @param value
     * @return redisTemplate.opsForHash().get(key,value)
     */
    public Object hashGetKeyValue(String key,String value){
        return redisTemplate.opsForHash().get(key,value);
    }



    /**
     * hashGetKey,获取hashKey对应的所有键值
     * @param key
     * @return
     */
    public static Map<Object, Object> hashGetKey(String key){
        return redisTemplate.opsForHash().entries(key);
    }



    /**
     * hmset HashSet
     * @param key String
     * @param map Map<String, Object>
     * @return true/fasle
     */
    public boolean hmset(String key,Map<String, Object> map){
        try{
            redisTemplate.opsForHash().putAll(key,map);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // HashSet 并设置时间
    public boolean hmset(String key,Map<String, Object> map,long time){
        try{
            redisTemplate.opsForHash().putAll(key, map);
            if (time>0){
                expire(key,time);
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }


    // 向一张hash表中放入数据,如果不存在将创建
    public static boolean hset(String key, String item, Object value){
        try{
            redisTemplate.opsForHash().put(key,item,value);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }


    // 向一张hash表中放入数据,如果不存在将创建
    public boolean hset(String key,String item,Object value,long time){
        try{
            redisTemplate.opsForHash().put(key,item,value);
            if(time>0){
                expire(key,time);
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // 删除hash表中的值
    public void hdel(String key,Object... item) {
        redisTemplate.opsForHash().delete(key,item);
    }

    // 判断hash表中是否有该项的值
    public boolean hHasKey(String key,String item) {
        return redisTemplate.opsForHash().hasKey(key,item);
    }

    // hash递增 如果不存在,就会创建一个 并把新增后的值返回
    public double hincr(String key,String item,double by) {
        return redisTemplate.opsForHash().increment(key,item,by);
    }

    // hash递减
    public double hdecr(String key,String item,double by) {
        return redisTemplate.opsForHash().increment(key,item,-by);
    }


    // 根据key获取Set中的所有值
    public Set<Object> sGet(String key) {
        try{
            return redisTemplate.opsForSet().members(key);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // 根据value从一个set中查询,是否存在
    public boolean sHasKey(String key,Object value) {
        try{
            return redisTemplate.opsForSet().isMember(key,value);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // 将数据放入set缓存
    public long sSet(String key,Object... values) {
        try{
            return redisTemplate.opsForSet().add(key,values);
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    // 将set数据放入缓存
    public long sSetAndTime(String key,long time,Object... values){
        try{
            Long count=redisTemplate.opsForSet().add(key,values);
            if (time> 0) {
                expire(key, time);
            }
            return count;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }


    // 获取set缓存的长度
    public long sGetSetSize(String key){
        try{
            return redisTemplate.opsForSet().size(key);
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    // 移除值为value的
    public long setRemove(String key,Object... values){
        try{
            Long count=redisTemplate.opsForSet().remove(key,values);
            return count;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }


    // 获取list缓存的内容
    public List<Object> lGet(String key, long start, long end){
        try{
            return redisTemplate.opsForList().range(key,start,end);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // 获取list缓存的长度
    public long lGetListSize(String key){
        try{
            return redisTemplate.opsForList().size(key);
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    // 通过索引 获取list中的值
    public Object lGetIndex(String key,long index){
        try{
            return redisTemplate.opsForList().index(key,index);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // 将list放入缓存
    public boolean lSet(String key, Object value){
        try{
            redisTemplate.opsForList().rightPush(key,value);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }


    // 将list放入缓存
    public boolean lSet(String key,Object value,long time){
        try{
            redisTemplate.opsForList().rightPush(key,value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }


    // 将list放入缓存
    public boolean lSet(String key, List<Object> value){
        try{
            redisTemplate.opsForList().rightPushAll(key,value);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }


    // 将list放入缓存
    public boolean lSet(String key,List<Object> value,long time){
        try{
            redisTemplate.opsForList().rightPushAll(key,value);
            if(time>0) {
                expire(key, time);
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }


    // 根据索引修改list中的某条数据
    public boolean lUpdateIndex(String key,long index,Object value){
        try{
            redisTemplate.opsForList().set(key,index,value);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }


    // 移除N个值为value
    public long lRemove(String key,long count,Object value){
        try{
            Long remove=redisTemplate.opsForList().remove(key,count,value);
            return remove;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
