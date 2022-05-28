import Vue from "vue";
import VueRouter from "vue-router";
import UserList from "../views/UserList.vue";
import { auth as store } from "../store/auth.module";
import Login from "../views/Login";
import Tracks from "../views/Tracks";
import Playlists from "../views/Playlists";
import TracksAPI from "../views/TracksAPI";
import Payment from "../views/Payment";
import PaymentSuccess from "../views/PaymentSuccess";
import PaymentFail from "../views/PaymentFail";
import Comments from "../views/Comments";
import WebSocketGreeting from "../components/WebSocketGreeting";
Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/topic/greetings",
    name: "WebSocketGreeting",
    component: WebSocketGreeting,
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
    name: "Users",
    component: UserList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin) {
        next();
      } else {
        next({ name: "Playlists" });
      }
    },
  },
  {
    path: "/tracks",
    name: "Tracks",
    component: Tracks,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/playlists",
    name: "Playlists",
    component: Playlists,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/tracksAPI",
    name: "TracksAPI",
    component: TracksAPI,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/comments",
    name: "Comments",
    component: Comments,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/payment",
    name: "Payment",
    component: Payment,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/payment/success",
    name: "PaymentSuccess",
    component: PaymentSuccess,
  },
  {
    path: "/payment/fail",
    name: "PaymentFail",
    component: PaymentFail,
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
