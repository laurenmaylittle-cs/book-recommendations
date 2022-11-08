import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import '@mdi/font/css/materialdesignicons.css'
import vuetify from './plugins/vuetify'

// Import the Auth0 configuration
import {clientId, domain} from '../auth_config.json';
// Import the plugin here
import {Auth0Plugin} from './auth';

Vue.config.productionTip = false
Vue.use(Vuetify)

// Install the authentication plugin here
// Vue.use(Auth0Plugin, {
//   domain,
//   clientId,
//   onRedirectCallback: appState => {
//     router.push(
//       appState && appState.targetUrl
//         ? appState.targetUrl
//         : window.location.pathname
//     ).then(r => console.log("error"));
//   }
// });

Vue.use(Auth0Plugin, {
  domain,
  clientId
});

Vue.config.productionTip = false;

new Vue({
  router,
  store,

  icons: {
    iconfont: 'mdi'
  },

  vuetify,
  render: h => h(App)
}).$mount('#app')
