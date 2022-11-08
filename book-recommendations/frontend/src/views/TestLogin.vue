<template>
  <div v-if="!$auth.loading">
    <!-- show login when not authenticated -->
    <button
      v-if="!$auth.isAuthenticated"
      @click="login"
    >
      Log in
    </button>
    <!-- show logout when authenticated -->
    <button @click="logout">
      Log out
    </button>
  </div>
</template>

<script>
export default {
  name: "TestLogin",
  methods: {
    // Log the user in
    async login() {
      await this.$auth.loginWithRedirect();
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
