<template>
  <v-container>
    <div v-if="rating">
      <h3>
        {{ heading }}
      </h3>
      <p
        v-if="ratingsCount > 0 && ratingsCount !== null"
        class="xs1"
      >
        Based on <a
          :href="reviewsUrl"
        >{{ ratingsCount }}
          ratings</a>
      </p>
      <v-rating
        v-model="bookRating"
        background-color="orange lighten-3"
        color="orange"
        large
        readonly
      />
    </div>
  </v-container>
</template>

<script>
export default {
  name: "AverageRatings",
  props: {
    rating: {type: Number, required: true},
    heading: {type: String, required: true},
    ratingsCount: {type: Number, required: true},
    id: {type: String, required: true}
  },
  data: () => ({
    bookRating: 0,
    reviewsUrl: ''
  }),
  mounted() {
    this.bookRating = this.rating
    this.reviewsUrl = "https://books.google.co.uk/books?id=" + this.id + "&sitesec=reviews"
  }
}
</script>

<style scoped>

</style>
