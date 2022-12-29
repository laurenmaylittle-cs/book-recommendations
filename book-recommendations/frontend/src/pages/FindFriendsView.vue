<template>
  <v-container>
    <v-row class="pl-3">
      <v-col cols="10">
        <v-card>
          <div class="div_center d-flex align-center pl-4">
            <v-text-field
              v-model="searchParam"
              label="Search by email"
              clearable
              type="String"
              @keyup.enter="loadSearch(searchParam)"
            />
            <v-btn
              text
              @click.native="loadSearch(searchParam)"
            >
              <v-icon>mdi-magnify</v-icon>
            </v-btn>
          </div>
        </v-card>
      </v-col>
    </v-row>
    <v-row
      v-if="isLoading"
      class="justify-center pt-8"
    >
      <v-progress-circular
        :size="70"
        :width="7"
        color="primary"
        indeterminate
      />
    </v-row>
    <v-row
      v-for="user in userList"
      v-else-if="hasSearched"
      :key="user.email"
    >
      <v-col cols="10">
        <v-card
          v-if="user.email !== $auth.user.email"
          class="ml-5 mt-2 mb-2"
          height="150px"
        >
          <v-row>
            <v-col
              cols="2"
            >
              <v-avatar
                v-if="user.picture !== null"
                size="100px"
                class="ml-3 mt-5 mb-5 mr-3"
              >
                <img
                  :src="user.picture"
                >
              </v-avatar>
              <v-avatar
                v-else
                color="indigo"
                size="100px"
                class="ml-3 mt-5 mb-5 mr-3"
              >
                <v-icon dark>
                  mdi-account-circle
                </v-icon>
              </v-avatar>
            </v-col>
            <v-col
              cols="5"
              class="pt-15 pl-8 pr-0"
            >
              <h3>
                {{ user.name }}
              </h3>
            </v-col>
            <v-col class="pt-15">
              <v-btn
                v-if="isAFollower(user.followers)"
                @click.native="unfollowUser(user.email)"
              >
                Following
              </v-btn>
              <v-btn
                v-else
                color="primary"
                @click.native="followUser(user.email)"
              >
                Follow
              </v-btn>
            </v-col>
          </v-row>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import {followUser, getUsersSearch, unfollowUser} from "@/api/find-friends";

export default {
  name: "FindFriendsView",
  data: () => ({
    searchParam: '',
    userList: [],
    isLoading: false,
    hasSearched: false,
    isAFollowerOfUser: false
  }),
  methods: {
    async loadSearch() {
      this.isLoading = true;
      this.hasSearched = true;
      let token = await this.$auth.getTokenSilently({audience: 'https://localhost:5001/api'})
      this.userList = await getUsersSearch(this.searchParam, token)
      this.isLoading = false;
      this.isAFollowerOfUser = false
    },
    async followUser(userToFollow) {
      const token = await this.$auth.getTokenSilently(
        {audience: 'https://localhost:5001/api'});
      await followUser(this.$auth.user.email, userToFollow, token)
      this.isAFollowerOfUser = true
    },
    async unfollowUser(userToUnfollow) {
      const token = await this.$auth.getTokenSilently(
        {audience: 'https://localhost:5001/api'});
      await unfollowUser(this.$auth.user.email, userToUnfollow, token)
      this.isAFollowerOfUser = false
      this.isAFollower(null)
    },
    isAFollower(listOfFollowers) {
      listOfFollowers.forEach(follower => this.checkIfUserIsAFollower(follower.followerEmail))
      return this.isAFollowerOfUser
    },
    checkIfUserIsAFollower(followerEmail) {
      if (this.$auth.user.email === followerEmail) {
        this.isAFollowerOfUser = true;
      }
    }
  }
}
</script>

<style scoped>

</style>
