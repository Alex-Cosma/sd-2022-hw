import Vue from "vue";
import VueRouter from "vue-router";
import UserList from "../views/UserList.vue";
import { auth as store } from "../store/auth.module";
import Login from "../views/Login";
import CategoryList from "@/views/CategoryList";
import CategoryThreadList from "@/views/CategoryThreadList";
import CategoryThreadEditList from "@/views/CategoryThreadEditList";
import ThreadPostList from "@/views/ThreadPostList";
import AllThreadsList from "@/views/AllThreadsList";
import Notifications from "@/views/Notifications";

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
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/categories",
    name: "Categories",
    component: CategoryList,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/threads-from-category/:id",
    name: "Threads",
    component: CategoryThreadList,
    props: true,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/edit-threads-from-category/:id",
    name: "Edit/Delete Threads",
    component: CategoryThreadEditList,
    props: true,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/posts-from-thread/:id",
    name: "Posts",
    component: ThreadPostList,
    props: true,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/threads",
    name: "All Threads",
    component: AllThreadsList,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/notifications",
    name: "Notifications",
    component: Notifications,
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
