<template>
  <v-container>
    <!-- show login when not authenticated -->
    <div v-if="!$auth.loading">
      <v-btn
        v-if="!$auth.isAuthenticated"
        @click="login"
      >
        Log in
      </v-btn>
      <v-btn
        v-if="$auth.isAuthenticated"
        @click="logout"
      >
        Log out
      </v-btn>
    </div>
  </v-container>
</template>

<script>
export default {
  name: "TestLogin",
  methods: {
    // Log the user in
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
