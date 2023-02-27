<template>
  <v-container>
    <v-btn
      v-model="editFlag"
      color="primary"
      class="ma-7"
      @click="editBookshelf"
    >
      Edit
    </v-btn>
    <v-btn
      v-if="editFlag"
      :disabled="!isAnyBookSelected"
      color="secondary"
      class="ma-7 white--text"
      @click="openDeleteDialog"
    >
      Delete from bookshelf
    </v-btn>
    <v-dialog
      v-model="deleteDialog"
      max-width="400"
      @click:outside="closeDeleteDialog"
    >
      <v-card>
        <v-form
          @submit="deleteBooks"
        >
          <v-card-actions>
            <v-btn
              color="#DC143C"
              class="ma-7 white--text"
              @click="deleteBooks"
            >
              Remove from collection
            </v-btn>
            <v-btn
              color="secondary"
              text
              @click="closeDeleteDialog"
            >
              Cancel
            </v-btn>
          </v-card-actions>
        </v-form>
      </v-card>
    </v-dialog>
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
          :selectable="editFlag"
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
      previousCollectionId: "",
      isLoading: true,
      previousBookData: null,
      loadCollectionBooksEmitted: false,
      collectionBooks: [],
      booksSelected: [],
      isAnyBookSelected: false,
      editFlag: false,
      deleteDialog: false
    }
  },
  async activated() {
    EventBus.$on("load-collection-books", this.getBooksInCollection);

    await this.$nextTick()

    if (this.loadCollectionBooksEmitted === false) {
      this.collectionId = this.previousCollectionId;
      this.collectionBooks = this.previousBookData;
      this.isLoading = false;
    }
  },
  deactivated() {
    this.editFlag = false;
    this.previousCollectionId = this.collectionId;
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
      this.collectionBooks = await getBooksInCollection(
        this.collectionId,
        await this.$auth.getTokenSilently()
      )
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
    },
    bookUnselected(isbn) {
      const index = this.booksSelected.indexOf(isbn);
      if (index !== -1) {
        this.booksSelected.splice(index, 1);
      }
      this.checkIfBooksSelected();
    },
    checkIfBooksSelected() {
      this.isAnyBookSelected = this.booksSelected.length > 0;
    },
    async deleteBooks() {
      const deleteBooksParams = new URLSearchParams({bookshelfId: this.collectionId, bookIds: this.booksSelected});
      await deleteBooksInCollection(deleteBooksParams, await this.$auth.getTokenSilently());
      this.booksSelected = []
      this.checkIfBooksSelected();
      await this.getBooksInCollection(this.collectionId);
    },
    editBookshelf() {
      this.editFlag = !this.editFlag;
    },
    openDeleteDialog() {
      this.deleteDialog = true;
    },
    closeDeleteDialog() {
      this.deleteDialog = false;
    }
  }
}
</script>
