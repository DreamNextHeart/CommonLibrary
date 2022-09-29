package yj.sansui.bean.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * User，用户实体类
 *
 * @author sansui
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User {
    /**
     * id 用户序号
     * username 用户名
     * phone 手机号码
     * password 密码
     * salt 盐值
     * role 角色
     * department 部门
     */
    @TableId(value ="id",type= IdType.AUTO)
    private Integer id;

    @NotNull(message = "用户名不允许为空")
    private String username;

    @Size(min = 11,max = 11,message = "请输入11位手机号码")
    private String phone;

    @NotNull(message = "密码不允许为空")
    private String password;

    private String salt;

    @NotNull(message = "角色不允许为空")
    private String role;

    @NotNull(message = "部门不允许为空")
    private String department;
}
