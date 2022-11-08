<template>
  <!-- Check that the SDK client is not currently loading before accessing is methods -->
  <!--  <div v-if="!$auth.loading">-->
  <!--    &lt;!&ndash; show login when not authenticated &ndash;&gt;-->
  <!--    <button v-if="!$auth.isAuthenticated" @click="login`">Log in</button>-->
  <!--    &lt;!&ndash; show logout when authenticated &ndash;&gt;-->
  <!--&lt;!&ndash;    <button v-if="$auth.isAuthenticated" @click="logout">Log out</button>&ndash;&gt;-->
  <!--  </div>-->

  <div>
    <button @click="logout">
      Log out
    </button>
  </div>
</template>

<script>

import {getTestData} from '@/api/test'

export default {
  name: 'HomeView',
  data: () => ({
    testData: ''
  }),
  mounted () {
    this.getTest()
  },
  methods: {
    async getTest () {
      this.testData = await getTestData()
    },
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
