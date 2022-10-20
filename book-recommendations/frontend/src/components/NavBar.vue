<template>
  <v-app-bar
    app
    color="#46648c"
    dark
  >
    <div class="d-flex align-center">
      <v-hover v-slot="{ hover }">
        <v-btn
          href="/home"
          text
          :style="{ 'background-color': hover ? 'white' : '#46648c', 'color' : hover ? '#46648c' : 'white' }"
        >
          <v-icon>mdi-book-open-page-variant</v-icon>
          <span class="mr-2">{{ branding }}</span>
        </v-btn>
      </v-hover>
    </div>

    <v-spacer />
  </v-app-bar>
</template>

<script>

export default {
  name: 'NavBar',
  props: {
    branding: {
      type: String,
      default: ""
    }
  }
}
</script>

<style scoped>
</style>
