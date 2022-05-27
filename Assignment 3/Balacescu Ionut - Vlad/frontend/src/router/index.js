import Vue from "vue";
import VueRouter from "vue-router";
import UserList from "../views/UserList.vue";
import HomePage from "../views/HomePage.vue";
import { auth as store } from "../store/auth.module";
import Login from "../views/Login";
import GameList from "../views/GameList";
import GameInfo from "../views/GameInfo";
import WishList from "../views/WishList";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/users",
    name: "Users",
    component: UserList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin) {
        next();
      } else {
        next({ name: "home" });
      }
    },
  },
  {
    path: "/homePage",
    name: "HomePage",
    component: HomePage,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "HomePage" });
      }
    },
  },
  {
    path: "/games",
    name: "GameList",
    component: GameList,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "GameList" });
      }
    },
  },
  {
    path: "/games/:id?",
    name: "GameInfo",
    component: GameInfo,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "GameInfo" });
      }
    },
  },
  {
    path: "/wishlist/:id?",
    name: "WishList",
    component: WishList,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "WishList" });
      }
    },
  },
  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue"),
  },
];

const router = new VueRouter({
  routes,
});

export default router;
