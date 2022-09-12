package yj.sansui.bean.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * UserEntity 用户实体类，对应版本1.0.0
 * @author Sansui
 */
@Data
@TableName(value = "shop_user")
public class UserEntity {
    /**
     * id 主键，用户的唯一标识，有唯一性，不可重复
     */
    @TableId
    private Integer id;

    /**
     * name，用户姓名，不允许为空
     */
    @NotNull(message = "名字不允许为空")
    private String name;

    /**
     * age，用户年龄，最小值为0，不允许为负数
     */
    @Min(value = 0,message = "年龄不允许为负数")
    private Integer age;
}
