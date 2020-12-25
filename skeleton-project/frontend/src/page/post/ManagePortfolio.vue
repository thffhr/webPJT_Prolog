<template>
  <div id="manageP">
    <div id="tagMenue" style="margin-top:50px">
      <span
        v-if="allTagState"
        @click="allTagOnOff"
        style="cursor: pointer; color: black;"
      >태그 전체 선택 해제</span>
      <span v-else @click="allTagOnOff" style="cursor: pointer; color: black;">태그 전체 선택</span>

      <div class="tagButtons box-table2">
        <div v-if="tags.length > 0">
          <!-- <b-button-group size="sm"> -->
          <!-- <div class="col-lg-12"> -->
          <b-button
            pill
            v-for="(tag, idx) in tags"
            :key="idx"
            :pressed="tagState(tag)"
            @click="filtering(tag)"
            variant="secondary"
            style="display: inline-block; color: black;"
            class="m-1"
          >#{{ tag.tagName }}</b-button>
        </div>
        <!-- </div> -->
        <div v-else>태그가 없습니다.</div>
        <!-- </b-button-group> -->
      </div>
      <!-- <b-container>
      <b-row align-h="between">
      <b-col cols="4">-->
      <!-- </b-col>
      <b-col cols="4" style="text-align: right;">-->

      <br />
      <hr />

      <span
        v-if="isIncludeNoTag"
        @click="showNotagProject"
        style="cursor: pointer; color: black;"
      >태그 없는 프로젝트 숨기기</span>
      <span
        v-else
        @click="showNotagProject"
        style="cursor: pointer; text-align: right; color: black;"
      >태그 없는 프로젝트 보여주기</span>
      <!-- 아이콘도 경험관리랑 통일 -->
      <!-- 아래 아이콘은 임시 -->
      <b-icon icon="hand-index"></b-icon>
    </div>

    <!-- <span v-if="isIncludeNoTag" @click="showNotagProject" style="cursor: pointer; color: black;">{{ showNoTag }}</span>
    <span v-else @click="showNotagProject" style="cursor: pointer; text-align: right; color: black;">{{ showNoTag }}</span>-->
    <!-- </b-col>
      </b-row>
    </b-container>-->
    <!-- 여기까지 -->

    <!-- <div
      v-for="portfolio in portfolios"
      :key="portfolio.pid"
      style="display: inline-block;"
      class="m-1"
    >
      <b-card :title="portfolio.title" style="max-width: 20rem; min-height: 17rem;" class="mb-2">
        <b-card-text>
          <br />
          {{ portfolio.start_date }} ~ {{ portfolio.end_date }}
          <hr />
          {{ portfolio.contents }}
          <hr />
          태그
          <ul>
            <li
              v-for="portfolioTag in portfolio.tag"
              :key="portfolioTag.tid"
              style="display: inline-block;"
              class="m-1"
            >
              #{{ portfolioTag.tagName }}
            </li>
          </ul>
        </b-card-text>
        <b-button href="#" variant="primary">다운로드(버튼, 미완)</b-button>
      </b-card>
    </div>
    <hr />-->

    <!-- 위랑 동일 / 카드 형태만 다름 -->
    <!-- <b-container>
    <b-row align-v="start">-->
    <div
      v-for="portfolio in portfolios"
      :key="portfolio.pid"
      style="display: inline-block;"
      class="columns is-multiline"
    >
      <b-card v-if="showProject(portfolio)" class="poCard m-3">
        <!-- v-bind:style="cardColor"
        v-on:mouseover="change_color()"
        v-on:mouseout="origin_color()"-->
        <div>
          <!-- <b-container>
                <b-row align-h="between">
                  <b-col style="text-align: right; padding-right: 0;" cols="4">
                    <b-img
                      @click="deleteP(portfolio)"
                      style="cursor:pointer;"
                      v-bind:src="require(`@/assets/img/icons8-trash-24.png`)"
                      width="15px"
                    ></b-img>
                  </b-col>
                </b-row>
          </b-container>-->
          <div id="poMenue">
            <b-dropdown size="lg" variant="text" toggle-class="text-decoration-none" no-caret>
              <template v-slot:button-content>
                <b-icon icon="three-dots"></b-icon>
              </template>
              <b-dropdown-item @click="gotoDetail(portfolio.pid)">더보기</b-dropdown-item>
              <b-dropdown-item @click="downloadAllZip(portfolio.pid)">파일 다운로드</b-dropdown-item>
              <b-dropdown-item
                class="mr-2"
                @click="deleteP(portfolio)"
                style="cursor:pointer;"
                v-bind:src="require(`@/assets/img/icons8-trash-24.png`)"
                width="15px"
              >삭제</b-dropdown-item>
            </b-dropdown>
          </div>
          <div class="poTitle" @click="gotoDetail(portfolio.pid)">
            <h2 v-if="portfolio.title.length > 15">{{ portfolio.title.slice(0, 15) }}...</h2>
            <h2 v-else>{{ portfolio.title }}</h2>
          </div>
          <small style="display: inline;">{{ portfolio.start_date }} ~ {{ portfolio.end_date }}</small>
          <!-- <div style="float: right; display: inline;"><b-button size="sm" variant="outline-dark">download</b-button></div> -->
          <p
            v-if="portfolio.contents.length > 60"
            class="mt-3"
          >{{ portfolio.contents.slice(0, 60) }}...</p>
          <p v-else class="mt-3">{{ portfolio.contents }}</p>
          <!-- 태그 출력 -->
          <div id="tags">
            <div v-if="portfolio.tag.length > 3">
              <div
                v-for="portfolioTag in portfolio.tag.slice(0, 3)"
                :key="portfolioTag.tid"
                style="display: inline-block;"
              >
                <h4>
                  <b-badge
                    pill
                    class="mr-2"
                    id="tag"
                    text-variant="black"
                  >#{{ portfolioTag.tagName }}</b-badge>
                </h4>
              </div>...
            </div>
            <div v-else-if="portfolio.tag.length > 0" class="tags">
              <div
                v-for="portfolioTag in portfolio.tag"
                :key="portfolioTag.tid"
                style="display: inline-block;"
              >
                <h4>
                  <b-badge
                    pill
                    class="mr-2"
                    id="tag"
                    text-variant="black"
                  >#{{ portfolioTag.tagName }}</b-badge>
                </h4>
              </div>
            </div>
            <div v-else class="tags">태그를 추가해보세요</div>
          </div>
        </div>
      </b-card>
    </div>
    <!-- </b-row>
    </b-container>-->
    <!-- (+) 버튼 -->
    <div id="poPlusBtn" v-on:click="addProject">
      <div v-if="portfolios.length == 0">
        <div class="row">
          <div class="col-button-custom">
            <b-icon icon="journal-plus" font-scale="5"></b-icon>
          </div>
        </div>
        <div class="row">
          <h3 class="mr-auto ml-auto mt-3">프로젝트를 기록해보세요.</h3>
        </div>
      </div>
      <div class="row" v-else>
        <div class="col-button-custom">
          <b-icon icon="plus-circle" font-scale="3"></b-icon>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import constants from "../../lib/constants.js";
