<template>
  <v-row>
    <v-col cols="12">
      <v-select
        v-model="selectedCategories"
        :items="items"
        label="Filter by category"
        multiple
        clearable
        @change="onChange"
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
</template>

<script>
export default {
  name: "BestSellerFilter",
  props: {
    bestSellerCategories: {
      type: Array,
      required: true
    }
  },
  data: () => ({
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
      "blue-grey lighten-4",
    ],
  }),
  computed: {
    items() {
      return this.bestSellerCategories.map(category => {
        return {
          text: category.list_name,
          value: category.list_id
        }
      }).sort((a, b) => a.text.localeCompare(b.text));
    }
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
    },
    onChange() {
      this.$emit("change", this.selectedCategories);
    }
  }
}
</script>
