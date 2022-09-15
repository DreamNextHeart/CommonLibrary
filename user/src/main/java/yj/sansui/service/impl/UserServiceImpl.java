package yj.sansui.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import yj.sansui.bean.dto.UserDTO;
import yj.sansui.bean.entity.User;
import yj.sansui.exception.CommonException;
import yj.sansui.exception.ExceptionCode;
import yj.sansui.mapper.UserMapper;
import yj.sansui.result.Result;
import yj.sansui.service.UserService;
import yj.sansui.utils.TkipUtil;
import yj.sansui.utils.VerifyCode;

import javax.annotation.Resource;

/**
 * @author sansui
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    UserService userService;

    @Override
    public Result loginIn(UserDTO userDTO) {
        VerifyCode.checkCode(userDTO.getCode());
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("username",userDTO.getUsername());
        User user=userService.getOne(wrapper);
        if(user == null){
            throw new CommonException(ExceptionCode.USERNAME_EXCEPTION,"用户名："+userDTO.getUsername()+"不存在");
        }else {
            String salt= user.getSalt();
            String password=userDTO.getPassword();
            password+=salt;
            password= TkipUtil.degst(password);
            if(password.equals(user.getPassword())){
                StpUtil.login(user.getUsername());
                SaTokenInfo tokenInfo=StpUtil.getTokenInfo();
                String token=tokenInfo.getTokenValue();
                return new Result(200,"登陆成功");
            }else {
                throw new CommonException(ExceptionCode.USERNAME_EXCEPTION,"用户名："+userDTO.getUsername()+"密码错误");
            }
        }
    }

    /**
     * register，注册服务
     *
     * @param user
     * @return
     */
    @Override
    public Result register(User user) {
        String password=user.getPassword();
        String salt= TkipUtil.getSalt();
        password+=salt;
        password=TkipUtil.degst(password);
        user.setPassword(password);
        user.setSalt(salt);
        userService.save(user);
        return new Result(200,"注册成功");
    }


}
