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
        <div v-if="title === 'Followers' || title === 'Follower'">
          <p
            v-for="user in listOfUsers"
            :key="user.id"
          >
            {{ user.followerEmail }}
          </p>
        </div>
        <div v-else-if="title === 'Following'">
          <p
            v-for="user in listOfUsers"
            :key="user.id"
          >
            {{ user.followingEmail }}
          </p>
        </div>
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
