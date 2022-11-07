<template>
  <section class="layout">
    <view-book-thumbnail :thumbnail="bookData.volumeInfo.imageLinks.thumbnail" />
    <view-book-details
      :title="bookData.volumeInfo.title"
      :author="bookData.volumeInfo.authors[0]"
      :genre="bookData.volumeInfo.categories[0]"
      :description="bookData.volumeInfo.description"
    />
  </section>
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
        this.bookData = await getBookInfo('3cmKzgEACAAJ') //hard set id for now, this will need to change
      }
    }
  }
</script>

<style scoped>
  .layout {
    margin: 75px;
  }
</style>
