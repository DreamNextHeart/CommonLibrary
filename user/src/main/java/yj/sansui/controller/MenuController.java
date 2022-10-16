package yj.sansui.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import yj.sansui.bean.entity.Menu;
import yj.sansui.service.MenuService;
import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


@RestController
public class MenuController {
    @Resource
    MenuService menuService;

    @GetMapping("/getMenuTree")
    public Set<Menu> getMenuTree(){
        return menuService.getMenuTree();
    }
}
