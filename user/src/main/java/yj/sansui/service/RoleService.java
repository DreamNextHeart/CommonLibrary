package yj.sansui.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yj.sansui.bean.entity.Role;

import java.util.ArrayList;

/**
 * @author zpli
 */
public interface RoleService extends IService<Role> {
    /**
     * getRoleByUser，根据User的id获取Role
     * @return Role
     */
    Role getRoleByUserId(Integer id);

    /**
     * getPermsList 获取权限列表
     * @param id user的id
     * @return getPermsList
     */
    ArrayList<String> getPermsList(Integer id);
}
