import router from "@/router";
import NProgress from 'nprogress'
import {getToken} from "@/assets/token/token";
import store from "@/store";
import {isRelogin} from "@/utils/request";

router.beforeEach((to, from, next) => {
    //进度条开始
    NProgress.start()
    const token=getToken()
    console.log(token)
    //存在token
    if(token!==null||true){
        if(to.path==='/login'||to.path==='/register'){
            console.log("在登陆")
            next({path: '/'})
            NProgress.done()
        }else {
            if(store.getters.roles.length===0){
                console.log("1")
                isRelogin.show = true
                //判断当前用户是否已经拉取完user信息
                store.dispatch('getInfo').then(()=>{
                    console.log("2")
                    isRelogin.show = false
                    store.dispatch('GenerateRoutes').then(accessRoutes=>{

                    })
                })

            }
        }
    }
});
