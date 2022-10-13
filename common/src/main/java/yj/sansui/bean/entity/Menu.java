package yj.sansui.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zpli
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("menu")
public class Menu {
    /**
     * menuId：菜单id
     * menuName：菜单名
     * parentId：父级菜单id
     * perms：权限
     * childrenList: 树状菜单导航栏
     */
    @TableId(value = "menu_id",type = IdType.AUTO)
    private Integer menuId;

    @NotNull(message = "菜单名不允许为空")
    private String menuName;

    @NotNull(message = "父菜单id不允许为空")
    private Integer parentId;

    @NotNull(message = "权限不允许为空")
    private String perms;

    @TableField(exist = false)
    private Set<Menu> childrenSet = new HashSet<>();
}
