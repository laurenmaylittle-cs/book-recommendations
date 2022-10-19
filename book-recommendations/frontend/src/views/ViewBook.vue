<template>
  <div class="container">
    <div class="thumbnail">
      <img
        src=""
        alt=""
      >
    </div>

    <div class="bookInfo">
      <h1>BOOK TITLE</h1>
      <h2>AUTHOR</h2>
      <h3>GENRE</h3>
      <br>
      <span>Description</span>
    </div>

    <div class="rating">
      <table>
        <tr>
          <th>Rating</th>
          <td>PLACEHOLDER</td>
        </tr>
        <tr>
          <th>Your rating</th>
          <td>PLACEHOLDER</td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'ViewBook'
  }
</script>

<style scoped>

  .container {
    padding: 100px 50px;
    position: center;
  }

  .bookInfo, .rating{
    float: top;
    padding-top: 30px;
    padding-left: 400px;
    padding-bottom: 50px;
  }

  .thumbnail {
    height: 400px;
    width: 300px;
    outline-color: gray;
    outline-style: solid;
    float: left;
  }

</style>
