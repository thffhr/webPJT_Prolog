<template>
  <div id="poDetail">
    <b-icon id="backToList" @click="backToList" icon="chevron-left" font-scale="2"></b-icon>
    <b-container>
      <b-row>
        <b-col md="1"></b-col>
      </b-row>
      <b-row>
        <b-col md="5">
          <h2
            v-if="!updateTitleState"
            class="title"
            style="display:inline-block; margin-left: auto;"
          >
            {{ pjtDetail.title }}
            <b-img
              v-if="isLogedIn"
              @click="updateTitle()"
              style="margin-left: 1rem; cursor: pointer;"
              v-bind:src="require(`@/assets/img/icons8-pencil-24.png`)"
              width="15px"
              class="mr-2 mt-auto mb-auto"
            ></b-img>
            <!-- {{router.params.pid}} -->
          </h2>
          <h2 v-else class="title" style="color: grey; margin-left: auto;">
            <input
              ref="titleInput"
              size="18%"
              :value="pjtDetail.title"
              @input="pjtDetail.title = $event.target.value"
              @keyup.enter="
                sendUpdateInfo();
                updateTitle();
              "
              style="display: inline-block;"
            />
            <b-img
              @click="
                sendUpdateInfo();
                updateTitle();
              "
              style="margin-left: 1rem; cursor: pointer;"
              v-bind:src="require(`@/assets/img/icons8-save-close-64.png`)"
              width="15px"
              class="mr-2 mt-auto mb-auto"
            ></b-img>
            <!-- {{router.params.pid}} -->
          </h2>
        </b-col>
        <b-col md="6">
          <div style="text-align: right;">
            <b-button @click="downloadAllZip()" size="sm" variant="outline-dark">
              <b-icon-cloud-download class="mr-1"></b-icon-cloud-download>전체
              파일 다운로드
            </b-button>
          </div>
        </b-col>
      </b-row>
    </b-container>

    <div class="tabs">
      <!-- <b-tabs content-class align="center"> -->
      <b-tabs content-class fill>
        <b-tab title="프로젝트 정보" active>
          <b-container class="tabContents">
            <b-row>
              <b-col v-if="!updateDateState" class="mt-5 mb-3">
                <span>
                  프로젝트 진행 기간 : {{ pjtDetail.start_date }} ~
                  {{ pjtDetail.end_date }}
                </span>
                <b-img
                  v-if="isLogedIn"
                  @click="updateDate()"
                  style="margin-left: 1rem; cursor: pointer;"
                  v-bind:src="require(`@/assets/img/icons8-pencil-24.png`)"
                  width="15px"
                  class="mr-2"
                ></b-img>
              </b-col>
              <b-col v-else class="mt-5 mb-3">
                <span style="display: inline-block;">
                  프로젝트 진행 기간 :
                  <input
                    ref="start_dateInput"
                    :value="pjtDetail.start_date"
                    @input="pjtDetail.start_date = $event.target.value"
                    @keyup.enter="
                      sendUpdateInfo();
                      updateDate();
                    "
                    size="4"
                  />
                  ~
                  <input
                    :value="pjtDetail.end_date"
                    @input="pjtDetail.end_date = $event.target.value"
                    @keyup.enter="
                      sendUpdateInfo();
                      updateDate();
                    "
                    size="4"
                  />
                </span>
                <!-- <b-img :src="require(`@/assets/img/icons8-save-close-64.png`)" width="15px" style="display: inline-block; margin-left: 1rem; cursor: pointer;" @click="sendUpdateInfo(); updateDate()"></b-img> -->
                <b-img
                  @click="
                    sendUpdateInfo();
                    updateDate();
                  "
                  style="margin-left: 1rem; cursor: pointer;"
                  v-bind:src="require(`@/assets/img/icons8-save-close-64.png`)"
                  width="15px"
                  class="mr-2"
                ></b-img>
              </b-col>
            </b-row>
            <b-row>
              <b-col class="mt-3 mb-3">
                <div>
                  <!-- 태그 -->
                  <!-- <div v-if="updateP">
                      <div v-for="(experienceTag,tid) in experience.tags" :key="experienceTag.tid">
                        <span class="txt_tag">
                          <span>#</span>
                          <span>{{experienceTag.tagName}}</span>
                          <b-img
                            @click="deleteTag(experience.tags, experienceTag ,tid, experience)"
                            style="width:18px; height:18px; cursor:pointer"
                            v-bind:src="require(`@/assets/img/icons8-close-window-50.png`)"
                          >
                            <span>삭제</span>
                          </b-img>
                        </span>
                      </div>

                      <span class="inp_tag">
                        <span>#</span>
                        <div style="inline-block" value="태그" placeholder="Tag입력">
                          <input
                            id="tagText"
                            v-model="tagText"
                            v-on:keyup.enter="addTag(experience.tags, experience.exid,tagText)"
                            type="text"
                            class="tf_g"
                            placeholder="태그입력"
                            style="box-sizing: content-box; width: 50px;"
                          />
                        </div>
                      </span>
                  </div>-->
                  <div
                    v-for="(ptag, j) in pjtDetail.tag"
                    :key="j"
                    class="tag mr-2 mt-1"
                    style="display: inline-block;"
                  >
                    # {{ ptag.tagName }}
                    <!-- <b-img :src="require(`@/assets/img/icons8-trash-24.png`)" width="15px" style="margin-left: 5%; cursor: pointer;" @click="deleteTag(ptag.tid)"></b-img> -->
                    <b-icon
                      v-if="isLogedIn"
                      icon="x"
                      aria-hidden="true"
                      style="margin-left: 1px; cursor: pointer;"
                      @click="deleteTag(ptag.tid)"
                    ></b-icon>
                  </div>
                  <div v-if="isLogedIn" id="mtbauto" style="display: inline-block;">
                    <div
                      style="display: inline-block; margin-top: auto; margin-bottom: auto; padding: 12px 0;"
                    >#</div>
                    <b-form-input
                      class="inp_tag"
                      v-model="newTag"
                      @keyup.enter="addNewTag(newTag)"
                      placeholder="새 태그 입력"
                      style="box-sizing: content-box; width: 100px; height:24px; display: inline-block; margin-top: 5px;"
                    ></b-form-input>
                  </div>
                  <!-- <b-icon
                    icon="plus"
                    aria-hidden="true"
                    style="margin-left: 5 %; cursor: pointer;"
                    
                  ></b-icon>-->
                </div>
              </b-col>
            </b-row>
            <b-row>
              <b-col class="mt-3 mb-3">
                <h5>
                  프로젝트 정의
                  <div v-if="isLogedIn" style="display:inline-block;">
                    <b-img
                      v-if="!updateContentsState"
                      @click="updateContents"
                      style="margin-left: 1rem; cursor: pointer;"
                      v-bind:src="require(`@/assets/img/icons8-pencil-24.png`)"
                      width="15px"
                      class="mr-2"
                    ></b-img>
                    <b-img
                      v-else
                      @click="
                        sendUpdateInfo();
                        updateContents();
                      "
                      style="margin-left: 1rem; cursor: pointer;"
                      v-bind:src="
                        require(`@/assets/img/icons8-save-close-64.png`)
                      "
                      width="15px"
                      class="mr-2"
                    ></b-img>
                  </div>
                </h5>
                <div v-if="!updateContentsState" class="mt-3 mb-3">{{ pjtDetail.contents }}</div>
                <div v-else class="mt-3 mb-3">
                  <textarea
                    ref="contentsInput"
                    class="textarea-contents"
                    :value="pjtDetail.contents"
                    @input="pjtDetail.contents = $event.target.value"
                  ></textarea>
                  <!-- <b-img :src="require(`@/assets/img/icons8-save-close-64.png`)" width="15px" style="display: inline-block; margin-left: 1rem; cursor: pointer;" @click="sendUpdateInfo(); updateContents()"></b-img> -->
                </div>
              </b-col>
            </b-row>
          </b-container>
          <div></div>
        </b-tab>
        <b-tab title=" 파일 목록 ">
          <b-container class="tabContents">
            <div v-if="pjtDetail.files.length > 0">
              <b-row
                v-for="(id, m) in pjtDetail.files"
                :key="m"
                class="mr-3"
                id="file"
                text-variant="black"
              >
                <b-col class="mt-4 mb-2">
                  {{ id.fileName }}
                  <!-- 파일 다운로드 -->
                  <b-button @click="fileDownload(id)" size="sm" variant="outline-dark" class="ml-1">
                    <b-icon-download></b-icon-download>
                  </b-button>
                  <b-button
                    @click="fileDelete(id)"
                    v-if="isLogedIn"
                    size="sm"
                    variant="outline-dark"
                    class="ml-1"
                  >
                    <b-icon-x></b-icon-x>
                  </b-button>
                </b-col>
              </b-row>
            </div>
            <div v-else>파일을 추가해보세요.</div>
            <input
              v-if="isLogedIn"
              type="file"
              ref="fileUpload"
              style="display: none"
              multiple="multiple"
              @change="uploadMultipleFiles($event)"
            />
            <b-icon
              v-if="isLogedIn"
              @click="$refs.fileUpload.click()"
              style="cursor: pointer"
              icon="plus-square"
              aria-hidden="true"
            ></b-icon>
            <b-row>
              <!-- <b-icon-plus-square class="mr-1" @click="uploadFile"></b-icon-plus-square> -->
            </b-row>
          </b-container>
        </b-tab>
        <!-- <b-tab title="Disabled" disabled>
          <p class="tabContents">I'm a disabled tab!</p>
        </b-tab>-->
      </b-tabs>
    </div>
  </div>
