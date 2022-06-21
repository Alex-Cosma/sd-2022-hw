import Vue from "vue";
import VueRouter from "vue-router";
import UserList from "../views/AdminPage.vue";
import { auth as store } from "../store/auth.module";
import Login from "../views/Login";
import MoviesMainPage from "@/views/MoviesMainPage";
import Movie from "@/views/Movie.vue";
import Watchlist from "@/views/Watchlist.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/admin",
    name: "Users",
    component: UserList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin) {
        next();
      } else {
        next({ name: "Items" });
      }
    },
  },
  {
    path: "/movies",
    name: "Movies",
    component: MoviesMainPage,
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
  {
    path: "/movies/id=:id",
    name: "Movie",
    component: Movie,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/movies/watchlist",
    name: "Watchlist",
    component: Watchlist,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },

];

const router = new VueRouter({
  routes,
});

export default router;
