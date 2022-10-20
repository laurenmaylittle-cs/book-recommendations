<template>
  <!-- Need to decide on thumbnail size -->
  <v-img
    :lazy-src="thumbnail"
    max-height="600px"
    max-width="400px"
    :src="thumbnail"
  />
</template>

<script>
  export default {
    name: 'ViewBookThumbnail',
    props: {
      thumbnail: {type: String, default: 'https://m.media-amazon.com/images/I/51mtZy7oJVL.jpg'} //replace this with a default image
    }
  }
</script>

<style scoped>

</style>
