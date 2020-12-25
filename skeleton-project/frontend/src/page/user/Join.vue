<template>
  <div id="join">
    <!-- <div v-if="isJoin" style="padding:50px 0;">
      <h4>로그인 해주세요.</h4>
    </div>-->
    <b-container>
      <b-row class="my-1">
        <b-col role="group" cols="12">
          <b-form-text class="joinTitle mb-2" id="email-help">회원가입을 위해 이메일 인증이 필요합니다.</b-form-text>
        </b-col>
      </b-row>
      <b-row class="my-1">
        <b-col role="group" cols="9">
          <b-form-input
            id="uid"
            v-model="uid"
            :state="uidState"
            aria-describedby="uid-help uid-feedback"
            placeholder="아이디를 입력해주세요"
            trim
            type="text"
          ></b-form-input>
          <b-form-invalid-feedback id="uid-feedback">{{ uidFeedback }}</b-form-invalid-feedback>
        </b-col>
      </b-row>
      <b-row class="my-1">
        <b-col role="group" cols="9">
          <b-form-input
            id="nickName"
            v-model="nickName"
            :state="nickNameState"
            aria-describedby="nickName-help nickName-feedback"
            placeholder="닉네임을 입력해주세요"
            trim
            type="text"
          ></b-form-input>
          <b-form-invalid-feedback id="nickName-feedback">{{ nicknameFeedback }}</b-form-invalid-feedback>
        </b-col>
      </b-row>
      <b-row class="my-1">
        <b-col class="emailInput" role="group" cols="9">
          <div class="emailInput">
            <b-form-input
              id="email"
              v-model="email"
              :state="emailState"
              aria-describedby="email-help email-feedback"
              placeholder="이메일을 입력해주세요"
              trim
              type="text"
            ></b-form-input>
            <b-form-invalid-feedback id="email-feedback">{{ emailFeedback }}</b-form-invalid-feedback>
          </div>
        </b-col>
        <b-col class="align-self-center">
          <b-overlay spinner-variant="secondary" spinner-small :show="show" rounded="sm">
            <div :aria-hidden="show ? 'true' : null">
              <b-button
                size="sm"
                variant="light"
                id="emailAuthentication"
                @click="emailAuthentication"
                :disabled="show"
              >메일 인증</b-button>
            </div>
          </b-overlay>
        </b-col>
      </b-row>
      <b-row class="my-1">
        <b-col role="group" cols="9">
          <b-form-input
            id="password"
            v-model="password"
            placeholder="비밀번호를 입력해주세요"
            :state="passwordState"
            aria-describedby="password-help password-feedback"
            trim
            :type="passwordType"
          ></b-form-input>
          <span :class="{ active: passwordType === 'text' }">
            <i class="fas fa-eye"></i>
          </span>
          <b-form-invalid-feedback id="password-feedback">{{ passwordFeedback }}</b-form-invalid-feedback>
        </b-col>
      </b-row>
      <b-row class="my-1">
        <b-col role="group" cols="9">
          <b-form-input
            id="password-confirm"
            v-model="passwordConfirm"
            :state="passwordConfirmState"
            placeholder="비밀번호를 한번 더 입력해주세요"
            trim
            :type="passwordConfirmType"
          ></b-form-input>
          <span :class="{ active: passwordConfirmType === 'text' }">
            <i class="fas fa-eye"></i>
          </span>
          <b-form-invalid-feedback id="password-feedback">비밀번호를 정확히 입력해주세요.</b-form-invalid-feedback>
        </b-col>
      </b-row>
      <b-row class="my-1">
        <b-col role="group" cols="12">
          <b-button @click="join" class="mt-3 ml-auto mr-auto">회원가입</b-button>
        </b-col>
      </b-row>
    </b-container>

    <!-- <b-row>
              <b-col cols="9">
                <label>
                  <input v-model="isTerm" type="checkbox" id="term" />
                  <span>약관에 동의합니다</span>
                </label>
                <span class="go-term">약관 보기</span>
              </b-col>
    </b-row>-->
  </div>
</template>

<script>
import constants from "../../lib/constants";
import axios from "axios";
import EventBus from "../post/EventBus";

