<template>
  <v-container>
    <v-sheet color="background">
      <v-tabs
        background-color="transparent"
        color="background"
        vertical
      >
        <v-tab>Collections</v-tab>
        <v-tab>Wishlist</v-tab>
        <v-tab-item>
          <v-sheet color="background">
            <create-collection-modal
              ref="createModal"
              @collection-created="getNewCollection"
            />
            <v-row class="mt-3">
              <collection-item
                v-for="(collection, index) in collections"
                :key="collection.id"
                :collection-name="collection.name"
                :collection-color="collectionColors[index]"
              />
            </v-row>
          </v-sheet>
        </v-tab-item>
        <v-tab-item>
          <v-sheet color="background">
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
import axios from "axios";
import CreateCollectionModal from "@/components/bookshelf/CreateCollectionModal.vue";
import CollectionItem from "@/components/bookshelf/CollectionItem.vue";

export default {
  name: "CollectionsPage",
  components: {CollectionItem, CreateCollectionModal},
  data: () => ({
    collections: [],
  }),
  computed: {
    collectionColors() {
      const colors = [];
      for (let i = 0; i < this.collections.length; i++) {
        const hue = Math.floor(Math.random() * 360);
        const saturation = Math.floor(Math.random() * 40) + 30;
        const lightness = Math.floor(Math.random() * 20) + 80;

        const color = `hsl(${hue}, ${saturation}%, ${lightness}%)`;
        colors.includes(color) ? i-- : colors.push(color);

        //move this to a method to prevent it from re-calculating on every new collection
      }
      return colors;
    },
  },
  mounted() {
    this.getCollections();
  },
  methods: {
    async getCollections() {
      const result = await axios.get("/api/private/bookshelf/collections", {
        headers: {
          Authorization: `Bearer ${await this.$auth.getTokenSilently()}`,
        },
      });
      this.collections = result.data;
    },
    async getNewCollection(url) {
      const resourcePath = new URL(url).pathname;
      const newCollection = await axios.get(resourcePath, {
        headers: {
          Authorization: `Bearer ${await this.$auth.getTokenSilently()}`,
        },
      });
      this.collections.push(newCollection.data);
      this.$refs.createModal.resetState();
    },
  },
}
</script>

<style scoped>
.background {
  background-color: #E4E4E4 !important;
}
</style>
