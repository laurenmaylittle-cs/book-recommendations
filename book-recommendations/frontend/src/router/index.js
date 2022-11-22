import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import SearchView from "@/views/SearchView";
import HomePage from "@/pages/HomePage";

Vue.use(VueRouter)

const routes = [
  {
    path: '/default-home-vue',
    name: 'home',
    component: HomeView
  },
  {
    path: '/default-about-vue',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/home',
    name: 'homePage',
    component: HomePage
  },
  {
    path: '/search/:searchTerm',
    name: 'search',
    component: SearchView
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
