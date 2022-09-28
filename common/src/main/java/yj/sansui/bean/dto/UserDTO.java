package yj.sansui.bean.dto;

import lombok.Data;

/**
 * UserDTO,登录信息
 * @author sansui
 */
@Data
public class UserDTO {
    private String username;
    private String phone;
    private String password;
    private String code;
    private String token;
}
