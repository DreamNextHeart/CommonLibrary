import {fetchPermission} from "@/api/user/userApi";
import {recursionRouter, setDefaultRoute} from "@/utils/recursion-router";
import dynamicRoutes from "@/router/dynamic-router";
import router, {DynamicRoutes} from "@/router";

export default {
    namespace: true,
    state: {
        permissionList: null,
        sidebarMenu: [],//导航菜单
        currentMenu: ''//高亮选中
    },
    getters: {},
    mutations: {
        SET_PERMISSION(state,routes){
            state.permissionList = routes;
        },
        CLEAR_PERMSSION(state){
            state.permissionList = null;
        },
        SET_MENU(state,menu){
            state.sidebarMenu = menu;
        },
        CLEAR_MENU(state){
            state.sidebarMenu = []
        }
    },

    //异步访问
    actions: {
        async FETCH_PERMISSION({commit,state}){
            const permissionList = await fetchPermission();
            //筛选
            const routes=recursionRouter(permissionList,dynamicRoutes);
            const MainContainer=DynamicRoutes.find(v=> v.path==="");
            const children=MainContainer.children;
            children.push(...routes);

            //生成菜单
            commit("SET_MENU",children);

            //设置默认路由
            setDefaultRoute([MainContainer]);

            // 初始化路由
            const initialRoutes = router.options.routes;
            router.addRoutes(DynamicRoutes);

            commit("SET_PERMISSION",[ ...initialRoutes , ...DynamicRoutes])
        }
    }
}
