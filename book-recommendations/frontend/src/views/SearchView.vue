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
          :thumbnail="book.imageLinks.smallThumbnail"
        />
      </v-col>
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
      searchTerm: this.$route.params.searchTerm,
      isLoading: true
    }
  },
  async mounted() {
    await this.searchByAuthor(this.searchTerm)
    this.isLoading = false
  },
  methods: {
    async searchByAuthor(author) {
      this.searchResults = await searchByAuthor(author)
    },
    getNumberOfResults() {
      const numberOfResults = this.searchResults.length
      if (numberOfResults > 1) {
        return "Showing " + numberOfResults + " results for " + this.searchTerm
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
