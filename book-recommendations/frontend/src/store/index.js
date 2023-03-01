import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    collectionValidationRules: [
      v => !!v || 'Collection name is required',
      v => (v && v.length <= 15)
        || 'Collection name must be less than 15 characters'
    ]
  },
  getters: {
    collectionValidationRules: state => state.collectionValidationRules
  },
  mutations: {},
  actions: {},
  modules: {}
})
