<template>
  <div class="div_center d-flex align-center">
    <v-select
      class="ml-3"
      style="width: 90px"
      v-model="selectedQueryFilter"
      :items="queryFilters"
      item-text="displayText"
      item-value="value"
      return-object
      single-line
    ></v-select>
    <v-text-field
      v-model="queryTerm"
      :label="getSearchTypeDescription"
      clearable
      type="String"
      @keyup.enter="loadSearch(queryTerm)"
    />
    <v-btn
      text
      :style="{ 'background-color': '#46648c', 'color': 'white' }"
      @click.native="loadSearch(queryTerm)"
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
    queryTerm: "",
    selectedQueryFilter: { displayText: 'Title', value: 'title'},
    queryFilters: [
      {displayText: 'Title', value: 'title'},
      {displayText: 'Author', value: 'author'}
    ],
  }),
  methods: {
    loadSearch() {
      this.$router.push({name: 'search', params: {searchType: this.selectedQueryFilter.value , searchTerm: this.queryTerm}}).catch(() => {
      })
      window.location.reload()
    }
  },
  computed: {
    getSearchTypeDescription() {
      return `Search by ${this.selectedQueryFilter.displayText}`
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
