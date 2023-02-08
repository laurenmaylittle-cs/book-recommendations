<template>
  <div>
    <v-card>
      <v-card-actions class="justify-center mb-0 pt-6">
        <router-link
          :to="{ name: 'book', params: {isbn:isbn}, query: {title: title, authors: authors}}"
        >
          <!-- TODO BES-66 redirect to view book page-->
          <v-img
            class="rounded mb-0"
            :lazy-src="thumbnail"
            height="192px"
            width="128px"
            :src="thumbnail"
          />
        </router-link>
      </v-card-actions>
      <v-card-text class="mt-0">
        <div class="text-subtitle-2 text--primary mt-0">
          {{ truncateText(title, 30) }}
        </div>
        <div class="text-subtitle-2 font-italic font-size-small">
          {{ getTruncatedAuthor }}
        </div>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>

export default {
  name: 'BookDetails',
  props: {
    title: {
      type: String,
      default: 'Book title'
    },
    authors: {
      type: String,
      default: 'Author'
    },
    publishedDate: {
      type: String,
      default: 'Published date'
    },
    thumbnail: {
      type: String,
      default: 'https://storage.googleapis.com/du-prd/books/images/9781538719824.jpg'
    },
    isbn: {
      type: String,
      required: true
    }
  },
  computed: {
    getTruncatedAuthor() {
      if (this.authors === "") {
        return this.formatDate()
      }
      return `${this.truncateText(this.authors, 50)} - ${this.formatDate()}`
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

/deep/ .v-card {
  width: 250px;
  height: 350px;
}
</style>
