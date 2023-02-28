<template>
  <v-container>
    <v-sheet color="background">
      <v-tabs
        background-color="transparent"
        color="background"
        :vertical="!isAPhone"
      >
        <v-tab>Collections</v-tab>
        <v-tab>Wishlist</v-tab>
        <v-tab-item>
          <v-sheet
            color="background"
            min-height="200"
          >
            <create-collection-modal
              ref="createModal"
              @collection-created="getNewCollection"
            />
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
            <v-row class="mt-3">
              <collection-item
                v-for="(collection, index) in collections"
                :key="collection.id"
                :collection-name="collection.name"
                :collection-color="colors[index]"
                @click.native="goToCollection(collection.id)"
              />
            </v-row>
          </v-sheet>
        </v-tab-item>
        <v-tab-item>
          <v-sheet
            min-height="200"
            color="background"
          >
            <p>
              Wishlist
            </p>
          </v-sheet>
        </v-tab-item>
      </v-tabs>
    </v-sheet>
  </v-container>
</template>

<script>
import CreateCollectionModal from "@/components/bookshelf/CreateCollectionModal.vue";
import CollectionItem from "@/components/bookshelf/CollectionItem.vue";
import {getCollection, getCollectionsForUser} from "@/api/bookshelf";
import {generatePastelColors} from "@/util/util";
import {EventBus} from "@/event-bus";

export default {
  name: "CollectionsPage",
  components: {CollectionItem, CreateCollectionModal},
  data: () => ({
    collections: [],
    colors: [],
    isLoading: true,
  }),
  computed: {
    isAPhone() {
      return this.$vuetify.breakpoint.xs;
    }
  },
  watch: {
    collections: {
      handler() {
        this._computeCollectionColors();
      },
      deep: true
    }
  },
  async mounted() {
    await this.getCollections();
  },
  methods: {
    async getCollections() {
      this.collections = await getCollectionsForUser(await this.$auth.getTokenSilently());
      this._computeCollectionColors();
      this.isLoading = false;
    },
    async getNewCollection(url) {
      const pathForAccessingCollection = new URL(url).pathname;
      const newCollection = await getCollection(pathForAccessingCollection,
        await this.$auth.getTokenSilently());
      const index = this.collections.findIndex(collection => collection.name > newCollection.name);
      if (index === -1) {
        this.collections.push(newCollection);
      } else {
        this.collections.splice(index, 0, newCollection);
      }
      this._computeCollectionColors();
      this.$refs.createModal.resetState();
    },
    _computeCollectionColors() {
      generatePastelColors(this.colors, this.collections.length, {min: 50, max: 60},
        {min: 80, max: 90})
    },
    async goToCollection(collectionId) {
      await this.$router.push({name: 'bookshelfBooksPage'});
      EventBus.$emit('load-collection-books', {
        collectionId: collectionId,
        collectionName: this.collections.find(collection => collection.id === collectionId).name
      });
    }
  }
}
</script>

<style scoped>
.background {
  background-color: #E4E4E4 !important;
}
</style>
