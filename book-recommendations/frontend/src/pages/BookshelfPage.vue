<template>
  <v-container>
    <v-row
      v-if="isLoading"
      class="justify-center"
    >
      <v-progress-circular
        :size="70"
        :width="7"
        color="purple"
        indeterminate
      />
    </v-row>
    <v-sheet color="background">
      <v-tabs
        background-color="transparent"
        color="background"
        vertical
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
            <v-row class="mt-3">
              <collection-item
                v-for="(collection, index) in collections"
                :key="collection.id"
                :collection-name="collection.name"
                :collection-color="colors[index]"
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
import {getCollection, getCollectionsForUser} from "@/api/bookshelf-api-calls";

export default {
  name: "CollectionsPage",
  components: {CollectionItem, CreateCollectionModal},
  data: () => ({
    collections: [],
    colors: [],
    isLoading: true,
  }),
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
      this.collections.push(newCollection);
      this._computeCollectionColors();
      this.$refs.createModal.resetState();
    },
    _computeCollectionColors() {
      for (let i = this.colors.length; i < this.collections.length; i++) {
        const hue = Math.floor(Math.random() * 360);
        const saturation = Math.floor(Math.random() * 20 + 50);
        const lightness = Math.floor(Math.random() * 20) + 80;

        const color = `hsl(${hue}, ${saturation}%, ${lightness}%)`;
        this.colors.includes(color) ? i-- : this.colors.push(color);
      }
    },
  }
}
</script>

<style scoped>
.background {
  background-color: #E4E4E4 !important;
}
</style>
