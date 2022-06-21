import Vue from "vue";
import VueRouter from "vue-router";
import UserList from "../views/UserList.vue";
import AdminPropertyList from "../views/PropertyList.vue";
import PropertyListUser from "../views/PropertyListUser.vue";
import BookingListUser from "../views/BookingListUser.vue";
import SearchPropertyList from "../views/SearchPropertyList.vue";
import { auth as store } from "../store/auth.module";
import Login from "../views/Login";
import AdminOpts from "../views/AdminOptions.vue";
import UserOpts from "../views/UserOptions.vue";
// import GoogleBooksApi from "../views/GoogleApi/GoogleBooksApi.vue"

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/options",
    name: "AdminOpts",
    component: AdminOpts,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin) {
        next();
      } else {
        next({ name: "UserOpts" });
      }
    },
  },
  {
    path: "/user-options",
    name: "UserOpts",
    component: UserOpts,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/users",
    name: "UserList",
    component: UserList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin) {
        next();
      } else {
        next({ name: "Properties" });
      }
    },
  },
  {
    path: "/admin-properties",
    name: "AdminProperties",
    component: AdminPropertyList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin) {
        next();
      } else {
        next({ name: "Properties" });
      }
    },
  },
  {
    path: "/user-properties",
    name: "PropertyListUser",
    component: PropertyListUser,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/search-properties",
    name: "SearchPropertyList",
    component: SearchPropertyList,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/bookings",
    name: "BookingListUser",
    component: BookingListUser,
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
