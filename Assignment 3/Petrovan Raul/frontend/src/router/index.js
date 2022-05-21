import Vue from "vue";
import VueRouter from "vue-router";
import UserList from "../views/UserList.vue";
import { auth as store } from "../store/auth.module";
import Login from "../views/Login";
import Reports from "@/views/Reports";
import StudentsList from "@/views/StudentsList";
import FlightsList from "@/views/FlightsList";
import UserSettings from "@/views/UserSettings";
import Weather from "@/views/Weather";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/settings",
    name: "Settings",
    component: UserSettings,
    beforeEnter: (to, from, next) => {
      if (store.getters.isFI(store.state) || store.getters.isStudent(store.state)) {
        next();
      } else {
        next({ name: "About" });
      }
    },
  },
  {
    path: "/users",
    name: "Users",
    component: UserList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin(store.state)) {
        next();
      } else {
        next({ name: "About" });
      }
    },
  },
  {
    path: "/my-students",
    name: "Students",
    component: StudentsList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isFI(store.state)) {
        next();
      } else {
        next({ name: "About" });
      }
    },
  },
  {
    path: "/my-flights",
    name: "Flights",
    component: FlightsList,

  },
  {
    path: "/reports",
    name: "Reports",
    component: Reports,
    beforeEnter: (to, from, next) => {
      if (store.getters.isFI(store.state) || store.getters.isStudent(store.state)) {
        next();
      } else {
        next({ name: "About" });
      }
    },
  },
  {
    path: "/weather",
    name: "Weather",
    component: Weather,
    beforeEnter: (to, from, next) => {
      if (store.getters.isFI(store.state) || store.getters.isStudent(store.state)) {
        next();
      } else {
        next({ name: "About" });
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
