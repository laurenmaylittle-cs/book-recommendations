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
      v-if="!isLoading"
      align="center"
      class="pb-0 pt-0"
    >
      <v-col class="ml-10 pt-12">
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
            <h2 v-if="index === 0 && bookData.authors.length === 0">
              By
              <router-link :to="{ name: 'search', params: {searchTerm: author}}">
                {{ author }}
              </router-link>
            </h2>
            <h2 v-else-if="index === 0">
              By
              <router-link :to="{ name: 'search', params: {searchTerm: author}}">
                {{ author }},
              </router-link>
            </h2>
            <h2 v-else-if="index === bookData.authors.length -1">
              <router-link :to="{ name: 'search', params: {searchTerm: author}}">
                {{ author }}
              </router-link>
            </h2>
            <h2 v-else>
              <router-link :to="{ name: 'search', params: {searchTerm: author}}">
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
          isbn="9780753827666"
        />
      </v-col>
      <v-col class="pb-0 mb-0 mt-2">
        <view-book-thumbnail :thumbnail="bookData.imageLinks.thumbnail" />
      </v-col>
    </v-row>
    <v-row
      v-if="!isLoading"
      align="left"
      class="pt-0 ma-0"
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

export default {
  name: 'ViewBook',
  components: {
    AboutBook,
    UserRatings,
    AverageRatings,
    ViewBookThumbnail
  },
  data: () => ({
    bookData: '',
    isLoading: true
  }),
  async mounted() {
    await this.getBookData()
    this.isLoading = false;
  },
  methods: {
    async getBookData() {
      this.bookData = await getBookInfo('9780753827666');
    },
    concatDetails(details) {
      if (details != null && details.length > 1) {
        return details.join(', ');
      } else {
        return details.toString();
      }
    }
  }
}
</script>

