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
          :items="bestSellerCategories"
          label="Filter by category"
          multiple
          chips
          deletable-chips
          clearable
        >
          <template #selection="{ item }">
            <v-chip
              :color="getChipColor(item)"
              close
              @click:close="removeChip(item)"
            >
              {{ item.text }}
            </v-chip>
          </template>
        </v-select>
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
    chipColors: [
      "red lighten-4",
      "pink lighten-4",
      "purple lighten-4",
      "deep-purple lighten-4",
      "indigo lighten-4",
      "blue lighten-4",
      "light-blue lighten-4",
      "cyan lighten-4",
      "teal lighten-4",
      "green lighten-4",
      "light-green lighten-4",
      "lime lighten-4",
      "yellow lighten-4",
      "amber lighten-4",
      "orange lighten-4",
      "deep-orange lighten-4",
      "brown lighten-4",
      "grey lighten-4",
    ]
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
  },
  methods: {
    removeChip(item) {
      const index = this.selectedCategories.indexOf(item.value);
      if (index >= 0) {
        this.selectedCategories.splice(index, 1);
      }
    },
    getChipColor(item) {
      const index = this.selectedCategories.indexOf(item.value);
      return this.chipColors[index];
    }
  }
}
</script>
