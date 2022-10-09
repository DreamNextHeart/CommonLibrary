package yj.sansui.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yj.sansui.bean.entity.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {
    /**
     * getChildrenList 获取子菜单
     * @param menu 菜单
     * @param menuList 菜单列表
     * @return Menu
     */
    Menu getChildren(Menu menu, List<Menu> menuList);

    /**
     * getMenuTree
     * @param id
     * @return
     */
    List<Menu> getMenuTree(Integer id);


}
