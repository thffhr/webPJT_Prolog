<template>
  <div id="app">
    <Header :isHeader="isHeader" />
    <Navbar />
    <router-view />
    <Footer />

  </div>
</template>

<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
<script
  src="https://kit.fontawesome.com/48447e1308.js"
  crossorigin="anonymous"
></script>
<script>
import Header from "./components/common/Header.vue";
import Navbar from "./components/common/Navbar.vue";
import Footer from "./components/common/Footer.vue";
import constants from "./lib/constants";

export default {
  name: "App",
  components: {
    Header,
    Navbar,
    Footer,
  },
  watch: {
    $route(to) {
      this.checkUrl(to.name);
    },
  },
  methods: {
    checkUrl(url) {
      let array = [constants.URL_TYPE.USER.LOGIN, constants.URL_TYPE.USER.JOIN];

      let isHeader = true;
      array.map((path) => {
        if (url === path) isHeader = false;
      });
      this.isHeader = isHeader;
    },
  },

  data: function() {
    return {
      isHeader: true,
      constants,
    };
  },
};
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700;900&display=swap");
* {
  font-family: "Noto Sans KR", sans-serif;
  word-break: keep-all;
}
#app {
  background-color: #e7e7e7;
}
html {
  background-color: #e7e7e7;
}
a:link {
  color: #000;
}
a:visited {
  color: #000;
}
</style>
