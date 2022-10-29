<template>
  <!-- Need to decide on thumbnail size -->
  <v-img
    class="rounded ml-10 mb-10"
    :lazy-src="thumbnail"
    max-height="150px"
    max-width="100px"
    :src="thumbnail"
  />
</template>

<script>
export default {
  name: 'ViewBookThumbnail',
  props: {
    thumbnail: {
      type: String,
      default: 'https://storage.googleapis.com/du-prd/books/images/9781538719824.jpg'
    } //replace this with a default image
  }
}
</script>

<style scoped>
</style>
