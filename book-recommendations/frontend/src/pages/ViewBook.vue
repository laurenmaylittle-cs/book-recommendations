<template>
  <v-container>
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
    <v-row align="center">
      <v-col>
        <view-book-thumbnail :thumbnail="bookData.imageLinks.thumbnail" />
      </v-col>
      <v-col>
        <view-book-details
          :title="bookData.title"
          :author="bookData.authors"
          :genre="bookData.categories"
          :description="bookData.description"
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

  async mounted () {
    await this.getBookData()
    this.isLoading = false;
  },

  methods: {
    async getBookData () {
      this.bookData = await getBookInfo('9781302482541');
    }
  }

}
</script>

