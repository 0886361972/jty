// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from "vue";
import App from "./Xpj.vue";
import router from "./router";
import axios from "axios";
import VueAxios from "vue-axios";

//引入新葡京
import iView from "iview";
import "../my-theme/dist/iview.css";
Vue.use(iView);


//import 'iview/dist/styles/iview.css';    // 使用 CSS

Vue.config.productionTip = false


Vue.use(VueAxios, axios)
axios.defaults.baseURL = 'http://14.47.125.43:9999/';
//axios.defaults.baseURL ='http://localhost:8080/';

/* eslint-disable no-new */

/*
 // 引入全部组件
 import Vue from 'vue';
 import Mint from 'mint-ui';
 Vue.use(Mint);
 // 按需引入部分组件
 import { Cell, Checklist } from 'minu-ui';
 Vue.component(Cell.name, Cell);
 Vue.component(Checklist.name, Checklist);
 */


/*
 import Mint from 'mint-ui';
 Vue.use(Mint);


 import BootstrapVue from 'bootstrap-vue';

 Vue.use(BootstrapVue);
 import 'bootstrap/dist/css/bootstrap.css'
 import 'bootstrap-vue/dist/bootstrap-vue.css'
*/


new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: {App}
})
