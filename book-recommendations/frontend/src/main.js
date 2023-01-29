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

import {audience, clientId, domain} from "./auth/auth_config.json";

Vue.config.productionTip = false
Vue.use(Vuetify)

//Install the authentication plugin here
Vue.use(Auth0Plugin, {
  domain,
  clientId,
  audience,
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
