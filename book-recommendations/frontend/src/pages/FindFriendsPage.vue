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
    <user-table
      v-else-if="hasSearched && userList.length > 0 && $vuetify.breakpoint.xs"
      :user-list="userList"
    />
    <user-card
      v-for="user in userList"
      v-else-if="hasSearched && userList.length > 0"
      :key="user.email"
      :user="user"
    />
    <div
      v-else-if="hasSearched && userList.length === 0"
      class="pa-5"
    >
      No results found
    </div>
  </v-container>
</template>

<script>
import {getUsersSearch} from "@/api/find-friends";
import UserTable from "@/components/findfriends/UserTable";
import UserCard from "@/components/findfriends/UserCard";

export default {
  name: "FindFriendsView",
  components: {UserCard, UserTable},
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
      let token = await this.$auth.getTokenSilently()
      this.userList = await getUsersSearch(this.searchParam, token)
      this.isLoading = false;
    }
  }
}
</script>

<style scoped>

</style>
