import Vue from 'vue'
import VueRouter from 'vue-router'
import ViewBookPage from "@/pages/ViewBookPage.vue";
import ProfileView from '../views/ProfileView.vue';
import {authGuard} from '@/auth/authGuard';
import SearchView from "@/views/SearchView";
import HomePage from "@/pages/HomePage";
import FindFriendsPage from "@/pages/FindFriendsPage";
import BookShelfPage from "@/pages/BookshelfPage"
import IndividualBookshelf from "@/pages/IndividualBookshelf";


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Root',
    component: HomePage,
  },
  {
    path: '/book/:isbn',
    name: 'book',
    component: ViewBookPage,
    props: true
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
    path: '/search/:searchType/:searchTerm',
    name: 'search',
    component: SearchView
  },
  {
    path: '/home',
    name: 'homePage',
    component: HomePage
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
    component: FindFriendsPage,
    beforeEnter: authGuard
  },
  {
    path: '/bookshelf',
    name: 'bookshelf',
    component: BookShelfPage,
    beforeEnter: authGuard
  },
  {
    path: '/bookshelf/:collectionId',
    name: 'individualBookshelf',
    component: IndividualBookshelf,
    beforeEnter: authGuard
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
