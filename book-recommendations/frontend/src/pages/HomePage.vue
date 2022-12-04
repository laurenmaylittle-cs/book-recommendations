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
    <v-row>
      <v-col cols="12">
        <v-select
          v-if="!isLoading"
          v-model="selectedCategories"
          filled
          :items="bestSellerCategories"
          label="Filter by category"
          multiple
        />
      </v-col>
    </v-row>
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

export default {
  name: "HomePage",
  components: {BookCategory: BookCategoryCarousel},
  data: () => ({
    categories: [],
    isLoading: true,
    selectedCategories: [],
  }),
  computed: {
    bestSellerCategories() {
      return this.categories.map(category => {
        return {
          text: category.list_name,
          value: category.list_id
        }
      }).sort((a, b) => a.text.localeCompare(b.text));
    },
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
  }
}
</script>
