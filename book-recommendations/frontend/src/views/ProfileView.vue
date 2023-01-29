<template>
  <!--  TODO BES-36 this is just a placeholder (proper implementation is missing) to test functionality of AuthGuard-->
  <div>
    <div>
      <img
        :src="$auth.user.picture"
        alt="User profile picture"
      >
      <h2>{{ $auth.user.name }}</h2>
      <p>{{ $auth.user.email }}</p>
    </div>

    <div>
      <pre>{{ JSON.stringify($auth.user, null, 2) }}</pre>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: 'ProfileView',
  async mounted() {
    let token = await this.$auth.getTokenSilently({
      audience: 'https://bestreads/api'
    });
    console.log("token", token);
    let result = await axios.get('api/private/get-private', {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    console.log(result);
  }
}

</script>
