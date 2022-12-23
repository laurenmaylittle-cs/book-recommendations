<template>
  <v-img
    class="ma-12 pa-3 rounded"
    :lazy-src="thumbnail"
    max-height="300"
    max-width="200"
    :src="thumbnail"
  />
</template>

<script>
export default {
  name: 'ViewBookThumbnail',
  props: {
    thumbnail: {type: String, required: true}
  }
}
</script>
