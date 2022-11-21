<template>
  <v-app-bar
    app
    color="#46648c"
    dark
  >
    <div class="d-flex align-center">
      <v-hover v-slot="{ hover }">
        <v-btn
          href="/home"
          text
          :style="{ ...getHoverEffect(hover) }"
        >
          <v-icon>mdi-book-open-page-variant</v-icon>
          <span class="mr-2">{{ serviceName }}</span>
        </v-btn>
      </v-hover>
    </div>

    <v-spacer />
    <router-link
      v-if="$auth.isAuthenticated"
      to="/profile"
      class="mr-2"
      style="color: white; text-decoration: none"
    >
      Profile
    </router-link
    >
    <div v-if="!$auth.loading">
      <!-- show login when not authenticated -->
      <button v-if="!$auth.isAuthenticated" @click="login">Log in</button>
      <!-- show logout when authenticated -->
      <button v-if="$auth.isAuthenticated" @click="logout">Log out</button>
    </div>
  </v-app-bar>
</template>

<script>
export default {
  name: 'NavBar',
  props: {
    serviceName: {
      type: String,
      default: ""
    }
  },
  methods: {
    getHoverEffect(hover) {
      return {'background-color': hover ? 'white' : '#46648c', 'color': hover ? '#46648c' : 'white'}
    },
    login() {
      this.$auth.loginWithRedirect();
    },
    // Log the user out
    logout() {
      this.$auth.logout({
        returnTo: window.location.origin
      });
    }
  }
}
</script>

<style scoped>
</style>
