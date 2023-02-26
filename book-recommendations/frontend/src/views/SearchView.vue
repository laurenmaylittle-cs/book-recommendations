<template>
  <v-container>
    <v-row
      v-if="isLoading"
      class="justify-center pt-10"
    >
      <v-progress-circular
        :size="70"
        :width="7"
        color="primary"
        indeterminate
      />
    </v-row>
    <v-row
      v-else
      class="pt-6"
    >
      <p>
        {{ getNumberOfResults }}
      </p>
    </v-row>
    <v-row no-gutters>
      <v-col
        v-for="book in searchResults"
        :key="book.isbn"
      >
        <book-details
          origin="search"
          :authors="checkForMultipleAuthors(book.authors)"
          :title="book.title"
          :published-date="book.publishedDate"
          :thumbnail="book.imageLinks.thumbnail"
          :isbn="book.isbn"
          :book-data="book"
        />
      </v-col>
    </v-row>
    <v-row
      v-if="!isLoading"
      class="justify-center"
    >
      <v-btn
        v-if="previousPageAvailable"
        class="mx-2"
        fab
        dark
        color="primary"
        @click="previousPage"
      >
        <v-icon dark>
          mdi-chevron-left
        </v-icon>
      </v-btn>
      <v-btn
        v-if="nextPageAvailable"
        class="mx-2"
        fab
        dark
        color="primary"
        @click="nextPage"
      >
        <v-icon dark>
          mdi-chevron-right
        </v-icon>
      </v-btn>
    </v-row>
  </v-container>
</template>

<script>
import {searchByAuthor, searchByTitle} from "@/api/search";
import BookDetails from "@/components/search/BookDetails";
import {EventBus} from "@/event-bus";

export default {
  name: "SearchView",
  components: {BookDetails},
  data: function () {
    return {
      searchResults: [],
      nextSearchResults: [],
      searchTerm: "",
      searchType: "",
      isLoading: true,
      nextPageAvailable: true,
      previousPageAvailable: false,
      currentStartIndex: 0,
      numberOfItemsPerPage: 40
    }
  },
  computed: {
    getNumberOfResults() {
      const numberOfResults = this.searchResults.length;
      if (numberOfResults > 1) {
        return `Showing ${this.currentStartIndex} to ${(this.currentStartIndex
          + numberOfResults)} results for ${this.searchTerm}`
      } else if (numberOfResults === 1) {
        return `Showing ${numberOfResults} result for ${this.searchTerm}`
      }
      return `There are no results for ${this.searchTerm}`
    },
  },
  activated() {
    EventBus.$on('search-triggered', this.performSearch);
    this._updatePageTitle();
  },
  deactivated() {
    EventBus.$off('search-triggered')
  },
  methods: {
    async performSearch(queryDetails) {
      this._resetAndUpdateSearchData(queryDetails);
      this._updatePageTitle();
      this.isLoading = true;

      if (queryDetails.searchType === "title") {
        await this.searchByTitle(queryDetails.searchTerm, this.currentStartIndex);
      } else {
        await this.searchByAuthor(queryDetails.searchTerm, this.currentStartIndex);
      }

      this.nextPageAvailable = this.searchResults.length >= this.numberOfItemsPerPage;
      this.isLoading = false;
    },
    _resetAndUpdateSearchData(queryDetails) {
      this.searchResults = [];
      this.nextSearchResults = [];
      this.searchTerm = queryDetails.searchTerm;
      this.searchType = queryDetails.searchType;
    },
    _updatePageTitle() {
      document.title = `Search results for ${this.searchTerm}`;
    },
    async searchByAuthor(author, startIndex) {
      this.searchResults = await searchByAuthor(author, startIndex)
    },
    async searchByTitle(title, startIndex) {
      this.searchResults = await searchByTitle(title, startIndex)
    },
    async previousPage() {
      this.currentStartIndex = this.currentStartIndex - this.numberOfItemsPerPage
      if (this.searchType === "title") {
        await this.searchByTitle(this.searchTerm, this.currentStartIndex)
      } else {
        await this.searchByAuthor(this.searchTerm, this.currentStartIndex)
      }

      if (this.currentStartIndex === 0) {
        this.previousPageAvailable = false
      }
      document.body.scrollIntoView()
    },
    async nextPage() {
      this.currentStartIndex = this.currentStartIndex + this.numberOfItemsPerPage

      if (this.searchType === "title") {
        this.nextSearchResults = await this.searchByTitle(this.searchTerm, this.currentStartIndex)
      } else {
        this.nextSearchResults = await this.searchByAuthor(this.searchTerm, this.currentStartIndex)
      }
      this.previousPageAvailable = this.currentStartIndex > 0

      if (this.nextSearchResults.length !== this.numberOfItemsPerPage) {
        this.nextPageAvailable = false
      } else {
        this.nextPageAvailable = true
        this.searchResults = this.nextSearchResults
      }

      document.body.scrollIntoView()
    },
    checkForMultipleAuthors(authors) {
      if (authors === undefined || authors === null) {
        return ""
      }
      let authorList = "";
      for (let i = 0; i < authors.length; i++) {
        authorList === "" ? authorList = authors[i] : authorList = authorList + ", " + authors[i]
      }
      return authorList
    }
  }
}
</script>

<style scoped>
section {
  display: grid;
}
</style>
