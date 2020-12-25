<template>



  <div 
         id="manageA">
    <!-- 수정 시 사이드바 등장 -->
    <!-- 사이드바 -->
    <b-sidebar id="sidebar-right" right shadow no-header>
      <div>
        <b-tabs class="m-3" align="center">
          <b-tab title="경험" active>
            <Board id="board-s-e">
              <div
                v-for="(ex, ex_idx) in nav_ex_outlist[this.changinIdx]"
                :key="ex.exid"
              >
                <Card class="card" :id="'card-s-e-' + ex.exid" draggable="true">
                  <div>
                          <div class="card_title">{{ ex.title }}</div>
                           <hr class="white_line" />
                          <div class="card_date">{{ ex.startdate }} ~ {{ex.enddate}}</div>
                          <div class="card_contents">{{ ex.contents }}</div>
                        </div>
                </Card>
              </div>
              
            <Board id="board-s-e" class="dragSpace dragText"
              >Drag Here!</Board
            >
            </Board>
          </b-tab>
          <b-tab title="포트폴리오">
            <Board id="board-s-p">
              <div
                v-for="(port, p_idx) in nav_port_outlist[this.changinIdx]"
                :key="port.pid"
              >
                <Card class="card" :id="'card-s-p-' + port.pid" draggable="true">
                   <div>
                          <div class="card_title">{{ port.title }}</div>
                           <hr class="white_line" />
                          <div class="card_date">{{ port.startDate }} ~ {{port.endDate}}</div>
                          <div class="card_contents">{{ port.contents }}</div>
                        </div>
                </Card>
              </div>
              <Board id="board-s-p" class="dragSpace dragText"
              >Drag Here!</Board
            >
            </Board>
            
          </b-tab>
          <!-- <b-tab title="Disabled" disabled><p>I'm a disabled tab!</p></b-tab> -->
        </b-tabs>
      </div>
    </b-sidebar>
    <!--/ 사이드바 -->

    <!-- 기간 선택 모달 -->
    <div v-if="is_show">
      <modal
        name="example"
        @before-open="beforeOpen"
        @before-close="beforeClose"
        draggable
      >
        <div class="custom-modal-header">
          지원회사 기간 선택
          <div class="modal-button">
            <b-img
              @click="$modal.hide('example')"
              style="cursor:pointer"
              v-bind:src="require(`@/assets/img/icons8-xbox-x-50.png`)"
              width="30px"
            ></b-img>
          </div>
        </div>
        <div class="modal-contents">
            <br>
          <select v-on:input="updateValue($event.target.value)">
            <option v-for="option in periods">{{ option.apTerm }}</option>
          </select>

          <div>
            <br>
            <p>선택하신 기간은 {{ search_input_period }}입니다.</p>
            <p>맞으시면 확인 버튼을 눌러주세요.</p>
          </div>
          <button
            @click="searchByPeriod(search_input_period)"
            type="button"
            class="btn btn-primary"
          >
            확인
          </button>
        </div>
      </modal>
    </div>
    <!-- 기간 선택 모달 -->

    <!-- 검색 창 -->
    <div class="container marketing">
      <div class="custom_search_container">
        <form
          class="fleft"
          name="topSearchForm"
          id="topSearchForm"
          action="/goods/search"
        >
          <input type="hidden" name="keyword_log_flag" value="Y" />
          <div class="search-input">
            <b-img
              type="image"
              @click="searchA(search_input_text, search_input_period)"
              style="cursor:pointer"
              v-bind:src="require(`@/assets/img/icons8-search-50.png`)"
              width="25px"
            ></b-img>
            <input
              type="text"
              placeholder="회사명 혹은 제목"
              v-on:keyup.enter="searchA(search_input_text, search_input_period)"
              v-model="search_input_text"
              value
              autocomplete="off"
              autofocus
              title="회사명으로 검색해보아요~"
              class="search_top_text"
            />
            <input
              readonly
              type="text"
              v-model="search_input_period"
              value
              autocomplete="off"
              autofocus
              title="기간선택해보아요~"
              class="search_top_period"
            />
            <b-img
              type="image"
              @click="calendar()"
              style="cursor:pointer"
              v-bind:src="require(`@/assets/img/icons8-calendar-50.png`)"
              width="25px"
            ></b-img>
          </div>
        </form>
      </div>

      <hr class="featurette-divider" />
      <!--/ 검색 창 -->
    </div>

    <!-- 로딩 창 -->

    <div v-show="loadingFlag" class ="container marketing" style="text-align: center">
      <div>
        <b-img v-bind:src="require(`@/assets/gif/Ellipsis-1.3s-144px.gif`)"> </b-img>
      </div>
    </div>
    <!-- /로딩 창 -->

    <!-- 케러셀 -->
    <!-- <div>
      <b-carousel
        id="carousel-1"
        v-model="slide"
        :interval="4000"
        controls
        indicators
        background="#ababab"
        img-width="1024"
        img-height="480"
        style="text-shadow: 1px 1px 2px #333;"
        @sliding-start="onSlideStart"
        @sliding-end="onSlideEnd"
      >
        <div v-for="(apply,idx) in apply_lists" :key="apply.apid">
          <div class="custom-contents">
            <b-carousel-slide
              caption="First slide"
              text="Nulla vitae elit libero, a pharetra augue mollis interdum."
              img-src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbbxeSR%2FbtqGJFWlSNZ%2FkiqfkXc5BjdCCqOKQx2yKK%2Fimg.jpg"
    >-->
    <!-- 제목 -->
    <!-- <div>
                <h2>{{apply.apCompany}}</h2>
              </div>
              <div>
                <h4>{{apply.apTerm}}</h4>
    </div>-->

    <!-- 날짜 -->
    <!-- <div class="date-align">
                <small>{{apply.ap_term}}</small>
    </div>-->

    <!-- 내용 -->
    <!-- <div>
                <p class="txt_line">{{apply.apDesc}}</p>
              </div>
            </b-carousel-slide>
          </div>
        </div>
      </b-carousel>
    </div>-->
    <!-- /케러셀 -->

    <!-- 목록 디테일 -->

    <!-- 임시 지원목록 상세 -->

    <input v-model="synCheck" style="display:none" />

    <!-- 지원목록 -->
    <div v-bind="selected_apply" v-if="isEmptyApply()">
      <div class="custom-temp col-md-12">
        <!-- 제목 -->
        <div>
          <h2>{{ selected_apply.apCompany }}</h2>
        </div>
        <div>
          <h4>{{ selected_apply.apTerm }}</h4>
        </div>

        <!-- 날짜 -->
        <div class="date-align">
          <small>{{ selected_apply.ap_term }}</small>
        </div>

        <!-- 내용 -->
        <div>
          <p class="txt_line">{{ selected_apply.apDesc }}</p>
        </div>
      </div>
    </div>
    <!-- /지원목록 -->

    <!-- 임시 지원목록 리스트 -->

    <!-- 목록 리스트 -->
    <div class="applyCardList">
    <div  v-for="(apply, ap_idx) in apply_lists" :key="apply.apid">
      <div class="applyCard mb-3">
        <!-- 안되면 div로 빼주자 -->
        <b-container header-tag="header" class="applyCardHeader p-1" role="tab">
         
          <b-row>
            <b-col>
                <div v-if="!isEditClicked_list[ap_idx]">
                   <h2 class="applyTitle ml-4 mt-3">{{ apply.apTitle }}</h2>
                </div>
            </b-col>
          </b-row>

         
            <div class="applyTitleSize">
              <div  v-if="isEditClicked_list[ap_idx]">
                <div >
                      <b-form-textarea
                      class="applyTitle textArea"
                      v-model="apply.apTitle"
                      placeholder="제목을 입력해주세요."
                      rows="3"
                    ></b-form-textarea>
                </div>
              </div>
            </div>

          <b-row>
            <b-col cols="3">
              <b-row>
                <div v-if="!isEditClicked_list[ap_idx]">
                   <h2 class="ml-4 mt-3">{{ apply.apCompany }}</h2>
                </div>
                <div v-if="isEditClicked_list[ap_idx]" class="ml-4 mt-3">
                    <b-form-input
                      v-model="apply.apCompany"
                      placeholder="회사명을 입력해주세요."
                    ></b-form-input>
                </div>
              </b-row>
            </b-col>
            <b-col cols="6">
              <b-row>
                <div v-if="!isEditClicked_list[ap_idx]">
                  <p class="mt-3" style="width:100%">{{ apply.apTerm }}</p>
                </div>
                <div v-if="isEditClicked_list[ap_idx]" style="display:contents">
                    <b-col class="period-padding" col="3">
                      <b-form-input
                        v-model="apply.apTerm"
                        placeholder="기간을 입력해주세요."
                      ></b-form-input>
                    </b-col>

                      <b-col col="6">
                     <select v-model="apply.apTerm" style="height: 36.5px;">
                        <option v-for="option in periods" :key="option.aptid">{{option.apTerm}}</option>
                     </select>
                    </b-col>

                </div>
              </b-row>
              <b-row v-if="!isEditClicked_list[ap_idx]">
                
              </b-row>
              <b-row v-if="isEditClicked_list[ap_idx]">
                <b-col class="contents-padding " >
                  <b-form-textarea
                    v-model="apply.apDesc"
                    placeholder="내용을 입력하세요."
                    rows="3"
                  ></b-form-textarea>
                </b-col>
              </b-row>
            </b-col>
          </b-row>

        </b-container>
        <div id="applyCardBtn">
          <div v-b-toggle.sidebar-right style="outline:none;">
            <div
              class="aBtn"
              v-if="isEditClicked_list[ap_idx]"
              @click="saveEdit(apply.apid, apply, ap_idx)"
            >
              <b-img
                v-bind:src="require(`@/assets/img/icons8-save-close-64.png`)"
                width="15px"
              ></b-img>
            </div>
            <div
              class="aBtn"
              v-if="!isEditClicked_list[ap_idx] && !isEditCheck"
              @click="clickeEdit(apply.apid, ap_idx)"
            >
              <b-img
                v-bind:src="require(`@/assets/img/icons8-pencil-24.png`)"
                width="15px"
              ></b-img>
            </div>
          </div>
          <div class="aBtn" @click="deleteA(apply.apid, ap_idx)">
            <b-img
              v-bind:src="require(`@/assets/img/icons8-trash-24.png`)"
              width="15px"
            ></b-img>
          </div>
        </div>

         <hr class="featurette-divider" />
             
        <transition name="fade">
          <div v-show="isEditClicked_list_show[ap_idx]">
            <!-- 여기에서 지원목록에 포함되어 있는 경험/포폴 보여줌 -->
            <b-row></b-row>
            <div class="applyCardBody">
              <div
                v-if="
                  nav_ex_inlist[ap_idx] == 0 &&
                    nav_port_inlist[ap_idx] == 0 &&
                    !isEditClicked_list[ap_idx]
                "
                style="text-align:center; margin:30px 0;"
              >
                <h4>수정버튼을 눌러 관련 자료들을 추가해보세요.</h4>
              </div>
              <div v-else>
                <!-- 수정중이 아닐때 -->
                <!-- 경험-->
                <div v-if="!isEditClicked_list[ap_idx]">
                  <div class="ApplysubTitle">경험</div>
                  <div class="boardNoE">
                    <div
                      v-for="(ex, exid) in nav_ex_inlist[ap_idx]"
                      :key="ex.exid"
                    >
                      <div class="cardNoE">
                        <div class="card_title">{{ ex.title }}</div>
                         <hr class="featurette-divider" />
                        <div class="card_date">{{ ex.startdate }} ~ {{ex.enddate}}</div>
                        <div class="card_contents">{{ ex.contents }}</div>
                      </div>
                    </div>
                  </div>

                  <!-- 포폴 -->
                  <div class="ApplysubTitle">프로젝트</div>
                  <div class="boardNoP">
                    <div
                      v-for="(port, pid) in nav_port_inlist[ap_idx]"
                      :key="port.pid"
                    >
                      <div class="cardNoP">
                        <div class="card_title">{{ port.title }}</div>
                         <hr class="featurette-divider" />
                        <div class="card_date">{{ port.startDate }} ~ {{port.endDate}}</div>
                        <div class="card_contents">{{ port.contents }}</div>
                        
                      </div>
                    </div>
                  </div>
                </div>

                <!-- 수정중 일때 -->
                <!-- 경험 -->
                <div v-if="isEditClicked_list[ap_idx]">
                  <div class="ApplysubTitle">경험</div>
                  <Board id="board-d-e">
                    <div
                      v-for="(ex, exid) in nav_ex_inlist[ap_idx]"
                      :key="ex.exid"
                    >
                      <Card class="card" :id="'card-b-e-' + ex.exid" draggable="true">
                        <div>
                          <div class="card_title">{{ ex.title }}</div>
                           <hr class="white_line" />
                          <div class="card_date">{{ ex.startdate }} ~ {{ex.enddate}}</div>
                          <div class="card_contents">{{ ex.contents }}</div>
                        </div>
                      </Card>
                    </div>
                    <Board id="board-d-e" class="dragSpace dragText"
                      >Drag Here!</Board
                    >
                  </Board>

                  <!-- 포폴 -->
                  <div class="ApplysubTitle">프로젝트</div>
                  <Board id="board-d-p">
                    <div
                      v-for="(port, pid) in nav_port_inlist[ap_idx]"
                      :key="port.pid"
                    >
                      <Card class="card" :id="'card-b-p-' + port.pid" draggable="true">
                        <div>
                          <div class="card_title">{{ port.title }}</div>
                           <hr class="white_line" />
                          <div class="card_date">{{ port.startDate }} ~ {{port.endDate}}</div>
                          <div class="card_contents">{{ port.contents }}</div>
                        </div>
                      </Card>
                    </div>
                    <Board id="board-d-p" class="dragSpace dragText"
                      >Drag Here!</Board
                    >
                  </Board>
                </div>
              </div>
            </div>
            <input v-model="isEditCheck" style="display:none" />
          </div>
        </transition>

        <div
          @click="clickeDetail(apply.apid, ap_idx)"
          class="custom-button-align col-lg-12"
        >
          <b-img
            style="cursor:pointer"
            v-bind:src="require(`@/assets/img/icons8-down-button-48.png`)"
            width="30px"
          ></b-img>
        </div>
      </div>
    </div>
  </div>
    <!-- 추가하기 버튼 -->
    <div
      id="exPlusBtn"
      v-on:click="addApply"
      style="width:350px; margin: 50px auto"
    >
      <div v-if="apply_lists.length == 0">
        <div class="row">
          <div class="col-button-custom">
            <b-icon icon="card-checklist" font-scale="5"></b-icon>
          </div>
        </div>

        <div class="row">
          <h3 class="mr-auto ml-auto mt-3">지원 목록을 기록해보세요.</h3>
        </div>
      </div>
      <div class="row" v-else>
        <div class="col-button-custom">
          <b-icon icon="plus-circle" font-scale="3"></b-icon>
        </div>
      </div>
    </div>

    <!-- /추가하기 버튼-->
  </div>
