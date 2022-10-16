package yj.sansui.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import yj.sansui.bean.entity.Menu;
import yj.sansui.bean.entity.Role;
import yj.sansui.bean.entity.User;
import yj.sansui.mapper.MenuMapper;
import yj.sansui.mapper.RoleMapper;
import yj.sansui.mapper.UserMapper;
import yj.sansui.service.RoleService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Resource
    public UserMapper userMapper;
    @Resource
    public RoleMapper roleMapper;
    @Resource
    public MenuMapper menuMapper;

    /**
     * getRoleByUser，根据User的id获取Role
     *
     * @return Role
     */
    @Override
    public Role getRoleByUserId(Integer id) {
        return null;
    }

    /**
     * getPermsList 获取权限列表
     * @param id user的id
     * @return getPermsList
     */
    @Override
    public ArrayList<String> getPermsList(Integer id) {
        User user= userMapper.getUser(id);
        Set<Role> roles=roleMapper.returnRole(user.getUserId());
        Set<Menu> menuSet=new HashSet<>();
        ArrayList<String> permsList=new ArrayList<>();
        for(Role role: roles){
            menuSet.addAll(menuMapper.returnMenu(role.getRoleId()));
        }
        ArrayList<Menu> menuList=new ArrayList(menuSet);

        for(int temp=0;temp<menuSet.size();temp++){
            Menu menu=menuList.get(temp);
            permsList.add(menu.getPerms());
        }
        return permsList;
    }


}
