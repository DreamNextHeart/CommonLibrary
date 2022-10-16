package yj.sansui.service.impl;


import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import yj.sansui.bean.entity.Menu;
import yj.sansui.bean.entity.Role;
import yj.sansui.bean.entity.User;
import yj.sansui.mapper.MenuMapper;
import yj.sansui.mapper.RoleMapper;
import yj.sansui.mapper.UserMapper;
import yj.sansui.service.MenuService;
import yj.sansui.service.UserService;
import yj.sansui.utils.LibraryUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author sansui
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Resource
    UserMapper userMapper;

    @Resource
    RoleMapper roleMapper;

    @Resource
    MenuMapper menuMapper;

    @Resource
    UserService userService;

    public Set<Menu> getMenu() {
        String phone=(String) StpUtil.getLoginId();
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("phone",phone);
        User user=userService.getOne(wrapper);
        Set<Role> roles = roleMapper.returnRole(user.getUserId());
        Set<Menu> menuSet = new HashSet<>();
        for (Role role : roles) {
            menuSet.addAll(menuMapper.returnMenu(role.getRoleId()));
        }
        return menuSet;
    }

    @Override
    public Set<Menu> getMenuTree() {
        Set<Menu> menuSet = getMenu();
        Set<Menu> topMenuSet = menuSet.stream().filter(
                s -> s.getParentId().equals(0)).collect(Collectors.toSet());
        for (Menu menu : topMenuSet) {
            getChildren(menu, menuSet);
        }
        return topMenuSet;
    }

    /**
     * getChildrenList 获取子菜单
     *
     * @param menu    菜单
     * @param menuSet 菜单列表
     * @return Menu
     */
    @Override
    public Menu getChildren(Menu menu, Set<Menu> menuSet) {
        for (Menu tempMenu : menuSet) {
            if (LibraryUtil.equals(tempMenu.getParentId(), menu.getMenuId())) {
                menu.getChildrenSet().add(getChildren(tempMenu, menuSet));
            }
        }
        return menu;
    }
}
