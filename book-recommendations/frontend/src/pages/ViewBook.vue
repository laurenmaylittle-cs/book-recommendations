<template>
  <v-container>
    <v-row align="center">
      <v-col>
        <view-book-thumbnail :thumbnail="bookData.volumeInfo.imageLinks.thumbnail" />
      </v-col>
      <v-col>
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
      this.bookData = await getBookInfo('hxL2qWMAgv8C') //hard set id for now, this will need to change
    }
  }
}
</script>

<style scoped>
</style>
