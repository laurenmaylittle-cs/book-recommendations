<template>
  <div class="div_center d-flex align-center">
    <v-text-field
      v-model="searchParam"
      label="Search by author"
      clearable
      type="String"
      @keyup.enter="loadSearch(searchParam)"
    />
    <v-btn
      text
      :style="{ 'background-color': '#46648c', 'color': 'white' }"
      @click.native="loadSearch(searchParam)"
    >
      <v-icon>mdi-magnify</v-icon>
    </v-btn>
  </div>
</template>

<script>
export default {
  name: "SearchBar",
  props: {
    searchTerm: {
      type: String,
      default: ""
    }
  },
  data: () => ({
    searchParam: ""
  }),
  methods: {
    loadSearch() {
      this.$router.push({name: 'search', params: {searchTerm: this.searchParam}}).catch(() => {
      })
      window.location.reload()
    }
  }
}
</script>

<style scoped>
.div_center {
  display: table;
  margin: 0 auto;
}
</style>
