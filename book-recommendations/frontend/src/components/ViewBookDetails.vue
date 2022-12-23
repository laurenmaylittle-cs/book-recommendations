<template>
  <v-container class="pa-2 ml-0 mr-0 mt-5">
    <v-card>
      <p
        v-if="originalDescription"
        class="font-italic pl-12 pr-12 pt-12 pb-0"
      >
        {{ description }}
      </p>
      <v-layout justify-center>
        <v-card-actions>
          <v-btn
            v-if="isShortDescription"
            @click="viewWholeDescription"
          >
            View more
          </v-btn>
          <v-btn
            v-else
            @click="minimiseDescription"
          >
            View less
          </v-btn>
        </v-card-actions>
      </v-layout>
    </v-card>
  </v-container>
</template>

<script>
export default {
  name: 'ViewBookDetails',
  props: {
    originalDescription: {type: String, required: true}
  },
  data: () => ({
    description: '',
    isShortDescription: true
  }),
  mounted() {
    this.description = this.truncateDescription()
  },
  methods: {
    truncateDescription() {
      console.log(this.originalDescription)
      return `${this.truncateText(this.originalDescription, 400)}`
    },
    truncateText(text, maxCharacterCount) {
      if (text.length > maxCharacterCount) {
        return text.substring(0, maxCharacterCount) + '...'
      }
      return text
    },
    viewWholeDescription() {
      this.description = this.originalDescription
      this.isShortDescription = false
    },
    minimiseDescription() {
      this.description = this.truncateDescription()
      this.isShortDescription = true
    }
  }
}
</script>

