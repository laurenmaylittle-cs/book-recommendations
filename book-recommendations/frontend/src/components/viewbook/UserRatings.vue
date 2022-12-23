<template>
  <v-container v-if="$auth.isAuthenticated && $auth.user !== undefined">
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
        <v-dialog
          v-model="updateDialog"
          persistent
          width="300"
        >
          <template #activator="{ on, attrs }">
            <v-btn
              color="primary"
              dark
              x-small
              v-bind="attrs"
              v-on="on"
            >
              Update your rating
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
                @click="updateDialog = false"
              >
                close
              </v-btn>
              <v-btn
                color="green darken-1"
                text
                @click="updateUserRating"
              >
                Rate
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
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
              @click="postUserRating"
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
import {getUserRating, saveUserRating, updateUserRating} from "@/api/viewBook";

export default {
  name: "UserRatings",
  props: {
    title: {type: String, required: true}
  },
  data: () => ({
    bookRating: '',
    hasRating: false,
    dialog: false,
    updateDialog: false,
    isLoading: true
  }),
  watch: {
    // auth created hook is being executed after the created and mounted hooks
    // from this component are run
    // therefore this functionality can't be run in mounted/created in here
    // instead we watch for a change in the $auth.user variable to indicate that it's been populated
    async '$auth.user'() {
      await this.getRatingsData()
      if (this.bookRating !== null && this.bookRating !== "") {
        this.hasRating = true
      }
      this.isLoading = false
    }
  },
  async mounted() {
    await this.getRatingsData()
    if (this.bookRating !== null && this.bookRating !== "") {
      this.hasRating = true
    }
  },
  methods: {
    async getRatingsData() {
      const token = await this.$auth.getTokenSilently(
        {audience: 'https://localhost:5001/api'});
      this.bookRating = await getUserRating(this.$auth.user.email, '9780753827666', token)
    },
    async postUserRating() {
      const token = await this.$auth.getTokenSilently(
        {audience: 'https://localhost:5001/api'});
      this.hasRating = true
      await saveUserRating(this.$auth.user.email, '9780753827666', this.bookRating, token)
    },
    async updateUserRating() {
      const token = await this.$auth.getTokenSilently(
        {audience: 'https://localhost:5001/api'});
      this.hasRating = true
      this.updateDialog = false
      await updateUserRating(this.$auth.user.email, '9780753827666', this.bookRating, token)
    }
  }
}
</script>

<style scoped>

</style>
