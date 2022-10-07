import router from "./index"
import Cookies from 'js-cookie'

router.beforeEach((to,from,next) =>{
    if(!Cookies.get('token')){
        // 未登录  页面是否需要登录
        if(to.matched.length > 0 && !to.matched.some(record => record.meta.requiresAuth)){
            next();
        }else{
            next({
                path:"/login"
            })
        }
    }else{
        // 用户已经登录  路由的访问权限
        if(!Cookies.state.permission.permissionList){
            Cookies.dispatch("permission/FETCH_PERMISSION").then(() =>{
                next({
                    path:to.path
                })
            })
        }else{
            // store存在权限
            if(to.path !== "/login"){
                next();
            }else{
                next(from.fullPath)
            }
        }
    }
})
