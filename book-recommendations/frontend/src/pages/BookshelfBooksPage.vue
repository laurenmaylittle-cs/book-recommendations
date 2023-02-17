<template>
  <v-container>
    <v-row
      v-if="isLoading"
      class="justify-center pt-10"
    >
      <v-progress-circular
        :size="70"
        :width="7"
        color="primary"
        indeterminate
      />
    </v-row>
    <v-row v-if="!isLoading">
      <h1 class="mt-0 mb-5 ml-4">
        {{ collectionBooks.collectionName }}
      </h1>
      <v-btn
        text
        class="mt-1"
        @click="openDialog"
      >
        <v-icon>
          mdi-pencil
        </v-icon>
      </v-btn>
    </v-row>
    <v-row>
      <v-dialog
        v-model="dialog"
        max-width="290"
        @click:outside="dialog=false"
      >
        <v-card>
          <v-card-text>
            <v-form
              ref="form"
              onsubmit="dialog=false"
              @submit="updateCollection"
            >
              <v-container>
                <v-row>
                  <v-col>
                    <v-text-field
                      v-model="collectionName"
                      label="Collection name"
                      :rules="collectionNameValidation"
                    />
                  </v-col>
                </v-row>
              </v-container>
              <v-card-actions>
                <v-btn
                  ref="saveCollectionBtn"
                  color="primary"
                  text
                  @click="updateCollection"
                >
                  Update
                </v-btn>
                <v-btn
                  ref="closeBtn"
                  color="secondary"
                  text
                  @click="dialog=false"
                >
                  Cancel
                </v-btn>
              </v-card-actions>
            </v-form>
          </v-card-text>
        </v-card>
      </v-dialog>
    </v-row>
    <v-row no-gutters>
      <v-col
        v-for="book in collectionBooks.booksInCollection"
        :key="book.title"
      >
        <book-details
          :author="book.author"
          :title="book.title"
          :thumbnail="book.thumbnail"
          :isbn="book.isbn"
          :published-date="book.publishedDate"
        />
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
import BookDetails from "@/components/search/BookDetails";
import {getBooksInCollection, updateCollectionName} from "@/api/bookshelfBooks";

export default {
  name: "CollectionBooksPage",
  components: {BookDetails},
  data: function () {
    return {
      collectionId: this.$route.params.collectionId,
      isLoading: true,
      collectionBooks: [],
      collectionNameValidation: [
        v => !!v || 'Collection name required',
        v => (v && v.length <= 16) || 'Collection name must be less than 16 characters'
      ],
      dialog: false,
      collectionName: ''
    }
  },
  async mounted() {
    await this.getBooksInCollection();
    this.isLoading = false;
    this.collectionName = this.collectionBooks.collectionName
  },
  methods: {
    async getBooksInCollection() {
      this.collectionBooks = await getBooksInCollection(this.collectionId,
        await this.$auth.getTokenSilently())
    },
    checkForMultipleAuthors(authors) {
      if (authors === undefined || authors === null) {
        return ""
      }
      let authorList = "";
      for (let i = 0; i < authors.length; i++) {
        authorList === "" ? authorList = authors[i] : authorList = authorList + ", " + authors[i]
      }
      return authorList
    },
    openDialog() {
      this.dialog = true;
      this.$nextTick(() => {
        //workaround for vuetify bug not clearing validation  when submitting forms
        this.$refs.form.resetValidation();
      });
    },
    async updateCollection() {
      if (!this.$refs.form.validate()) {
        return;
      }
      let token = await this.$auth.getTokenSilently();

      this.collectionBooks.collectionName = this.collectionName
      console.log("in method")
      await updateCollectionName(this.collectionId, token, this.collectionName)
      this.dialog = false;
      this.collectionUpdateInProgress = true;
    }
  }
}
</script>
