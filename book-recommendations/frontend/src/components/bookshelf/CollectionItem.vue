<template>
  <v-col cols="2">
    <v-card
      :color="collectionColor"
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
  }
}
</script>

<style scoped>

</style>
