<template>
  <div id="Compare">
    <b-img :src="require(`@/assets/img/animat-lightbulb-color.gif`)" contain width="300px"></b-img>
    <div v-if="!auth">
      <h1 class="Message">가입 인증 단계입니다.</h1>
      <h3 class="Message">인증을 완료하고 간편하게 IT포트폴리오를 준비해보세요!</h3>
      <div id="Btn" @click="compare()">인증 완료</div>
    </div>
    <div v-else>
      <h1 class="Message">인증완료 되었습니다.</h1>
      <h3 class="Message">계속 회원가입을 진행해주세요!</h3>
    </div>
  </div>
</template>

<script>
import constants from "../../lib/constants";
import axios from "axios";

export default {
  name: "PageNotFound",
  data: () => {
    return {
      constants,
      code: "",
      email: "",
      auth: false,
    };
  },
  created() {},
  methods: {
    compare() {
      let code = this.$route.params.code;
      let email = this.$route.params.email;
      console.log(code);
      console.log(email);
      axios
        .get(this.$SERVER_URL + "/email/compare-code", {
          params: {
            code: code,
            email: email,
          },
        })
        .then((response) => {
          if (response.data.status) {
            alert("이메일 인증이 성공하였습니다!");
            this.auth = true;
            // this.$router.push({ name: constants.URL_TYPE.MAIN.NOLOGINHOME });
          } else {
            alert("이메일 인증이 실패하였습니다.");
          }
          console.log(response);
        });
    },
  },
};
</script>

<style>
#Compare {
  width: 80%;
  margin: 50px auto;
  text-align: center;
}
.Message {
  margin-bottom: 50px;
}
#Btn {
  display: inline;
  border-radius: 5px;
  background-color: #7a63ff;
  color: white;
  padding: 10px;
}
#Btn:hover {
  cursor: pointer;
}
</style>