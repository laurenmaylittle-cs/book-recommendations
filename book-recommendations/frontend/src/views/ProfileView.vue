<template>
  <v-container>
    <v-row>
      <v-btn
        v-if="$auth.isAuthenticated && ($vuetify.breakpoint.xs || $vuetify.breakpoint.sm)"
        :right="true"
        :absolute="true"
        @click="logout"
      >
        Logout
      </v-btn>
    </v-row>
    <v-row>
      <v-btn
        @click="getBooksCSV"
      >
        Download Books CSV
      </v-btn>
      <v-btn
        @click="getInteractionsCSV"
      >
        Download Interactions CSV
      </v-btn>
    </v-row>
    <v-row>
      <v-col cols="3">
        <v-avatar
          size="100px"
          class="ma-5"
        >
          <img
            :src="$auth.user.picture"
            alt="Profile picture"
            referrerpolicy="no-referrer"
          >
        </v-avatar>
      </v-col>
    </v-row>
    <v-row>
      <h2 class="pl-5 pt-0">
        {{ $auth.user.name }}
      </h2>
    </v-row>
    <v-row>
      <p class="pl-5 pt-0">
        {{ $auth.user.email }}
      </p>
    </v-row>
    <v-row class="pl-5 pb-3">
      <h4 class="pr-5">
        5 Followers
      </h4>
      <h4 class="pl-5">
        10 Following
      </h4>
    </v-row>
    <v-row>
      <router-link
        to="/find-friends"
        style="text-decoration: none; color: inherit;"
        class="pl-5"
      >
        <v-icon large>
          mdi-account-plus
        </v-icon>
        Find friends
      </router-link>
    </v-row>
  </v-container>
</template>

<script>

export default {
  name: 'ProfileView',
  data() {
    return {
      style: {
        backgroundColor: '#E4E4E4'
      }
    }
  },
  methods: {
    logout() {
      this.$auth.logout({
        returnTo: window.location.origin
      });
    },
    getBooksCSV() {
      this.$router.push('/api/downloadBooksCsv');
      window.location.reload();
    },
    getInteractionsCSV() {
      this.$router.push('/api/downloadInteractionsCsv');
      window.location.reload();
    }
  }
}
</script>
