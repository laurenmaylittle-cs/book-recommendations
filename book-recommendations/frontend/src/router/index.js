import Vue from 'vue'
import VueRouter from 'vue-router'
import ViewBookPage from "@/pages/ViewBookPage.vue";
import ProfileView from '../views/ProfileView.vue';
import {authGuard} from '@/auth/authGuard';
import SearchView from "@/views/SearchView";
import HomePage from "@/pages/HomePage";
import FindFriendsView from "@/pages/FindFriendsView";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Root',
    component: HomePage,
  },
  {
    path: '/book',
    name: 'book',
    component: ViewBookPage
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
  },
  {
    path: '/profile',
    name: 'profile',
    component: ProfileView,
    beforeEnter: authGuard
  },
  {
    path: '/find-friends',
    name: 'find-friends',
    component: FindFriendsView,
    beforeEnter: authGuard
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
