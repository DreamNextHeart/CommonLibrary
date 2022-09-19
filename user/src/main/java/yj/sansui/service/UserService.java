package yj.sansui.service;


import com.baomidou.mybatisplus.extension.service.IService;
import yj.sansui.bean.dto.UserDTO;
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
    Result loginInRemember(UserDTO userDTO);

    /**
     * register，注册服务
     * @param user
     * @return
     */
    Result register(User user);



}