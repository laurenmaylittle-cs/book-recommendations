<template>
  <v-container>
    <h3 v-if="rating">
      {{ heading }}
      <v-rating
        v-model="bookRating"
        background-color="orange lighten-3"
        color="orange"
        large
        readonly
      />
    </h3>
  </v-container>
</template>

<script>
export default {
  name: "AverageRatings",
  props: {
    rating: {type: Number, required: true},
    heading: {type: String, required: true}
  },
  data: () => ({
    bookRating: '',
  }),
  mounted() {
    this.bookRating = this.rating
  }
}
</script>

<style scoped>

</style>
