<template>
  <v-col cols="2">
    <v-hover v-slot="{ hover }">
      <v-card
        :color="getHoverEffect(hover)"
        height="150"
        width="200"
        max-height="200"
      >
        <v-card-text
          class="d-flex flex-column align-center justify-center fill-height text-h6 font-weight-bold"
        >
          {{ collectionName }}
        </v-card-text>
      </v-card>
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
  methods: {
    getHoverEffect(hover) {
      return hover ? "transparent" : this.collectionColor;
    },
  },
}
</script>

<style scoped>

</style>
