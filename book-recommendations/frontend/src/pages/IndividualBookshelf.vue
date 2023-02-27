<template>
  <v-container>
    <v-btn
      v-if="isAnyBookSelected"
      color="#DC143C"
      class="ma-7 white--text"
      @click="deleteBooks"
    >
      Delete from bookshelf
    </v-btn>
    <v-checkbox
      v-model="marvanFlag"
    />
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
          origin="other"
          :author="book.author"
          :title="book.title"
          :thumbnail="book.thumbnail"
          :isbn="book.isbn"
          :published-date="book.publishedDate"
          :book-data="book"
          :selectable="marvanFlag"
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
import {EventBus} from "@/event-bus";

export default {
  name: "IndividualBookshelf",
  components: {BookDetails},
  data: function () {
    return {
      collectionId: "",
      isLoading: true,
      previousBookData: null,
      loadCollectionBooksEmitted: false,
      collectionBooks: [],
      booksSelected: [],
      isAnyBookSelected: false,
      marvanFlag: false
    }
  },
  async activated() {
    EventBus.$on("load-collection-books", this.getBooksInCollection);

    await this.$nextTick()

    if (this.loadCollectionBooksEmitted === false) {
      this.collectionBooks = this.previousBookData;
      this.isLoading = false;
    }
  },
  deactivated() {
    this.collectionId = "";
    this.isLoading = true;
    this.loadCollectionBooksEmitted = false;
    this.previousBookData = this.collectionBooks;
    this.collectionBooks = [];
    this.booksSelected = [];
    this.checkIfBooksSelected();
    EventBus.$off("load-collection-books");
  },
  methods: {
    async getBooksInCollection(collectionId) {
      this.loadCollectionBooksEmitted = true;
      this.collectionId = collectionId;
      this.collectionBooks = await getBooksInCollection(this.collectionId,
        await this.$auth.getTokenSilently())
      this.isLoading = false;
    },
    checkForMultipleAuthors(authors) {
      if (!authors || authors.length === 0) {
        return '';
      }

      return authors.reduce((acc, curr, index) => {
        return index === 0 ? curr : `${acc}, ${curr}`;
      }, '')
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
      const deleteBooksParams = new URLSearchParams({bookshelfId: this.collectionId, bookIds: this.booksSelected});
      console.log(deleteBooksParams.toString())
      await deleteBooksInCollection(deleteBooksParams, await this.$auth.getTokenSilently());
      this.booksSelected = []
      this.checkIfBooksSelected();
      await this.getBooksInCollection(this.collectionId);
    }
  }
}
</script>
