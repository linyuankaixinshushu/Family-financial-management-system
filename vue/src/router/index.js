import Vue from 'vue'
import VueRouter from 'vue-router'
import global from "@/config/global";




Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/login.vue'),
  },
  {
    path: '/register/',
    name: 'Register',
    component: () => import('../views/register.vue')
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export const setRoutes = () => {
  let storeMenus = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).menu : [];
  if(storeMenus) {
    let manageRoute = {
      path: '/', name: 'manage', component: ()=> import("../views/manage.vue"), children: [], redirect: 'home'}
    global.all_menus.forEach((i) => {
      if(i.path && storeMenus.includes(i.index)) {
        let itemMenu = {
          path: i.path, name: i.name, component: () => import('../views/' + i.path + '.vue')
        }
        manageRoute.children.push(itemMenu)
      }
      if(i.children != null) {
        i.children.forEach((j) => {
          if(storeMenus.includes(j.index)) {
          let jtemMenu = {
            path: j.path, name: j.name, component: () => import('../views/' + j.path + '.vue')
          }
          manageRoute.children.push(jtemMenu) }
        })
      }
    })
    const currentRoutesNames = router.getRoutes().map(v => v.name);
    if(!currentRoutesNames.includes('manage')) {
      router.addRoute(manageRoute)
    }
  }
}

setRoutes()

// router.beforeEach((to,from,next) => {
//     localStorage.setItem("currentPathName", to.name)
//     store.commit("setPath")
//     const storeMenus = localStorage.getItem("user")
//     if(storeMenus) {
//       next("/404")
//     } else {
//       next("/login")
//     }
// })

export default router
