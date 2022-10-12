package yj.sansui.service;


import com.baomidou.mybatisplus.extension.service.IService;
import yj.sansui.bean.dto.UserDTO;
import yj.sansui.bean.entity.Role;
import yj.sansui.bean.entity.User;
import yj.sansui.result.Result;

import java.util.Map;

/**
 * @author sansui
 */
public interface UserService extends IService<User> {
    /**
     * loginInNotRemember,登录服务，不记住密码，检验账号密码
     * @param userDTO
     * @return
     */
    Map<String,Object> loginInNotRemember(UserDTO userDTO);

    /**
     * loginInRemember,登录服务，记住密码，检验账号和token
     * @param userDTO
     * @return
     */
    Map<String, Object> loginInRemember(UserDTO userDTO);

    /**
     * register，注册服务
     * @param userDTO
     * @return
     */
    Result register(UserDTO userDTO);

    /**
     * active，激活服务
     * @param code 激活码
     * @return
     */
    Result active(String code);

    /**
     * getUserInfo，根据token获取用户信息
     * @param token
     * @return User对象
     */
    User getUserInfo(String token);


}
