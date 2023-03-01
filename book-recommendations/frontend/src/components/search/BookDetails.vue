<template>
  <div>
    <v-hover
      v-slot="{ hover }"
      ref="hoverEffectRef"
    >
      <v-card
        :color="getHoverEffect(hover)"
        outlined
        @click="emitViewBook"
      >
        <div
          v-if="selectable"
          style="height:10px; display: flex; justify-content: flex-end"
        >
          <!--@click.stop stops the emitViewBook event from happening within the checkbox-->
          <v-checkbox
            :value="selected"
            @click.stop="changeSelected()"
          />
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
    </v-hover>
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
      return `${this.truncateText(this.authors, 30)} - ${this.formatDate()}`
    }
  },
  deactivated() {
    //clear the hover effect when navigating away from the page
    this.$refs.hoverEffectRef._data.isActive = false;
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
        this.selected = !this.selected;
        this.$emit(this.selected ? "selected" : "unselected", this.isbn, this.title);
      }
    },
    getHoverEffect(hover) {
      return hover ? "blue-grey lighten-4" : "#FFFFFF";
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
