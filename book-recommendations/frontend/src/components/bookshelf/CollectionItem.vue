<template>
  <v-col
    :cols="numberOfColsToTake"
    :offset="getOffSet"
  >
    <v-hover
      v-slot="{ hover}"
    >
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
    getOffSet() {
      if (this.$vuetify.breakpoint.xs) {
        return 2;
      }
      return 0;
    },
    numberOfColsToTake() {
      const breakpointValues = {
        xl: 2,
        lg: 3,
        md: 4,
        xs: 12
      };
      const breakpoint = Object.keys(breakpointValues).find(
        breakpoint => this.$vuetify.breakpoint[breakpoint]);

      return breakpointValues[breakpoint] || 5;
    }
  },
  methods: {
    getHoverEffect(hover) {
      return hover ? "white" : this.collectionColor;
    },
  }
}
</script>

<style scoped>

</style>