</template>

<script>
import constants from "../../lib/constants.js";
import axios from "axios";
import Vue from "vue";
import DragDrop from "vue-drag-n-drop";
import dragula from "dragula";

import Board from "./Board";
import Card from "./Card";
import EventBus from "./EventBus";

//const SERVER_URL = "http://localhost:8080";

export default {
  name: "ManageApply",
  display: "Clone",
  order: 2,
  components: {
    DragDrop,
    Board,
    Card,
  },
  data: () => {
    return {
      slide: 0,
      sliding: null,
      uid: "",

      periods: [],
      is_show: false,
      search_input_period: "미정",
      search_input_text: "",

      apply_lists: [],
      selected_apply: [],

      isLeftNavClicked: false,
      leftNavImgsrc: "icons8-arrow-right-64.png",
      intervalid1: "",
      intervalid2: "",

      nav_port_outlist: [],
      nav_ex_outlist: [],
      nav_port_inlist: [],
      nav_ex_inlist: [],
      isEditClicked_list: [],
      isEditClicked_list_show: [],
      isEditCheck: false,
      isEditCheck2: false,
      synCheck: false,

      changingApid: 0,
      changinIdx: 0,

      count_d_e: 0,
      count_d_p: 0,
      count_s_e: 0,
      count_s_p: 0,

      ThreadFlag: true,

      loadingFlag: false,
    };
  },
  computed: {},
  watch: {},

  mounted() {},
  created() {
    //이벤트 버스

    //s -> d는 추가
    //d -> s는 삭제
    //e는 경험, p는 포트폴리오
    //시작 | 끝 | 경험or포트 | 경험or포트 아이디
    EventBus.$on("BoardToApply", (payload) => {
      this.msg = payload;
      //alert(this.msg);

      var myregexp2 = new RegExp("-");
      var op = payload.split(myregexp2);

      //
      if (this.ThreadFlag) {
        this.ThreadFlag = false;

        //경험
        if (op[2] == "e") {
          //추가
          if (op[0] == "s") {
            this.$nextTick(() => {
              this.addE(op[3]);
            });
          }
          //삭제
          else if (op[0] == "d") {
            this.deleteE(op[3]);
          }
        }
        //포트
        else if (op[2] == "p") {
          //추가
          if (op[0] == "s") {
            this.$nextTick(() => {
              this.addP(op[3]);
            });
          }
          //삭제
          else if (op[0] == "d") {
            this.deleteP(op[3]);
          }
        }
      }
    });

    //지원기간 가져오기, 없을경우만
    if (constants.APPLY_PERIOD == null) {
      axios
        .get(this.$SERVER_URL + `/period`, {})
        .then((response) => {
          //console.log(response.data.object);
          constants.APPLY_PERIOD = response.data.object;
          this.periods = constants.APPLY_PERIOD;
          console.log(constants.APPLY_PERIOD);
          //
        })
        .catch((error) => {});
    } else {
      this.periods = constants.APPLY_PERIOD;
    }

    //지원목록 가져오기
    axios
      .get(this.$SERVER_URL + `/apply`, {
        params: {
          uid: localStorage["uid"],
        },
      })
      .then((response) => {
        console.log(response);
        console.log(response.data.object);
        this.apply_lists = response.data.object;

        //수정하기 클릭햇냐
        this.isEditClicked_list = [];
        this.isEditClicked_list_show = [];

        for (var i = 0; i < this.apply_lists.length; i++) {
          this.isEditClicked_list.push(false);
          this.isEditClicked_list_show.push(false);
        }

        console.log(response.data.object[0].portfolioTags);
      })
      .catch((error) => {
        console.log(error);
      });
  },

  methods: {
    onSlideStart(slide) {
      this.sliding = true;
    },
    onSlideEnd(slide) {
      this.sliding = false;
    },
    calendar() {
      this.is_show = true;
      this.$modal.show("example");
    },
    updateValue: function(period) {
      this.search_input_period = period;
    },
    searchByPeriod: function(period) {
      //검색어 없음, 처음 미정일때
      if (period == "") {
        alert("기간을 선택하세요.");
        return;
      }

      //검색어 있으면
      this.$modal.hide("example");
    },
    beforeOpen(event) {
      this.is_show = true;
      console.log("Opening...");
    },
    beforeClose(event) {
      console.log("Closing...");
      this.is_show = false;
      event.cancel();
    },
    /*  view_apply_detail(in_select_apply){
      alert("임시");
      this.selected_apply = in_select_apply;
    }, */
    isEmptyApply() {
      if (this.selected_apply == "") {
        return false;
      }
      return true;
    },

    getComponentData() {
      return {
        on: {
          change: this.handleChange,
          input: this.inputChanged,
        },
        attrs: {
          wrap: true,
        },
        props: {
          value: this.activeNames,
        },
      };
    },
    log: function(evt) {
      window.console.log(evt);
    },
    searchA: function(text, period) {
      
      //로딩 창 키기
      this.loadingFlag = true;
      
      if (period == "미정") period = "";
      this.isEditCheck = false;

      axios
        .get(this.$SERVER_URL + `/apply/search`, {
          params: {
            uid: localStorage["uid"],
            searchTxt: text,
            period: period,
          },
        })
        .then((response) => {

          if(response.data.status){
            console.log(response);
            console.log(response.data.object);
            this.apply_lists = response.data.object;
            console.log(response.data.object[0].portfolioTags);

            //수정하기 클릭햇냐
            this.isEditClicked_list = [];
            this.isEditClicked_list_show = [];
            for (var i = 0; i < this.apply_lists.length; i++) {
              this.isEditClicked_list.push(false);
              this.isEditClicked_list_show.push(false);
            }
          }
          else{
            this.apply_lists = [];
          }

          //로딩창 끄기
          this.loadingFlag = false;
        })
        .catch((error) => {
          console.log(error);
          //로딩창 끄기
          this.loadingFlag = false;
        });
    },
    clickeEdit: function(apid, idx) {
      //alert(apid + " - " + idx);




      this.isEditClicked_list[idx] = !this.isEditClicked_list[idx];
      this.isEditCheck = !this.isEditCheck;
      this.synCheck = !this.synCheck;
      console.log("clickeEdit이에요!");

      this.getPortOutNav(apid, idx);

      this.changingApid = apid;
      this.changinIdx = idx;

      this.isEditClicked_list_show[idx] = true;

      // this.getExOutNav(apid);
    },
    // 이거추가
    clickeDetail(apid, idx) {
      //업데이트 클릭이 되어있을때 만.
        //alert("디테일 눌려요;;");
        this.isEditClicked_list_show[idx] = !this.isEditClicked_list_show[idx];
        
        this.getPortOutNav(apid,idx);
        this.synCheck = !this.synCheck;
      
      //
      if (this.isEditCheck2) {
        this.isEditCheck2 = false;
      }
    },

    saveEdit(apid, apply, idx) {
      //alert("저장 눌려요;;");
      this.isEditClicked_list[idx] = !this.isEditClicked_list[idx];
      this.isEditCheck = !this.isEditCheck;
      this.synCheck = !this.synCheck;
      console.log("saveEdit이에요!");
      console.log(apply.apCompany);

      //저장했을때는 작동안하게 하자..
      this.isEditCheck2 = true;

      if (!this.isEditClicked_list[idx]) {
        this.isEditClicked_list_show[idx] = false;
      }

      axios
        .put(this.$SERVER_URL + `/apply`, {
          uid: localStorage["uid"],
          apid: apid,
          apCompany: apply.apCompany,
          apTerm: apply.apTerm,
          apDesc: apply.apDesc,
          apTitle: apply.apTitle
        })
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 여기까지 추가
    getPortOutNav: function(apid, idx) {
      // console.log("1)getPortOutNav왔어요");
      axios
        .get(this.$SERVER_URL + `/apply/outportfolio`, {
          params: {
            uid: localStorage["uid"],
            apid: apid,
          },
        })
        .then((response) => {
          

          var cell;
          cell = document.getElementsByClassName("toRemove");

          console.log("길이" + cell.length);
          console.log(cell);

          var count = cell.length;

          while(cell.length>0){
            cell.parentNode.removeChild();
          }
  



            // response.data.object = 경험리스트 현재 3개
            var temp = response.data.object;
            // nav_port_outlist = 지원목록 objects
            var temp_list = this.nav_port_outlist;
            // temp_list_idx = 
            var temp_list_idx = 0;

              //만약 없으면
              if(this.nav_port_outlist.length == 0){
                    //alert("gd");

                    this.nav_port_outlist = [];
                    this.nav_port_outlist.splice(0);
                    
                    for(var j = 0; j< this.apply_lists.length; j++){
                      this.nav_port_outlist.push(temp);
                    }

                    temp_list = this.nav_port_outlist;

                    this.nav_port_outlist.splice(0);
                    console.log("hi3 - " + i);
                }
            else{
              this.nav_port_outlist.splice(0);
            }
             for(var i = 0; i < this.apply_lists.length; i++) {
                
                //제대로 nav_port_outlist가 초기화 되있니?
                   if(idx == i){
                      this.nav_port_outlist.push(temp);
                      console.log("hi1 - " + i);
                    }
                    else{
                      this.nav_port_outlist.push(temp_list[i]);
                      console.log("hi2 - " + i);
                    }
            }


          //this.nav_port_outlist[idx] = temp;
          console.log(idx);
          console.log(this.nav_port_outlist[idx]);

          this.getExOutNav(apid, idx);
        })
        .catch((error) => {});
    },

    getExOutNav: function(apid, idx) {
      // console.log("2)getExOutNav왔어요");
      axios
        .get(this.$SERVER_URL + `/apply/outexp`, {
          params: {
            uid: localStorage["uid"],
            apid: apid,
          },
        })
        .then((response) => {

             var cell;
          cell = document.getElementsByClassName("toRemove");

          console.log("길이" + cell.length)
          console.log(cell);

          var count = cell.length;

          while(cell.length>0){
            cell.parentNode.removeChild();
          }

            var temp = response.data.object;
            var temp_list = this.nav_ex_outlist;
            var temp_list_idx = 0;

          //만약 없으면
              if(this.nav_ex_outlist.length == 0){
                    //alert("gd");

                    this.nav_ex_outlist = [];
                    this.nav_ex_outlist.splice(0);
                    
                    for(var j = 0; j< this.apply_lists.length; j++){
                      this.nav_ex_outlist.push(temp);
                    }

                    temp_list = this.nav_ex_outlist;

                    this.nav_ex_outlist.splice(0);
                    console.log("hi3 - " + i);
                }
              else{
                this.nav_ex_outlist.splice(0);
              }
             for(var i = 0; i < this.apply_lists.length; i++) {
                
                //제대로 nav_port_outlist가 초기화 되있니?
                   if(idx == i){
                      this.nav_ex_outlist.push(temp);
                      console.log("hi1 - " + i);
                    }
                    else{
                      this.nav_ex_outlist.push(temp_list[i]);
                      console.log("hi2 - " + i);
                    }
            }
      
      //this.nav_ex_outlist[idx] = temp;

          this.nav_ex_outlist.splice(0);

          for (var i = 0; i < this.apply_lists.length; i++) {
            this.nav_ex_outlist.push(temp);
          }
          this.nav_ex_outlist[idx] = temp;

          this.getPortInNav(apid, idx);
        })
        .catch((error) => {});
    },
    // 여기 수정해야돼!!!!!!!!!!!!!
    getPortInNav: function(apid, idx) {
      // console.log("3)getPortInNav왔어요");
      axios
        .get(this.$SERVER_URL + `/apply/inportfolio`, {
          params: {
            uid: localStorage["uid"],
            apid: apid,
          },
        })
        .then((response) => {
          if (response.data.data != "연결된 포트폴리오가 없습니다.") {


               var cell;
          cell = document.getElementsByClassName("toRemove");

          console.log("길이" + cell.length)
          console.log(cell);

          var count = cell.length;

          while(cell.length>0){
            cell.parentNode.removeChild();
          }

            var temp = response.data.object;
            var temp_list = this.nav_port_inlist;
            var temp_list_idx = 0;

             //만약 없으면
              if(this.nav_port_inlist.length == 0){
                    //alert("gd");

                    this.nav_port_inlist = [];
                    this.nav_port_inlist.splice(0);
                    
                    for(var j = 0; j< this.apply_lists.length; j++){
                      this.nav_port_inlist.push(temp);
                    }

                    temp_list = this.nav_port_inlist;

                    this.nav_port_inlist.splice(0);
                    console.log("hi3 - " + i);
                }
              else{
                  this.nav_port_inlist.splice(0);
              }
             for(var i = 0; i < this.apply_lists.length; i++) {
                
                //제대로 nav_port_outlist가 초기화 되있니?
                   if(idx == i){
                      this.nav_port_inlist.push(temp);
                      console.log("hi1 - " + i);
                    }
                    else{
                      this.nav_port_inlist.push(temp_list[i]);
                      console.log("hi2 - " + i);
                    }
            }


            //this.nav_port_inlist[idx] = response.data.object;
            
            
          }
          this.getExInNav(apid, idx);
        })
        .catch((error) => {});
    },
    getExInNav: function(apid, idx) {
      // console.log(apid + " - 4)getExInNav왔어요");
      axios
        .get(this.$SERVER_URL + `/apply/inexp`, {
          params: {
            uid: localStorage["uid"],
            apid: apid,
          },
        })
        .then((response) => {
          if (response.data.data != "연결된 경험이 없습니다.") {


               var cell;
          cell = document.getElementsByClassName("toRemove");

          console.log("길이" + cell.length)
          console.log(cell);

          var count = cell.length;

          while(cell.length>0){
            cell.parentNode.removeChild();
          }

            var temp = response.data.object;
            var temp_list = this.nav_ex_inlist;
            var temp_list_idx = 0;

          //만약 없으면
              if(this.nav_ex_inlist.length == 0){
                    //alert("gd");

                    this.nav_ex_inlist = [];
                    this.nav_ex_inlist.splice(0);
                    
                    for(var j = 0; j< this.apply_lists.length; j++){
                      this.nav_ex_inlist.push(temp);
                    }

                    temp_list = this.nav_ex_inlist;

                    this.nav_ex_inlist.splice(0);
                    console.log("hi3 - " + i);
                }
              else{
               this.nav_ex_inlist.splice(0);
              }
             for(var i = 0; i < this.apply_lists.length; i++) {
                
                //제대로 nav_port_outlist가 초기화 되있니?
                   if(idx == i){
                      this.nav_ex_inlist.push(temp);
                      console.log("hi1 - " + i);
                    }
                    else{
                      this.nav_ex_inlist.push(temp_list[i]);
                      console.log("hi2 - " + i);
                    }
            }
          }
          this.synCheck != this.synCheck;
        })
        .catch((error) => {});
    },
    // 여기까지 수정해야돼!!!!!!!!!!!!
    addApply: function() {
      axios
        .post(this.$SERVER_URL + `/apply`, {
          apCompany: "회사명",
          apDesc: "설명",
          apTerm: "미정",
          apTitle: "제목",
          uid: localStorage["uid"],
        })
        .then((response) => {
          //빈 지원리스트일 경우
          if (this.apply_lists == null) {
            this.apply_lists = [];
          }

          //표면상 추가
          this.apply_lists.push(response.data.object);
        })
        .catch((error) => {
          //alert("실패");
          console.log(error);
        });
    },
    deleteA: function(apid, idx) {
      axios
        .delete(this.$SERVER_URL + `/apply`, {
          params: {
            uid: localStorage["uid"],
            apid: apid,
          },
        })
        .then((response) => {
          //alert("넵 port")
          this.$delete(this.apply_lists, idx);
          this.$delete(this.isEditClicked_list, idx);

          this.isEditCheck = false;
          this.isEditCheck2 = false;
          console.log("삭제했어요");

          //바꾸고 있는게 삭제하려는 거일 경우 모두 리셋
          if (this.changingApid == apid) {
            for (var i = 0; i < this.apply_lists.length; i++) {
              this.isEditClicked_list[i] = false;
              this.isEditClicked_list_show[i] = false;
            }
          }
        })
        .catch((error) => {});
    },
    addE(exid) {
      console.log("경험 추가" + this.changingApid + "-" + exid);
      axios
        .post(this.$SERVER_URL + `/apply/addExp`, {
          apid: this.changingApid,
          exid: exid,
        })
        .then((response) => {
          this.ThreadFlag = true;
        })
        .catch((error) => {
          this.ThreadFlag = true;
        });
    },
    addP(pid) {
      console.log("포트 추가" + this.changingApid + "-" + pid);
      axios
        .post(this.$SERVER_URL + `/apply/addPortfolio`, {
          apid: this.changingApid,
          pid: pid,
        })
        .then((response) => {
          this.ThreadFlag = true;
        })
        .catch((error) => {
          this.ThreadFlag = true;
        });
    },
    deleteE(exid) {
      console.log("경험 삭제" + this.changingApid + "-" + exid);
      axios
        .delete(this.$SERVER_URL + `/apply/deleteExp`, {
          data: {
            apid: this.changingApid,
            exid: exid,
          },
        })
        .then((response) => {
          this.ThreadFlag = true;
        })
        .catch((error) => {
          this.ThreadFlag = true;
        });
    },
    deleteP(pid) {
      console.log("포트 삭제" + this.changingApid + "-" + pid);
      axios
        .delete(this.$SERVER_URL + `/apply/deletePortfolio`, {
          data: {
            apid: this.changingApid,
            pid: pid,
          },
        })
        .then((response) => {
          this.ThreadFlag = true;
        })
        .catch((error) => {
          this.ThreadFlag = true;
        });
    },
    test(idx, ap_term){
      //alert(idx);
      //gg
      ap_term = idx;
    }
  },
};
</script>

<style>
 

#manageA {
  width: 70%;
  margin: 50px auto;
}

