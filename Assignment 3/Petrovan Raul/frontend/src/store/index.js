import Vue from "vue";
import Vuex from "vuex";
import { createModule } from 'vuex-toast'

import { auth } from "./auth.module";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    auth,
    toast: createModule({
      dismissInterval: 8000,
    })
  },
});
