<template>
  <v-col>
    <router-link
      :to="{ name: 'book', params: {isbn:isbn},
             query: {title: bookTitle, image: bookImageLink, authors: authors}}"
      style="text-decoration: none"
    >
      <v-hover v-slot="{ hover }">
        <v-sheet
          :color="getHoverEffect(hover)"
          class="d-flex flex-column align-center justify-center"
          :elevation="hover ? 12 : 0"
          rounded
        >
          <v-img
            class="rounded"
            max-width="200"
            max-height="300"
            :src="bookImageLink"
          />
          <div class="flex-wrap text-body-2 mt-1">
            {{ getUpdatedTitle(bookTitle) }}
          </div>
        </v-sheet>
      </v-hover>
    </router-link>
  </v-col>
</template>

<script>
import {titleCase} from "title-case";

export default {
  name: "BookItem",
  props: {
    bookImageLink: {
      type: String,
      required: true,
      default: "Unavailable",
    },
    authors: {
      type: Array,
      required: true,
      default: () => [],
    },
    bookTitle: {
      type: String,
      required: true,
      default: "Unavailable",
    },
    isbn: {
      type: String,
      required: true
    }
  },
  methods: {
    getUpdatedTitle(title) {
      return titleCase(title.toLowerCase());
    },
    getHoverEffect(hover) {
      return hover ? "blue-grey lighten-4" : "transparent";
    },
  }
}
</script>
