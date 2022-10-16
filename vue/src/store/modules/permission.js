import {constantRoutes} from "@/router";
import {getMenuTree} from "@/api/menu/menuApi";

const permission={
    state:{
        routes: [],
        addRoutes: [],
        defaultRoutes: [],
        topBarRouters: [],
        sideBarRouters: []
    },
    mutations:{
        SET_ROUTES: (state, routes) => {
            state.addRoutes = routes
            //追加、合并、去重数组
            state.routes = constantRoutes.concat(routes)
        },
        SET_DEFAULT_ROUTES: (state, routes) => {
            state.defaultRoutes = constantRoutes.concat(routes)
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
        GenerateRoutes({commit}){
            return new Promise(resolve => {
                //向后端请求路由数据
                getMenuTree().then(response=>{
                    const sdata = JSON.parse(JSON.stringify(response.data))
                    const rdata = JSON.parse(JSON.stringify(response.data))
                    console.log("我是sdata:")
                    console.log(sdata,"===")
                    console.log(rdata)
                })
            })
        },

    }
}

// 遍历后台传来的路由字符串，转换为组件对象
function filterAsyncRouter(asyncRouterMap,lastRouter = false, type = false){
    return asyncRouterMap.filter(route=>{
        if(type&&route.children){

        }
    })
}

//遍历路由，查找子路由
function filterChildren(childrenMap,lastRouter=false){
    const children=[]
    childrenMap.forEach((el,index)=>{
        if(el.children&&el.children.length){
            // if(el.component===)
        }
    })
}


export default permission
