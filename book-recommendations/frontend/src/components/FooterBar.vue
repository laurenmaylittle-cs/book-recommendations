<template>
  <v-footer color="#46648c">
    <span class="div_center">{{ new Date().getFullYear() }} — {{ branding }}</span>
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
.div_center {
  display: table;
  margin: 0 auto;
}
</style>
