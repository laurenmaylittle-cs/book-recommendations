<template>
  <p>{{ searchTerm }}</p>
</template>

<script>
import {searchByAuthor} from "@/api/search";

export default {
  name: "SearchView",
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
    }
  }
}
</script>

<style scoped>

</style>
