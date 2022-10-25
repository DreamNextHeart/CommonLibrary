import router, {dynamicRouter, errorRouter} from "@/router";
import {getToken} from "@/assets/token/token";
import store from "@/store";
import {isRelogin} from "@/utils/request";


router.beforeEach((to, from, next) => {
    console.log("进入路由守卫")
    const token=getToken()
    console.log(token)
    const excludePath = ['error']
    //存在token
    if(token!==null &&excludePath.indexOf(to.name) === -1){
        if(to.path==='/login'||to.path==='/register'){
            console.log("在登陆")
            next({path: '/'})
        }else {
            console.log("不在登录")
            // 判断当前用户是否已拉取完user_info信息
            if(store.getters.roles.length===0){
                isRelogin.show = true
                //判断当前用户是否已经拉取完user信息
                store.dispatch('getInfo').then(response=>{
                    console.log(response.data.data.roles)
                    const roles=response.data.data.roles
                    isRelogin.show = false

                    store.dispatch('GenerateRoutes',{roles}).then(accessedRouters=>{
                        console.log("原")
                        console.log(router.getRoutes())
                        errorRouter.forEach(temp => router.addRoute(temp))
                        accessedRouters.forEach(temp=>router.addRoute(temp))
                        let menuList=router.options.routes[0].children
                        menuList=menuList.concat(accessedRouters[0].children)
                        router.options.routes=menuList
                        console.log(router.options.routes)
                        next({...to,replace: true})
                    })
                }).catch(error=>{
                    console.log(error)
                })
            }else {
                next()
            }
        }
    }else {
        next('/login');
    }
});
