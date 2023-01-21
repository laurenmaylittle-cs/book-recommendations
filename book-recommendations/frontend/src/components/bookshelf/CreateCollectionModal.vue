<template>
  <v-container>
    <v-row>
      <v-btn
        ref="createCollectionBtn"
        class="mt-3"
        color="primary"
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
        @click:outside="resetState"
      >
        <v-card>
          <v-card-text>
            <v-form ref="form">
              <v-container>
                <v-row>
                  <v-col>
                    <v-text-field
                      v-model="collectionName"
                      label="Collection name"
                      :rules="[rules.required]"
                    />
                  </v-col>
                </v-row>
              </v-container>
              <v-card-actions>
                <v-btn
                  ref="saveCollectionBtn"
                  color="primary"
                  text
                  @click="createCollection"
                >
                  Create
                  <v-progress-circular
                    v-if="collectionUpdateInProgress"
                    indeterminate
                    color="primary"
                  />
                </v-btn>
                <v-btn
                  ref="closeBtn"
                  color="secondary"
                  text
                  @click="resetState"
                >
                  Cancel
                </v-btn>
              </v-card-actions>
            </v-form>
          </v-card-text>
        </v-card>
      </v-dialog>
    </v-row>
  </v-container>
</template>

<script>

import {createNewCollection} from "@/api/bookshelf";

export default {
  name: "CreateCollectionModal",
  data: () => ({
    dialog: false,
    collectionName: "",
    collectionUpdateInProgress: false,
    rules: {
      required: value => !!value || 'Required.',
    },
  }),
  methods: {
    async createCollection() {
      if (!this.$refs.form.validate()) {
        return;
      }
      this.collectionUpdateInProgress = true;
      const params = new URLSearchParams({name: this.collectionName});
      const createResult = await createNewCollection(params, await this.$auth.getTokenSilently());
      this.$emit("collection-created", createResult.headers.location);
    },
    resetState() {
      this.collectionName = "";
      this.dialog = false;
      this.collectionUpdateInProgress = false;
      this.$refs.form.reset();
    },
  }
}
</script>

<style scoped>
</style>
