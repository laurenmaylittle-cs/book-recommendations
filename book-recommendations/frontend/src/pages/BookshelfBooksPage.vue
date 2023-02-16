<template>
  <v-container>
    <v-row
      v-if="isAnyBookSelected"
    >
      <v-col>
        <v-btn
          color="error"
          class="pe2"
          @click="deleteBooks"
        >
          Delete from bookshelf
        </v-btn>
      </v-col>
    </v-row>
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
    <v-row no-gutters>
      <v-col
        v-for="book in collectionBooks"
        :key="book.title"
      >
        <book-details
          :author="book.author"
          :title="book.title"
          :thumbnail="book.thumbnail"
          :isbn="book.isbn"
          :published-date="book.publishedDate"
          :selectable="true"
          @selected="bookSelected"
          @unselected="bookUnselected"
        />
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
import BookDetails from "@/components/search/BookDetails";
import {deleteBooksInCollection, getBooksInCollection} from "@/api/bookshelfBooks";

export default {
  name: "CollectionBooksPage",
  components: {BookDetails},
  data: function () {
    return {
      collectionId: this.$route.params.collectionId,
      isLoading: true,
      collectionBooks: [],
      booksSelected: [],
      isAnyBookSelected: false
    }
  },
  async mounted() {
    await this.getBooksInCollection();
    this.isLoading = false;
  },
  methods: {
    async getBooksInCollection() {
      this.collectionBooks = await getBooksInCollection(this.collectionId,
        await this.$auth.getTokenSilently())
    },
    checkForMultipleAuthors(authors) {
      if (authors === undefined || authors === null) {
        return ""
      }
      let authorList = "";
      for (let i = 0; i < authors.length; i++) {
        authorList === "" ? authorList = authors[i] : authorList = authorList + ", " + authors[i]
      }
      return authorList
    },
    bookSelected(isbn) {
      this.booksSelected.push(isbn);
      this.checkIfBooksSelected();
      console.log(this.booksSelected.toString());
    },
    bookUnselected(isbn) {
      const index = this.booksSelected.indexOf(isbn);
      if (index !== -1) {
        this.booksSelected.splice(index, 1);
        console.log(this.booksSelected.toString());
      }
      this.checkIfBooksSelected();
    },
    checkIfBooksSelected() {
      this.isAnyBookSelected = this.booksSelected.length > 0;
    },
    async deleteBooks() {
      await deleteBooksInCollection(this.collectionId,
        await this.$auth.getTokenSilently(),
        this.booksSelected)
    }
  }
}
</script>
