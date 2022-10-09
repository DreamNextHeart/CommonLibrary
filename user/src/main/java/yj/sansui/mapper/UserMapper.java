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
    User getUser(@Param("id") Integer id);
}
