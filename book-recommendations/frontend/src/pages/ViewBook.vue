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
        <h2
          v-if="bookData.authors"
        >
          {{ bookData.authors }}
        </h2>
        <h3
          v-if="bookData.categories"
        >
          {{ bookData.categories }}
        </h3>
        <br>
        <average-ratings
          :rating="bookData.averageRating"
          heading="Average rating"
        />
        <user-ratings :title="bookData.title" />
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
      <v-col
        class="pl-0 mt-0"
      >
        <view-book-details
          :original-description="bookData.description"
        />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import ViewBookThumbnail from "@/components/viewbook/ViewBookThumbnail";
import ViewBookDetails from "@/components/viewbook/ViewBookDetails";
import {getBookInfo} from "@/api/view-book";
import AverageRatings from "@/components/viewbook/AverageRatings";
import UserRatings from "@/components/viewbook/UserRatings";

export default {
  name: 'ViewBook',
  components: {
    UserRatings,
    AverageRatings,
    ViewBookThumbnail,
    ViewBookDetails
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
    }
  }
}
</script>

