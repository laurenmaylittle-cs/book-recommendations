<template>
  <v-container>
    <v-row>
      <v-btn
        color="#26c6da"
        @click="dialog = true"
      >
        <v-icon>mdi-plus</v-icon>
        Create Collection
      </v-btn>
    </v-row>
    <v-row>
      <v-dialog
        v-model="dialog"
        max-width="290"
      >
        <v-card>
          <!--a form to create a new collection-->
          <v-card-title>
            <span class="headline">New collection</span>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <v-col>
                  <v-text-field
                    v-model="collectionName"
                    label="Collection name"
                    required
                  />
                </v-col>
              </v-row>
            </v-container>
            <v-card-actions>
              <v-spacer/>
              <v-btn
                color="green darken-1"
                text
                @click="createCollection"
              >
                Create
              </v-btn>
            </v-card-actions>
          </v-card-text>
        </v-card>
      </v-dialog>
    </v-row>
  </v-container>
</template>

<script>

import axios from "axios";

export default {
  name: "CreateCollectionModal",
  data: () => ({
    dialog: false,
    collectionName: ""
  }),
  methods: {
    async createCollection() {
      const url = "/api/private/bookshelf/collections";
      const params = new URLSearchParams({name: this.collectionName});
      const config = {
        headers: {
          Authorization: `Bearer ${await this.$auth.getTokenSilently()}`
        }
      };
      const createResult = await axios.post(url, params, config);

      this.$emit("collection-created", createResult.headers.location);
      this.dialog = false;
    },
  }
}
</script>

<style scoped>

</style>
