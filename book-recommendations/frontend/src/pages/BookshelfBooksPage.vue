<template>
  <v-container>
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
        />
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
import BookDetails from "@/components/search/BookDetails";
import {getBooksInCollection} from "@/api/bookshelfBooks";
import {EventBus} from "@/event-bus";

export default {
  name: "CollectionBooksPage",
  components: {BookDetails},
  data: function () {
    return {
      collectionId: "",
      collectionTitle: "",
      isLoading: true,
      previousBookData: null,
      collectionBooks: [],
      loadCollectionBooksEmitted: false,
    }
  },
  async activated() {
    EventBus.$on("load-collection-books", this.getBooksInCollection);

    await this.$nextTick()

    if (this.loadCollectionBooksEmitted === false) {
      this.collectionBooks = this.previousBookData;
      this.updatePageTitle();
      this.isLoading = false;
    }
  },
  deactivated() {
    this.collectionId = "";
    this.isLoading = true;
    this.loadCollectionBooksEmitted = false;
    this.previousBookData = this.collectionBooks;
    this.collectionBooks = [];
    EventBus.$off("load-collection-books");
  },
  methods: {
    async getBooksInCollection(collectionData) {
      this.collectionTitle = collectionData.collectionName;
      this.updatePageTitle();
      this.loadCollectionBooksEmitted = true;
      this.collectionId = collectionData.collectionId;
      this.collectionBooks = await getBooksInCollection(this.collectionId,
        await this.$auth.getTokenSilently())
      this.isLoading = false;
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
    updatePageTitle() {
      document.title = `${this.collectionTitle} - Books`
    },
  }
}
</script>
