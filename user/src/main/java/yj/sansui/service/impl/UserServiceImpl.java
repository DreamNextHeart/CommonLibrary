package yj.sansui.service.impl;

import cn.dev33.satoken.session.SaSession;
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
import yj.sansui.utils.LibraryUtil;
import yj.sansui.utils.TkipUtil;
import yj.sansui.utils.VerifyCode;
import yj.sansui.version2.RedisConstant;
import yj.sansui.version2.RedisUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserServiceImpl，服务类
 * @author sansui
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    UserService userService;

    /**
     * loginIn，登录方法
     * @param userDTO UserDTO
     * @return 验证码正确/不正确，用户不存在/存在，
     */
    @Override
    public Map<String,Object> loginInNotRemember(UserDTO userDTO) {
        //验证码校验
        VerifyCode.checkCode(userDTO.getCode());
        //确认数据库存在该用户
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("username",userDTO.getUsername());
        User user=userService.getOne(wrapper);
        if(LibraryUtil.isEmpty(user)){
            throw new CommonException(ExceptionCode.USERNAME_EXCEPTION,"用户名："+userDTO.getUsername()+"不存在");
        }else {
            //获取盐值
            String salt= user.getSalt();
            String password=userDTO.getPassword();
            password+=salt;
            //加密算法
            password= TkipUtil.degst(password);
            //密码验证是否相等
            if(LibraryUtil.equals(password,user.getPassword())){
                //用户登录
                StpUtil.login(user.getUsername());
                String token=StpUtil.getTokenValue();
                List<String> list=StpUtil.getPermissionList();
                Map<String,Object> map=new HashMap<>(3);
                map.put("user",userDTO.getUsername());
                map.put("token",token);
                map.put("permission",list);
                return map;
            }else {
                throw new CommonException(ExceptionCode.USERNAME_EXCEPTION,"用户名："+userDTO.getUsername()+"密码错误");
            }
        }
    }

    /**
     * loginInRemember,登录服务，记住密码，检验账号和token
     *
     * @param userDTO
     * @return
     */
    @Override
    public Result loginInRemember(UserDTO userDTO) {
        String username=userDTO.getUsername();
        String token=userDTO.getToken();
        if(RedisUtil.hasStringKey(RedisConstant.SESSION+username)&&RedisUtil.hasStringKey(RedisConstant.LAST_ACTIVITY+token)){
            return new Result("验证通过");
        }else {
            throw  new CommonException(ExceptionCode.TOKEN_OUT_OF_DATE,"token已过期，请重新登录");
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
