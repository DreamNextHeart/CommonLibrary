package yj.sansui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import yj.sansui.bean.entity.Menu;

import java.util.List;

/**
 * @author zpli
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    @Select("select menu.* from role_menu,menu where role_menu.role_id = #{role_id} and menu.menu_id = role_menu.menu_id")
    List<Menu> returnMenu(@Param("role_id") Integer id);
}
