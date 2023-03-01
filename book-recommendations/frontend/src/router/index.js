import Vue from 'vue'
import VueRouter from 'vue-router'
import ViewBookPage from "@/pages/ViewBookPage.vue";
import ProfileView from '../views/ProfileView.vue';
import {authGuard} from '@/auth/authGuard';
import SearchView from "@/views/SearchView";
import HomePage from "@/pages/HomePage";
import FindFriendsPage from "@/pages/FindFriendsPage";
import BookShelfPage from "@/pages/BookshelfPage"
import IndividualBookshelfPage from "@/pages/IndividualBookshelfPage";


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Root',
    component: HomePage,
    meta: {
      title: "BestReads"
    }
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
    component: HomePage,
    meta: {
      title: "BestReads"
    }
  },
  {
    path: '/profile',
    name: 'profile',
    component: ProfileView,
    beforeEnter: authGuard,
    meta: {
      title: "Profile"
    }
  },
  {
    path: '/find-friends',
    name: 'find-friends',
    component: FindFriendsPage,
    beforeEnter: authGuard,
    meta: {
      title: "Search for friends"
    }
  },
  {
    path: '/bookshelf',
    name: 'bookshelf',
    component: BookShelfPage,
    beforeEnter: authGuard,
    meta: {
      title: "Bookshelf"
    }
  },
  {
    path: '/bookshelf/books',
    name: 'individualBookshelfPage',
    component: IndividualBookshelfPage,
    beforeEnter: authGuard
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  document.title = to.meta.title || "BestReads";
  next();
});

export default router