export default {
  components: {},
  created() {},
  watch: {},
  data: () => {
    return {
      constants,
      // 확인확인
      isJoin: false,
      show: false,
      email: "",
      uid: "",
      nickName: "",
      password: "",
      passwordConfirm: "",
      isTerm: false,
      passwordType: "password",
      passwordConfirmType: "password",
      uidFeedback: "",
      nicknameFeedback: "",
      emailFeedback: "",
      passwordFeedback: "",
      uidDupcheck: true,
      nickDupcheck: true,
      emailDupcheck: true,
      passwordCheck: true,
    };
  },
  computed: {
    uidState() {
      if (this.uid.length > 0 ? true : false) {
        let form = new FormData();
        form.append("uid", this.uid);
        axios
          .post(this.$SERVER_URL + "/account/signup/idcheck", form)
          .then((response) => {
            this.uidDupcheck = response.data.status;
          })
          .catch((error) => {
            console.log(error.response);
          });
        if (this.uidDupcheck) {
          return true;
        } else {
          this.uidFeedback = "아이디가 중복되었습니다.";
          return false;
        }
      } else {
        this.uidFeedback = "필수 입력값입니다.";
        return false;
      }
    },
    nickNameState() {
      if (this.nickName.length > 0 ? true : false) {
        let form = new FormData();
        form.append("nickname", this.nickName);
        axios
          .post(this.$SERVER_URL + "/account/signup/nicknamecheck", form)
          .then((response) => {
            this.nickDupcheck = response.data.status;
          })
          .catch((error) => {
            console.log(error.response);
          });
        if (this.nickDupcheck) {
          return true;
        } else {
          this.nicknameFeedback = "닉네임이 중복되었습니다.";
          return false;
        }
      } else {
        this.nicknameFeedback = "필수 입력값입니다.";
        return false;
      }
    },
    emailState() {
      if (
        this.email.indexOf("@") < 0 ||
        this.email.indexOf("@") >= this.email.indexOf(".com")
      ) {
        this.emailFeedback = "이메일 형식이 맞지 않습니다.";
        return false;
      } else {
        let form = new FormData();
        form.append("email", this.email);
        axios
          .post(this.$SERVER_URL + "/account/signup/emailcheck", form)
          .then((response) => {
            this.emailDupcheck = response.data.status;
          })
          .catch((error) => {
            console.log(error.response);
          });
        if (this.emailDupcheck) {
          return true;
        } else {
          this.emailFeedback = "이메일이 중복되었습니다.";
          return false;
        }
      }
    },
    passwordState() {
      if (this.password.length > 0) {
        var pattern1 = /[0-9]/;
        var pattern2 = /[a-zA-Z]/;
        if (
          !pattern1.test(this.password) ||
          !pattern2.test(this.password) ||
          this.password.length < 8
        ) {
          this.passwordFeedback =
            "비밀번호는 8자리 이상 문자, 숫자로 작성해주세요.";
          return false;
        } else {
          return true;
        }
      } else {
        this.passwordFeedback = "필수 입력값입니다.";
        return false;
      }
    },
    passwordConfirmState() {
      if (this.password) {
        if (this.password === this.passwordConfirm) {
          this.passwordCheck = true;
          return true;
        } else {
          return false;
        }
      }
    },
  },
  methods: {
    // 이메일 인증 메서드 만들기
    emailAuthentication() {
      this.show = true;
      axios
        .put(this.$SERVER_URL + "/email/auth", {
          subject: "블로그 가입 인증 메일입니다.",
          toEmail: this.email,
        })
        .then((response) => {
          console.log(response);
          this.show = !this.show;
          alert("회원가입 인증 메일이 발송되었습니다. 메일을 확인해 주세요.");
        })
        .catch((error) => {
          console.log(error.response);
          this.show = !this.show;
          alert("이메일 발송에 실패하였습니다.");
        });
    },
    join() {
      if (
        this.uidDupcheck &&
        this.nickDupcheck &&
        this.emailDupcheck &&
        this.passwordCheck
      ) {
        axios
          .delete(this.$SERVER_URL + `/email/${this.email}`, this.email)
          .then((response) => {
            if (response.data.status) {
              let form = new FormData();
              axios
                .post(this.$SERVER_URL + `/account`, {
                  uid: this.uid,
                  email: this.email,
                  password: this.password,
                  nickname: this.nickName,
                })
                .then((response) => {
                  if (response.data.status == true) {
                    // 왜 이거 안돼냐!!!!!!!!!!
                    // document.getElementById("close").click();
                    console.log("회원가입 여기까지 오니?");
                    // EventBus.$emit("closeModal");
                    this.$router.go({
                      name: constants.URL_TYPE.MAIN.NOLOGINHOME,
                    });
                    alert("회원가입이 완료되었습니다.");
                  } else {
                    alert("회원가입 실패하였습니다. 다시 시도 해 주세요.");
                  }
                })
                .catch((error) => {
                  console.log(error);
                });
            }
          })
          .catch((error) => {
            console.log(error.response);
            alert("이메일 인증이 완료되지 않았습니다.");
          });
      }
    },
  },
};
</script>

<style>
#join {
  margin-left: auto;
  margin-right: auto;
  text-align: center;
  margin-bottom: 40px;
}
.emailInput {
  /* display: inline; */
}
#eamil {
  /* margin-left: 93.2px; */
}
</style>
