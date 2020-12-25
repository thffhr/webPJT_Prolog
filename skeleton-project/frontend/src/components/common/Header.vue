<template>
  <div id="header" v-if="isHeader && constants.IS_LOGED_IN">
    <h1 id="blogLogo">
      <!-- 로그인 시에만 블로그 로고 보이기 -->
      <!-- <router-link
        v-if="constants.IS_LOGED_IN"
        v-bind:to="{ name: constants.URL_TYPE.MAIN.LOGINHOME }"
      >-->
      <!-- 로고 이미지 -->
      <b-img :src="require(`@/assets/img/logo2-5.png`)" width="50" @click="goToMain"></b-img>
      <!-- </router-link> -->
    </h1>
    <!-- 로그인 시에만 사용자 메뉴 보이기 -->
    <div id="userMenue">
      <b-dropdown
        id="dropdown"
        right
        text="Right align"
        variant="text"
        toggle-class="text-decoration-none"
      >
        <template v-slot:button-content v-if="constants.IS_LOGED_IN">
          <!-- 사용자의 프로필 사진 -->
          <img
            class="headerProfile mr-2"
            ref="headerProfile"
            :src="profileImgsrc"
            style="width: 2rem; height: 2rem;"
          />

          <div id="userName">{{ nickname }}</div>님, 환영합니다.
        </template>

        <b-dropdown-item href="#" @click="logout">로그아웃</b-dropdown-item>
        <b-dropdown-item href="#" @click="$bvModal.show('confirmPassword')">
          회원정보 수정
          <b-modal id="confirmPassword" hide-footer>
            <template v-slot:modal-title>비밀번호 재확인</template>
            <div class="d-block text-center">
              <ConfirmPassword />
            </div>
          </b-modal>
        </b-dropdown-item>
        <b-dropdown-item href="#" @click="copyUrl">포트폴리오 제출용 링크</b-dropdown-item>
      </b-dropdown>
    </div>
  </div>
</template>

<script>
import constants from "../../lib/constants";
import ConfirmPassword from "../../page/user/ConfirmPassword.vue";
import axios from "axios";

export default {
  name: "Header",
  components: { ConfirmPassword },
  props: ["isHeader"],
  computed: {
    nickname: function () {
      return localStorage["nickname"];
    },
  },
  watch: {},
  data: function () {
    return {
      constants,
      uid: localStorage["uid"],
      profileImgsrc:
        "https://cdn4.iconfinder.com/data/icons/small-n-flat/24/user-alt-512.png",
    };
  },
  created() {
    if (constants.IS_LOGED_IN) {
      axios
        .get(this.$SERVER_URL + `/account/ckprofile/${localStorage["uid"]}`)
        .then((response) => {
          if (response.data.status) {
            if (response.data.object == null) {
            } else {
              this.profileImgsrc =
                this.$SERVER_URL + `/account/profile/${localStorage["uid"]}`;
            }
          } else {
            console.log("프로필 이미지 가져오는 중 에러 발생");
          }
        });
    }
  },
  methods: {
    logout() {
      localStorage.removeItem("uid");
      localStorage.removeItem("nickname");
      localStorage.removeItem("email");
      localStorage.removeItem("password");
      localStorage.removeItem("check");
      constants.IS_LOGED_IN = false;
      this.$router.push({ name: constants.URL_TYPE.MAIN.NOLOGINHOME });
      this.$router.go();
    },
    goToMain() {
      this.$router.push({ path: `/${localStorage["uid"]}` });
    },
    copyUrl() {
      var url = `http://i3a605.p.ssafy.io/#/${localStorage["uid"]}`;
      // window.clipboardData.setData("Text", url);
      const copyURL = document.createElement("input");
      copyURL.value = url;
      document.body.appendChild(copyURL);
      copyURL.select();
      document.execCommand("copy");
      document.body.removeChild(copyURL);
      alert("링크가 복사되었습니다");
    },
  },
};
</script>

<style>
#header {
  display: block;
  /* position: absolute; */
  top: 0;
  /* padding: 0px; */
  background-color: #e7e7e7;
  height: 65px;
}
#blogLogo {
  position: absolute;

  padding: 5px 40px;
  margin: 0;
}
#blogLogo:hover {
  cursor: pointer;
}
#userMenue {
  position: absolute;
  right: 0;
  padding: 10px;
  /* float: right; */
}
.headerProfile {
  border-radius: 50%;
  border-color: #ffcabd;
  background-color: white;
}
#userName {
  display: inline;
}
</style>
