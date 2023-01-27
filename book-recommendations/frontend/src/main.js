import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import '@mdi/font/css/materialdesignicons.css'
import vuetify from './plugins/vuetify'

// Import the plugin here
import {Auth0Plugin} from './auth';

const authConfig = require("./auth/auth_config");
const domain = "bestreadsprod.eu.auth0.com";
const clientId = "jNnYObZDCGx46fo9rbRaHqJbwrZyoNBX";

Vue.config.productionTip = false
Vue.use(Vuetify)

//Install the authentication plugin here
Vue.use(Auth0Plugin, {
  domain,
  clientId,
  onRedirectCallback: appState => {
    router.push(
      appState && appState.targetUrl
        ? appState.targetUrl
        : window.location.pathname
    );
  }
});

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  vuetify,
  icons: {
    iconfont: 'mdi'
  },
  render: h => h(App)
}).$mount('#app')
