<template>
  <v-container
    fill-height
  >
    <v-row
      v-if="isLoading"
      class="justify-center"
    >
      <v-progress-circular
        :size="70"
        :width="7"
        color="primary"
        indeterminate
      />
    </v-row>
    <v-row
      v-if="bookData === null && !isLoading"
      class="pt-6"
    >
      <p>
        {{ errorMessage }}
      </p>
    </v-row>
    <v-row
      v-if="!isLoading && bookData !== null"
      class="pb-0 pt-0 align-center"
    >
      <v-col class="ml-4 pt-4">
        <!-- TODO BES-70 show all authors and genres depending on the data returned from the API -->
        <h1
          v-if="bookData.title"
          class="mt-0"
        >
          {{ bookData.title }}
        </h1>
        <div
          v-if="bookData.authors"
        >
          <div
            v-for="(author, index) in bookData.authors"
            :key="index"
          >
            <h2 v-if="index === 0 && bookData.authors.length === 1">
              By
              <a @click="emitAuthorSearch(author)">
                {{ author }}
              </a>
            </h2>
            <h2 v-else-if="index === 0">
              By
              <a @click="emitAuthorSearch(author)">
                {{ author }},
              </a>
            </h2>
            <h2 v-else-if="index === bookData.authors.length -1">
              <a @click="emitAuthorSearch(author)">
                {{ author }}
              </a>
            </h2>
            <h2 v-else>
              <a @click="emitAuthorSearch(author)">
                {{ author }},
              </a>
            </h2>
          </div>
        </div>
        <average-ratings
          :id="bookData.id"
          :rating="bookData.averageRating ? parseInt(bookData.averageRating) : 0"
          heading="Average rating"
          :ratings-count="bookData.ratingsCount ? parseInt(bookData.ratingsCount) : 0"
        />
        <template v-if="!$auth.isAuthenticated">
          <v-alert
            outlined
            type="info"
            :width="$vuetify.breakpoint.xs ? '100%' : '75%'"
          >
            Log in to add your own ratings and manage collections
          </v-alert>
        </template>

        <template v-if="$auth.isAuthenticated">
          <template v-if="!ratingsLoaded && !collectionsLoaded">
            <v-progress-circular
              :size="30"
              :width="7"
              color="secondary"
              indeterminate
            />
          </template>
          <user-ratings
            :isbn="
              isbn.toString()"
            @user-rating-loaded="ratingsLoaded = true"
          />
          <book-collections
            :book-isbn="isbn.toString()"
            :book-data="bookData"
            @collections-loaded="collectionsLoaded = true"
          />
        </template>
      </v-col>
      <view-book-thumbnail :thumbnail="bookData.imageLinks.thumbnail" />
    </v-row>
    <v-row
      v-if="!isLoading && bookData !== null"
      class="pt-0 ma-0 align-center"
    >
      <about-book
        :category="concatDetails(bookData.categories)"
        :published-date="bookData.publishedDate"
        :original-description="bookData.description"
        :pages="bookData.pageCount"
        :publisher="bookData.publisher"
      />
    </v-row>
  </v-container>
</template>

<script>
import ViewBookThumbnail from "@/components/viewbook/ViewBookThumbnail";
import {getBookInfo} from "@/api/view-book";
import AverageRatings from "@/components/viewbook/AverageRatings";
import UserRatings from "@/components/viewbook/UserRatings";
import AboutBook from "@/components/viewbook/AboutBook";
import {EventBus} from "@/event-bus";
import BookCollections from "@/components/viewbook/BookCollections.vue";

export default {
  name: 'ViewBook',
  components: {
    BookCollections,
    AboutBook,
    UserRatings,
    AverageRatings,
    ViewBookThumbnail
  },
  data: function () {
    return {
      bookData: null,
      isbn: "",
      isLoading: false,
      previousBookData: null,
      viewBookEmitted: false,
      ratingsLoaded: false,
      collectionsLoaded: false,
    }
  },
  computed: {
    errorMessage() {
      return "No results found for ISBN: " + this.isbn
    },
  },
  async activated() {
    //view-book - from search results
    //view-book-other - from any other origin (home, collections - will perform a get request to get this data with isbn and title)
    //search-triggered - search by isbn
    EventBus.$on('view-book', this.populateBookData);
    EventBus.$on(['view-book-other', 'search-triggered'], this.getBookData);

    await this.$nextTick();

    if (this.viewBookEmitted === false) {
      this.bookData = this.previousBookData;
      this.isLoading = false;
    }
  },
  deactivated() {
    this.previousBookData = this.bookData;
    this.bookData = null;
    this.isLoading = false;
    this.viewBookEmitted = false;
    this.collectionsLoaded = false;
    this.ratingsLoaded = false;
    EventBus.$off(['search-triggered', 'view-book', 'view-book-other']);
  },
  methods: {
    populateBookData(bookData) {
      this.viewBookEmitted = true;
      if (bookData) {
        this.bookData = bookData;
        this.isbn = bookData.isbn;
      }

      this.isLoading = false;
    },
    async getBookData(queryData) {
      this.isLoading = true;
      this.viewBookEmitted = true;
      this.isbn = queryData.isbn || queryData.searchTerm; //when coming from SearchBar, isbn is added to searchTerm property
      if (!this.validateIsbn(this.isbn)) {
        this.bookData = null;
        this.isLoading = false;
        return;
      }
      try {
        this.bookData = await getBookInfo(this.isbn, queryData.title, queryData.authors);
        this.isLoading = false;
      } catch (error) {
        this.isLoading = false;
      }
    },
    validateIsbn(isbn) {
      return isbn.length === 10 || isbn.length === 13;
    },
    async emitAuthorSearch(author) {
      await this.$router.push({name: 'search'});
      EventBus.$emit('search-triggered', {
        searchType: 'author',
        searchTerm: author
      });
    },
    concatDetails(details) {
      if (!details) {
        return null;
      }
      if (details.length > 1) {
        return details.join(', ');
      }
      return details.toString();
    }
  }
}
</script>

