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
    <best-seller-filter
      :best-seller-categories="categories"
      @change="updateBestSellerList"
    />
    <book-category
      v-for="category in filteredCategories"
      :key="category.list_id"
      :best-seller-category="category.list_name"
      :books="category.books"
    />
  </v-container>
</template>

<script>
import BookCategoryCarousel from "@/components/home/BookCategoryCarousel";
import {getBestSellers} from "@/api/home-page-api-calls";
import BestSellerFilter from "@/components/home/BestSellerFilter";

export default {
  name: "HomePage",
  components: {BookCategory: BookCategoryCarousel, BestSellerFilter},
  data: () => ({
    categories: [],
    isLoading: true,
    selectedCategories: [],
  }),
  computed: {
    filteredCategories() {
      if (this.selectedCategories.length === 0) {
        return this.categories;
      }
      return this.categories.filter(category => this.selectedCategories.includes(category.list_id));
    }
  },
  async mounted() {
    this.categories = await getBestSellers();
    this.categories.sort((a, b) => a.list_name.localeCompare(b.list_name));
    this.isLoading = false;
  },
  methods: {
    updateBestSellerList(selectedCategories) {
      this.selectedCategories = selectedCategories;
    }
  }
}
</script>
