<template>
  <div>
    <h1>
      <v-icon>mdi-book-open-page-variant</v-icon>
      {{ title }}
    </h1>
    <h2>
      <v-icon>mdi-account-edit</v-icon>
      {{ author }}
    </h2>
  </div>
</template>

<script>
export default {
  name: 'BookDetails',
  props: {
    title: {type: String, default: 'Book Title'},
    author: {type: String, default: 'Author'}
  }
}
</script>

<style scoped>
div {
  margin: 50px;
  padding: 10px;
  outline: solid black 1px;
  border-radius: 5px;
  width: auto;
  height: auto;
  display: block;
  float: left;
}

p {
  font-style: italic;
  font-size: small;
}
</style>
