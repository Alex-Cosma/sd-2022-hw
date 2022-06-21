import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '@/views/HomeView.vue';
import AboutView from '@/views/AboutView.vue';
import PublicPage from "@/views/PublicPage.vue";
import Dashboard from "@/views/Dashboard.vue";
import AdminPage from "@/views/AdminPage.vue";

const createSimpleRoute = (path, name, component, meta = {}) => ({path, name, component, meta});

const routes = [
    createSimpleRoute('/home', 'home', HomeView),
    createSimpleRoute('/about', 'about', AboutView),
    createSimpleRoute('/', 'main', PublicPage),
    createSimpleRoute('/dashboard', 'dashboard', Dashboard, {
        roles: ['CUSTOMER']
    }),
    createSimpleRoute('/admin', 'admin', AdminPage, {
        roles: ['ADMIN']
    })
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
