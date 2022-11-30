<template>
  <div class="ma-12 pa-2">
    <h1
      v-if="title"
    >
      <v-icon>mdi-book-open-page-variant</v-icon>
      {{ title }}
    </h1>
    <h2
      v-if="author"
    >
      <v-icon>mdi-account-edit</v-icon>
      {{ concatDetails(author) }}
    </h2>
    <h3
      v-if="genre"
    >
      <v-icon>mdi-filter</v-icon>
      {{ concatDetails(genre) }}
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
    concatDetails(details) {
      if (details !== '') {
        if (details.length === 1){
          return details.toString();
        } else {
          let info = '';
          for (let i = 0; i < details.length; i++) {
            if (i !== details.length - 1) {
              info += details[i] + ', ';
            } else {
              info += details[i]
            }
          }
          return info;
        }
      }
    }
  }
}
</script>

