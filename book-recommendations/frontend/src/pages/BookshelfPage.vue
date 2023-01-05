<template>
  <v-container>
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
    getHoverEffect(hover) {
      return hover ? "blue-grey lighten-4" : "transparent";
    },
  },
}
</script>

<style scoped>

</style>
