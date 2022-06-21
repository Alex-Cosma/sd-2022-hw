import Vue from "vue";
import VueRouter from "vue-router";
import { auth as store } from "../store/auth.module";
import Login from "../views/Login";
import Admin from "../views/Admin";
import RegularUser from "../views/RegularUser";
import Trainer from "../views/Trainer";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/admin",
    name: "Admin",
    component: Admin,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/trainer",
    name: "Trainer",
    component: Trainer,
    beforeEnter: (to, from, next) => {
      if (store.getters.isTrainer) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/regular_user",
    name: "RegularUser",
    component: RegularUser,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
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
