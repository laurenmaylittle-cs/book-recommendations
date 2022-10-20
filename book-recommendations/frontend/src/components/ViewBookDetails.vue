<template>
  <div>
    <h1>{{ title }}</h1>
    <h2>{{ author }}</h2>
    <h3>{{ genre }}</h3>
    <br>
    <p>{{ description }}</p>
  </div>
</template>

<script>
  export default {
    name: 'ViewBookDetails',
    props: {
      title: { type: String, default: 'Book Title'},
      author: { type: String, default: 'Author'},
      genre: { type: String, default: 'Genre'},
      description: { type: String, default: 'Description'}
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
  max-height: 200px;
  display: block;
  float: left;
}

p {
  font-style: italic;
}

</style>
