import router, {dynamicRouter} from "@/router";
import {getToken} from "@/assets/token/token";
import store from "@/store";
import {isRelogin} from "@/utils/request";

router.beforeEach((to, from, next) => {
    const token=getToken()
    console.log(token)
    //存在token
    if(token!==null){
        if(to.path==='/login'||to.path==='/register'){
            console.log("在登陆")
            next({path: '/'})
        }else {
            // 判断当前用户是否已拉取完user_info信息
            if(store.getters.roles.length===0){
                isRelogin.show = true
                //判断当前用户是否已经拉取完user信息
                store.dispatch('getInfo').then(response=>{
                    console.log(response.data.data.roles)
                    const roles=response.data.data.roles
                    isRelogin.show = false
                    store.dispatch('GenerateRoutes',{roles}).then(()=>{
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
