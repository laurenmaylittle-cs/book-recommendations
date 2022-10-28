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
          :style="{ ...styleComputed(hover) }"
        >
          <v-icon>mdi-book-open-page-variant</v-icon>
          <span class="mr-2">{{ serviceName }}</span>
        </v-btn>
      </v-hover>
    </div>
    <search-bar :title="title" />
  </v-app-bar>
</template>

<script>
import SearchBar from '@/components/SearchBar'

export default {
  name: 'NavBar',
  components: {SearchBar},
  props: {
    serviceName: {
      type: String,
      default: ""
    }
  },
  data() {
    return {
      title: 'Search',
      rules: [v => v.length <= 25 || 'Max 25 characters']
    }
  },
  methods: {
    styleComputed(hover) {
      return {'background-color': hover ? 'white' : '#46648c', 'color': hover ? '#46648c' : 'white'}
    }
  }
}
</script>

<style scoped>
/deep/ .v-text-field {
  width: 300px;
}

/deep/ .v-input__control {
  margin-top: 25px;
}
</style>
