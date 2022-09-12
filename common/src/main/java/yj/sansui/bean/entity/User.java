package yj.sansui.bean.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * User，用户实体类
 *
 * @author sansui
 */
@Data
@TableName("library_user")
public class User {
    /**
     * id 用户序号
     * username 用户名
     * password 密码
     * salt 盐值
     * role 角色
     * department 部门
     */
    @TableId
    private Integer id;

    @NotNull(message = "用户名不允许为空")
    private String username;

    @NotNull(message = "密码不允许为空")
    private String password;

    private String salt;

    @NotNull(message = "角色不允许为空")
    private String role;

    @NotNull(message = "部门不允许为空")
    private String department;
}
