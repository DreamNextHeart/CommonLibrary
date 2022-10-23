import {createRouter, createWebHistory} from 'vue-router'


export const constantRouter = [
    {
        path: '/',
        name: 'index',
        title: '首页',
        component: () => import("@/views/index"),
        redirect: '/welcome',
        children: [
            {
                path: '/page1',
                name: 'page1',
                title: '第一页',
                component: () => import("@/pages/page1"),
                meta: {
                    title: '第一页'
                },
                children: [
                    {
                        path: '/page3',
                        name: 'page3',
                        title: '第三页',
                        component: () => import("@/pages/page3"),
                        meta: {
                            title: '第三页'
                        }
                    }
                ]
            },
            {
                path: '/page2',
                name: 'page2',
                title: '第二页',
                component: () => import("@/pages/page2"),
                meta: {
                    title: '第二页'
                },
                children: [
                    {
                        path: '/page4',
                        name: 'page4',
                        title: '第四页',
                        component: () => import("@/pages/page4"),
                        meta: {
                            title: '第四页'
                        }
                    }
                ]
            },
            {
                path: '/login',
                name: 'Login',
                title: '登录页',
                component: () => import("@/views/login/login"),
                meta: {
                    title: '登录页'
                }
            },
            {
                path: '/register',
                name: 'register',
                title: '注册页',
                component: () => import("@/views/login/register"),
                meta: {
                    title: '注册页'
                }
            }
        ]
    }
]

export const dynamicRouter = [
    {
        path: '/',
        name: 'index1',
        title: '首页1',
        component: () => import("@/views/index"),
        redirect: '/welcome',
        children: [
            {
                path: '/admin',
                name: 'admin',
                title: '管理员页面',
                component: () => import("@/views/user/admin"),
                meta: {
                    roles: ["admin", "super_admin"],
                    title: '管理员页面'
                },
                children: [
                    {
                        path: '/admin/page5',
                        name: 'page5',
                        title: '第五页',
                        component: () => import("@/pages/page5"),
                        meta: {
                            title: '第五页'
                        }
                    }
                ]
            },
            {
                path: '/superAdmin',
                name: 'superAdmin',
                title: '超级管理员页面',
                component: () => import("@/views/user/superAdmin"),
                meta: {
                    roles: ["super_admin"],
                    title: '超级管理员页面'
                }
            },
            {
                path: '/user',
                name: 'user',
                title: '用户页面',
                component: () => import("@/views/user/user"),
                meta: {
                    roles: ["user", "admin", "super_admin"],
                    title: '用户页面'
                }
            }
        ]
    }

]

export const errorRouter = [
    {
        path: '/:pathMatch(.*)', redirect: '/404', hidden: true
    }
]


const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes: constantRouter
})


export default router
