<template>
  <!--  TODO BES-36 this is just a placeholder (proper implementation is missing) to test functionality of AuthGuard-->
  <div>
    <div>
      <img
        :src="$auth.user.picture"
        alt=""
      >
      <h2>{{ $auth.user.name }}</h2>
      <p>{{ $auth.user.email }}</p>
    </div>

    <div>
      <pre>{{ JSON.stringify($auth.user, null, 2) }}</pre>
    </div>
  </div>
</template>
