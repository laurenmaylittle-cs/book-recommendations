<template>
  <v-container>
    <v-btn
      v-if="!isLoading"
      v-model="editFlag"
      color="primary"
      class="ma-7"
      @click="editBookshelf"
    >
      {{ editBtnText }}
    </v-btn>
    <v-btn
      v-if="editFlag"
      :disabled="!isAnyBookSelected"
      color="secondary"
      class="ma-7 white--text"
      @click="openDeleteDialog"
    >
      Remove from collection
    </v-btn>
    <v-dialog
      v-model="deleteDialog"
      max-width="420"
      @click:outside="closeDeleteDialog"
    >
      <v-card>
        <v-form
          @submit="deleteBooks"
        >
          <v-card-title>
            Remove from collection
          </v-card-title>
          <v-card-text>
            Are you sure you want to remove the following books?
            <v-list dense>
              <v-list-item
                v-for="(title, index) in booksSelectedITitle"
                :key="index"
              >
                <v-list-item-icon>
                  <v-icon>mdi-circle-small</v-icon>
                </v-list-item-icon>
                <v-list-item-content>
                  {{ title }}
                </v-list-item-content>
              </v-list-item>
            </v-list>
          </v-card-text>
          <v-card-actions>
            <v-btn
              color="#DC143C"
              class="ma-7 white--text"
              @click="deleteBooks"
            >
              Remove from collection
              <v-progress-circular
                v-if="deleteInProgress"
                indeterminate
                color="secondary"
              />
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
        :cols="getNumberOfColumns"
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
  name: "IndividualBookshelfPage",
  components: {BookDetails},
  data: function () {
    return {
      collectionId: "",
      collectionTitle: "",
      previousCollectionId: "",
      isLoading: true,
      previousBookData: null,
      loadCollectionBooksEmitted: false,
      collectionBooks: [],
      booksSelectedIsbn: [],
      booksSelectedITitle: [],
      isAnyBookSelected: false,
      editFlag: false,
      deleteDialog: false,
      editBtnText: "Edit",
      deleteInProgress: false,
    }
  },
  computed: {
    getNumberOfColumns() {
      const breakpointValues = {
        xl: 2,
        lg: 3,
        md: 4,
        xs: 12
      };
      const breakpoint = Object.keys(breakpointValues).find(
        breakpoint => this.$vuetify.breakpoint[breakpoint]);

      return breakpointValues[breakpoint] || 5;
    }
  },
  async activated() {
    EventBus.$on("load-collection-books", this.entryMethod);

    await this.$nextTick()

    if (this.loadCollectionBooksEmitted === false) {
      this.collectionId = this.previousCollectionId;
      this.collectionBooks = this.previousBookData;
      this.updatePageTitle();
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
    this.booksSelectedIsbn = [];
    this.booksSelectedITitle = [];
    this.editBtnText = "Edit"
    this.checkIfBooksSelected();
    EventBus.$off("load-collection-books");
  },
  methods: {
    async entryMethod(collectionData) {
      this.loadCollectionBooksEmitted = true;
      this.collectionTitle = collectionData.collectionName;
      this.collectionId = collectionData.collectionId;
      this.updatePageTitle();
      await this.getBooksInCollection()
    },
    async getBooksInCollection() {
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
    bookSelected(isbn, title) {
      this.booksSelectedIsbn.push(isbn);
      this.booksSelectedITitle.push(title);
      this.checkIfBooksSelected();
    },
    bookUnselected(isbn, title) {
      const indexIsbn = this.booksSelectedIsbn.indexOf(isbn);
      if (indexIsbn !== -1) {
        this.booksSelectedIsbn.splice(indexIsbn, 1);
      }

      const indexTitle = this.booksSelectedITitle.indexOf(title);
      if (indexTitle !== -1) {
        this.booksSelectedITitle.splice(indexTitle, 1);
      }

      this.checkIfBooksSelected();
    },
    checkIfBooksSelected() {
      this.isAnyBookSelected = this.booksSelectedIsbn.length > 0;
    },
    async deleteBooks() {
      if (this.booksSelectedIsbn.length === 0) {
        return;
      }
      this.deleteInProgress = true;
      const deleteBooksParams = new URLSearchParams(
        {bookshelfId: this.collectionId, bookIds: this.booksSelectedIsbn});
      this.collectionBooks = await deleteBooksInCollection(deleteBooksParams,
        await this.$auth.getTokenSilently());
      this.booksSelectedIsbn = [];
      this.booksSelectedITitle = [];
      this.closeDeleteDialog();
      this.checkIfBooksSelected();
      this.deleteInProgress = false;
    },
    editBookshelf() {
      this.editFlag = !this.editFlag;
      if (this.editFlag) {
        this.editBtnText = "Done";
      } else {
        this.editBtnText = "Edit";
      }

    },
    openDeleteDialog() {
      this.deleteDialog = true;
    },
    closeDeleteDialog() {
      this.deleteDialog = false;
    },
    updatePageTitle() {
      document.title = `${this.collectionTitle} - Books`
    },
  }
}
</script>
<style scoped>
.v-list .v-list-item {
  padding: 0;
}

.v-list .v-list-item__icon {
  margin-right: 4px;
}
</style>
