<template>
  <div class="div_center d-flex align-center">
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
          <v-icon>
            mdi-filter-menu-outline
          </v-icon>
        </v-btn>
      </template>
      <v-list>
        <v-list-item @click="updateSearchBar('title')">
          Title
        </v-list-item>
        <v-list-item @click="updateSearchBar('author')">
          Author
        </v-list-item>
        <v-list-item @click="updateSearchBar('isbn')">
          ISBN
        </v-list-item>
      </v-list>
    </v-menu>
    <v-col
      :cols="numberOfColsToTake"
      class="pa-0"
    >
      <v-text-field
        v-model="queryTerm"
        :label="getSearchTypeDescription"
        clearable
        type="String"
        @keyup.enter="loadSearch(queryTerm)"
      />
    </v-col>
    <v-btn
      text
      :style="{ 'background-color': '#46648c', 'color': 'white' }"
      class="pa-0"
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
    selectedQueryFilter: {displayText: 'Title', value: 'title'},
    queryFilters: [
      {displayText: 'ISBN', value: 'isbn'},
      {displayText: 'Author', value: 'author'},
      {displayText: 'Title', value: 'title'}
    ],
  }),
  computed: {
    getSearchTypeDescription() {
      if (this.selectedQueryFilter.value === "isbn") {
        return `Search by ${this.selectedQueryFilter.displayText}`;
      }
      return `Search by ${this.selectedQueryFilter.value}`;
    },
    numberOfColsToTake() {
      if (this.$vuetify.breakpoint.xs) {
        return 6;
      }
      return 9;
    }
  },
  methods: {
    loadSearch() {
      if (this.selectedQueryFilter.value !== 'isbn') {
        this.$router.push({
          name: 'search',
          params: {searchType: this.selectedQueryFilter.value, searchTerm: this.queryTerm}
        }).catch(() => {
        })
      } else {
        this.$router.push({name: 'book', params: {isbn: this.queryTerm}}).catch(() => {
        })
      }
      window.location.reload()
    },
    updateSearchBar(selectedQuery) {
      if (selectedQuery === "isbn") {
        this.selectedQueryFilter = this.queryFilters[0];
      } else if (selectedQuery === "author") {
        this.selectedQueryFilter = this.queryFilters[1];
      } else {
        this.selectedQueryFilter = this.queryFilters[2];
      }
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
