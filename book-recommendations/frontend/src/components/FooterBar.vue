<template>
  <v-footer color="#46648c">
    <v-spacer />
    <div class="div_center">
      <span>{{ new Date().getFullYear() }} — {{ branding }}</span>
    </div>
  </v-footer>
</template>

<script>
export default {
  name: 'FooterBar',
  props: {
    branding: String
  }
}
</script>

<style scoped>
.div_center {
  text-align: center;
}
</style>
