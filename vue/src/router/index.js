import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from  '@/views/home'
import Home from "@/views/home"
import NotFound from "@/views/errorPage/404"
import Forbidden from "@/views/errorPage/403"



Vue.use(VueRouter)

//无需筛选权限的路由
const routes = [
  {
    path: '/login',
    component: () => import('@/views/login/login')
  },
  {
    path: '/register',
    component: ()=>import('@/views/login/register')
  }
]

/**
 * 根据用户的权限不同，所能看到的页面和可操作性也不同
 * super_admin->上帝权限
 * admin->管理员权限
 * user->用户权限
 *
 * @type {[{}]}
 */
export const DynamicRoutes=[
  {
    path: "",
    component: Layout,
    name:'container',
    redirect:"home",
    meta: {
      requiresAuth: true,
      name:"首页"
    },
    children:[
      {
        path:"home",
        component:Home,
        name:"home",
        meta:{
          // 匹配规则
          name:"首页",
          icon:"icon-name"
        }
      }
    ]
  },
  {
    path:"/403",
    component:Forbidden
  },
  {
    path:"*",
    component:NotFound
  }
]

const router = new VueRouter({
  mode: 'history',
  routes
})

export default router
