<template>
  <v-container>
    <v-row>
      <v-col class="text-h6 cyan--text text--darken-4 d-flex justify-left">
        {{ bestSellerCategory }}
      </v-col>
    </v-row>
    <v-carousel
      hide-delimiters
      class="mt-4"
      height="340"
      :continuous="false"
    >
      <template v-for="(_, index) in books">
        <v-carousel-item
          v-if="(index + 1) % numberOfBooksToDisplay === 1 || numberOfBooksToDisplay === 1"
          :key="index"
        >
          <v-row>
            <template v-for="(n, i) in numberOfBooksToDisplay">
              <book-item
                v-if="(+index + i) < books.length"
                :key="i"
                :book-image-link="books[+index + i].imageLinks.thumbnail"
                :book-title="books[+index + i].title"
              />
            </template>
          </v-row>
        </v-carousel-item>
      </template>
    </v-carousel>
  </v-container>
</template>

<script>
import BookItem from "@/components/home/BookItem";

export default {
  name: "BookCategoryCarousel",
  components: {BookItem},
  props: {
    books: {
      type: Array,
      required: true,
    },
    bestSellerCategory: {
      type: String,
      required: true,
    }
  },
  computed: {
    numberOfBooksToDisplay() {
      if (this.$vuetify.breakpoint.xl) {
        return 5;
      }
      if (this.$vuetify.breakpoint.lg) {
        return 4;
      }
      if (this.$vuetify.breakpoint.md) {
        return 3;
      }
      return 2;
    }
  }
}
</script>

