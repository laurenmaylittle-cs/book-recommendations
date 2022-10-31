<template>
  <div>
    <p>{{ getNumberOfResults() }} for "{{ searchTerm }}"</p>
    <section
      v-for="item in searchResults"
      :key="item.id"
      class="layout"
    >
      <search-book-thumbnail :thumbnail="item.imageLinks.smallThumbnail" />
      <book-details
        :author="item.authors[0]"
        :title="item.title"
        :published-date="item.publishedDate"
      />
    </section>
  </div>
</template>

<script>
import {searchByAuthor} from "@/api/search";
import SearchBookThumbnail from "@/components/SearchBookThumbnail";
import BookDetails from "@/components/BookDetails";

export default {
  name: "SearchView",
  components: {BookDetails, SearchBookThumbnail},
  data: function () {
    return {
      searchResults: [],
      searchTerm: this.$route.params.searchTerm
    }
  },
  mounted() {
    this.searchByAuthor(this.searchTerm)
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
    }
  }
}
</script>

<style scoped>
p {
  margin-left: 10px;
  margin-top: 10px;
}
</style>
