package yj.sansui.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yj.sansui.bean.entity.Menu;

import java.util.List;
import java.util.Set;

public interface MenuService extends IService<Menu> {
    /**
     * getChildrenList 获取子菜单
     * @param menu 菜单
     * @param menuSet 菜单列表
     * @return Menu
     */
    Menu getChildren(Menu menu, Set<Menu> menuSet);

    /**
     * getMenuTree
     * @param id
     * @return
     */
    Set<Menu> getMenuTree(Integer id);


}
