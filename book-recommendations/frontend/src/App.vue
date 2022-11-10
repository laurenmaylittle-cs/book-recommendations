<template>
  <v-app>
    <nav-bar :service-name="serviceName" />
    <v-main>
      <v-container>
        <router-view/>
      </v-container>
    </v-main>
    <footer-bar :service-name="serviceName" />
  </v-app>
</template>

<script>

import NavBar from '@/components/NavBar'
import FooterBar from "@/components/FooterBar";
import {getServiceName} from "@/api/branding";

export default {
  name: 'App',
  components: {FooterBar, NavBar},
  data: () => ({
    serviceName: ''
  }),
  mounted() {
    this.getServiceName()
  },
  methods: {
    async getServiceName() {
      this.serviceName = await getServiceName()
    }
  }
}
</script>
