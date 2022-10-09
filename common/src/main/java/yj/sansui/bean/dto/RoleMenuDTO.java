package yj.sansui.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RoleMenuDTO,角色菜单对应关系
 * @author zpli
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenuDTO {
    private Integer roleId;
    private Integer menuId;
}
