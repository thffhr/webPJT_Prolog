<template>
  <div class="manageEx" id="manageEx">
    <!-- Three columns of text below the carousel -->
    <!-- 모든 태그들 -->
    <div id="tagManue">
      <span
        v-if="allTagState"
        @click="allTagOnOff"
        style="cursor: pointer; color: black;"
      >태그 전체 선택 해제</span>
      <span v-else @click="allTagOnOff" style="cursor: pointer; color: black;">태그 전체 선택</span>

      <div class="row box-table">
        <div v-if="tags.length > 0">
          <b-button
            pill
            v-for="(tag, idx) in tags"
            :key="idx"
            :pressed="tagState(tag)"
            @click="filtering(tag)"
            variant="secondary"
            style="display: inline-block; color: black;"
            class="m-1"
          ># {{ tag.tagName }}</b-button>
        </div>
        <div v-else>태그가 없습니다.</div>
      </div>

      <br />
      <hr />

      <h6 v-if="isIncludeNoTag" class="noTag mt-3 mb-3" @click="exceptNoTagClickE()">
        태그 없는 게시물 숨기기
        <b-icon icon="hand-index"></b-icon>
        <!-- <b-icon icon="hand-index-thumb"></b-icon> -->
        <!-- <b-img
          style="cursor:pointer"
          :pressed.sync="this.isIncludeNoTag"
          v-bind:src="require(`@/assets/img/${NoTagImgSrcT}`)"
          width="20px"
        ></b-img>-->
      </h6>
      <h6 v-else class="noTag mt-3 mb-3" @click="exceptNoTagClickE()">
        태그 없는 게시물 보여주기
        <b-icon icon="hand-index"></b-icon>
      </h6>
    </div>

    <!-- 경험목록 -->
    <div id="experienceList">
      <div v-for="(experience, exid) in experiences" :key="experience.exid">
        <div class="card-copy" v-if="showProject(experience)">
          <div class="row card-body">
            <b-container>
              <b-row>
                <b-col cols="3">
                  <!-- 제목 -->
                  <p v-if="experience.clicked">
                    <b-form-input
                      placeholder="제목을 입력해주세요."
                      v-model="experience.title"
                      @blur="experience.clicked = true"
                      style="width:200px"
                    ></b-form-input>
                    <!-- <input v-model="experience.title" @blur="experience.clicked = true" /> -->
                  </p>
                  <div v-else>
                    <h2>{{ experience.title }}</h2>
                  </div>
                  <!-- 날짜 -->
                  <div v-if="experience.clicked">
                    <b-form-input
                      class="txt_line"
                      style="display:inline; width:150px;"
                      placeholder="2020-00-00"
                      v-model="experience.startdate"
                      @blur="
                        experience.clicked = true;
                        $emit('update');
                      "
                      @keyup.enter="$emit('update')"
                    ></b-form-input>
                    <!-- <input
                        class="txt_line"
                        placeholder="2020-00-00"
                        v-model="experience.startdate"
                        @blur="
                        experience.clicked = true;
                        $emit('update');
                      "
                        @keyup.enter="$emit('update')"
                    />-->
                    ~
                    <b-form-input
                      class="txt_line"
                      style="width:150px"
                      placeholder="2020-00-00"
                      v-model="experience.enddate"
                      @blur="
                      experience.clicked = true;
                      $emit('update');
                    "
                      @keyup.enter="$emit('update')"
                    ></b-form-input>
                    <!-- <input
                      class="txt_line"
                      placeholder="2020-00-00"
                      v-model="experience.enddate"
                      @blur="
                      experience.clicked = true;
                      $emit('update');
                    "
                      @keyup.enter="$emit('update')"
                    />-->
                  </div>
                  <div class="date-align" v-else>
                    <small>
                      {{ experience.startdate }} ~
                      {{ experience.enddate }}
                    </small>
                  </div>
                </b-col>
                <b-col align-self="stretch" cols="8">
                  <!-- 태그 -->
                  <!-- 태그 수정 -->
                  <div class="editor_tag" v-if="experience.clicked">
                    <!-- exid도잇어야함 -->
                    <div
                      class="mt-3 mr-1;"
                      style="display: inline-block;"
                      v-for="(experienceTag, tid) in experience.tags"
                      :key="experienceTag.tid"
                    >
                      <span class="txt_tag" style="display: inline-block;">
                        <div class="tag">
                          # {{ experienceTag.tagName }}
                          <b-icon
                            icon="x"
                            @click="
                          deleteTag(
                            experience.tags,
                            experienceTag,
                            tid,
                            experience
                          )
                        "
                            style="width:18px; height:18px; cursor:pointer margin-left: 5px; padding-bottom: 0;"
                            v-bind:src="
                          require(`@/assets/img/icons8-close-window-50.png`)
                        "
                          >
                            <!-- <span>삭제</span> -->
                          </b-icon>
                        </div>
                      </span>
                    </div>

                    <div id="mtbauto" style="display: inline-block;">
                      <div
                        style="display: inline-block; margin-top: auto; margin-bottom: auto; padding: 12px 0;"
                      >#</div>
                      <b-form-input
                        class="inp_tag"
                        v-model="tagText"
                        @keyup.enter="addTag(experience.tags, experience.exid, tagText)"
                        placeholder="새 태그 입력"
                        style="box-sizing: content-box; width: 85px; height:24px; display: inline-block; margin-top: 10px;"
                      ></b-form-input>
                    </div>

                    <!-- <input
                        id="tagText"
                        v-model="tagText"
                        v-on:keyup.enter="
                          addTag(experience.tags, experience.exid, tagText)
                        "
                        type="text"
                        class="tf_g"
                        placeholder="태그입력"
                        style="box-sizing: content-box; width: 100px; height:24px"
                    />-->
                    <!-- </div> -->
                    <!-- </b-form-group> -->
                  </div>
                  <!-- 태그 출력 -->
                  <div v-else>
                    <div v-if="experience.tags.length>0">
                      <div
                        style="display: inline-block;"
                        class="tag"
                        v-for="experienceTag in experience.tags"
                        :key="experienceTag.tid"
                      ># {{ experienceTag.tagName }}</div>
                    </div>
                    <div v-else>태그를 추가해주세요.</div>
                  </div>
                  <!-- 내용 -->
                  <p v-if="experience.clicked">
                    <b-form-textarea
                      value="Contents"
                      placeholder="내용을 입력하세요."
                      rows="3"
                      v-model="experience.contents"
                      @blur="
                      experience.clicked = true;
                      $emit('update');
                    "
                      @keyup.enter="$emit('update')"
                    ></b-form-textarea>
                    <!-- <textarea></textarea> -->
                  </p>
                  <div v-else class="mt-3 mb-3">
                    <p class="txt_line">{{ experience.contents }}</p>
                  </div>
                </b-col>
              </b-row>
            </b-container>
            <!--버튼-->
            <div style="display:inline; margin-right:20px">
              <!-- <p class="icon-right" style="margin-bottom: 0;"> -->
              <b-img
                @click="clickeEdit(experience)"
                :pressed.sync="experience.clicked"
                style="cursor:pointer"
                v-bind:src="require(`@/assets/img/${experience.imgsrc}`)"
                width="15px"
                class="mr-2"
              ></b-img>
              <b-img
                @click="deleteE(exid, experience)"
                style="cursor:pointer"
                v-bind:src="require(`@/assets/img/icons8-trash-24.png`)"
                width="15px"
              ></b-img>
              <!-- </p> -->
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 경험목록 끝 -->

    <!-- (+) 버튼 -->
    <div v-on:click="addExp" id="exPlusBtn" style="width:350px; margin: 30px auto">
      <div v-if="experiences.length == 0">
        <div class="row">
          <div class="col-button-custom">
            <b-icon icon="journal-plus" font-scale="5"></b-icon>
            <!-- <b-img
            v-on:click="addExp"
            :src="require(`@/assets/img/icons8-plus-50.png`)"
            width="60px"
            v-bind:style="buttonStyle"
            v-on:mouseover="change_button"
            v-on:mouseout="origin_button"
            ></b-img>-->
          </div>
        </div>

        <div class="row">
          <h3 class="mr-auto ml-auto mt-3">활동 경험을 기록해보세요.</h3>

          <!-- <img
          class="featurette-image img-responsive center-block"
          src="https://t1.daumcdn.net/cfile/tistory/21151B4E53E83DAF2C"
          alt="Generic placeholder image"
          />-->
        </div>
      </div>
      <div class="row" v-else>
        <div class="col-button-custom">
          <b-icon icon="plus-circle" font-scale="3"></b-icon>
        </div>
      </div>
    </div>

    <!-- <hr class="featurette-divider" /> -->
  </div>
