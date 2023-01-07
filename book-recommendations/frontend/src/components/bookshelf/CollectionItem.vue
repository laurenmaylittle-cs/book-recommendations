<template>
  <v-col :cols="numberOfColsToTake">
    <v-hover v-slot="{ hover }">
      <v-sheet
        :color="getHoverEffect(hover)"
        rounded
        height="150"
        width="200"
      >
        <div
          class="d-flex flex-column align-center justify-center fill-height text-h6"
        >
          {{ collectionName }}
        </div>
      </v-sheet>
    </v-hover>
  </v-col>
</template>

<script>
export default {
  name: "CollectionItem",
  props: {
    collectionName: {
      type: String,
      required: true,
    },
    collectionColor: {
      type: String,
      default: "#26c6da",
    },
  },
  computed: {
    numberOfColsToTake() {
      if (this.$vuetify.breakpoint.xl) {
        return 2;
      }
      if (this.$vuetify.breakpoint.lg) {
        return 3;
      }
      if (this.$vuetify.breakpoint.md) {
        return 4;
      }
      return 5;
    }
  },
  methods: {
    getHoverEffect(hover) {
      return hover ? "transparent" : this.collectionColor;
    },
  }
}
</script>

<style scoped>

</style>
