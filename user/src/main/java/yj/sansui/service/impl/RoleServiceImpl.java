package yj.sansui.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import yj.sansui.bean.entity.Role;
import yj.sansui.mapper.RoleMapper;
import yj.sansui.service.RoleService;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    /**
     * getRoleByUser，根据User获取Role
     *
     * @return Role
     */
    @Override
    public Role getRoleByUser() {
        return null;
    }
}
