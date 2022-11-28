<template>
  <div class="ma-12 pa-2">
    <h1
      v-if="title"
    >
      <v-icon>mdi-book-open-page-variant</v-icon>
      {{ title }}
    </h1>
    <!-- TODO BES-70 ensure this works with multiple authors/genres -->
    <h2
      v-if="author"
    >
      <v-icon>mdi-account-edit</v-icon>
      {{ concatenateAuthor(author) }}
    </h2>
    <h3
      v-if="genre"
    >
      <v-icon>mdi-filter</v-icon>
      {{ concatenateGenre(genre) }}
    </h3>
    <br>
    <p
      v-if="description"
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
    title: {type: String},
    author: {type: Array},
    genre: {type: Array},
    description: {type: String}
  },
  methods: {
    concatenateAuthor(author) {
      if (author !== '') {
        if (author.length === 1){
          return author.toString();
        } else {
          let authors = '';
          for (let i = 0; i < author.length; i++) {
            if (i !== author.length - 1) {
              authors += author[i] + ', ';
            } else {
              authors += author[i]
            }
          }
          return authors;
        }
      }
    },
    concatenateGenre(genre) {
      if (genre !== '') {
        if (genre.length === 1){
          return genre.toString();
        } else {
          let genres = '';
          for (let i = 0; i < genre.length; i++) {
            if (i !== genre.length - 1) {
              genres += genre[i] + ', ';
            } else {
              genres += genre[i]
            }
          }
          return genres;
        }
      }
    }
  }
}
</script>

