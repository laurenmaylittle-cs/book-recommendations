<template>
  <div class="div_center d-flex align-center">
    <v-select
      v-model="selectedQueryFilter"
      class="ml-3"
      style="width: 90px"
      :items="queryFilters"
      item-text="displayText"
      item-value="value"
      return-object
      single-line
    />
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
    selectedQueryFilter: { displayText: 'ISBN', value: 'isbn'},
    queryFilters: [
      {displayText: 'ISBN', value: 'isbn'},
      {displayText: 'Author', value: 'author'},
      {displayText: 'Title', value: 'title'}
    ],
  }),
  computed: {
    getSearchTypeDescription() {
      return `Search by ${this.selectedQueryFilter.displayText}`;
    }
  },
  methods: {
    loadSearch() {
      if (this.selectedQueryFilter.value !== 'isbn') {
        this.$router.push({name: 'search', params: {searchType: this.selectedQueryFilter.value , searchTerm: this.queryTerm}}).catch(() => {
        })
      } else {
        this.$router.push({name: 'book', params: {isbn: this.queryTerm}}).catch(() => {
        })
      }
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
