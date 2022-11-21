<template>
  <v-container>
    <v-row align="center">
      <v-col>
        <view-book-thumbnail :thumbnail="bookData.imageLinks.thumbnail" />
      </v-col>
      <v-col>
        <!-- TODO BES-70 show all authors and genres depending on the data returned from the API -->
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
    bookData: ''
  }),

  async mounted () {
    await this.getBookData()
  },

  methods: {
    async getBookData () {
      this.bookData = await getBookInfo('9781905294930');
    }
  }

}
</script>

