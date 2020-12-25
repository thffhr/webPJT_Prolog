<template>
    <div
        :id="id"
        class="card2"
        :draggable="draggable"
        @dragstart="dragStart"
        @dragover.stop
        
        @dragover.prevent
        @drop.prevent="drop"
        >
        <slot />
    </div>
</template>

<script>
import EventBus from './EventBus';


export default {
    name: 'Card',
    props: ['id', 'draggable'],
    methods:{
        dragStart: e => {
            const target = e.target;

            e.dataTransfer.setData('card_id', target.id);
           // alert("여기요" + target.id);!!

            if(target.parentNode.id == ""){
                e.dataTransfer.setData('parent_id',target.parentNode.parentNode.id);
            }
            else{
                e.dataTransfer.setData('parent_id',target.parentNode.id);
            }

            setTimeout(() => {
                target.style.display = "none";
            },0);

        },

          drop: e=>{
        
        
        }
    },
      
}
</script>