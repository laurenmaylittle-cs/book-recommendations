<template>
  <!--  TODO BES-36 this is just a placeholder (proper implementation is missing) to test functionality of AuthGuard-->
  <div>
    <div>
      <img
        :src="$auth.user.picture"
        alt="User profile picture"
      >
      <h2>{{ $auth.user.name }}</h2>
      <p>{{ $auth.user.email }}</p>
    </div>
    <v-btn
      v-if="$auth.isAuthenticated"
      @click="logout"
    >
      Logout
    </v-btn>

    <div>
      <pre>{{ JSON.stringify($auth.user, null, 2) }}</pre>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ProfileView',
  methods: {
    logout() {
      this.$auth.logout({
        returnTo: window.location.origin
      });
    }
  }
}
</script>