</template>

<script>
import constants from "../../lib/constants.js";
import axios from "axios";

//const SERVER_URL = "http://localhost:8080";

export default {
  name: "ManageExperience",
  data: () => {
    return {
      tagText: "",
      tags: [],
      uid: localStorage["uid"],
      experiences: [],
      noTagExperiences: [],
      selectedTags: [],
      isIncludeNoTag: true,
      NoTagImgSrcT: "icons8-doorbell-50.png",
      ex: [],
      experienceTags: {
        "": [],
      },
      allTagState: true,
      // mystyle: {
      //   width: "",
      //   height: "",
      //   opacity: "",
      // },
      // buttonStyle: {
      //   width: "",
      //   opacity: "",
      // },
    };
  },
  beforeCreate() {},
  computed: {
    nickNameState() {},
  },

  created() {
    //alert(this.$SERVER_URL + `/portfolio/all`);
    if (!constants.IS_LOGED_IN) {
      this.$router.push({ name: constants.URL_TYPE.MAIN.NOLOGINHOME });
    }

    axios
      .get(this.$SERVER_URL + `/experience/Tags`, {
        params: {
          uid: localStorage["uid"],
        },
      })
      .then((response) => {
        // alert(this.$SERVER_URL + `/experience/Tags`);
        //
        this.tags = response.data.object;
        Array.prototype.forEach.call(this.tags, (t) =>
          Object.assign(t, { imgsrc: "icons8-plus-64.png" })
        );
        // 태그 먼저 불러오도록 하게 만들기 위해 태그 불러오기 안에 경험 불러오기 넣었음
        // 정확한 이유를 모르겠음. 이렇게 해야 오류 없이 태그리스트가 잘 나옴
        axios
          .get(this.$SERVER_URL + `/experience/all`, {
            params: {
              uid: localStorage["uid"],
            },
          })
          .then((response) => {
            this.experiences = response.data.object;

            Array.prototype.forEach.call(this.experiences, (t) =>
              Object.assign(t, { imgsrc: "icons8-pencil-24.png" })
            );
          })
          .catch((error) => {});
        /* 
        Array.prototype.forEach.call(this.tags, tag => 
          this.selectedTags.push(tag.tid)
        ) */
      })
      .catch((error) => {});
  },
  methods: {
    editTodo: function (todo) {
      this.editedTodo = todo;
    },
    changebgcolor: function () {
      this.mystyle.opacity = "0.7";
    },
    originalcolor: function () {
      this.mystyle.opacity = "0.6";
    },
    // change_button: function () {
    //   this.buttonStyle.opacity = "1";
    //   this.buttonStyle.width = "61px";
    // },
    // origin_button: function () {
    //   this.buttonStyle.opacity = "0.6";
    //   this.buttonStyle.width = "60px";
    // },

    tagState(tag) {
      return tag.state;
    },

    allTagOnOff() {
      this.allTagState = !this.allTagState;
      if (this.allTagState == true) {
        let tmp = [];
        this.tags.forEach(function (tag) {
          tag.state = true;
          tmp.push(tag.tid);
        });
        this.selectedTags = tmp;
      } else {
        this.selectedTags = [];
        Array.prototype.forEach.call(
          this.tags,
          (tag) => (tag.state = this.allTagState)
        );
      }
    },

    exceptNoTagClickE: function () {
      //lert("바뀔 값" + this.isIncludeNoTag);
      this.isIncludeNoTag = !this.isIncludeNoTag;
      if (this.isIncludeNoTag == true) {
        axios
          .get(this.$SERVER_URL + `/experience/all`, {
            params: {
              uid: localStorage["uid"],
            },
          })
          .then((response) => {
            this.experiences = response.data.object;
            Array.prototype.forEach.call(this.experiences, (t) =>
              Object.assign(t, { imgsrc: "icons8-pencil-24.png" })
            );
          })
          .catch((error) => {});
      } else {
        let tmp = [];
        Array.prototype.forEach.call(this.experiences, (experience) => {
          if (experience.tags.length > 0) {
            tmp.push(experience);
          }
        });
        this.experiences = tmp;
      }
    },

    showTag(tag) {
      if (tag.tagName == null) {
        return false;
      } else {
        return true;
      }
    },

    //하나의 프로젝트를 보여줄거?
    showProject(experience) {
      var selectedTags = this.selectedTags;
      var cnt = 0;
      experience.tags.forEach(function (tag) {
        if (selectedTags.includes(tag.tid)) {
          cnt++;
        }
        return cnt;
      });

      if (
        cnt == selectedTags.length ||
        (experience.tags.length == 0 && this.isIncludeNoTag)
      ) {
        return true;
      } else {
        return false;
      }
    },

    getTag() {
      axios
        .get(this.$SERVER_URL + `/experience/Tags`, {
          params: {
            uid: localStorage["uid"],
          },
        })
        .then((response) => {
          // alert(this.$SERVER_URL + `/experience/Tags`);
          //
          this.tags = response.data.object;
          Array.prototype.forEach.call(this.tags, (t) =>
            Object.assign(t, { imgsrc: "icons8-plus-64.png" })
          );

          /*  Array.prototype.forEach.call(this.tags, tag => 
          this.selectedTags.push(tag.tid)
        ) */
        })
        .catch((error) => {
          //alert("실패");
        });
    },

    filtering(tag) {
      //tag가 선택되어있다면
      if (this.selectedTags.includes(tag.tid)) {
        this.selectedTags.splice(this.selectedTags.indexOf(tag.tid), 1);
        tag.imgsrc = "icons8-plus-64.png";
      } else {
        //tag가 선택되어 있지 않다면
        this.selectedTags.push(tag.tid);
        tag.imgsrc = "icons8-close-window-26.png";
      }
      tag.state = !tag.state;
    },

    addExp: function () {
      var date = new Date();
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var day = date.getDate();

      if (day < 10) day = "0" + day;
      if (month < 10) month = "0" + month;

      var startdate = year + "-" + month + "-" + day;

      //alert(this.uid);
      axios
        .post(this.$SERVER_URL + `/experience`, {
          title: "제목",
          uid: this.uid,
          contents: "내용을 입력해주세요.",
        })
        .then((response) => {
          response.data.object.startdate = startdate;
          response.data.object.enddate = startdate;
          response.data.object.imgsrc = "icons8-pencil-24.png";

          axios
            .get(this.$SERVER_URL + `/experience/Tags`, {
              params: {
                uid: localStorage["uid"],
              },
            })
            .then((response) => {
              this.tags = response.data.object;
              Array.prototype.forEach.call(this.tags, (t) =>
                Object.assign(t, { imgsrc: "icons8-plus-64.png" })
              );
              // 태그 먼저 불러오도록 하게 만들기 위해 태그 불러오기 안에 경험 불러오기 넣었음
              // 정확한 이유를 모르겠음. 이렇게 해야 오류 없이 태그리스트가 잘 나옴
              axios
                .get(this.$SERVER_URL + `/experience/all`, {
                  params: {
                    uid: localStorage["uid"],
                  },
                })
                .then((response) => {
                  this.experiences = response.data.object;
                  Array.prototype.forEach.call(this.experiences, (t) =>
                    Object.assign(t, { imgsrc: "icons8-pencil-24.png" })
                  );
                })
                .catch((error) => {});
            })
            .catch((error) => {});
        })
        .catch((error) => {
          //alert("실패");
        });
    },

    deleteE: function (exid, experience) {
      axios
        .delete(this.$SERVER_URL + `/experience/${experience.exid}`)
        .then((response) => {
          //alert(exid);
          this.$delete(this.experiences, exid);
          this.getTag();
          //alert("삭제완료 " + experience.exid);
        })
        .catch((error) => {
          //alert("실패");
        });
    },

    deleteE: function (exid, experience) {
      axios
        .delete(this.$SERVER_URL + `/experience/${experience.exid}`)
        .then((response) => {
          //alert(exid);
          this.$delete(this.experiences, exid);
          this.getTag();
          //alert("삭제완료 " + experience.exid);
        })
        .catch((error) => {});
    },

    clickeEdit: function (experience) {
      experience.clicked = !experience.clicked;
      //저장하기
      if (!experience.clicked) {
        this.editE(experience);
        experience.imgsrc = "icons8-pencil-24.png";
      }
      //수정하기
      else {
        experience.imgsrc = "icons8-save-close-64.png";
      }
      //alert(experience.clicked);
    },

    editE: function (experience) {
      axios
        .put(this.$SERVER_URL + `/experience`, {
          id: experience.exid,
          title: experience.title,
          uid: this.uid,
          contents: experience.contents,
          startdate: experience.startdate,
          enddate: experience.enddate,
        })
        .then((response) => {
          //alert("성공");
        })
        .catch((error) => {});
    },

    deleteTag: function (tags, experienceTag, idx, experience) {
      axios
        .delete(
          this.$SERVER_URL +
            `/experience/${experienceTag.tid}/${experience.exid}`
        )
        .then((response) => {
          //경험안에 태그 삭제
          tags.splice(idx, 1);

          //위에보이는 태그 삭제
          //만약 더있으면 삭제안함!(중복된애가있으면)
          var count = 0;
          //2중포문
          Array.prototype.forEach.call(
            this.experiences,
            (i) => {
              Array.prototype.forEach.call(i.tags, (j) => {
                if (experienceTag.tid == j.tid) {
                  count++;
                }
              }); //j
            } //i
          );

          //삭제했을때 0개가되면 위에 보여주는 태그도 삭제.
          if (count == 0) {
            var index = 0;
            Array.prototype.forEach.call(this.tags, (i) => {
              if (experienceTag.tid == i.tid) {
                this.tags.splice(index, 1);
                //alert(index);
              }
              index++;
            });
          }
        })
        .catch((error) => {});
    },
    addTag: function (tags, exid, tagText) {
      this.tagText = "";
      //alert(this.uid);
      axios
        .post(this.$SERVER_URL + `/tag`, {
          tagName: tagText,
        })
        .then((response) => {
          //조인테이블과 이어주기
          this.addTagLink(tags, exid, response.data.object.tid, tagText);

          //가장위쪽 태그테이블
          var res = Object.assign(response.data.object, {
            imgsrc: "icons8-plus-64.png",
          });

          //비어있으면 푸시에 에러남..
          //이미있으면 태그창에 추가안하기 위한 count
          var count = 0;
          Array.prototype.forEach.call(this.tags, (t) => {
            if (response.data.object.tid == t.tid) {
              count++;
            }
          });

          //이미 있으면 안함 없으면 추가
          if (count == 0) {
            try {
              this.tags.push(res);
            } catch {
              this.tags = [];
              this.tags.push(res);
            }
          }

          //showProject를 위함
          //this.selectedTags.push(response.data.object.tid);
        })
        .catch((error) => {});
    },
    addTagLink: function (tags, exid, tid, tagText) {
      axios
        .post(this.$SERVER_URL + `/experience/tag`, {
          exid: exid,
          tid: tid,
        })
        .then((response) => {
          var temp = { tid: tid, tagName: tagText, state: false };
          tags.push(temp);
        })
        .catch((error) => {});
    },
  },
};
</script>

