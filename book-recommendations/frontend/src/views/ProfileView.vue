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
    <v-row class="pl-2">
      <v-col
        cols="2"
      >
        <followers-following
          :title="formatTitle()"
          :total="followerFollowingDetails.totalFollowers"
          :list-of-users="followerFollowingDetails.allFollowers"
        />
      </v-col>
      <v-col
        cols="2"
        class="pl-10"
      >
        <followers-following
          title="Following"
          :total="followerFollowingDetails.totalFollowing"
          :list-of-users="followerFollowingDetails.allFollowing"
        />
      </v-col>
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
import FollowersFollowing from "@/components/profile/FollowersFollowing";

export default {
  name: 'ProfileView',
  components: {FollowersFollowing},
  data() {
    return {
      style: {
        backgroundColor: '#E4E4E4'
      },
      followerFollowingDetails: '',
      title: ''
    }
  },
  async mounted() {
    const token = await this.$auth.getTokenSilently();
    this.followerFollowingDetails = await getFollowersAndFollowing(this.$auth.user.email, token)
    this.getListOfFollowerEmails(followerFollowingDetails.allFollowers)
  },
  methods: {
    formatTitle() {
      if (this.followerFollowingDetails.totalFollowers === 1) {
        return "Follower"
      }
      return "Followers"
    }
  }
}
</script>
