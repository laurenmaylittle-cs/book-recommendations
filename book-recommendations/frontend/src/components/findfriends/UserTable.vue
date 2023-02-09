<template>
  <div class="ma-3">
    <v-simple-table>
      <tbody>
        <tr
          v-for="user in userList"
          :key="user.email"
          class="ma-5"
          height="75px"
        >
          <td width="10%">
            <v-avatar
              v-if="user.picture !== null"
              size="50px"
            >
              <img
                :src="user.picture"
                alt="Profile picture"
                referrerpolicy="no-referrer"
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
          </td>
          <td width="70%">
            {{ user.name }}
          </td>
        </tr>
      </tbody>
    </v-simple-table>
  </div>
</template>

<script>
export default {
  name: "UserTable",
  props: {
    userList: {type: Array, required: true}
  },
  data() {
    return {
      headers: [
        {text: '', value: 'picture', sortable: false},
        {text: '', value: 'name', sortable: false}
      ]
    }
  }
}
</script>

<style scoped>
</style>
