<template>
  <div>
    <v-card
      :class="{ 'selected': selected }"
      @click="changeSelected"
    >
      <v-card-actions class="justify-center mb-0 pt-6">
        <router-link
          :to="{ name: 'book', params: {isbn:isbn}}"
        >
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
    author: {
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
    },
    selectable: {
      type: Boolean,
      required: false,
      default: false
    }
  },
  data() {
    return {
      selected: false,
    };
  },
  computed: {
    getTruncatedAuthor() {
      if (this.author === "") {
        return this.formatDate()
      }
      return `${this.truncateText(this.author, 50)} - ${this.formatDate()}`
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
    },
    changeSelected() {
      if (this.selectable) {
        if (this.selected) {
          this.selected = false;
          this.$emit("unselected", this.isbn);
        } else {
          this.selected = true;
          this.$emit("selected", this.isbn);
        }
      }
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
.selected {
  /* !important to make sure that style take precedence and is not override by scoped styles */
  background-color: Khaki !important;
}
</style>
