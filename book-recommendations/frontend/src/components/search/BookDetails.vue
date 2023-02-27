<template>
  <div>
    <v-card
      :class="{ 'selected': selected }"
      @click="emitViewBook"
    >
      <div
        v-if="selectable"
        style="margin: 0px"
        class="d-flex flex-column align-end"
      >
        <!--@click.stop stops the emitViewBook event from happening within the checkbox-->
        <v-checkbox @click.stop="changeSelected()" />
      </div>
      <v-card-actions class="justify-center mb-0 pt-6">
        <a @click="emitViewBook">
          <v-img
            class="rounded mb-0"
            :lazy-src="thumbnail"
            height="192px"
            width="128px"
            :src="thumbnail"
          />
        </a>
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
import {EventBus} from "@/event-bus";

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
    },
    origin: {
      type: String, // from which page the book is being viewed (effect how the book data is displayed on view book)
      required: true // either 'search' or 'other'
    },
    bookData: {
      type: Object,
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
    },
    async emitViewBook() {
      await this.$router.push({name: 'book'});
      switch (this.origin) {
        case 'search':
          EventBus.$emit('view-book', this.bookData);
          break;
        case 'other':
          EventBus.$emit('view-book-other', this.bookData);
          break;
      }

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
