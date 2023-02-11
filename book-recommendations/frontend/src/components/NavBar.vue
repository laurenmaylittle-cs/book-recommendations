<template>
  <v-app-bar
    app
    color="#46648c"
    dark
  >
    <div class="d-flex align-center">
      <v-hover v-slot="{ hover }">
        <v-btn
          text
          :style="{ ...getHoverEffect(hover), boxShadow: 'none' }"
          @click="redirectToHomePage"
        >
          <v-icon>mdi-book-open-page-variant</v-icon>
          <span class="mr-2">{{ serviceName }}</span>
        </v-btn>
      </v-hover>
    </div>
    <search-bar :search-term="searchTerm" />
    <v-spacer />
    <router-link
      to="/bookshelf"
      custom
      :style="{ ...getHoverEffect(), boxShadow: 'none'}"
    >
      <template #default="{ navigate }">
        <v-btn @click="navigate">
          <v-icon>mdi-bookshelf</v-icon>
        </v-btn>
      </template>
    </router-link>
    <!-- TODO BES-36 do proper implementation of profile link, just placeholder to demo AuthGuard page
    authentication-->
    <div v-if="!$auth.loading">
      <!-- show login when not authenticated -->
      <button
        v-if="!$auth.isAuthenticated"
        @click="login"
      >
        Log in
      </button>
      <!-- show logout when authenticated -->
      <h4 v-if="$auth.isAuthenticated">
        Hi {{ $auth.user.name }}
      </h4>
      <button
        v-if="$auth.isAuthenticated"
        @click="logout"
      >
        Log out
      </button>
    </div>
    <div v-if="!$auth.loading">
      <router-link
        v-if="$auth.isAuthenticated"
        to="/profile"
        class="mr-6"
        style="color: white; text-decoration: none"
      >
        <img
          v-if="$auth.isAuthenticated"
          alt="Profile Picture"
          referrerpolicy="no-referrer"
          :src="$auth.user.picture"
          class="ml-3 mt-1"
          style="border-radius: 50%; max-width:50px;"
        >
      </router-link>
    </div>
  </v-app-bar>
</template>

<script>
import SearchBar from '@/components/search/SearchBar'
import {EventBus} from "@/event-bus";

export default {
  name: 'NavBar',
  components: {SearchBar},
  props: {
    serviceName: {
      type: String,
      default: ""
    }
  },
  data() {
    return {
      searchTerm: 'Search'
    }
  },
  methods: {
    getHoverEffect(hover) {
      return {'background-color': hover ? 'white' : '#46648c', 'color': hover ? '#46648c' : 'white'}
    },
    async redirectToHomePage() {
      const isOnHomePage = this.$router.currentRoute.name === 'homePage';
      if (!isOnHomePage) {
        await this.$router.push({name: 'homePage'});
      }
      EventBus.$emit('refresh-homepage');
    },
    login() {
      this.$auth.loginWithRedirect();
    },
    logout() {
      this.$auth.logout({
        returnTo: window.location.origin
      });
    }
  }
}
</script>

<style scoped>
/deep/ .v-text-field {
  width: 300px;
}

/deep/ .v-input__control {
  margin-top: 25px;
}

.v-btn {
  box-shadow: none;
}
</style>
