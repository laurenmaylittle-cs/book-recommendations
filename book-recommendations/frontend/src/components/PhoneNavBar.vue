<template>
  <v-app-bar
    app
    color="#46648c"
    dark
  >
    <v-menu
      left
      bottom
    >
      <template #activator="{ on, attrs }">
        <v-btn
          icon
          v-bind="attrs"
          v-on="on"
        >
          <v-icon>mdi-menu</v-icon>
        </v-btn>
      </template>

      <v-list>
        <v-list-item
          @click="homeRedirect"
        >
          <v-icon>mdi-home</v-icon>
          Home
        </v-list-item>
        <v-list-item
          @click="() => {}"
        >
          <v-icon>mdi-bookshelf</v-icon>
          Bookshelf
        </v-list-item>
        <v-list-item
          @click="profileRedirect"
        >
          <v-icon>mdi-account</v-icon>
          Profile
        </v-list-item>
      </v-list>
    </v-menu>
    <search-bar />
  </v-app-bar>
</template>

<script>

import SearchBar from "@/components/search/SearchBar";

export default {
  name: 'PhoneNavBar',
  components: {SearchBar},
  methods: {
    homeRedirect() {
      this.$router.push({name: 'Root'}).catch(() => {
      })
    },
    profileRedirect() {
      this.$router.push({name: 'profile'}).catch(() => {
      })
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
</style>
