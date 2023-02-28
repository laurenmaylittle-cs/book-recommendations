<template>
  <v-sheet
    rounded
    color="transparent"
  >
    <v-subheader v-if="isEnabledInACollection">
      Book is in the following collections:
    </v-subheader>
    <template
      v-for="(collection, index) in collectionsMap"
    >
      <v-chip
        v-if="collection.enabled"
        :key="collection.id"
        :color="collectionColors[index]"
        class="ma-2"
        @click="navigateToCollection(collection)"
      >
        {{ collection.name }}
      </v-chip>
    </template>
    <v-col v-if="collectionsLoaded">
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
                      :append-outer-icon="shouldCreateCollection ? 'mdi-plus-circle' : 'mdi-close-circle'"
                      clear-icon="mdi-close-circle"
                      clearable
                      :rules="collectionValidationRules"
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
              @click="clearDialogAndResetState"
            >
              Cancel
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>
  </v-sheet>
</template>

<script>
import {generatePastelColors} from "@/util/util";
import {getUserCollections, saveUserCollections} from "@/api/view-book";
import {mapGetters} from "vuex";
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
    collectionColors: [],
    collectionsLoaded: false,
  }),
  computed: {
    collectionButtonText() {
      return this.isEnabledInACollection
        ? "Edit collections"
        : "Add book to collections";
    },
    isEnabledInACollection() {
      return this.collectionsMap.filter((x) => x.enabled === true).length > 0;
    },
    isCollectionsChanged() {
      //deep compare of the two
      return JSON.stringify(this.collectionsToUpdate) !== JSON.stringify(this.collectionsMap);
    },
    shouldCreateCollection() {
      return this.newCollectionName.length > 0;
    },
    ...mapGetters(['collectionValidationRules'])
  },
  async mounted() {
    await this.getUsersCollections();
    this.$emit('collections-loaded');
  },
  methods: {
    createCollection() {
      if (!this.$refs.form.validate()) {
        return;
      }
      //negative id to indicate that this is a new collection
      this.collectionsToUpdate.push({
        id: -(this.collectionsToUpdate.length + 1),
        name: this.newCollectionName,
        enabled: false,
      });
      this.newCollectionName = "";
      this.showInput = false;
    },
    async getUsersCollections() {
      const token = await this.$auth.getTokenSilently();
      const userCollections = await getUserCollections(token, this.bookData.isbn);

      this.collectionsMap = userCollections.sort((a, b) => a.name.localeCompare(b.name));
      this._computeCollectionColors();
      this.collectionsToUpdate = this._createDeepCopyOfCollections();
      this.collectionsLoaded = true;
    },
    async saveCollection() {
      this.collectionSaveInProgress = true;
      const token = await this.$auth.getTokenSilently();
      const collectionsAndBook = {
        collections: this.collectionsToUpdate,
        book: this.bookData,
      };
      const savedCollections = await saveUserCollections(token, collectionsAndBook);

      this.collectionsMap = savedCollections.sort((a, b) => a.name.localeCompare(b.name));
      this._computeCollectionColors();
      this.collectionsToUpdate = this._createDeepCopyOfCollections();
      this.collectionSaveInProgress = false;
      this.clearDialog();
    },
    async navigateToCollection(collection) {
      await this.$router.push({name: 'bookshelfBooksPage'});
      EventBus.$emit('load-collection-books', {
        collectionId: collection.id,
        collectionName: collection.name
      });
    },
    clearDialogAndResetState() {
      this.collectionsToUpdate = this._createDeepCopyOfCollections();
      this.clearDialog();
    },
    clearDialog() {
      this.dialog = false;
      this.clear();
    },
    clear() {
      this.newCollectionName = '';
      this.showInput = false;
    },
    _computeCollectionColors() {
      generatePastelColors(this.collectionColors, this.collectionsMap.length, {min: 50, max: 60},
        {min: 80, max: 90})
    },
    _createDeepCopyOfCollections() {
      return JSON.parse(JSON.stringify(this.collectionsMap));
    },
  }
}
</script>

<style scoped>

</style>
