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
          <span class="mr-2">{{ brandingData }}</span>
        </v-btn>
      </v-hover>
    </div>

    <v-spacer />
  </v-app-bar>
</template>

<script>
import {getBranding} from "@/api/branding";

export default {
  name: 'NavBar',
  data: () => ({
    brandingData: ''
  }),
  mounted() {
    this.getBrandingData()
  },
  methods: {
    async getBrandingData() {
      this.brandingData = await getBranding()
    }
  }
}
</script>

<style scoped>
</style>
