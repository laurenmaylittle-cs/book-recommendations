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
      <v-list
        v-for="filter in queryFilters"
        :key="filter.value"
      >
        <v-list-item @click="selectedQueryFilter = filter">
          {{ filter.displayText }}
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
      {displayText: 'ISBN', value: 'isbn'},
      {displayText: 'Author', value: 'author'},
      {displayText: 'Title', value: 'title'}
    ],
  }),
  computed: {
    getSearchTypeDescription() {
      return `Search by ${this.selectedQueryFilter.displayText}`;
    },
    numberOfColsToTake() {
      if (this.$vuetify.breakpoint.xs) {
        return 6;
      }
      return 9;
    }
  },
  methods: {
    async loadSearch() {
      const routeName = this.selectedQueryFilter.value === 'isbn' ? 'book' : 'search';
      const emitSearchEvent = () => {
        // Remove hyphens and spaces from ISBN
        if (this.selectedQueryFilter.value === 'isbn') {
          this.queryTerm = this.queryTerm.replace(/[-\s]+/g, "");
        }
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
