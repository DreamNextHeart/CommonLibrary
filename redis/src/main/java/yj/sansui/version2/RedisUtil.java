package yj.sansui.version2;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * RedisUtil,redis工具类，内涵各种操作redis服务器的操作方法
 *
 * @author Sansui
 */
@Component("RedisUtil2")
public class RedisUtil {
    public ObjectMapper objectMapper;

    /**
     * String专用
     */
    public static StringRedisTemplate stringRedisTemplate;

    /**
     * Object专用
     */
    public static RedisTemplate<String, Object> objectRedisTemplate;

    /**
     * 标记：是否已初始化成功
     */
    public boolean isInit;

    @Autowired
    public void init(RedisConnectionFactory connectionFactory) {

        final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
        final String DATE_PATTERN = "yyyy-MM-dd";
        final String TIME_PATTERN = "HH:mm:ss";
        final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
        final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);
        // 不重复初始化
        if(this.isInit) {
            return;
        }

        // 指定相应的序列化方案
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer();
        // 通过反射获取Mapper对象, 增加一些配置, 增强兼容性
        try {
            Field field = GenericJackson2JsonRedisSerializer.class.getDeclaredField("mapper");
            field.setAccessible(true);
            ObjectMapper objectMapper = (ObjectMapper) field.get(valueSerializer);
            this.objectMapper = objectMapper;
            // 配置[忽略未知字段]
            this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // 配置[时间类型转换]
            JavaTimeModule timeModule = new JavaTimeModule();
            // LocalDateTime序列化与反序列化
            timeModule.addSerializer(new LocalDateTimeSerializer(DATE_TIME_FORMATTER));
            timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DATE_TIME_FORMATTER));
            // LocalDate序列化与反序列化
            timeModule.addSerializer(new LocalDateSerializer(DATE_FORMATTER));
            timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DATE_FORMATTER));
            // LocalTime序列化与反序列化
            timeModule.addSerializer(new LocalTimeSerializer(TIME_FORMATTER));
            timeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(TIME_FORMATTER));
            this.objectMapper.registerModule(timeModule);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        // 构建StringRedisTemplate
        StringRedisTemplate stringTemplate = new StringRedisTemplate();
        stringTemplate.setConnectionFactory(connectionFactory);
        stringTemplate.afterPropertiesSet();
        // 构建RedisTemplate
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(keySerializer);
        template.setHashKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);
        template.setHashValueSerializer(valueSerializer);
        template.afterPropertiesSet();

        // 开始初始化相关组件
        stringRedisTemplate = stringTemplate;
        objectRedisTemplate = template;
        this.isInit = true;
    }

    /**
     * setObjectRedisTemplate，注入Bean——RedisTemplate
     *
     * @param stringRedisTemplate StringRedisTemplate
     */
    @Resource
    public void setObjectRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        RedisUtil.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * setStringRedisTemplate，注入Bean——RedisTemplate
     *
     * @param objectRedisTemplate StringRedisTemplate
     */
    @Resource
    public void setStringRedisTemplate(RedisTemplate<String, Object> objectRedisTemplate) {
        RedisUtil.objectRedisTemplate = objectRedisTemplate;
    }

