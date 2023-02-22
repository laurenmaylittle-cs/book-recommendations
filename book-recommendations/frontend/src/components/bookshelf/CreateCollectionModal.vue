<template>
  <v-container>
    <v-row>
      <v-btn
        ref="createCollectionBtn"
        class="mt-3"
        color="primary"
        @click="openDialog"
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
            <v-form
              ref="form"
              onsubmit="return false"
              @submit="createCollection"
            >
              <v-container>
                <v-row>
                  <v-col>
                    <v-text-field
                      v-model="collectionName"
                      label="Collection name"
                      :rules="collectionValidationRules"
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
import {mapGetters} from "vuex";

export default {
  name: "CreateCollectionModal",
  data: () => ({
    dialog: false,
    collectionName: "",
    collectionUpdateInProgress: false,
  }),
  computed: {
    ...mapGetters(['collectionValidationRules'])
  },
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
    openDialog() {
      this.dialog = true;
      this.$nextTick(() => {
        //workaround for vuetify bug not clearing validation  when submitting forms
        this.$refs.form.resetValidation();
      });
    },
    resetState() {
      this.collectionName = "";
      this.dialog = false;
      this.collectionUpdateInProgress = false;
      this.$refs.form.reset();
    }
  }
}
</script>

<style scoped>
</style>
