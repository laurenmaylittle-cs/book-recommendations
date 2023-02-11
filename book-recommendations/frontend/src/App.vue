<template>
  <v-app>
    <phone-nav-bar
      v-if="$vuetify.breakpoint.xs || $vuetify.breakpoint.sm"
    />
    <nav-bar
      v-else
      :service-name="serviceName"
    />
    <v-main :style="style">
      <v-container fluid>
        <keep-alive
          :key="$route.fullPath"
          :max="3"
        >
          <router-view />
        </keep-alive>
      </v-container>
    </v-main>
    <footer-bar :service-name="serviceName" />
  </v-app>
</template>

<script>

import NavBar from '@/components/NavBar'
import FooterBar from "@/components/FooterBar";
import {getServiceName} from "@/api/branding";
import PhoneNavBar from "@/components/PhoneNavBar";

export default {
  name: 'App',
  components: {PhoneNavBar, FooterBar, NavBar},
  data() {
    return {
      serviceName: '',
      style: {
        backgroundColor: '#E4E4E4'
      }
    }
  },
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