<style>
.manageEx {
  margin-top: 50px;
  width: 80%;
  padding: 0;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 40px;
}
.box-table {
  border: 1px solid #888888;
  border-radius: 5px;
  margin-top: 50px;
  margin-bottom: 10px;
  padding: 2%;
  background: #eeeeee;
  opacity: 0.8;
}
#tagManue {
  text-align: right;
}
#tagManue .box-table {
  text-align: left;
}
.noTag {
  display: inline;
  padding: 5px;
}
.noTag:hover {
  cursor: pointer;
}
#exPlusBtn {
  margin: 30px 10px;
}
#exPlusBtn:hover {
  cursor: pointer;
  opacity: 0.5;
}
/* .img-circle {
  position: relative;
  background: #000000;

  border-radius: 50%;
  height: 200px;
  width: 200px;
  opacity: 0.6;
  background-size: cover;
  margin-left: auto;

  margin-right: auto;

  border: 3px solid #222222;
  box-shadow: 0 0 8px rgb(111, 111, 111);
} */

.img-circle .content {
  width: 65%;
  height: 65%;
  text-align: center;
  position: relative;
  transform: translate(25%, 30%);
  font-size: 15px;
  color: white;
  margin-top: auto;
  margin-bottom: auto;
}

/* 이 부분 잘 모르겠다 */
.txt_line p {
  overflow: hidden;
  text-overflow: ellipsis;
  width: 250px;

  white-space: normal;
  line-height: 1.2;
  max-height: 2.4em;

  /* margin: auto; */
  text-align: center;
}

