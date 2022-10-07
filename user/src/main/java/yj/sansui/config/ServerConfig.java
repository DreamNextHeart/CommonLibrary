package yj.sansui.config;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author sansui
 */
@Configuration
public class ServerConfig implements ApplicationListener<WebServerInitializedEvent> {
    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        // 项目启动获取启动的端口号
        ServerConfig.serverPort = event.getWebServer().getPort();
    }

    /**
     * 当前服务使用的端口号
     */
    private static int serverPort;

    public static int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        ServerConfig.serverPort = serverPort;
    }

    /**
     * 获取请求基地址
     *
     * @return String 请求基地址
     **/
    public static String getBaseUrl() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        assert address != null;
        return "http://"+address.getHostAddress() +":"+ getServerPort();
    }

    /**
     * getAddress，获取ip地址
     *
     * @return String 请求ip地址
     **/
    public static String getAddress() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        assert address != null;
        return "http://"+address.getHostAddress();
    }

}
