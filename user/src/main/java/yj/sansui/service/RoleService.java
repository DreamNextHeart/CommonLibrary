package yj.sansui.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yj.sansui.bean.entity.Role;

/**
 * @author zpli
 */
public interface RoleService extends IService<Role> {
    /**
     * getRoleByUser，根据User获取Role
     * @return Role
     */
    Role getRoleByUser();

}
