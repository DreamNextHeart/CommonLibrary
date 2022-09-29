package yj.sansui.service.impl;


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
 *
 * @author sansui
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    UserService userService;

    /**
     * loginIn，登录方法
     *
     * @param userDTO UserDTO
     * @return 验证码正确/不正确，用户不存在/存在，用户名，token，权限/角色
     */
    @Override
    public Map<String, Object> loginInNotRemember(UserDTO userDTO) {
        //验证码校验
        VerifyCode.checkCode(userDTO.getCode());
        //确认数据库存在该用户
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("phone", userDTO.getPhone());
        User user = userService.getOne(wrapper);

        if (LibraryUtil.isEmpty(user)) {
            //不存在该用户
            throw new CommonException(ExceptionCode.USERNAME_EXCEPTION, "用户名：" + userDTO.getPhone() + "不存在");
        } else {
            //获取盐值
            String salt = user.getSalt();
            String password = userDTO.getPassword();
            password += salt;
            //加密算法
            password = TkipUtil.degst(password);
            //密码验证是否相等
            if (LibraryUtil.equals(password, user.getPassword())) {
                //用户登录
                StpUtil.login(user.getPhone());
                //获取token
                String token = StpUtil.getTokenValue();
                //获取权限
                List<String> permissionList = StpUtil.getPermissionList();
                //获取角色
                List<String> roleList = StpUtil.getRoleList();
                Map<String, Object> map = new HashMap<>(4);
                map.put("user", userDTO.getPhone());
                map.put("token", token);
                map.put("permission", permissionList);
                map.put("role", roleList);
                return map;
            } else {
                //密码错误
                throw new CommonException(ExceptionCode.USERNAME_EXCEPTION, "用户名：" + userDTO.getPhone() + "密码错误");
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
        String phone = userDTO.getPhone();
        String token = userDTO.getToken();
        //验证phone和token是否存在
        if (RedisUtil.hasStringKey(RedisConstant.SESSION + phone) && RedisUtil.hasStringKey(RedisConstant.LAST_ACTIVITY + token)) {
            return new Result("验证通过");
        } else {
            throw new CommonException(ExceptionCode.TOKEN_OUT_OF_DATE, "token已过期，请重新登录");
        }
    }

    /**
     * register，注册服务
     *
     * @param userDTO
     * @return
     */
    @Override
    public Result register(UserDTO userDTO) {
        String password = userDTO.getPassword();
        String salt = TkipUtil.getSalt();
        password += salt;
        password = TkipUtil.degst(password);
        userDTO.setPassword(password);
        User user = User.builder()
                .username(userDTO.getUsername())
                .phone(userDTO.getPhone())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .salt(salt)
                .build();
        if(userService.save(user)){
            return new Result(200, "注册成功");
        }else {
            throw new CommonException(ExceptionCode.REGISTER_ERROR,"手机号码："+userDTO.getPhone()+"注册失败");
        }
    }


}