.icon-right {
  text-align: end;
  padding: 5%;
  margin-right: 10%;
}

.icon-right img {
  margin-right: 8%;
}

.col-cotents h2 {
  text-align: center;
}

.date-align {
  text-align: left;
}

.col-button-custom {
  margin-left: auto;
  margin-right: auto;
}

.col-button-custom {
  margin-left: auto;
  margin-right: auto;
}
.tag {
  display: inline;
  color: white;
  padding: 5px 10px;
  background-color: #4b5f83;
  border-radius: 100px;
  margin: 5px;
}
/* .tag-custom {
  width: 30%;
  display: flex;
} */

.txt_tag {
  display: flex;
  position: relative;
  margin: 0 15px 0 0;
  font-size: 13px;
  vertical-align: top;

  white-space: nowrap;
}

/* .editor_tag {
  display: flex;
  width: 100%;
  min-height: 50px;
  margin: 0 auto;
  padding: 0 0 5px;
  box-sizing: border-box;
  font-size: 0;
} */

/* .inp_tag {
  display: inline !important;
  margin: 16px 26px 0 0;
  font-size: 13px;
  color: #909090;
  vertical-align: top;
} */

/* .inp_tag .tf_g {
  display:inline;
  display: inline-block;
  box-sizing: content-box;
  width: 100px;
  height:24px;
  margin: 0;
  border: 0;
  font-size: 13px;
  color: #333;
  vertical-align: top;
  outline: none;
  background: #eeeeee;
} */

input::placeholder {
  color: black;
  text-decoration-color: black;
  font-style: italic;
}

.txt_tag .tf_g {
  display: inline-block;
  margin: 0;
  border: 0;
  font-size: 13px;
  color: #333;
  vertical-align: top;
  outline: none;
  background: #eeeeee;
}
.tag-margin-custom {
  padding: 0.5%;
  margin: 5px;
}

.card-copy {
  position: relative;
  display: flex;
  flex-direction: column;
  min-width: 0;
  word-wrap: break-word;
  background-color: #bedcff;
  background-clip: border-box;
  border: 1px solid rgba(0, 0, 0, 0.125);
  border-radius: 5px;
  margin-bottom: 5%;
  /* box-shadow: 2px 2px 2px rgb(111, 111, 111); */
}
#experienceList {
  margin-top: 30px;
}

.temp1 {
  display: none;
  margin: 0;
}

#manageEx .active {
  border-color: black !important;
  border-bottom-color: black !important;
}
#manageEx .b-icon.bi {
  vertical-align: 0;
  transform: translate(20%, 25%);
}
.card-copy:hover {
  box-shadow: 5px 5px 5px 0 grey;
  transition: transform 0.3s ease-in;
  transform: translate(0, -5px);
}
</style>