</template>

<script>
import constants from "../../lib/constants.js";
import axios from "axios";

export default {
  components: {},
  // props: {
  //   pid: Number,
  // },
  data: () => {
    return {
      isLogedIn: constants.IS_LOGED_IN,
      uid: localStorage["uid"],
      pjtDetail: [],
      updateP: false,
      updateDateState: false,
      updateContentsState: false,
      updateTitleState: false,
      newTag: "",
      uploadFiles: [],
    };
  },
  created() {
    this.uid = this.$route.params.uid;
    this.isLogedIn = constants.IS_LOGED_IN;
    // console.log(this.$route.params.uid);
    // console.log(this.$route.params.pid);
    axios
      .get(this.$SERVER_URL + `/portfolio/${this.$route.params.pid}`, {
        params: { pid: this.$route.params.pid },
      })
      .then((response) => {
        // console.log(response);
        if (response.data.status) {
          this.pjtDetail = response.data.object;
        }
      });
  },
  methods: {
    updateDate() {
      this.updateDateState = !this.updateDateState;
      if (this.updateDateState == true) {
        this.$nextTick(() => {
          this.$refs.start_dateInput.focus();
        });
      }
    },
    updateContents() {
      this.updateContentsState = !this.updateContentsState;
      if (this.updateContentsState == true) {
        this.$nextTick(() => {
          this.$refs.contentsInput.focus();
        });
      }
    },
    updateTitle() {
      this.updateTitleState = !this.updateTitleState;
      if (this.updateTitleState == true) {
        this.$nextTick(() => {
          this.$refs.titleInput.focus();
        });
      }
    },
    sendUpdateInfo() {
      axios
        .put(this.$SERVER_URL + `/portfolio`, {
          uid: this.uid,
          pid: this.pjtDetail.pid,
          title: this.pjtDetail.title,
          contents: this.pjtDetail.contents,
          startDate: this.pjtDetail.start_date,
          endDate: this.pjtDetail.end_date,
        })
        .then((response) => {
          // alert('수정완료')
        })
        .catch((error) => {});
    },
    deleteTag(tid) {
      axios
        .delete(this.$SERVER_URL + `/tag`, {
          data: {
            pid: this.pjtDetail.pid,
            tid: tid,
          },
        })
        .then((response) => {
          // alert('태그삭제')
          // 태그 다시 받아야 새로고침효과
          axios
            .get(this.$SERVER_URL + `/portfolio/${this.pjtDetail.pid}`, {
              params: { pid: this.pjtDetail.pid },
            })
            .then((response) => {
              if (response.data.status) {
                this.pjtDetail = response.data.object;
              }
            });
        })
        .catch((error) => {});
    },
    addNewTag(newTag) {
      // console.log(this.pjtDetail.pid);
      // console.log(newTag);
      if (newTag.length == 0 || newTag.length > 10) {
        alert("1자 이상 10자 이하로 입력해주세요");
      } else {
        let form = new FormData();
        form.append("pid", this.pjtDetail.pid);
        form.append("tag", newTag);
        axios
          .post(this.$SERVER_URL + `/portfolio/tag`, form)
          .then((response) => {
            // alert("태그!")
            // 태그 다시 받아야 새로고침효과..
            axios
              .get(this.$SERVER_URL + `/portfolio/${this.pjtDetail.pid}`, {
                params: { pid: this.pjtDetail.pid },
              })
              .then((response) => {
                if (response.data.status) {
                  this.pjtDetail = response.data.object;
                }
              });
            this.newTag = "";
          })
          .catch((error) => {});
      }
    },
    uploadFile(event) {
      const formData = new FormData();
      formData.append("file", event.target.files[0]);
      formData.append("pid", this.pjtDetail.pid);

      axios
        .post(
          this.$SERVER_URL + `/uploadFile/${this.pjtDetail.pid}`,
          formData,
          {
            headers: {
              "content-Type": "multipart/form-data",
            },
          }
        )
        .then((response) => {
          // console.log(response + "res");

          axios
            .get(this.$SERVER_URL + `/portfolio/${this.$route.params.pid}`, {
              params: { pid: this.$route.params.pid },
            })
            .then((response) => {
              // console.log(response);
              if (response.data.status) {
                this.pjtDetail = response.data.object;
              }
            });
        })
        .catch((error) => {});
    },
    uploadMultipleFiles(event) {
      const formData = new FormData();

      for (var i = 0; i < event.target.files.length; i++) {
        let file = event.target.files[i];
        // formData.append('file[' + i + ']', file);
        formData.append("file", file);
      }
      formData.append("pid", this.pjtDetail.pid);

      axios
        .post(this.$SERVER_URL + `/uploadMultipleFiles`, formData, {
          headers: {
            "content-Type": "multipart/form-data",
          },
        })
        .then((response) => {
          // console.log(response + "res");

          axios
            .get(this.$SERVER_URL + `/portfolio/${this.$route.params.pid}`, {
              params: { pid: this.$route.params.pid },
            })
            .then((response) => {
              // console.log(response);
              if (response.data.status) {
                this.pjtDetail = response.data.object;
              }
            });
        })
        .catch((error) => {});
    },
    fileDownload(id) {
      // console.log(fileName)
      axios
        .get(this.$SERVER_URL + `/downloadFile/${id.fileName}`, {
          responseType: "blob",
        })
        .then((response) => {
          // console.log(response)
          const url = window.URL.createObjectURL(new Blob([response.data]));
          const link = document.createElement("a");
          link.href = url;
          link.setAttribute("download", id.fileName);
          document.body.appendChild(link);
          link.click();
        })
        .catch((error) => {});
    },
    downloadAllZip() {
      axios
        .get(
          this.$SERVER_URL + `/downloadPortfolio`,
          {
            params: {
              pid: this.pjtDetail.pid,
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
    fileDelete(id) {
      axios
        .delete(this.$SERVER_URL + `/deleteFile/${id.id}`)
        .then((response) => {
          axios
            .get(this.$SERVER_URL + `/portfolio/${this.$route.params.pid}`, {
              params: { pid: this.$route.params.pid },
            })
            .then((response) => {
              // console.log(response);
              if (response.data.status) {
                this.pjtDetail = response.data.object;
              }
            });
        })
        .catch((error) => {});
    },
    backToList() {
      this.$router.push({ name: constants.URL_TYPE.POST.MANAGEPORTFOLIO });
    },
  },
};
</script>

<style>
#poDetail {
  margin-top: 50px;
  margin-bottom: 40px;
}
#backToList {
  position: absolute;
  left: 8%;
  margin: 5px;
}
#backToList:hover {
  cursor: pointer;
  opacity: 0.5;
}
.active {
  background-color: rgb(212, 211, 211) !important;
  border-bottom-color: rgb(212, 211, 211) !important;
}
.tabContents {
  background-color: rgb(212, 211, 211);
  padding: 20px 0;
  text-align: left;
}
.tabs {
  margin-top: 40px;
  margin-bottom: 40px;
  margin-left: 50px;
  margin-right: 50px;
}
.title {
  text-align: left;
  margin-left: 120px;
}
.textarea-contents {
  width: 90%;
  height: 20rem;
}
.tag {
  display: inline;
  color: white;
  padding: 5px 10px;
  background-color: #4b5f83;
  border-radius: 100px;
  margin: 5px;
}
.inp_tag {
  /* background-color: lightslategrey; */
  /* color: black; */
  display: flex;
  font-size: 13px;
  /* height: 30px; */
  display: inline-block;
  margin: auto 2px;
  border: 0;
  color: #270949;
  /* vertical-align: top; */
  outline: none;
  background: #eeeeee;
}
</style>
