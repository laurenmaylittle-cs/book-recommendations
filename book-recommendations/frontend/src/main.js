import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import '@mdi/font/css/materialdesignicons.css'
import vuetify from './plugins/vuetify'

Vue.config.productionTip = false
Vue.use(Vuetify)

new Vue({
  router,
  store,
  vuetify,
  icons: {
    iconfont: 'mdi'
  },
  render: h => h(App)
}).$mount('#app')
