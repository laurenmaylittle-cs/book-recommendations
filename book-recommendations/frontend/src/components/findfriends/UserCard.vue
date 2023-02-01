<template>
  <v-col cols="10">
    <v-card
      v-if="email !== $auth.user.email"
      class="ml-5 mt-2 mb-2"
      height="150px"
    >
      <v-row>
        <v-col
          cols="2"
        >
          <v-avatar
            v-if="picture !== null"
            size="100px"
            class="ml-3 mt-5 mb-5 mr-3"
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
            class="ml-3 mt-5 mb-5 mr-3"
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
            {{ name }}
          </h3>
        </v-col>
        <v-col class="pt-15">
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
        </v-col>
      </v-row>
    </v-card>
  </v-col>
</template>

<script>
export default {
  name: "UserCard",
  props: {
    name: {type: String, required: true},
    email: {type: String, required: true},
    picture: {type: String, required: true},
    followingList: {type: Array, required: true},
  },
  data: () => ({
    isFollowing: false
  }),
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
