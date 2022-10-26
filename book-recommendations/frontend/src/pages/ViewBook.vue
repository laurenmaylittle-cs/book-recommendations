<template>
  <section class="layout">
    <ViewBookThumbnail :thumbnail="book[0].link" />
    <ViewBookDetails
      :title="book[0].name"
      :author="book[0].author"
      :genre="book[0].genre"
      :description="book[0].description"
    />
  </section>
</template>

<script>
  import ViewBookThumbnail from "@/components/ViewBookThumbnail";
  import ViewBookDetails from "@/components/ViewBookDetails";

  export default {
    name: 'ViewBook',
    components: {
      ViewBookThumbnail,
      ViewBookDetails
    },
    data() {
      return {
        book: [
          {
            name : "Stormbreaker",
            author : 'Anthony Horrowitz',
            genre : 'Spy Fiction',
            description : 'Alex Rider\'s life changes completely when his uncle and guardian dies in a mysterious car accident. Searching his uncle\'s office for clues, Alex uncovers a shocking secret. His uncle was a spy for MI6 and now he knows about it, Alex has to join them as well, or be assassinated.',
            link : 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSaaav8YaRyvyoYjLHlKUovTkzZzTltgm4coifwxmaTFUUNrHf8'
          }
        ]
      }
    }
  }

</script>

<style scoped>

  .layout {
    margin: 75px;
  }

</style>
