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
    <h3>
      <v-icon>mdi-filter</v-icon>
      {{ genre }}
    </h3>
    <br>
    <p>
      <v-icon>mdi-card-text-outline</v-icon>
      {{ description }}
    </p>
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
    height: auto;
    display: block;
    float: left;
  }

  p {
    font-style: italic;
  }

</style>
