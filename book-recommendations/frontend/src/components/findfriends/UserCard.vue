<template>
  <v-row
    class="pa-3"
  >
    <v-col cols="10">
      <v-card>
        <v-row>
          <v-col
            cols="2"
            class="ml-10 mt-5 mb-5 mr-6"
          >
            <v-avatar
              v-if="user.picture !== null"
              size="100px"
            >
              <img
                :src="user.picture"
              >
            </v-avatar>
            <v-avatar
              v-else
              color="indigo"
              size="100px"
              class="ml-10 mt-5 mb-5 mr-3"
            >
              <v-icon dark>
                mdi-account-circle
              </v-icon>
            </v-avatar>
          </v-col>
          <v-col
            cols="5"
            class="pt-15 pl-8 pr-0"
          >
            <h3>
              {{ user.name }}
            </h3>
          </v-col>
          <v-col class="pt-15">
            <v-btn color="primary">
              Follow
            </v-btn>
          </v-col>
        </v-row>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
export default {
  name: "UserCard",
  props: {
    user: {type: JSON, required: true}
  }
}
</script>

<style scoped>

</style>
