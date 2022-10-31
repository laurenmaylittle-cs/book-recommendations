<template>
  <div>
    <h1>
      {{ title }}
    </h1>
    <h2>
      {{ author }}
    </h2>
    <h2>
      {{ publishedDate }}
    </h2>
    <h2>
      {{ genre }}
    </h2>
  </div>
</template>

<script>
export default {
  name: 'BookDetails',
  props: {
    title: {type: String, default: 'Book title'},
    author: {type: String, default: 'Author'},
    publishedDate: {type: String, default: 'Published date'},
    genre: {type: String, default: 'Genre'},
  }
}
</script>

<style scoped>
div {
  margin: 15px;
  width: auto;
  height: auto;
  font-size: small;
}

h2 {
  font-style: italic;
  font-size: small;
}
</style>
