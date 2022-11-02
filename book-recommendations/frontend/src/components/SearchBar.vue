<template>
  <div class="div_center d-flex align-center">
    <v-text-field
      :v-model="searchTerm"
      clearable
      type="String"
      @input.native="setAuthorName"
    />
    <v-btn
      text
      :style="{ 'background-color': '#46648c', 'color': 'white' }"
      @click.native="loadSearch(authorName)"
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
    authorName: ""
  }),
  methods: {
    setAuthorName(author) {
      this.authorName = author.target.value
    },
    loadSearch(searchTerm) {
      this.$router.push({name: 'search', params: {searchTerm: searchTerm}}).catch(() => {
      })
      window.location.reload()
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
