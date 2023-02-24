<template>
  <div class="ma-3">
    <td
      v-if="email !== $auth.user.email"
      width="10%"
    >
      <v-avatar
        v-if="picture !== null"
        size="50px"
        class="mr-3"
      >
        <img
          :src="picture"
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
    <td
      v-if="email !== $auth.user.email"
      width="70%"
    >
      {{ name }}
    </td>
    <td
      v-if="email !== $auth.user.email"
    >
      <v-btn
        v-if="isFollowing"
        @click="unfollow"
      >
        Following
      </v-btn>
      <v-btn
        v-else
        color="primary"
        @click="follow"
      >
        Follow
      </v-btn>
    </td>
  </div>
</template>

<script>
export default {
  name: "UserTable",
  props: {
    name: {type: String, required: true},
    email: {type: String, required: true},
    picture: {type: String, required: true},
    followingList: {type: Array, required: true}
  },
  data() {
    return {
      headers: [
        {text: '', value: 'picture', sortable: false},
        {text: '', value: 'name', sortable: false}
      ],
      isFollowing: false
    }
  },
  mounted() {
    this.isFollowing = this.isAFollowerOfUser()
  },
  methods: {
    follow() {
      this.isFollowing = true;
      this.$emit("follow", this.email);
    },
    unfollow() {
      this.isFollowing = false;
      this.$emit("unfollow", this.email);
    },
    isAFollowerOfUser() {
      this.followingList.forEach(follower => {
        if (follower.followerEmail === this.$auth.user.email) {
          this.isFollowing = true;
        }
      })
      return this.isFollowing
    }
  }
}
</script>

<style scoped>
</style>
