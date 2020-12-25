import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import Carousel3d from "vue-carousel-3d";
import VModal from "vue-js-modal";

import { BootstrapVue, BootstrapVueIcons } from "bootstrap-vue";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

Vue.use(BootstrapVue);
Vue.use(BootstrapVueIcons);

// 로그인 후 메인화면_프로젝트 목록 출력
Vue.use(Carousel3d);

//모달
Vue.use(VModal, { dynamicDefault: { draggable: true, resizable: true } });

// 서버 주소 전역 변수
// EC - "/api"
Vue.prototype.$SERVER_URL = "/api";
// Vue.prototype.$PAGE_URL = "/#";
// 개발 - "http://localhost:8080/api"
// Vue.prototype.$SERVER_URL = "http://localhost:8080/api";

/* eslint-disable no-new */
new Vue({
  el: "#app",
  router,
  components: { App },
  template: "<App/>",
});