.search-input {
  width: 70%;
  text-align: center;
}

.search_top_period {
  width: 20%;
  font-size: 16px;
  border: 0;
  outline: 0;
  font-family: "Noto Sans KR", "NanumGothic", "Nanum Gothic", "Malgun Gothic",
    "Apple SD Gothic Neo", dotum, sans-serif;
  font-weight: 500;
  color: #000;
  font-size: 20px;

  border-bottom: 3px solid #00000022;
  height: 45px;
  background: #e7e7e7ff;
}

.search_top_text {
  width: 50%;
  font-size: 16px;
  border: 0;
  outline: 0;
  font-family: "Noto Sans KR", "NanumGothic", "Nanum Gothic", "Malgun Gothic",
    "Apple SD Gothic Neo", dotum, sans-serif;
  font-weight: 500;
  color: #000;
  font-size: 20px;

  border-bottom: 3px solid #00000022;
  height: 45px;
  background: #e7e7e7ff;
}

.custom_search_container {
  margin: auto;
  text-align: -webkit-center;
}

.custom-modal-header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  padding: 1rem 1rem;
  border-bottom: 1px solid #dee2e6;
  border-top-left-radius: calc(0.3rem - 1px);
  border-top-right-radius: calc(0.3rem - 1px);

  background: #eeeeee;
  font-weight: 400;
}

