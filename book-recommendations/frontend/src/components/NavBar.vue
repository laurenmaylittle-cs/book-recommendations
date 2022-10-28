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
          :style="{ ...styleComputed(hover) }"
        >
          <v-icon>mdi-book-open-page-variant</v-icon>
          <span class="mr-2">{{ serviceName }}</span>
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
    serviceName: {
      type: String,
      default: ""
    }
  },
  methods: {
    styleComputed(hover) {
      return {'background-color': hover ? 'white' : '#46648c', 'color': hover ? '#46648c' : 'white'}
    }
  }
}
</script>

<style scoped>
</style>
