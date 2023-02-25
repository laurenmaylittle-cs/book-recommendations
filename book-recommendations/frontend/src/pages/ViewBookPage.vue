<template>
  <v-container
    fill-height
    fluid
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
      v-if="isValidISBN===false"
      class="pt-6"
    >
      <p>
        {{ errorMessage }}
      </p>
    </v-row>
    <v-row
      v-if="isValidISBN"
      align="center"
    >
      <v-row
        v-if="isValidISBN===false"
        class="pt-6"
      >
        <p>
          {{ errorMessage }}
        </p>
      </v-row>
      <v-row
        v-if="!isLoading && isValidISBN"
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
                <router-link :to="{ name: 'search', params: {searchTerm: author}}">
                  {{ author }}
                </router-link>
              </h2>
              <h2 v-else-if="index === 0">
                By
                <router-link
                  :to="{ name: 'search', params: {searchTerm: author, searchType: 'author'}}"
                >
                  {{ author }},
                </router-link>
              </h2>
              <h2 v-else-if="index === bookData.authors.length -1">
                <router-link
                  :to="{ name: 'search', params: {searchTerm: author, searchType: 'author'}}"
                >
                  {{ author }}
                </router-link>
              </h2>
              <h2 v-else>
                <router-link
                  :to="{ name: 'search', params: {searchTerm: author, searchType: 'author'}}"
                >
                  {{ author }},
                </router-link>
              </h2>
            </div>
          </div>
          <average-ratings
            :id="bookData.id"
            :rating="bookData.averageRating"
            heading="Average rating"
            :ratings-count="bookData.ratingsCount"
          />
          <user-ratings
            :isbn="isbn.toString()"
          />
        </v-col>
        <v-col class="ma-0">
          <view-book-thumbnail :thumbnail="bookData.imageLinks.thumbnail" />
        </v-col>
      </v-row>
      <v-row
        v-if="!isLoading && isValidISBN"
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
      <v-row v-if="!isLoading && isValidISBN">
        <book-category-carousel
          best-seller-category="You may also like"
          :books="recommendedIsbns"
        />
      </v-row>
    </v-row>
  </v-container>
</template>

<script>
import ViewBookThumbnail from "@/components/viewbook/ViewBookThumbnail";
import {getBookInfo} from "@/api/view-book";
import AverageRatings from "@/components/viewbook/AverageRatings";
import UserRatings from "@/components/viewbook/UserRatings";
import AboutBook from "@/components/viewbook/AboutBook";
import {getRecs} from "@/api/personalize";
import BookCategoryCarousel from "@/components/home/BookCategoryCarousel";

export default {
  name: 'ViewBook',
  components: {
    BookCategoryCarousel,
    AboutBook,
    UserRatings,
    AverageRatings,
    ViewBookThumbnail
  },
  data: function () {
    return {
      bookData: '',
      isLoading: true,
      isValidISBN: '',
      isbn: this.$route.params.isbn,
      recommendedIsbns: ''
    }
  },
  computed: {
    errorMessage() {
      return "No results found for ISBN: " + this.isbn
    },
    displayRecommendations() {
      return this.recommendedIsbns;
    }
  },
  async mounted() {
    if (this.validIsbn(this.isbn)) {
      await this.getBookData();
      this.isValidISBN = this.bookData !== null;
      await this.getRecommendations();
    } else {
      this.bookData = null;
      this.isValidISBN = false;
    }
    this.isLoading = false;
  },
  methods: {
    validIsbn(isbn) {
      return isbn.length === 10 || isbn.length === 13;
    },
    async getBookData() {
      this.bookData = await getBookInfo(this.isbn);
    },
    concatDetails(details) {
      if (details != null && details.length > 1) {
        return details.join(', ');
      } else if (details != null) {
        return details.toString();
      }
      return null;
    },
    async getRecommendations() {
      this.recommendedIsbns = await getRecs(this.isbn);
    }
  }
}
</script>

