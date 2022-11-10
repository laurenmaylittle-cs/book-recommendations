<template>
  <!-- Need to decide on thumbnail size -->
  <v-img
    class="ma-12 pa-3 rounded"
    :lazy-src="thumbnail"
    max-height="300px"
    max-width="200px"
    :src="thumbnail"
  />
</template>

<script>
export default {
  name: 'ViewBookThumbnail',
  props: {
    thumbnail: {type: String, default: 'https://storage.googleapis.com/du-prd/books/images/9781538719824.jpg'} //replace this with a default image
  }
}
</script>

<style scoped>
</style>
