<template>
  <v-footer color="#46648c">
    <span>{{ currentDate() }} — {{ serviceName }}</span>
  </v-footer>
</template>

<script>
export default {
  name: 'FooterBar',
  props: {
    serviceName: {
      type: String,
      default: ""
    }
  },
  methods: {
    currentDate() {
      return new Date().getFullYear()
    }
  }
}
</script>

<style scoped>
span {
  color: white;
}
</style>
