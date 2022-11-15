<template>
  <div class="ma-12 pa-2">
    <h1
      v-if="title !== 'defaultTitle'"
    >
      <v-icon>mdi-book-open-page-variant</v-icon>
      {{ title }}
    </h1>
    <!-- TODO BES-70 ensure this works with multiple authors/genres -->
    <h2
      v-if="author !== 'defaultAuthor'"
    >
      <v-icon>mdi-account-edit</v-icon>
      {{ author }}
    </h2>
    <h3
      v-if="genre !== 'defaultGenre'"
    >
      <v-icon>mdi-filter</v-icon>
      {{ genre }}
    </h3>
    <br>
    <p
      v-if="description !== 'defaultDescription'"
      class="font-italic"
    >
      <v-icon>mdi-card-text-outline</v-icon>
      {{ description }}
    </p>
  </div>
</template>

<script>
export default {
  name: 'ViewBookDetails',
  props: {
    title: {type: String, default: 'defaultTitle'},
    author: {type: String, default: 'defaultAuthor'},
    genre: {type: String, default: 'defaultGenre'},
    description: {type: String, default: 'defaultDescription'}
  }
}
</script>

<style scoped>

div {
  width: auto;
  height: auto;
  display: block;
  float: left;
}

</style>
