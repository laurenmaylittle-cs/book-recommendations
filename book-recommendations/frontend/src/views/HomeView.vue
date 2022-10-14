<template>
  <p>{{testData}}</p>
</template>

<script>

import { getTestData } from '@/api/test'

export default {
  name: 'HomeView',
  data: () => ({
    testData: ''
  }),
  mounted () {
    this.getTest()
  },
  methods: {
    async getTest () {
      this.testData = await getTestData()
    }
  }
}
</script>