.modal-contents {
  text-align: center;
}

.modal-button {
  text-align: right;
}

.apply_detail {
  text-align: center;
  position: inherit;
  margin: auto;
  top: 0;
  /* right: auto; */
  width: 1024;
  border: 0;
  z-index: 10;
}

.custom-apply-Btn {
  position: relative;
  top: 2px;
}
#sidebar-right {
  width: 15%;
}
#applyList {
  /* width: 60%; */
}
.applyCard {
  position: relative;
  padding: 15px;
  width: 100%;
  background-color: lightgray;
  border-radius: 30px;
  outline: none;
  box-shadow: 0 3px 1px -2px rgba(0,0,0,.2), 0 2px 2px 0 rgba(0,0,0,.14), 0 1px 5px 0 rgba(0,0,0,.12);
  margin-bottom: 0.5em;
}
.applyCardHeader {
  outline: none;
}
#applyCardBtn {
  position: absolute;
  display: flex;
  top: 20px;
  right: 20px;
}
.aBtn {
  cursor: pointer;
  outline: none !important;
  padding: 5px;
}
.contents_list {
  display: inline;
}

.custom-left {
  position: relative;
}

.custom-left-list {
  height: 100%;
}

.list-top {
  display: flex;
}

.custom-contents2 {
  width: 100%;
}

