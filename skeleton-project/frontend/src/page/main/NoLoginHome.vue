<template>
  <div id="nologinHome">
    <b-navbar class="nologinMenue ml-auto mr-auto" type="dark" v-if="!constants.IS_LOGED_IN">
      <b-navbar-nav class="ml-auto mr-auto">
        <b-nav-item class="mr-5" @click="scroll('Home')">Home</b-nav-item>
        <!-- <b-nav-item class="mr-5" href="#Home">Home</b-nav-item> -->
        <b-nav-item class="mr-5" @click="scroll('AboutUs')">About Us</b-nav-item>
        <b-nav-item class="mr-5" @click="scroll('Ourservice')">Our Service</b-nav-item>
        <b-nav-item class="mr-5" @click="scroll('Contact')">Contact</b-nav-item>
      </b-navbar-nav>
    </b-navbar>
    <div id="Home">
      <b-img :src="require(`@/assets/img/logo2-2.png`)" contain width="500" id="HomeLogo"></b-img>
      <div style="text-align: center;" class="mt-3">
        <b-button
          class="LoginBtn mr-5"
          @click="$bvModal.show('LoginModal')"
          style="text-align: center; cursor: pointer;"
        >로그인</b-button>
        <b-modal id="LoginModal" hide-footer>
          <template v-slot:modal-title>로그인</template>
          <div class="d-block text-center">
            <div class="custom-login-style">
              <b-form-input
                v-model="emailOrUid"
                id="emailOrUid"
                placeholder="이메일 혹은 아이디를 입력해주세요"
                type="text"
                class="mt-1 mb-1"
                style="width: 60%;"
              ></b-form-input>

              <b-form-input
                @keyup.enter="login"
                v-model="password"
                type="password"
                placeholder="영문, 숫자 혼용 8자 이상"
                class="mt-1 mb-1"
                style="width: 60%;"
              ></b-form-input>
              <span
                class="findUser text-secondary"
                @click="$bvModal.show('Find')"
                style="text-align: center; cursor: pointer;"
              >아이디 또는 비밀번호를 잊으셨나요?</span>
              <b-modal id="Find" hide-footer v-on:close="closeModal()" ref="Modal">
                <template v-slot:modal-title>
                  <p class="mt-2 mb-2">아이디/비밀번호 찾기</p>
                </template>
                <div class="d-block text-center">
                  <FindUserByEmail />
                </div>
              </b-modal>
            </div>
            <b-button class="mt-3" @click="login">로그인</b-button>
          </div>
        </b-modal>
        <b-button
          class="JoinBtn"
          @click="$bvModal.show('JoinModal')"
          style="text-align: center; cursor: pointer;"
        >회원가입</b-button>
        <b-modal id="JoinModal" hide-footer v-on:close="closeModal()" ref="Modal">
          <template v-slot:modal-title>
            <p class="mt-2 mb-2">회원가입</p>
            <small class="mb-2">회원 정보를 입력해주세요.</small>
          </template>
          <div class="d-block text-center">
            <Join />
          </div>
        </b-modal>
      </div>
    </div>

    <div id="AboutUs">
      <div id="AboutUsCarousel">
        <b-carousel
          id="carousel-fade"
          style="text-shadow: 0px 0px 2px #000"
          :interval="4000"
          fade
          indicators
          img-width="1024"
          img-height="480"
        >
          <b-carousel-slide
            caption="활동을 기록해보세요."
            img-src="https://cdn.pixabay.com/photo/2017/07/31/21/59/computer-2561477_1280.jpg"
          ></b-carousel-slide>
          <b-carousel-slide
            caption="프로젝트를 보기 쉽게 정리 해 보세요."
            img-src="https://cdn.pixabay.com/photo/2015/04/20/13/17/work-731198_1280.jpg"
          ></b-carousel-slide>
          <b-carousel-slide
            caption="금세 나만의 포트폴리오가 될 거에요."
            img-src="https://cdn.pixabay.com/photo/2012/02/22/19/38/business-15498_1280.jpg"
          ></b-carousel-slide>
          <b-carousel-slide
            caption="꿈을 향한 당신의 힘찬 첫 걸음을 시작해보세요!"
            img-src="https://cdn.pixabay.com/photo/2018/02/27/06/30/skyscraper-3184798_1280.jpg"
          ></b-carousel-slide>
        </b-carousel>
      </div>
    </div>

    <div class="BackColor">
      <div id="Ourservice">
        <b-row align-h="center" class="pt-5 mb-1">
          <h5>취업을 위해 열심히 참여했던 활동과 프로젝트들...</h5>
        </b-row>
        <b-row align-h="center" class="m-1">
          <h5>정작 필요할 땐 안보이고, 기억이 나지 않아 어려움을 겪고 계신가요?</h5>
        </b-row>
        <b-row align-h="center" class="m-1">
          <h5>
            그렇다면
            <div id="smallLogo1">
              <b-img :src="require(`@/assets/img/logo2-6.png`)" contain width="100"></b-img>
            </div>에서 아래와 같은 기능을 사용해보세요.
          </h5>
        </b-row>
        <b-row align-h="center">
          <b-col cols="3">
            <div id="service">
              <b-icon icon="list-ul" font-scale="5" class="m-2"></b-icon>
              <h2 class="mt-2 mb-5">정리</h2>
              <p>프로젝트 및 경험을</p>
              <p>보기 쉽게 정리하고,</p>
            </div>
          </b-col>
          <b-col cols="3">
            <div id="service">
              <b-icon icon="tags-fill" font-scale="5" class="m-2"></b-icon>
              <h2 class="mt-2 mb-5">태그</h2>
              <p>각 요소마다 태그를 달아</p>
              <p>활용성을 높이고,</p>
            </div>
          </b-col>
          <b-col cols="3">
            <div id="service">
              <b-icon icon="check2-square" font-scale="5" class="m-2"></b-icon>
              <h2 class="mt-2 mb-5">활용</h2>
              <p>한 눈에 필요한 자료를</p>
              <p>확인할 수 있습니다.</p>
            </div>
          </b-col>
        </b-row>
        <b-row align-h="center" class="m-1">
          <h5>당신의 기록이 쌓이고 나면</h5>
        </b-row>
        <b-row align-h="center" class="m-1">
          <h5>태그를 기반으로 나만의 키워드를 찾을 수 있고,</h5>
        </b-row>
        <b-row align-h="center" class="m-1">
          <h5>포트폴리오 제출용 링크도 생성 가능하답니다.</h5>
        </b-row>
        <b-row align-h="center" class="mt-3 mb-5">
          <h4 style="font-weight:bold;">지금 시작해보세요!</h4>
        </b-row>
      </div>

      <div id="Contact">
        <h3 class="m-3">Contact for</h3>
        <p>TeamLP@nocontact.here</p>
        <p>123-456-7890</p>
      </div>
      <!-- </b-row>
      </b-container>-->
    </div>
  </div>
