<template>
  <v-container>
    <v-row class="pl-3">
      <v-col cols="10">
        <v-card>
          <div class="div_center d-flex align-center pl-4">
            <v-text-field
              v-model="searchParam"
              label="Search by user's name"
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
      v-else-if="hasSearched && userList.length > 0"
      :key="user.email"
    >
      <user-card
        :name="user.name"
        :email="user.email"
        :is-a-follower="isAFollowerOfUser"
        :picture="user.picture"
        :following-list="user.followers"
        @follow="follow"
        @unfollow="unfollow"
      />
    </v-row>
  </v-container>
</template>

<script>
import {followUser, getUsersSearch, unfollowUser} from "@/api/find-friends";
import UserCard from "@/components/UserCard";

export default {
  name: "FindFriendsPage",
  components: {UserCard},
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
      const token = await this.$auth.getTokenSilently()
      this.userList = await getUsersSearch(this.searchParam, token)
      this.isLoading = false;
      this.isAFollowerOfUser = false
    },
    async follow(userToFollow) {
      const token = await this.$auth.getTokenSilently();
      await followUser(this.$auth.user.email, userToFollow, token)
      this.isAFollowerOfUser = true
    },
    async unfollow(userToUnfollow) {
      const token = await this.$auth.getTokenSilently();
      await unfollowUser(this.$auth.user.email, userToUnfollow, token)
      this.isAFollowerOfUser = false
    }
  }
}
</script>

<style scoped>
</style>
