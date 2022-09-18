package yj.sansui.version2;

/**
 * RedisConstant，redis的key常量
 * @author sansui
 */
public class RedisConstant {
    /** time是作为时间常量 */
    public static long time= 2592000;

    public static String All_User_Key = "allUser";
    public static String User_Id_Key = "id: ";
    public static String USER_KEY_USERNAME = "username: ";
    public static String LAST_ACTIVITY="satoken:login:last-activity:";
    public static String SESSION="satoken:login:session:";

    /** 常量，表示一个key永不过期 (在一个key被标注为永远不过期时返回此值) */
    public static final long NEVER_EXPIRE = -1;

    /** 常量，表示系统中不存在这个缓存 (在对不存在的key获取剩余存活时间时返回此值) */
    public static final long NOT_VALUE_EXPIRE = -2;
}
