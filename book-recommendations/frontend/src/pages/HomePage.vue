<template>
  <v-container class="fill-height">
    <v-row
      v-if="isLoading"
      class="justify-center"
    >
      <v-progress-circular
        :size="70"
        :width="7"
        color="purple"
        indeterminate
      />
    </v-row>
    <book-category
      v-for="category in categories"
      :key="category.list_id"
      :best-seller-category="category.list_name"
      :books="category.books"
    />
  </v-container>
</template>

<script>
import BookCategoryCarousel from "@/components/home/BookCategoryCarousel";
import getBestSellers from "@/api/home-page-api-calls";

export default {
  name: "HomePage",
  components: {BookCategory: BookCategoryCarousel},
  data: () => ({
    categories: [],
    isLoading: true
  }),
  async mounted() {
    await this.getBestSellers();
    this.isLoading = false;
  },
  methods: {
    async getBestSellers() {
      const response = await getBestSellers();
      this.categories = response.data;
    },
  }
}
</script>
