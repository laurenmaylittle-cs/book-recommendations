<template>
  <div>
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
      <p>{{ getNumberOfResults() }} for "{{ searchTerm }}"</p>
    </v-row>
    <v-container>
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
  </div>
</template>

<script>
import {searchByAuthor} from "@/api/search";
import BookDetails from "@/components/BookDetails";

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
      console.log(author)
      this.searchResults = await searchByAuthor(author)
    },
    getNumberOfResults() {
      const numberOfResults = this.searchResults.length
      if (numberOfResults > 1) {
        return "Showing " + numberOfResults + " results"
      } else if (numberOfResults === 1) {
        return "Showing " + numberOfResults + " result"
      }
      return "There are no results"
    },
    checkForMultipleAuthors(authors) {
      if (authors === undefined) {
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
p {
  padding-left: 100px;
  margin-top: 10px;
}

section {
  display: grid;
}
</style>