.left {
  float: left;
  position: relative;
  width: 50%;
  height: 100%;
}
.right {
  float: left;
  position: relative;
  width: 40%;
  margin-left: 5%;
  height: 100%;
}
@-webkit-keyframes wiggle {
  0% {
    -webkit-transform: rotate(0deg);
  }
  25% {
    -webkit-transform: rotate(2deg);
  }
  75% {
    -webkit-transform: rotate(-2deg);
  }
  100% {
    -webkit-transform: rotate(0deg);
  }
}
@keyframes wiggle {
  0% {
    transform: rotate(-2deg);
  }
  25% {
    transform: rotate(2deg);
  }
  75% {
    transform: rotate(-2deg);
  }
  100% {
    transform: rotate(0deg);
  }
}

.board {
  display: flex;
  flex-direction: column;

  width: 100%;

  background-color: #d4d3d3;

  /* border: 1.5px solid #d4e4e4; */
  border-radius: 5px;
  /* border-style: dotted; */
  padding: 25px;
}

.board .card2 {
  padding: 15px 25px;
  background-color: #999999;
  margin-bottom: 15px;
  border-radius:15px;
 
 border: 3px solid #797979;
 
  
  cursor: pointer;
}

.board .card2 div {
  color: #ffffff;
}

.boardNoE {
  display: flex;
  flex-direction: column;

  width: 100%;

  background-color: #d4d3d3;

  /* border: 1.5px solid #d4e4e4; */
  border-radius: 5px;
  /* border-style: dotted; */
  padding: 25px;
}

