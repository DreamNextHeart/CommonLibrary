package yj.sansui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import yj.sansui.bean.entity.User;

/**
 * @author sansui
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
