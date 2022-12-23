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
      <v-col class="ml-10">
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
        <h3 v-if="bookData.averageRating">
          Average rating
          <v-rating
            v-model="bookData.averageRating"
            background-color="orange lighten-3"
            color="orange"
            large
            readonly
          />
        </h3>
        <h3>
          Your rating
          <v-rating
            v-model="rating"
            background-color="orange lighten-3"
            color="orange"
            large
          />
        </h3>
      </v-col>
      <v-col class="pb-0 mb-0 mt-2">
        <view-book-thumbnail :thumbnail="bookData?.imageLinks?.thumbnail" />
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
import ViewBookThumbnail from "@/components/ViewBookThumbnail";
import ViewBookDetails from "@/components/ViewBookDetails";
import {getBookInfo} from "@/api/viewBook";

export default {
  name: 'ViewBook',
  components: {
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

