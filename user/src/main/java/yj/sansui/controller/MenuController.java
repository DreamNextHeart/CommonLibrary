package yj.sansui.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import yj.sansui.bean.entity.Menu;
import yj.sansui.service.MenuService;
import javax.annotation.Resource;
import java.util.List;

@RestController
public class MenuController {
    @Resource
    MenuService menuService;

    @GetMapping("getMenu")
    public List<Menu> getMenuTree(Integer id){
        return menuService.getMenuTree(id);
    }
}
