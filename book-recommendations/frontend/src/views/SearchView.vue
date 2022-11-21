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
    <div v-else>
      <v-row>
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
      <v-row
        class="pt-6 justify-center mb-5"
      >
        <v-pagination
          v-model="page"
          :length="6"
          @input="nextPage"
        />
      </v-row>
    </div>
  </v-container>
</template>

<script>
import {searchByAuthor, searchByAuthorWithPagination} from "@/api/search";
import BookDetails from "@/components/search/BookDetails";

export default {
  name: "SearchView",
  components: {BookDetails},
  data: function () {
    return {
      searchResults: [],
      searchTerm: this.$route.params.searchTerm,
      isLoading: true,
      page: 1,
      firstBookIndex: 1,
      lastBookIndex: 10
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
      const lastResultIndex = this.firstBookIndex + numberOfResults - 1
      if (numberOfResults >= 1) {
        return "Showing results " + this.firstBookIndex + " to " + lastResultIndex
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
    },
    async nextPage() {
      this.firstBookIndex = this.page * 10 - 9
      this.lastBookIndex = this.page * 10
      this.isLoading = true
      this.searchResults = await searchByAuthorWithPagination(this.searchTerm, this.firstBookIndex)
      this.isLoading = false
    }
  }
}
</script>

<style scoped>
section {
  display: grid;
}
</style>
