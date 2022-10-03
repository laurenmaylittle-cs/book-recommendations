<template>
  <div id="app">
    <nav>
      <router-link to="/api/test">Test</router-link>
    </nav>
    <router-view/>
    <p>{{testData}}</p>
  </div>
</template>

<script>
import {getTestData} from "@/api/test";

export default {
  data: () => ({
    testData: null
  }),
  created() {
    this.getTest()
  },
  methods: {
    async getTest() {
      let x = (await getTestData());
      console.log(x);
      this.testData = x;
    }
  }
}



</script>



<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

</style>


