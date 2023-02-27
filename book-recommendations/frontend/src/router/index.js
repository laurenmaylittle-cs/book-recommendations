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
    path: '/book',
    name: 'book',
    component: ViewBookPage,
  },
  {
    path: '/search',
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
    path: '/bookshelf/books',
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
