<template>
  <v-col
    :class="getThumbnailStyle"
    :cols="getNumberOfColumns"
  >
    <v-img
      class="mb-0 pa-3 rounded"
      :lazy-src="thumbnail"
      max-height="300"
      max-width="200"
      :src="thumbnail"
    />
  </v-col>
</template>

<script>
export default {
  name: 'ViewBookThumbnail',
  props: {
    thumbnail: {type: String, required: true}
  },
  computed: {
    getThumbnailStyle() {
      //center thumbnail on xs screens (phones)
      return this.$vuetify.breakpoint.xs ? "d-flex align-center justify-center"
        : "d-flex align-start justify-start";
    },
    getNumberOfColumns() {
      return this.$vuetify.breakpoint.xs ? 12 : 3;
    },
  }
}
</script>
