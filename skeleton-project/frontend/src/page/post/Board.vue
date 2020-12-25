<template>
  <div
      :id="id"
      class="board"
      @dragover.prevent
      @drop.prevent="drop"
      >
    <slot />
  </div>
</template>

<script>
import EventBus from './EventBus';

export default {
  name: 'Board',
  props: ['id'],

  created(){
   
  }
  ,
  methods: {
    drop: e=>{
      const card_id = e.dataTransfer.getData('card_id');
      const parent_id = e.dataTransfer.getData('parent_id');

      const card = document.getElementById(card_id);
      card.setAttribute('class', 'toRemove card2 card')

      //var contents = document.getElementsByClassName('card_contents')[0].remove();
      

      //alert(card.getAttribute('class'));

      card.style.display = "block";
      
      //ex) board-d-e | board-s-e
      //ex) card-s-e-384 !!

      var myregexp2 = new RegExp("-"); 
      var start = parent_id.split(myregexp2)[1];
      var board_s_compare = parent_id.split(myregexp2)[2];
      var board_d_compare = e.target.id.split(myregexp2)[2];
      var end = e.target.id.split(myregexp2)[1];
      var splited_bun = card_id.split(myregexp2)[2];
      var splited_idx = card_id.split(myregexp2)[3];

      //alert(board_s_compare +"-" +board_d_compare);
  /* 
      alert("시험" + start);
      alert("시험" + end);
      alert("시험" + splited_bun);
      alert("시험" + splited_idx);

      alert("옮겨지는 보드의 아이디(에서)" + parent_id);
      alert("옮기는 보드 아이디(으로)" + e.target.id);
      alert("드래그 하는 카드 아이디" + card_id); */

      //만약에 같은 요소를 선택했으면
      if(e.target.id == ""){
        
      }
      //같은 부모 아님 && 같은 종류의 드래그앤 드랍 && 시작과 끝이 달라야함
      else if((e.target.id != parent_id) && (board_s_compare == board_d_compare) && (start != end)){
        e.target.appendChild(card);
        document.getElementById(e.target.id).insertBefore(card, document.getElementById(e.target.id).firstElementChild);

        //alert("이벤트 실행 ㄱㄱ");
        //s -> d는 추가
        //d -> s는 삭제
        EventBus.$emit('BoardToApply', start + "-" + end + "-" + splited_bun + "-" + splited_idx);
      
      }

      //e.target.createElement("div");
      

       
    },
  
  /* delete:function(cardId){
    
    alert("끝")
    const card = document.getElementById(cardId);
    card.parentNode.removeChild(cardId);

  } */
  }
}
</script>
<style>

</style>