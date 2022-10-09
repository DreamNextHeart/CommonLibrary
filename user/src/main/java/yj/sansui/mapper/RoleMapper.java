package yj.sansui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import yj.sansui.bean.entity.Role;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    @Select("select role.* from user_role,role where user_role.user_id = #{user_id} and role.role_id = user_role.role_id")
    Role returnRole(@Param("user_id") Integer id);
}
