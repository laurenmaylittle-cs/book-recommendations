<template>
  <v-container>
    <!--  TODO BES-36 this is just a placeholder (proper implementation is missing) to test functionality of AuthGuard-->
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
        {{ followerFollowingDetails.totalFollowers }} Followers
      </h4>
      <h4 class="pl-5">
        {{ followerFollowingDetails.totalFollowing }} Following
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

import {getFollowersAndFollowing} from "@/api/profile";

export default {
  name: 'ProfileView',
  data() {
    return {
      style: {
        backgroundColor: '#E4E4E4'
      },
      followerFollowingDetails: ''
    }
  },
  async mounted() {
    const token = await this.$auth.getTokenSilently(
      {audience: 'https://localhost:5001/api'});
    this.followerFollowingDetails = await getFollowersAndFollowing(this.$auth.user.email, token)
  }
}
</script>
