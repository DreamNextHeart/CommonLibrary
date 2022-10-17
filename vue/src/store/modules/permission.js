import router, {constantRouter, dynamicRouter} from "@/router";
import {getMenuTree} from "@/api/menu/menuApi";
import user from "@/store/modules/user";

const permission={
    state:{
        router: constantRouter,
        addRouter: [],

        routes: [],
        addRoutes: [],
        defaultRoutes: [],
        topBarRouters: [],
        sideBarRouters: []
    },
    mutations:{
        SET_ROUTERS: (state, router) => {
            state.addRouter = router;
            state.router = constantRouter.concat(router);
        },

        SET_ROUTES: (state, routes) => {
            state.addRoutes = routes
            //追加、合并、去重数组
            state.routes = constantRouter.concat(routes)
        },
        SET_DEFAULT_ROUTES: (state, routes) => {
            state.defaultRoutes = constantRouter.concat(routes)
        },
        SET_TOPBAR_ROUTES: (state, routes) => {
            state.topBarRouters = routes
        },
        SET_SIDEBAR_ROUTERS: (state, routes) => {
            state.sideBarRouters = routes
        },
    },
    actions: {
        //生成路由
        GenerateRoutes({commit},data){
            return new Promise(resolve => {
                const {roles}=data;
                const accessedRouters=dynamicRouter.filter(v=>{
                    if (roles.indexOf('super_admin') >= 0) {
                        console.log("super_admin")
                        return true;
                    }
                    if (hasPermission(roles, v)) {
                        console.log("进入hasPermission(roles, v)判断")
                        if (v.children && v.children.length > 0) {
                            v.children = v.children.filter(child => {
                                if (hasPermission(roles, child)) {
                                    return child
                                }
                                return false
                            });
                            return v
                        }else {
                            return v
                        }
                    }
                    return false
                });
                commit('SET_ROUTERS',accessedRouters)
                accessedRouters.filter(()=>{
                    console.log("进入accessedRouters")
                    console.log(accessedRouters)
                    router.addRoute(accessedRouters);
                    console.log("现在router")
                    console.log(router.getRoutes())
                })
                console.log("accessedRouters")
                console.log(accessedRouters)
                resolve();
            })
        }

    }
}

function hasPermission(roles, route) {
    if (route.meta && route.meta.roles) {
        /**
         * some的作用是检测数组中是否存在指定条件的元素;
         * 若存在指定的元素则返回的结果是true,
         * 若不存在指定的元素则返回的结果是false
         */
        return roles.some(role => route.meta.roles.indexOf(role.roleName) >= 0)
    } else {
        return true
    }
}




export default permission
