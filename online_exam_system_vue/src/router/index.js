import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Index from "../views/Index";
import UserCenter from "../views/user/UserCenter";
import OnlineExam from "../views/OnlineExam";
import ShowExam from "../views/ShowExam";

import axios from "../axios";
import store from "../store";

Vue.use(VueRouter)

const routes = [
  {
    path: '/home',
    name: 'Home',
    component: Home,
    children: [
      {
        path: '/index',
        name: 'Index',
        meta: {
          title: "首页"
        },
        component: Index
      },
      {
        path: '/tpListAdd',
        name: 'TpListAdd',
        meta: {
          title: "试题添加"
        },
        component: () => import('@/views/examAdmin/TpListAdd.vue')
      },
      {
        path: '/tplistShow',
        name: 'TplistShow',
        meta: {
          title: "试卷详情"
        },
        component: () => import('@/views/examAdmin/TplistShow.vue')
      },
      {
        path: '/center',
        name: 'UserCenter',
        meta: {
          title: "个人中心"
        },
        component: () => import('@/views/user/UserCenter.vue')
      },
      {
        path: '/ShowExamInfo',
        name: 'ShowExamInfo',
        meta: {
          title: "试卷详情"
        },
        component: () => import('@/views/examAdmin/ShowExamInfo.vue')
      },
    ]
  },
  {
    path: '/onlineExam',
    name: 'OnlineExam',
    meta: {
      title: "考试界面"
    },
    component: OnlineExam
  },
  {
    path: '/ShowExam',
    name: 'ShowExam',
    meta: {
      title: "试卷详情"
    },
    component: ShowExam
  },

  {
    path: '/login',
    name: 'Login',
    meta: {
      title: "首页"

    },
    component: () => import( '@/views/Login.vue')
  },

  {
    path: '/',
    name: 'Login',
    meta: {
      title: "首页"
    },
    component: () => import( '@/views/Login.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

//拦截所有请求-->index拦截-->查看权限-->获取菜单路由权限返回
router.beforeEach((to, from, next) => {

  let hasRoute = store.state.menus.hasRoutes

  let token = localStorage.getItem("token")
  console.log("token:==================>")
  console.log(token)
  if((to.path === '/'||to.path === '/login') && token){
    next({path: '/home'})

  }else if (to.path === '/login'){
    next()

  }else if (!token) {
    console.log("login!!!!!!!!!!!")
    next({path: '/login'})

  } else if(token && !hasRoute) {
    axios.get("User/sys/menu/nav/"+token
    ).then(res => {
      console.log("nav==>")
      console.log(res)
      // 拿到menuList导航
      store.commit("setMenuList", res.data.data)

      /*// 拿到用户权限
      store.commit("setPermList", res.data.data.authoritys)*/


      // 动态绑定路由--获取现有路由
      let newRoutes = router.options.routes
      //先获得父级菜单、再获取子菜单
      res.data.data.forEach(menu => {
        if (menu.children) {
          menu.children.forEach(e => {

            // 转成路由
            let route = menuToRoute(e)

            // 把路由添加到路由管理中
            if (route) {
              newRoutes[0].children.push(route)
            }

          })
        }
      })

      console.log("newRoutes")
      console.log(newRoutes)
      //绑定
      router.addRoutes(newRoutes)

      hasRoute = true
      store.commit("changeRouteStatus", hasRoute)
    })
  }
  next()
})

// 导航转成路由
const menuToRoute = (menu) => {
  //判断菜单路由是否为空
  if (!menu.component) {
    return null
  }
  //路由所选属性
  let route = {
    name: menu.name,
    path: menu.path,
    meta: {
      icon: menu.icon,
      title: menu.title
    }
  }
  route.component = () => import('@/views/' + menu.component +'.vue')

  return route
}
export default router
