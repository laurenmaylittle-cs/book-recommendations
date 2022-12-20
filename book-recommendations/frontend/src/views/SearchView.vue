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
        {{ getNumberOfResults() }}
      </p>
    </v-row>
    <v-row no-gutters>
      <v-col
        v-for="book in searchResults"
        :key="book.title"
      >
        <book-details
          :author="checkForMultipleAuthors(book.authors)"
          :title="book.title"
          :published-date="book.publishedDate"
          :thumbnail="book.imageLinks.thumbnail"
        />
      </v-col>
    </v-row>
    <v-row v-if="!isLoading">
      <v-btn
        v-if="isPreviousPage"
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
      <v-spacer />
      <v-btn
        v-if="isNextPage"
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
import {searchByAuthor} from "@/api/search";
import BookDetails from "@/components/search/BookDetails";

export default {
  name: "SearchView",
  components: {BookDetails},
  data: function () {
    return {
      searchResults: [],
      nextSearchResults: [],
      searchTerm: this.$route.params.searchTerm,
      isLoading: true,
      isNextPage: true,
      isPreviousPage: false,
      currentStartIndex: 0
    }
  },
  async mounted() {
    await this.searchByAuthor(this.searchTerm, this.currentStartIndex)

    if (this.searchResults.length < 40) {
      this.isNextPage = false
    }
    this.isLoading = false
  },
  methods: {
    async searchByAuthor(author, startIndex) {
      this.searchResults = await searchByAuthor(author, startIndex)
    },
    async previousPage() {
      this.currentStartIndex = this.currentStartIndex - 40
      await this.searchByAuthor(this.searchTerm, this.currentStartIndex)

      if (this.currentStartIndex === 0) {
        this.isPreviousPage = false
      }
      document.body.scrollIntoView()
    },
    async nextPage() {
      this.currentStartIndex = this.currentStartIndex + 40

      this.nextSearchResults = await searchByAuthor(this.searchTerm, this.currentStartIndex)
      this.isPreviousPage = this.currentStartIndex > 0

      if (this.nextSearchResults.length !== 40) {
        this.isNextPage = false
      } else {
        this.isNextPage = true
        this.searchResults = this.nextSearchResults
      }

      document.body.scrollIntoView()
    },
    getNumberOfResults() {
      const numberOfResults = this.searchResults.length
      if (numberOfResults > 1) {
        return "Showing " + this.currentStartIndex + " to " + (this.currentStartIndex
          + numberOfResults) + " results for " + this.searchTerm
      } else if (numberOfResults === 1) {
        return "Showing " + numberOfResults + " result for " + this.searchTerm
      }
      return "There are no results for " + this.searchTerm
    },
    checkForMultipleAuthors(authors) {
      if (authors === undefined || authors === null) {
        return ""
      }
      var authorList = "";
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
