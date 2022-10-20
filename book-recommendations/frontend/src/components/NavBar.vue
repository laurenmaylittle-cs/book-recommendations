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
          <span class="mr-2">{{ branding }}</span>
        </v-btn>
      </v-hover>
    </div>

    <div class="div_center d-flex align-center">
      <v-text-field
        v-model="title"
        clearable
      />
      <v-btn
        href="/search"
        text
        :style="{ 'background-color': '#46648c', 'color': 'white' }"
      >
        <v-icon>mdi-magnify</v-icon>
      </v-btn>
    </div>
  </v-app-bar>
</template>

<script>

export default {
  name: 'NavBar',
  props: {
    branding: {
      type: String,
      default: ""
    }
  },
  data() {
    return {
      title: 'Search',
      rules: [v => v.length <= 25 || 'Max 25 characters']
    }
  }
}
</script>

<style scoped>
.div_center {
  display: table;
  margin: 0 auto;
}

/deep/ .v-text-field {
  width: 300px;
}

/deep/ .v-input__control {
  margin-top: 25px;
}
</style>
