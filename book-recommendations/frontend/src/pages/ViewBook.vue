<template>
  <v-container>
    <v-row align="center">
      <v-col>
        <view-book-thumbnail :thumbnail="bookData.volumeInfo.imageLinks.thumbnail" />
      </v-col>
      <v-col>
        <!-- TODO BES-70 show all authors and genres depending on the data returned from the API -->
        <view-book-details
          :title="bookData.volumeInfo.title"
          :author="bookData.volumeInfo.authors[0]"
          :genre="bookData.volumeInfo.categories[0]"
          :description="bookData.volumeInfo.description"
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
      this.bookData = await getBookInfo('hxL2qWMAgv8C') //TODO BES-63 get the book info using ISBN not the ID
    }
  }
}
</script>

<style scoped>
</style>