//--------------------Object

    /**
     * getByKey，根据key获取value
     *
     * @param key String
     * @return null/value
     */
    public static Object getObject(String key) {
        return key == null ? null : objectRedisTemplate.opsForValue().get(key);
    }

    /**
     * setKeyValueTime，设置key，value和对应过期时间
     *
     * @param key
     * @param value
     * @return true/false
     */
    public static boolean setObject(String key, Object value) {
        try {
            objectRedisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * setKeyValueTime，设置key，value和对应过期时间
     *
     * @param key
     * @param value
     * @param time
     * @return true/false
     */
    public static boolean setObjectTime(String key, Object value, long time) {
        try {
            if (time > 0) {
                objectRedisTemplate.opsForValue()
                        .set(key, value, time, TimeUnit.SECONDS);
            } else {
                objectRedisTemplate.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新Object
     *
     * @param key
     * @param object
     */
    public static void updateObject(String key, Object object) {
        long expire = getObjectTimeout(key);
        // -2 = 无此键
        if (expire == RedisConstant.NOT_VALUE_EXPIRE) {
            return;
        }
        setObjectTime(key, object, expire);
    }

    /**
     * 删除Object
     */
    public static void deleteObject(String key) {
        objectRedisTemplate.delete(key);
    }

    /**
     * 获取Object的剩余存活时间 (单位: 秒)
     */
    public static long getObjectTimeout(String key) {
        return objectRedisTemplate.getExpire(key);
    }

    /**
     * 修改Object的剩余存活时间 (单位: 秒)
     */
    public static void updateObjectTimeout(String key, long timeout) {
        // 判断是否想要设置为永久
        if (timeout == RedisConstant.NEVER_EXPIRE) {
            long expire = getObjectTimeout(key);
            if (expire == RedisConstant.NEVER_EXPIRE) {
                // 如果其已经被设置为永久，则不作任何处理
            } else {
                // 如果尚未被设置为永久，那么再次set一次
                setObjectTime(key, getObject(key), timeout);
            }
            return;
        }
        objectRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * incr，递增
     * 从key开始，递增因子是delta
     *
     * @param key
     * @param delta
     * @return Exception/value
     */
    public static long incrObject(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return objectRedisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * decr，递减
     * 从key开始，递减因子是delta
     *
     * @param key
     * @param delta
     * @return Exception/value
     */
    public static long decrObject(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return objectRedisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * hasKey，判断key是否存在
     * @param key String
     * @return true/false
     */
    public static boolean hasObjectKey(String key){
        try {
            return objectRedisTemplate.hasKey(key);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //--------------------String

    /**
     * 获取Value，如无返空
     */
    public static String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 写入Value，并设定存活时间 (单位: 秒)
     */
    public static void setString(String key, String value) {
            stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 写入Value，并设定存活时间 (单位: 秒)
     */
    public static void setStringTime(String key, String value, long timeout) {
        if (timeout == 0 || timeout <= RedisConstant.NOT_VALUE_EXPIRE) {
            return;
        }
        // 判断是否为永不过期
        if (timeout == RedisConstant.NEVER_EXPIRE) {
            stringRedisTemplate.opsForValue().set(key, value);
        } else {
            stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 修修改指定key-value键值对 (过期时间不变)
     */
    public static void updateString(String key, String value) {
        long expire = getStringTimeout(key);
        // -2 = 无此键
        if (expire == RedisConstant.NOT_VALUE_EXPIRE) {
            return;
        }
        setStringTime(key, value, expire);
    }

    /**
     * 删除Value
     */
    public static void deleteString(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 获取Value的剩余存活时间 (单位: 秒)
     */
    public static long getStringTimeout(String key) {
        return stringRedisTemplate.getExpire(key);
    }

    /**
     * 修改Value的剩余存活时间 (单位: 秒)
     */
    public static void updateStringTimeout(String key, long timeout) {
        // 判断是否想要设置为永久
        if (timeout == RedisConstant.NEVER_EXPIRE) {
            long expire = getStringTimeout(key);
            if (expire == RedisConstant.NEVER_EXPIRE) {
                // 如果其已经被设置为永久，则不作任何处理
            } else {
                // 如果尚未被设置为永久，那么再次set一次
                setStringTime(key, getString(key), timeout);
            }
            return;
        }
        stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * incrString，递增
     * 从key开始，递增因子是delta
     *
     * @param key
     * @param delta
     * @return Exception/value
     */
    public static long incrString(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }
    /**
     * decr，递减
     * 从key开始，递减因子是delta
     *
     * @param key
     * @param delta
     * @return Exception/value
     */
    public static long decrString(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return stringRedisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * hasKey，判断key是否存在
     * @param key String
     * @return true/false
     */
    public static boolean hasStringKey(String key){
        try {
            return stringRedisTemplate.hasKey(key);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
