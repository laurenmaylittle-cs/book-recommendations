<template>
  <v-container v-if="$auth.isAuthenticated">
    <div v-if="hasRating">
      <h3>
        Your rating
        <v-rating
          v-model="bookRating"
          background-color="orange lighten-3"
          color="orange"
          large
          readonly
        />
      </h3>
    </div>
    <div v-else>
      <v-dialog
        v-model="dialog"
        persistent
        width="300"
      >
        <template #activator="{ on, attrs }">
          <v-btn
            color="primary"
            dark
            v-bind="attrs"
            v-on="on"
          >
            Add your rating
          </v-btn>
        </template>
        <v-card>
          <v-card-title class="text-h5">
            Add your rating
          </v-card-title>
          <v-rating
            v-model="bookRating"
            class="ml-5"
            background-color="orange lighten-3"
            color="orange"
            large
          />
          <v-card-actions>
            <v-spacer />
            <v-btn
              color="green darken-1"
              text
              @click="dialog = false"
            >
              close
            </v-btn>
            <v-btn
              color="green darken-1"
              text
              @click="postUserData"
            >
              Rate
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>
  </v-container>
</template>

<script>
import {getUserRating} from "@/api/viewBook";

export default {
  name: "UserRatings",
  props: {
    title: {type: String, required: true}
  },
  data: () => ({
    bookRating: '',
    hasRating: false,
    dialog: false
  }),
  async mounted() {
    await this.getRatingsData()
    if (this.bookRating !== null && this.bookRating !== "") {
      this.hasRating = true
    }
  },
  methods: {
    async getRatingsData() {
      this.bookRating = await getUserRating('laurenmaylittle@gmail.com', '9780753827666')
    },
    async postUserData() {
      this.hasRating = true
    }
  }
}
</script>

<style scoped>

</style>
