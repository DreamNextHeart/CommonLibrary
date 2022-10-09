package yj.sansui.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import yj.sansui.bean.entity.Menu;
import yj.sansui.bean.entity.Role;
import yj.sansui.bean.entity.User;
import yj.sansui.mapper.MenuMapper;
import yj.sansui.mapper.RoleMapper;
import yj.sansui.mapper.UserMapper;
import yj.sansui.service.MenuService;
import yj.sansui.utils.LibraryUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Resource
    UserMapper userMapper;

    @Resource
    RoleMapper roleMapper;

    @Resource
    MenuMapper menuMapper;

    @Override
    public List<Menu> getMenuTree(Integer id){
        User user=userMapper.getUser(id);
        Role role=roleMapper.returnRole(user.getUserId());
        List<Menu> menuList=menuMapper.returnMenu(role.getRoleId());
        List<Menu> topMenuList=menuList.stream().filter(
                s->s.getParentId().equals(0)).collect(Collectors.toList());
        for (Menu menu : topMenuList) {
            getChildren(menu, menuList);
        }
        return topMenuList;
    }

    /**
     * getChildrenList 获取子菜单
     *
     * @param menu     菜单
     * @param menuList 菜单列表
     * @return Menu
     */
    @Override
    public Menu getChildren(Menu menu, List<Menu> menuList) {
        for(Menu menu1:menuList){
            if(LibraryUtil.equals(menu1.getParentId(),menu.getMenuId())){
                menu.getChildrenList().add(getChildren(menu1,menuList));
            }
        }
        return menu;
    }
}