.boardNoE .cardNoE {
  padding: 15px 25px;
  margin-bottom: 15px;
   border-radius:15px;


   background-color: #999999;
  margin-bottom: 15px;
  border-radius:15px;
 
 border: 3px solid #797979;
}



.boardNoP {
  display: flex;
  flex-direction: column;

  width: 100%;

  background-color: #d4d3d3;

  /* border: 1.5px solid #d4e4e4; */
  border-radius: 5px;
  /* border-style: dotted; */
  padding: 25px;
}

.boardNoP .cardNoP {
  padding: 15px 25px;
  margin-bottom: 15px;
   border-radius:15px;


   background-color: #999999;
  margin-bottom: 15px;
  border-radius:15px;
 
 border: 3px solid #797979;
}

.boardNoP .cardNoP div {
  color: #ffffff;
}

.boardNoE .cardNoE div {
  color: #ffffff;
}



.card2 > div{

 
  transition-property: background-color, border-radius,transform;
  transition-duration: 0.1s, 1.0s;
  transition-timing-function:ease-in;
}

.board > div:hover {

  
   transform: translate(0%, -10%);

}

.card2 :active {
  -webkit-animation: wiggle 0.6s 0s infinite ease-in;
  animation: wiggle 0.6s 0s infinite ease-in;
  border: 2px solid #9c85ff;
  background-color: #7a63ff;
   box-shadow: 0 0 8px #7a63ff;
  opacity: 0.6;
}