</template>

<script>
import constants from "../../lib/constants.js";
import axios from "axios";
import EventBus from "../post/EventBus";

import Join from "../../page/user/Join.vue";
import FindUserByEmail from "../../page/user/FindUserByEmail.vue";

//const SERVER_URL = "http://localhost:8080";

export default {
  name: "NoLoginHome",
  components: { FindUserByEmail, Join },
  props: [],
  computed: {},
  watch: {},
  created() {
    if (constants.IS_LOGED_IN) {
      this.$router.push({ name: constants.URL_TYPE.MAIN.LOGINHOME });
    }
    // EventBus.$on("closeModal", this.$refs.Modal.hide("LoginModal"));
  },
  data: () => {
    return {
      constants,
      emailOrUid: "",
      password: "",
      isLogedin: false,
      dismissSecs: 5,
      dismissCountDown: 0,
      // images: {
      //   logo:
      // },
    };
  },
  mounted() {},
  methods: {
    countDownChanged(dismissCountDown) {
      this.dismissCountDown = dismissCountDown;
    },
    showAlert() {
      this.dismissCountDown = this.dismissSecs;
    },
    login() {
      axios
        .post(this.$SERVER_URL + "/account/login/", {
          uidOrEmail: this.emailOrUid,
          password: this.password,
        })
        .then((response) => {
          console.log(response);
          if (response.data.data == "success") {
            localStorage.setItem("uid", response.data.object.uid);
            localStorage.setItem("nickname", response.data.object.nickname);
            localStorage.setItem("email", response.data.object.email);
            localStorage.setItem("password", response.data.object.password);
            localStorage.setItem("check", false);
            constants.IS_LOGED_IN = true;
            // alert("로그인 되었습니다!");
            // location.href = `http://localhost:3000/#/${response.data.object.uid}`;

            // this.$router.go({ path: `/${response.data.object.uid}` });
            this.$router.push({ path: `/${response.data.object.uid}` });
          } else {
            alert("이메일 또는 비밀번호가 잘못되었습니다.");
          }
        })
        .catch((error) => {
          alert("이메일 또는 비밀번호가 잘못되었습니다.");
          console.log(error.response);
        });
    },
    scroll(id) {
      document.getElementById(id).scrollIntoView();
    },
    // closeModal() {
    //   console.log("클로즈모달 여기까지 오니?");
    //   $bvModal.hide("LoginModal");
    // },
  },
};
</script>

<style>
#nologinHome {
  background-color: #270949;
  padding-top: 50px;
  color: #e7e7e7;
  text-align: center;
}
#nologinHomeContainer {
  width: 100%;
  margin: 0;
}
.BackColor {
  background-color: #e7e7e7;
}
#Home {
  margin: 150px auto;
}
#AboutUs {
  margin: 0;
  padding: 0;
  width: 100%;
}
#AboutUsCarousel {
  margin: 0 auto;
}
#Ourservice {
  color: black;
  width: 100%;
  text-align: center;
  padding: 50px 0;
}
#smallLogo1 {
  display: inline;
  margin-left: 10px;
  margin-right: -5px;
}
#service {
  background-color: #b6b6b6;
  border-radius: 10px;
  padding: 40px 20px;
  margin: 50px 0;
}
#Contact {
  color: black;
  padding: 30px 0;
  margin-bottom: 40px;
}
#findUser:hover {
  cursor: pointer;
  text-align: center;
}
.custom-login-style {
  text-align: -webkit-center;
}
</style>
