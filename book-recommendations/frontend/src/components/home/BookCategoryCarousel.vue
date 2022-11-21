<template>
  <v-container>
    <v-row>
      <v-col class="text-h6 cyan--text text--darken-4 d-flex justify-left">
        {{ bestSellerCategory }}
      </v-col>
    </v-row>
    <v-carousel
      class="mt-4"
      height="340"
      hide-delimiter-background
      :continuous="false"
    >
      <v-carousel-item>
        <v-row>
          <book-carousel-item
            v-for="book in books"
            :key="book.title"
            :book-image-link="book.imageLinks.thumbnail"
            :book-title="book.title"
          />
        </v-row>
      </v-carousel-item>
    </v-carousel>
  </v-container>
</template>

<script>
import BookCarouselItem from "@/components/home/BookCarouselItem";

export default {
  name: "BookCategoryCarousel",
  components: {BookCarouselItem},
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
}
</script>

