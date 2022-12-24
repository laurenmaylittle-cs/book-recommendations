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
      class="pa-3"
    >
      <v-col cols="10">
        <v-card v-if="user.email !== $auth.user.email">
          <v-row>
            <v-col
              cols="2"
              class="ml-10 mt-5 mb-5 mr-6"
            >
              <v-avatar
                v-if="user.picture !== null"
                size="100px"
              >
                <img
                  :src="user.picture"
                >
              </v-avatar>
              <v-avatar
                v-else
                color="indigo"
                size="100px"
                class="ml-10 mt-5 mb-5 mr-3"
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
import {followUser, getUsersSearch} from "@/api/find-friends";

export default {
  name: "FindFriendsView",
  data: () => ({
    searchParam: '',
    userList: [],
    isLoading: false,
    hasSearched: false
  }),
  methods: {
    async loadSearch() {
      this.isLoading = true;
      this.hasSearched = true;
      let token = await this.$auth.getTokenSilently({audience: 'https://localhost:5001/api'})
      this.userList = await getUsersSearch(this.searchParam, token)
      this.isLoading = false;
    },
    async followUser(userToFollow) {
      const token = await this.$auth.getTokenSilently(
        {audience: 'https://localhost:5001/api'});
      await followUser(this.$auth.user.email, userToFollow, token)
    }
  }
}
</script>

<style scoped>

</style>
