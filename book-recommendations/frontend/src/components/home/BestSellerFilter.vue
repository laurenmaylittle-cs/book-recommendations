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
import {generatePastelColors} from "@/util/util";

export default {
  name: "BestSellerFilter",
  props: {
    bestSellerCategories: {
      type: Array,
      required: true
    }
  },
  data: () => ({
    selectedCategories: []
  }),
  computed: {
    items() {
      return this.bestSellerCategories.map(category => {
        return {
          text: category.list_name,
          value: category.list_id
        }
      }).sort((a, b) => a.text.localeCompare(b.text));
    },
    chipColors() {
      const colors = [];
      generatePastelColors(colors, this.bestSellerCategories.length, {
        min: 30,
        max: 70
      }, {
        min: 80,
        max: 100
      });
      return colors;
    },
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
    },
  }
}
</script>
