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
    <user-card
      v-for="user in userList"
      v-else-if="hasSearched"
      :key="user.email"
      :user="user"
    />
  </v-container>
</template>

<script>
import {getUsersSearch} from "@/api/find-friends";
import UserCard from "@/components/findfriends/UserCard";

export default {
  name: "FindFriendsView",
  components: {UserCard},
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
    }
  }
}
</script>

<style scoped>

</style>
