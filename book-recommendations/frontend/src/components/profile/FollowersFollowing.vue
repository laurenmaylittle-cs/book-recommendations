<template>
  <v-dialog
    v-model="dialog"
    scrollable
    max-width="300px"
  >
    <template #activator="{ on, attrs }">
      <v-btn
        color="primary"
        dark
        v-bind="attrs"
        v-on="on"
      >
        {{ total }} {{ title }}
      </v-btn>
    </template>
    <v-card>
      <v-card-title>{{ title }}</v-card-title>
      <v-divider />
      <v-card-text style="height: 300px;" />
      <v-divider />
      <v-card-actions>
        <v-btn
          color="blue darken-1"
          text
          @click="dialog = false"
        >
          Close
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: "FollowersFollowing",
  props: {
    title: {
      type: String,
      default: ""
    },
    total: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      dialog: false,
    }
  }
}
</script>

<style scoped>

</style>
