package yj.sansui.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import yj.sansui.bean.dto.UserDTO;
import yj.sansui.bean.entity.User;
import yj.sansui.result.Result;

/**
 * @author sansui
 */
@Service
public interface UserService extends IService<User> {
    /**
     * loginIn,登录服务
     * @param userDTO
     * @return
     */
    Result loginIn(UserDTO userDTO);

    /**
     * register，注册服务
     * @param user
     * @return
     */
    Result register(User user);



}
