<template>
  <v-footer color="#46648c">
    <span>{{ getFooterInfo() }} </span>
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
    },
    getFooterInfo() {
      return this.currentDate() + ` - ${this.serviceName}`
    }
  }
}
</script>

<style scoped>
span {
  color: white;
}
</style>