import axios from "axios";
import lodash from "lodash";

//const SERVER_URL = "http://localhost:8080";

export default {
  name: "ManagePortfolio",
  data: () => {
    return {
      tags: [],
      portfolios: [],
      portfolioTags: [],
      selectedTags: [],
      buttonStyle: {
        width: "",
        opacity: "",
      },
      isIncludeNoTag: true,
      allTagState: true,
    };
  },
  created() {
    if (!constants.IS_LOGED_IN) {
      this.$router.push({ name: constants.URL_TYPE.MAIN.NOLOGINHOME });
    }

    axios
      .get(this.$SERVER_URL + `/portfolio/Tags`, {
        params: {
          uid: this.$route.params.uid,
        },
      })
      .then((response) => {
        // console.log("태그리스트");
        this.tags = response.data.object;

        // Array.prototype.forEach.call(this.tags, tag =>
        //   this.selectedTags.push(tag.tid)
        // )

        axios
          .get(this.$SERVER_URL + `/portfolio/all`, {
            params: {
              uid: this.$route.params.uid,
            },
          })
          .then((response) => {
            // console.log(response.data.object);
            this.portfolios = response.data.object;
          })
          .catch((error) => {});
      })
      .catch((error) => {});
  },
  methods: {
    addProject() {
      var date = new Date();
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var day = date.getDate();

      if (day < 10) day = "0" + day;
      if (month < 10) month = "0" + month;

      var startdate = year + "-" + month + "-" + day;

      axios
        .post(this.$SERVER_URL + `/portfolio`, {
          contents: "내용",
          title: "제목",
          uid: this.$route.params.uid,
          startDate: startdate,
          endDate: startdate,
        })
        .then((response) => {
          response.data.object.tag = [];

          axios
            .get(this.$SERVER_URL + `/portfolio/Tags`, {
              params: {
                uid: this.$route.params.uid,
              },
            })
            .then((response) => {
              this.tags = response.data.object;
              axios
                .get(this.$SERVER_URL + `/portfolio/all`, {
                  params: {
                    uid: this.$route.params.uid,
                  },
                })
                .then((response) => {
                  this.portfolios = response.data.object;
                })
                .catch((error) => {});
            })
            .catch((error) => {});
        })
        .catch((error) => {});
    },
    // change_button: function () {
    //   this.buttonStyle.opacity = "1";
    //   this.buttonStyle.width = "61px";
    // },
    // origin_button: function () {
    //   this.buttonStyle.opacity = "0.6";
    //   this.buttonStyle.width = "60px";
    // },

    showProject(tagInPortfolio) {
      var selectedTags = this.selectedTags;
      var cnt = 0;
      tagInPortfolio.tag.forEach(function (tag) {
        if (selectedTags.includes(tag.tid)) {
          cnt++;
        }
        return cnt;
      });
      if (
        cnt == selectedTags.length ||
        (tagInPortfolio.tag.length == 0 && this.isIncludeNoTag)
      ) {
        return true;
      } else {
        return false;
      }
    },

    filtering(tag) {
      tag.state = !tag.state;
      if (this.selectedTags.includes(tag.tid)) {
        this.selectedTags.splice(this.selectedTags.indexOf(tag.tid), 1);
      } else {
        this.selectedTags.push(tag.tid);
      }
    },

    showNotagProject() {
      this.isIncludeNoTag = !this.isIncludeNoTag;
      if (this.isIncludeNoTag == true) {
        axios
          .get(this.$SERVER_URL + `/portfolio/all`, {
            params: {
              uid: this.$route.params.uid,
            },
          })
          .then((response) => {
            // console.log(response.data.object);
            this.portfolios = response.data.object;
          })
          .catch((error) => {});
      } else {
        let tmp = [];
        Array.prototype.forEach.call(this.portfolios, (portfolio) => {
          if (portfolio.tag.length > 0) {
            tmp.push(portfolio);
          }
        });
        this.portfolios = tmp;
      }
    },

    deleteP: function (portfolio) {
      axios
        .delete(this.$SERVER_URL + `/portfolio/${portfolio.pid}`)
        .then((response) => {
          // this.$delete(this.portfolios, portfolio);
          this.portfolios.splice(this.portfolios.indexOf(portfolio), 1);
          // this.getTag();
          //alert("삭제완료 " + experience.exid);
          axios
            .get(this.$SERVER_URL + `/portfolio/Tags`, {
              params: {
                uid: this.$route.params.uid,
              },
            })
            .then((response) => {
              this.tags = response.data.object;
              // Array.prototype.forEach.call(this.tags, (tag) =>
              //   this.selectedTags.push(tag.tid)
              // );
            })
            .catch((error) => {});

          axios
            .get(this.$SERVER_URL + `/portfolio/all`, {
              params: {
                uid: this.$route.params.uid,
              },
            })
            .then((response) => {
              // console.log(response.data.object);
              this.portfolios = response.data.object;
            })
            .catch((error) => {});
        })

        .catch((error) => {});
    },
    tagState(tag) {
      return tag.state;
    },
    allTagOnOff() {
      this.allTagState = !this.allTagState;
      if (this.allTagState == true) {
        Array.prototype.forEach.call(
          this.tags,
          (tag) => (tag.state = this.allTagState),
          this.selectedTags.push(tag.tid)
        );
      } else {
        this.selectedTags = [];
        Array.prototype.forEach.call(
          this.tags,
          (tag) => (tag.state = this.allTagState)
        );
      }
    },
    gotoDetail(pid) {
      // console.log(pid);
      this.$router.push({
        path: `/PortfolioDetails/${this.$route.params.uid}/${pid}`,
      });
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
            const link = document.createElement("a");
            const url = response.request.responseURL;
            link.href = url;
            document.body.appendChild(link);
            link.click();
          }
        })
        .catch((error) => {});
    },

    // change_color() {
    //   this.cardColor.background = "light gray";
    // },
    // origin_color() {
    //   this.cardColor.background = "#bedcff";
    // },
  },
  // 버튼
  computed: {},
};
</script>

