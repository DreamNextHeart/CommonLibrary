package yj.sansui.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Role，角色表
 * @author sansui
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    /**
     * roleId：角色id
     * roleName：角色名
     */
    @TableId(value = "role_id",type = IdType.AUTO)
    private Integer roleId;

    @NotNull(message = "角色名不允许为空")
    private String roleName;
}
