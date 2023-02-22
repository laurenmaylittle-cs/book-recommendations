<template>
  <div v-if="collectionsLoaded">
    <v-sheet
      rounded
      color="transparent"
    >
      <v-subheader v-if="isEnabledInACollection">
        Book in following collections:
      </v-subheader>
      <template
        v-for="(collection, index) in collectionsMap"
      >
        <v-chip
          v-if="collection.enabled"
          :key="collection.id"
          :color="colors[index]"
          class="ma-2"
        >
          {{ collection.name }}
        </v-chip>
      </template>
      <v-col>
        <v-btn
          color="primary"
          @click="dialog = true"
        >
          {{ collectionButtonText }}
        </v-btn>
      </v-col>
      <div>
        <v-dialog
          v-model="dialog"
          max-width="500"
        >
          <v-card>
            <v-card-text>
              <v-row
                no-gutters
              >
                <v-col
                  cols="12"
                  class="mb-2 mt-5"
                >
                  <template v-if="!showInput">
                    <v-btn
                      rounded
                      color="primary"
                      outlined
                      @click.prevent="showInput = true"
                    >
                      <v-icon left>
                        mdi-plus
                      </v-icon>
                      Create a new collection
                    </v-btn>
                    <!--                    <a-->
                    <!--                      href="#"-->
                    <!--                      @click.prevent="showInput = true"-->
                    <!--                    >Create a new collection</a>-->
                  </template>
                  <template v-else>
                    <v-form
                      ref="form"
                      onsubmit="return false"
                      @submit="createCollection"
                    >
                      <v-text-field
                        v-model="newCollectionName"
                        label="Collection name"
                        :append-outer-icon="shouldCreateCollection ? 'mdi-plus' : 'mdi-close-circle'"
                        clear-icon="mdi-close-circle"
                        clearable
                        :rules="collectionNameValidation"
                        @click:append-outer="shouldCreateCollection ? createCollection() : clear()"
                        @click:clear="clear"
                        @keydown.enter="createCollection"
                      />
                    </v-form>
                  </template>
                </v-col>
              </v-row>
              <v-row no-gutters>
                <v-col
                  v-for="(collection, index) in collectionsToUpdate"
                  :key="index"
                  cols="6"
                >
                  <v-checkbox
                    v-model="collection.enabled"
                    :label="collection.name"
                  />
                </v-col>
              </v-row>
            </v-card-text>
            <v-card-actions>
              <v-btn
                v-if="isCollectionsChanged"
                color="primary"
                text
                @click="saveCollection"
              >
                Save
                <v-progress-circular
                  v-if="collectionSaveInProgress"
                  indeterminate
                  color="primary"
                />
              </v-btn>
              <v-btn
                color="secondary"
                text
                @click="clearDialog"
              >
                Cancel
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </div>
    </v-sheet>
  </div>
</template>

<script>
import axios from "axios";
import {generatePastelColors} from "@/util/util";
import {EventBus} from "@/event-bus";

export default {
  name: "BookCollections",
  props: {
    bookData: {
      type: Object,
      required: true
    }
  },
  data: () => ({
    dialog: false,
    showInput: false,
    newCollectionName: '',
    collectionsMap: [],
    collectionsToUpdate: [],
    collectionSaveInProgress: false,
    colors: [],
    collectionsLoaded: false,
    collectionNameValidation: [
      v => !!v || 'Collection name is required',
      v => (v && v.length <= 15) || 'Collection name must be less than 15 characters'
    ]
  }),
  computed: {
    collectionButtonText() {
      if (this.collectionsMap.filter(x => x.enabled === true).length > 0) {
        return 'Edit collections';
      } else {
        return 'Add book to collections';
      }
    },
    isEnabledInACollection() {
      return this.collectionsMap.filter(x => x.enabled === true).length > 0
    },
    isCollectionsChanged() {
      return JSON.stringify(this.collectionsToUpdate) !== JSON.stringify(this.collectionsMap);
    },
    shouldCreateCollection() {
      return this.newCollectionName.length > 0;
    },

  },
  async mounted() {
    await this.getRelatedCollections();
    this.collectionsLoaded = true;
    this.$emit('collections-loaded');
  },
  methods: {
    createCollection() {
      if (!this.$refs.form.validate()) {
        return;
      }
      this.collectionsToUpdate.push(
        {id: -(this.collectionsToUpdate.length + 1), name: this.newCollectionName, enabled: false});
      this.newCollectionName = '';
      this.showInput = false;
    },
    async getRelatedCollections() {
      const token = await this.$auth.getTokenSilently();
      const url = "/api/private/bookshelf/collections/book";
      const params = new URLSearchParams();
      params.append("isbn", this.bookData.isbn);

      const result = await axios.get(url, {
        params: params,
        headers: {
          Authorization: `Bearer ${token}`
        }
      });

      const sortedCollections = result.data.sort((a, b) => {
        if (a.name < b.name) {
          return -1;
        }
        if (a.name > b.name) {
          return 1;
        }
        return 0;
      });

      this.collectionsMap = sortedCollections;
      this._computeCollectionColors();
      this.collectionsToUpdate = JSON.parse(JSON.stringify(sortedCollections));
    },
    async saveCollection() {
      this.collectionSaveInProgress = true;
      const token = await this.$auth.getTokenSilently();
      const url = "/api/private/bookshelf/collections/book/update"

      // const body = [
      //   ...this.collectionsToUpdate
      // ]
      const book = {
        title: this.bookData.title,
        author: this.bookData.authors.join(', '),
        thumbnail: this.bookData.imageLinks.thumbnail,
        isbn: this.bookData.isbn,
      }

      const body = {
        collections: this.collectionsToUpdate,
        book: book
      }
      const result = await axios.put(url, body, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      const sortedCollections = result.data.sort((a, b) => {
        if (a.name < b.name) {
          return -1;
        }
        if (a.name > b.name) {
          return 1;
        }
        return 0;
      });

      this.collectionsMap = sortedCollections;
      this._computeCollectionColors();
      this.collectionsToUpdate = JSON.parse(JSON.stringify(sortedCollections));
      this.collectionSaveInProgress = false;
      this.clearDialog();
    },
    _computeCollectionColors() {
      generatePastelColors(this.colors, this.collectionsMap.length, {min: 50, max: 60},
        {min: 80, max: 90})
    },
    clearDialog() {
      this.dialog = false;
      this.clear();
    },
    clear() {
      this.newCollectionName = '';
      this.showInput = false;
    }
  }
}
</script>

<style scoped>

</style>
