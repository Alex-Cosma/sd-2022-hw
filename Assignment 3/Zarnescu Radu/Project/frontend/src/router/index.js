import Vue from "vue";
import VueRouter from "vue-router";
import Admin from "../views/Admin.vue";
import MovieList from "../views/MovieList.vue";
import { auth as store } from "../store/auth.module";
import Login from "../views/Login";
import Movie from "../views/Movie";
import Watchlist from "../views/Watchlist";

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
        next({ name: "Movies" });
      }
    },
  },
  {
    path: "/movies",
    name: "Movies",
    component: MovieList,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/movies/:id?",
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
    path: "/watchlist/:id?",
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
