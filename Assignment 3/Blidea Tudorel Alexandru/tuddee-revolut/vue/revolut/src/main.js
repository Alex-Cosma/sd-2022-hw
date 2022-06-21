import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import { loadFonts } from './plugins/webfontloader'
import vuetify from "./plugins/vuetify";

loadFonts();

createApp(App)
  .use(router)
  .use(store)
  .use(vuetify)
  .mount('#app')
