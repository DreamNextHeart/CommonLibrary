package yj.sansui.third.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * JwtInterceptorConfig，JWT框架配置类，配置拦截路径并注入Bean
 * @author sansui
 */
@Configuration
public class JwtInterceptorConfig extends WebMvcConfigurationSupport {
    /**
     * 配置拦截路径
     * @param registry InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //默认拦截所有路径
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**");
    }

    /**
     * 注入Bean
     */
    @Bean
    public JwtAuthenticationInterceptor authenticationInterceptor() {
        return new JwtAuthenticationInterceptor();
    }
}
