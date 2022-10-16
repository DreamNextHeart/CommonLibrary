package yj.sansui.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import yj.sansui.bean.entity.User;
import yj.sansui.service.RoleService;

import javax.annotation.Resource;
import java.util.ArrayList;


/**
 * @author sansui
 */

public class RoleController {
    @Resource
    RoleService roleService;
    @GetMapping("/getPermsList")
    public ArrayList<String> getPermsList(Integer id){
        return roleService.getPermsList(id);
    }
}
