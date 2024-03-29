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
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

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
     * userId 用户序号
     * username 用户名
     * phone 手机号码
     * password 密码
     * salt 盐值
     * activeCode 激活码
     * active 1为激活，0为未激活
     */
    @TableId(value ="user_id",type= IdType.AUTO)
    private Integer userId;

    @NotNull(message = "用户名不允许为空")
    private String userName;

    @Size(min = 11,max = 11,message = "请输入11位手机号码")
    private String phone;

    @NotNull(message = "邮箱不允许为空")
    private String email;

    @NotNull(message = "密码不允许为空")
    private String password;

    private String salt;


    private String activeCode;

    private char active;

    @TableField(exist = false)
    private Set<Role> roles;

    @TableField(exist = false)
    private Set<String> perms;
}
