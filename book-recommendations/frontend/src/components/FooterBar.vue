<template>
  <v-footer color="#46648c">
    <span>{{ new Date().getFullYear() }} — {{ branding }}</span>
  </v-footer>
</template>

<script>
export default {
  name: 'FooterBar',
  props: {
    branding: {
      type: String,
      default: ""
    }
  }
}
</script>

<style scoped>
span {
  color: white;
}
</style>
