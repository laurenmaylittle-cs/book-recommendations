<template>
  <v-container class="pa-2 ml-0 mr-0 mt-5">
    <h3 class="pb-2 pl-1">
      More about this edition
    </h3>
    <v-card class="pa-4">
      <v-row>
        <v-col
          v-if="category"
          cols="3"
        >
          <p>
            Genre:
          </p>
        </v-col>
        <v-col cols="3">
          <p v-if="category">
            {{ categories }}
          </p>
        </v-col>
        <v-col
          v-if="publisher"
          cols="3"
        >
          <p>
            Publisher:
          </p>
        </v-col>
        <v-col cols="3">
          <p v-if="publisher">
            {{ publisher }}
          </p>
        </v-col>
      </v-row>
      <v-row class="mt-0">
        <v-col
          v-if="publishedDate"
          cols="3"
        >
          <p>
            Published date:
          </p>
        </v-col>
        <v-col cols="3">
          <p v-if="publishedDate">
            {{ formatDate() }}
          </p>
        </v-col>
        <v-col
          v-if="pages"
          cols="3"
        >
          <p>
            Number of pages:
          </p>
        </v-col>
        <v-col cols="3">
          <p v-if="pages">
            {{ pages }}
          </p>
        </v-col>
      </v-row>
      <v-row>
        <p
          v-if="originalDescription"
          class="font-italic pa-4"
        >
          {{ description }}
        </p>
        <v-layout justify-center>
          <v-card-actions
            v-if="originalDescription"
            class="mb-5"
          >
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
      </v-row>
    </v-card>
  </v-container>
</template>

<script>
export default {
  name: "AboutBook",
  props: {
    category: {type: String, required: true},
    publishedDate: {type: String, required: true},
    originalDescription: {type: String, required: true},
    pages: {type: Number, required: true},
    publisher: {type: String, required: false}
  },
  data: () => ({
    categories: '',
    date: '',
    description: '',
    isShortDescription: true
  }),
  mounted() {
    this.categories = this.category
    this.date = this.publishedDate
    this.description = `${this.truncateText(this.originalDescription, 400)}`
  },
  methods: {
    formatDate() {
      const formattedDate = new Date(this.date);
      const monthNames = [
        "Jan", "Feb", "Mar", "Apr",
        "May", "Jun", "Jul", "Aug",
        "Sep", "Oct", "Nov", "Dec"
      ];

      const monthIndex = formattedDate.getMonth();
      return formattedDate.getDate() + " " + monthNames[monthIndex] + " "
        + formattedDate.getFullYear()
    },
    viewWholeDescription() {
      this.description = this.originalDescription
      this.isShortDescription = false
    },
    minimiseDescription() {
      this.description = `${this.truncateText(this.originalDescription, 400)}`
      this.isShortDescription = true
    },
    truncateText(text, maxCharacterCount) {
      if (text.length > maxCharacterCount) {
        return text.substring(0, maxCharacterCount) + '...'
      }
      return text
    }
  }
}
</script>

<style scoped>

</style>
