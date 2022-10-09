package yj.sansui.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import yj.sansui.bean.entity.Role;
import yj.sansui.bean.entity.User;
import yj.sansui.mapper.MenuMapper;
import yj.sansui.mapper.RoleMapper;
import yj.sansui.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class RoleController {
    @Resource
    UserMapper userMapper;

    @Resource
    RoleMapper roleMapper;

    @Resource
    MenuMapper menuMapper;

    @GetMapping("test")
    public List getUser(Integer id){
        User user=userMapper.selectUserAndRole(id);
        Role role=roleMapper.returnRole(user.getUserId());
        List list=menuMapper.returnMenu(role.getRoleId());
        return list;
    }
}
