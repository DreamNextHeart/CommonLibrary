import { createRouter, createWebHistory } from 'vue-router'
import {getToken} from "@/assets/token/token";



export const constantRouter = [
  {
    path: '/',
    name: 'page1',
    component: ()=>import("@/pages/page1")
  },
  {
    path: '/page1',
    name: 'page1',
    component: ()=>import("@/pages/page1")
  },
  {
    path: '/page2',
    name: 'page2',
    component: ()=>import("@/pages/page2")
  },
  {
    path: '/login',
    name: 'Login',
    component: ()=>import("@/views/login/login")
  },
  {
    path: '/register',
    name: 'register',
    component: ()=>import("@/views/login/register")
  }
]

export const dynamicRouter=[
  {
    path: '/admin',
    name: 'admin',
    component:()=>import("@/views/user/admin"),

    meta: {
      roles: ["admin","super_admin"],
      title: '管理员页面'
    }
  },
  {
    path: '/superAdmin',
    name: 'superAdmin',
    component:()=>import("@/views/user/superAdmin"),
    meta: {
      roles: ["super_admin"],
      title: '超级管理员页面'
    }
  },
  {
    path: '/user',
    name: 'user',
    component:()=>import("@/views/user/user"),
    meta: {
      roles: ["user","admin","super_admin"],
      title: '用户页面'
    }
  },
  {path: '*',redirect: '/404',hidden:true}
]


const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes: constantRouter
})



export default router
