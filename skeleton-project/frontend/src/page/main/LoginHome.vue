<template>
  <div class="loginHome">
    <div v-if="pjtAll.length >= 1">
      <carousel-3d :width="450" :height="350">
        <div v-for="(num, i) in slidesLength" :key="i">
          <!-- 클릭하면 포트폴리오 디테일 페이지로 이동 -->
          <slide id="carousel_card" :index="i">
            <div class="m-3">
              <h2 class="mb-2">
                <!-- 제목 -->
                <!-- <b-avatar variant="info" src="https://placekitten.com/300/300" class="mr-3"></b-avatar> -->
                <!-- 📁 -->
                <img
                  class="projectImage"
                  src="https://cdn1.iconfinder.com/data/icons/flat-business-icons/128/folder-512.png"
                  style="width: 5rem; height: 5rem;"
                />
                <span
                  class="gotoDetail pr-2 pl-2"
                  @click="gotoDetail(pjtAll[i].pid)"
                >{{ pjtAll[i].title }}</span>

                <!-- <b-button  size="sm" variant="outline-dark">상세보기</b-button> -->
              </h2>
              <div id="downloadBtn" @click="downloadAllZip(pjtAll[i].pid)">
                <b-icon icon="cloud-download" font-scale="1.2"></b-icon>
              </div>

              <small class="ml-2">
                <!-- 날짜 -->
                {{ pjtAll[i].start_date }} ~ {{ pjtAll[i].end_date }}
              </small>
              <p class="mt-2 mb-2 ml-2">
                <!-- 내용 -->
                {{ pjtAll[i].contents.slice(0, 80) }}
              </p>
              <div>
                <h6 class="mb-0" id="tags">
                  <!-- 태그 -->
                  <div v-if="pjtAll[i].tag.length > 0">
                    <div
                      style="display: inline-block; margin-bottom: 5px;"
                      v-for="(ptag, j) in pjtAll[i].tag.slice(0, 3)"
                      :key="j"
                      class="tag mr-3"
                    >
                      <!-- id말고 tag_name으로 바꾸기 -->
                      # {{ ptag.tagName }}
                    </div>
                  </div>
                  <div v-else class="ml-2">
                    <small>태그를 추가해보세요.</small>
                  </div>
                </h6>
              </div>
            </div>
          </slide>
        </div>
      </carousel-3d>
    </div>
    <!-- <router-link > -->
    <div v-else class="noPortfolio" @click="gotoPortfolio">
      <h4 class="mb-3">포트폴리오를 추가해보세요.</h4>
      <b-img :src="require(`@/assets/img/noportfolio.png`)" contain width="180px" id="HomeLogo"></b-img>
    </div>
    <!-- </router-link> -->
    <br />
    <br />
  </div>
</template>

<script>
import constants from "../../lib/constants";
import { Carousel3d, Slide } from "vue-carousel-3d";
import axios from "axios";

import router from "@/router";

export default {
  components: {
    Carousel3d,
    Slide,
  },
  data: () => {
    return {
      constants,
      slidesLength: 1,
      pjtAll: [],
    };
  },
  created() {
    if (!localStorage["check"]) {
      localStorage["check"] = true;
      this.$router.go();
    }
    axios
      .get(this.$SERVER_URL + "/portfolio/all/", {
        params: { uid: this.$route.params.uid },
      })
      .then((response) => {
        this.pjtAll = response.data.object;
        this.slidesLength = response.data.object;
      });
  },
  methods: {
    gotoDetail(pid) {
      this.$router.push({
        path: `/PortfolioDetails/${this.$route.params.uid}/${pid}`,
      });
    },
    gotoPortfolio() {
      this.$router.push({ path: `/ManagePortfolio/${this.$route.params.uid}` });
    },
    downloadAllZip(pid) {
      axios
        .get(
          this.$SERVER_URL + `/downloadPortfolio`,
          {
            params: {
              pid: pid,
              uid: this.$route.params.uid,
            },
          },
          {
            responseType: "blob",
          }
        )
        .then((response) => {
          if (response.data.status) {
            alert("저장된 파일이 없습니다. 파일을 추가하세요.");
          } else {
            console.log(response);
            const link = document.createElement("a");
            const url = response.request.responseURL;
            link.href = url;
            document.body.appendChild(link);
            link.click();
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style>
.loginHome {
  margin-top: 50px;
  margin-bottom: 40px;
}
#carousel_card {
  border: none;
  border-radius: 5px;
  padding: auto;
}
.gotoDetail:hover {
  cursor: pointer;
}
#tags {
  position: absolute;
  bottom: 15px;
}
.tag {
  display: inline;
  color: white;
  padding: 5px 10px;
  background-color: #4b5f83;
  border-radius: 100px;
}
#downloadBtn {
  position: absolute;
  right: 20px;
  top: 20px;
}
#downloadBtn:hover {
  cursor: pointer;
}
.projectImage {
  -webkit-filter: grayscale(100%);
}
.current .projectImage {
  -webkit-filter: grayscale(0%);
}
.current {
  background-color: #bedcff !important;
  /* color: white; */
}
.noPortfolio {
  width: 350px;
  margin: 100px auto;
  text-align: center;
  background-color: #e7e7e7;
}
.noPortfolio:hover {
  cursor: pointer;
  opacity: 0.5;
}
</style>
