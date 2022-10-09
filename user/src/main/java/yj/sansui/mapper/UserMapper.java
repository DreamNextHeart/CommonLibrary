package yj.sansui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import yj.sansui.bean.entity.User;

import java.util.List;

/**
 * @author sansui
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where user.user_id = #{id}")
    @Results(
            @Result(
                    property = "roles", column = "id",
                    javaType = List.class,
                    many = @Many(select = "yj.sansui.mapper.RoleMapper.returnRole")
            )
    )
    User selectUserAndRole(@Param("id") Integer id);
}
