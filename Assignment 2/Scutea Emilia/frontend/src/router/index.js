import Vue from "vue";
import VueRouter from "vue-router";
import UserList from "../views/UserList.vue";
import AdminBooksCRUD from "../views/AdminBooksCRUD.vue";
import AdminFirstPage from "../views/AdminFirstPage.vue";
import { auth as store } from "../store/auth.module";
import Login from "../views/Login";
import Bookstore from "../views/Bookstore";
import UserReviews from "../views/UserReviews";
import UserCart from "../views/UserCart";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/admin",
    name: "AdminFirstPage",
    component: AdminFirstPage,
  },
  {
    path: "/userreviews",
    name: "UserReviews",
    component: UserReviews,
  },
  {
    path: "/cart",
    name: "UserCart",
    component: UserCart,
  },
  {
    path: "/users",
    name: "Users",
    component: UserList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin) {
        next();
      } else {
        next({ name: "Books" });
      }
    },
  },
  {
    path: "/bookstore",
    name: "Bookstore",
    component: Bookstore,
  },
  {
    path: "/admin/books",
    name: "Books",
    component: AdminBooksCRUD,
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
      import(/* webpackChunkName: "about" */ "../views/AdminFirstPage.vue"),
  },
];

const router = new VueRouter({
  routes,
});

export default router;
