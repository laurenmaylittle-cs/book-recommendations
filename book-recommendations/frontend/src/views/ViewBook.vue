<template>
  <section class="layout">
    <ViewBookThumbnail />
    <ViewBookDetails />
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
    }
  }
</script>

<style scoped>

.layout {
  margin: 75px;
}

</style>
