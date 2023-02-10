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
      @click="loadSearch(queryTerm)"
    >
      <v-icon>mdi-magnify</v-icon>
    </v-btn>
  </div>
</template>

<script>
import {EventBus} from "@/event-bus";

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
      {displayText: 'Title', value: 'title'},
      {displayText: 'Author', value: 'author'},
      {displayText: 'ISBN', value: 'isbn'}
    ],
  }),
  computed: {
    getSearchTypeDescription() {
      return `Search by ${this.selectedQueryFilter.displayText}`;
    }
  },
  methods: {
    async loadSearch() {
      const routeName = this.selectedQueryFilter.value === 'isbn' ? 'book' : 'search';
      const emitSearchEvent = () => {
        EventBus.$emit('search-triggered', {
          searchType: this.selectedQueryFilter.value,
          searchTerm: this.queryTerm
        });
        this.queryTerm = '';
      };

      if (this.$route.name !== routeName) {
        await this.$router.push({name: routeName});
        emitSearchEvent();
      } else {
        emitSearchEvent();
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
