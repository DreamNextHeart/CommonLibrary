package yj.sansui.constant;

import yj.sansui.config.ServerConfig;

import javax.annotation.Resource;

/**
 * @author sansui
 */
public class Constant {
    /**
     * ACTIVE_URL,激活
     */
    public static String ACTIVE_URL = ServerConfig.getBaseUrl()+"/active?code=";
    public static String LOGIN_URL=ServerConfig.getAddress()+":8080";
}
