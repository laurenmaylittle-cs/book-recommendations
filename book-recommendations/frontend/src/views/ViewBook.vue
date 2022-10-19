<template>
  <div class="container">
    <div class="thumbnail">
      <img
        src=""
        alt=""
      >
    </div>

    <div class="bookInfo">
      <table>
        <tr>
          <th>Book title</th>
          <td>PLACEHOLDER</td>
        </tr>
        <tr>
          <th>Author</th>
          <td>PLACEHOLDER</td>
        </tr>
        <tr>
          <th>Genre</th>
          <td>PLACEHOLDER</td>
        </tr>
        <tr>
          <th>Description</th>
          <td>PLACEHOLDER</td>
        </tr>
      </table>
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
    float: bottom;
    padding-top: 50px;
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