.dragSpace {
  font-size: x-large;
  min-height: 50%;
  border: 5px solid #bbbbbb;
  border-radius: 15px;
  border-style: dotted;
}

.dragText {
  text-align: center;
  margin: 30px 0;
  justify-content: space-evenly;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}

.fade-enter-active {
  transition: all 0.3s ease;
}
.fade-leave-active {
  transition: all 0.8s cubic-bezier(1, 0.5, 0.8, 1);
}
.fade-enter, .fade-leave-to
/* .slide-fade-leave-active below version 2.1.8 */ {
  transform: translateX(10px);
  opacity: 0;
}

.custom-button-align {
  text-align: center;
}

.applyTitle{
  text-align: center;
}

.applyTitleSize{
      margin: auto;
    text-align: center;
    width: 50%;
}

.ApplysubTitle{
  padding-left:5%;
  font-size: 2em;
  font-family: "Noto Sans KR", "NanumGothic", "Nanum Gothic", "Malgun Gothic", "Apple SD Gothic Neo", dotum, sans-serif;
}
.card_title{
  font-size:2em;
  font-weight:bold;
}
.card_date{
  font-size:0.5em;
  color:#ffffff;
  text-align: right;
}
.card_contents{
}

.period-padding{
  margin-left: -0.5em;
}
.contents-padding{
  margin-left: -0.5em;
}

.white_line{
  margin-top: 1rem;
    margin-bottom: 1rem;
    border: 0;
    border-top: 1px solid rgba(255, 255, 255, 0.3);
}

#board-s-p .card_contents{
  display: none;
}


#board-s-e .card_contents{
  display: none;

}

</style>
