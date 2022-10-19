<template>
  <v-app>
    <nav-bar :branding="brandingData" />
    <v-main>
      <router-view />
    </v-main>
    <footer-bar :branding="brandingData" />
  </v-app>
</template>

<script>

import NavBar from '@/components/NavBar'
import {getBranding} from "@/api/branding";
import FooterBar from "@/components/FooterBar";

export default {
  name: 'App',
  components: {FooterBar, NavBar},
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
