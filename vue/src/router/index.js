import { createRouter, createWebHistory } from 'vue-router'
import page1 from "@/pages/page1";
import page2 from "@/pages/page2";
import Login from "@/views/login/login"


const routes = [
  {
    path: '/',
    name: 'page1',
    component: page1
  },
  {
    path: '/page1',
    name: 'page1',
    component: page1
  },
  {
    path: '/page2',
    name: 'page2',
    component: page2
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

//重置路由
export function resetRouter(){
  const  newRouter=createRouter({
    history: createWebHistory(process.env.BASE_URL)
  });
}

export default router
