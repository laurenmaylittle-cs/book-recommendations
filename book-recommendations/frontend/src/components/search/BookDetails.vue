<template>
  <div>
    <v-card>
      <v-card-actions class="justify-center mb-0 pt-6">
        <a href="/">
          <v-img
            class="rounded"
            :lazy-src="thumbnail"
            max-height="192px"
            max-width="128px"
            :src="thumbnail"
          />
        </a>
      </v-card-actions>
      <v-card-text class="mt-0">
        {{ truncateText(title, 50) }}
        <h2>
          {{ truncateText(author, 50) }} - {{ formatDate() }}
        </h2>
        <h2 />
      </v-card-text>
    </v-card>
  </div>
</template>

<script>

export default {
  name: 'BookDetails',
  props: {
    title: {type: String, default: 'Book title'},
    author: {type: String, default: 'Author'},
    publishedDate: {type: String, default: 'Published date'},
    thumbnail: {
      type: String,
      default: 'https://storage.googleapis.com/du-prd/books/images/9781538719824.jpg'
    }
  },
  methods: {
    formatDate() {
      let dateToFormat = new Date(this.publishedDate)
      return dateToFormat.getFullYear();
    },
    truncateText(text, maxCharacterCount) {
      if (text.length > maxCharacterCount) {
        return text.substring(0, maxCharacterCount) + '...'
      }
      return text
    }
  }
}
</script>

<style scoped>
div {
  margin: 0 15px 15px 15px;
  width: auto;
  height: auto;
  font-size: small;
}

h2 {
  font-style: italic;
  font-size: small;
}

/deep/ .v-card {
  width: 250px;
  height: 350px;
}
</style>
