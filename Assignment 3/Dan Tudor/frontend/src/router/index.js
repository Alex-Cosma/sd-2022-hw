import Vue from "vue";
import VueRouter from "vue-router";
import UserList from "../views/UserList.vue";
import { auth as store } from "../store/auth.module";
import Login from "../views/Login";
import StockList from "../views/StockList";
//import * as Console from "console";

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
        next({ name: "Stocks" });
      }
    },
  },
  {
    path: "/stocks",
    name: "Stocks",
    //user: store.getters.loggedIn(),
    component: StockList,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  // {
  //   path: "/items",
  //   name: "Items",
  //   component: ItemList,
  //   beforeEnter: (to, from, next) => {
  //     if (store.state.status.loggedIn) {
  //       next();
  //     } else {
  //       next({ name: "Home" });
  //     }
  //   },
  // },
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
