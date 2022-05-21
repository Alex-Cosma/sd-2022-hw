<template>
  <nav>
    <v-navigation-drawer v-model="drawer" app class="indigo">
      <v-list>
        <v-list-item v-for="link in accessibleLinks" :key="link" router :to="link.to">
          <v-list-item-action>
            <v-icon>{{ link.icon }}</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>{{ link.text }}</v-list-item-title>
          </v-list-item-content>

        </v-list-item>
      </v-list>
    </v-navigation-drawer>
  </nav>
</template>

<script>
import store from '../store'

export default {
  name: 'NavBar',
  data() {
    return {
      drawer: true,
      links: [
        {icon: '', text: 'Users', to: '/users', forAdmin: true},
        {icon: '', text: 'Report Generator', to: '/reports', forFI: true, forStudent: true},
        {icon: '', text: 'My Students', to: '/my-students', forFI: true},
        {icon: '', text: 'My Flights', to: '/my-flights', forFI: true, forStudent: true},
        {icon: '', text: 'Weather Data', to: '/weather', forFI: true, forStudent: true},
        {icon: '', text: 'Settings', to: '/settings', forFI: true, forStudent: true},
      ]
    };
  },
  computed: {
    accessibleLinks: function () {
      return this.links.filter(link => link.forAdmin && store.getters["auth/isAdmin"] ||
          link.forFI && store.getters["auth/isFI"] ||
          link.forStudent && store.getters["auth/isStudent"] ||
          link.forEveryone);
    }
  }
}
</script>