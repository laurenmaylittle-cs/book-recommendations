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
    thumbnail: {type: String} //TODO BES-66 once merged to master link to default thumbnail component
  }
}
</script>
