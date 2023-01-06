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
      <v-card-text style="height: 300px;">
        <v-row
          v-for="user in listOfUsers"
          :key="user.email"
        >
          <v-avatar
            size="50px"
            class="mt-5"
          >
            <img
              :src="user.picture"
              alt="Profile picture"
              referrerpolicy="no-referrer"
            >
          </v-avatar>
          <h3 class="ma-5 pt-2">
            {{ user.name }}
          </h3>
        </v-row>
      </v-card-text>
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
      required: true
    },
    total: {
      type: Number,
      required: true
    },
    listOfUsers: {
      type: Array,
      required: true
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
