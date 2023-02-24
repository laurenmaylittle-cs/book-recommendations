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
    <div
      v-else-if="hasSearched && userList.length > 0 && $vuetify.breakpoint.xs"
      class="ma-3"
    >
      <v-simple-table>
        <tbody>
          <tr
            v-for="user in userList"
            :key="user.email"
            class="ma-5"
            height="75px"
          >
            <user-table
              :picture="user.picture"
              :email="user.email"
              :name="user.name"
              :following-list="user.followers"
              @follow="follow"
              @unfollow="unfollow"
            />
          </tr>
        </tbody>
      </v-simple-table>
    </div>
    <v-row
      v-for="user in userList"
      v-else-if="hasSearched && userList.length > 0"
      :key="user.email"
    >
      <user-card
        :name="user.name"
        :email="user.email"
        :picture="user.picture"
        :following-list="user.followers"
        @follow="follow"
        @unfollow="unfollow"
      />
    </v-row>
    <div
      v-else-if="hasSearched && userList.length === 0"
      class="pa-5"
    >
      No results found
    </div>
  </v-container>
</template>

<script>
import {followUser, getUsersSearch, unfollowUser} from "@/api/find-friends";
import UserCard from "@/components/UserCard";
import UserTable from "@/components/findfriends/UserTable";

export default {
  name: "FindFriendsPage",
  components: {UserCard, UserTable},
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
      const indx = this.userList.findIndex(user => user.email === this.$auth.user.email);
      this.userList.splice(indx, 1);
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