<style>
#manageP {
  width: 80%;
  padding: 0;
  margin-top: 50px;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 40px;
}
.box-table2 {
  border: 1px solid #888888;
  border-radius: 5px;
  /* margin-top: 50px; */
  margin-top: 10px;
  margin-bottom: 10px;
  /* 이건 경험관리 수정할거면 같이 날려줘야함 */
  margin-left: -15px;
  margin-right: -15px;
  /* 여기까지 */
  padding: 2%;
  background: #eeeeee;
  opacity: 0.8;
}
#tagMenue {
  text-align: right;
}
.tagButtons {
  /* justify-content: center;s */
  /* align-items: center; */
  text-align: left;
}
.card.poCard {
  background-color: lightgrey;
  width: 23rem;
  height: 20rem;
}
.poCard:hover {
  background-color: #bedcff;
  box-shadow: 5px 5px 5px 0 grey;
  transition: transform 0.3s ease-in;
  transform: translate(0, -10px);
}

.poTitle {
  cursor: pointer;
}
#poMenue {
  position: absolute;
  right: 20px;
  top: 20px;
  padding: 0;
}
#poMenue:hover {
  cursor: pointer;
}

#tags {
  position: absolute;
  bottom: 15px;
}
#poPlusBtn {
  margin: 30px 10px;
}
#poPlusBtn:hover {
  cursor: pointer;
  opacity: 0.5;
}
.col-button-custom {
  margin-left: auto;
  margin-right: auto;
}

.img-custom {
  text-align: right;
}
#manageP .active {
  border-color: black !important;
  border-bottom-color: black !important;
}
</style>