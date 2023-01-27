<template>
  <v-container>
    <v-row
      v-if="isLoading"
      class="justify-center"
    >
      <v-progress-circular
        :size="70"
        :width="7"
        color="primary"
        indeterminate
      />
    </v-row>
    <v-row
      v-if="valid===false"
      class="pt-6"
    >
      <p>
        {{ errorMessage }}
      </p>
    </v-row>
    <v-row
      v-if="valid"
      align="center"
    >
      <v-col cols="3">
        <view-book-thumbnail :thumbnail="bookData.imageLinks.thumbnail" />
      </v-col>
      <v-col>
        <view-book-details
          :title="bookData.title"
          :author="bookData.authors"
          :genre="bookData.categories"
          :description="bookData.description"
        />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import ViewBookThumbnail from "@/components/ViewBookThumbnail";
import ViewBookDetails from "@/components/ViewBookDetails";
import {getBookInfo} from "@/api/viewBook";

export default {
  name: 'ViewBook',
  components: {
    ViewBookThumbnail,
    ViewBookDetails
  },
  data: function () {
    return {
      bookData: '',
      isLoading: true,
      valid: '',
      isbn: this.$route.params.isbn
    }
  },
  computed: {
    errorMessage() {
      return "Could not find a book with the ISBN: " + this.isbn
    }
  },
  async mounted() {
    if (this.validIsbn(this.isbn)) {
      await this.getBookData();
      if (this.bookData !== null) {
        this.valid = true;
      }
    } else {
      this.bookData = null;
      this.valid = false;
    }
    this.isLoading = false;

  },
  methods: {
    validIsbn(isbn) {
      return isbn.length === 10 || isbn.length === 13;
    },
    async getBookData() {
      this.bookData = await getBookInfo(this.isbn);
    }
  }
}
</script>

