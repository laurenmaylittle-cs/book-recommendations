<template>
  <section class="layout">
    <div class="thumbnail">
      <img
        src=""
        alt=""
      >
    </div>
    <div class="bookInfo">
      <h1>Book Title</h1>
      <h2>Author</h2>
      <h3>Genre</h3>
      <br>
      <h4>Description</h4>
    </div>
    <div class="rating">
      <span><b>Rating: </b>PLACEHOLDER</span>
      <br>
      <span><b>Your rating: </b>PLACEHOLDER</span>
    </div>
  </section>
</template>

<script>
  export default {
    name: 'ViewBook'
  }
</script>

<style scoped>
  section {
    background-color: beige;
  }
  .thumbnail {
    width: 300px;
    height: 400px;
    background-color: mintcream;
  }
  .bookInfo {
    background-color: mintcream;
    padding: 20px;
    width: 300px;
  }
  .rating {
    background-color: mintcream;
    padding: 20px;
    width: 300px;
  }

  .layout {
    width: auto;
    height: auto;
    padding: 50px;
    display: flex;
    gap: 50px;
    position: center;
    align-items: center;
  }
</style>
