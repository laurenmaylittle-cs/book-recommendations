<template>
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
</template>

<script>
export default {
  name: "FindFriendsView",
  data: () => ({
    searchParam: ""
  }),
  methods: {
    loadSearch() {
      //call search api
    }
  }
}
</script>

<style scoped>

</style>
