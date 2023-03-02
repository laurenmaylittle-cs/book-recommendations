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
      v-if="!isLoading"
      ref="bestSellerFilter"
      :best-seller-categories="categories"
      @change="updateBestSellerList"
    />
    <book-category
      v-for="category in filteredCategories"
      :key="category.list_id"
      :best-seller-category="category.list_name"
      :books="category.books"
    />
    <v-col
      v-if="categories.length > 0 && !isLoading"
    >
      <v-img
        width="124"
        src="@/assets/poweredby_nytimes.png"
        @click="goToNYTimes"
      />
    </v-col>
  </v-container>
</template>

<script>
import BookCategoryCarousel from "@/components/home/BookCategoryCarousel";
import {getBestSellers} from "@/api/home-page-api-calls";
import BestSellerFilter from "@/components/home/BestSellerFilter";
import {EventBus} from "@/event-bus";

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
    },
  },
  async activated() {
    EventBus.$on("refresh-homepage", async () => {
      await this.$nextTick()
      this.$refs.bestSellerFilter.reset();
      await this.setUpBestSellers();
    });
  },
  deactivated() {
    EventBus.$off("refresh-homepage");
  },
  async mounted() {
    await this.setUpBestSellers();
  },
  methods: {
    updateBestSellerList(selectedCategories) {
      this.selectedCategories = selectedCategories;
    },
    async setUpBestSellers() {
      this.selectedCategories = [];
      this.categories = await getBestSellers();
      this.categories.sort((a, b) => a.list_name.localeCompare(b.list_name));
      this.isLoading = false;
    },
    goToNYTimes() {
      window.open("https://developer.nytimes.com/", "_blank");
    },
  }
}
</script>
