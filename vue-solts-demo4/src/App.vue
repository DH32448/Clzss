<script setup>
import SlotsBase from "./components/SlotsBase.vue";
import SlotsTwo from "@/components/SlotsTwo.vue";
import SlostAttr from "@/components/SlostAttr.vue";
</script>
<script>
export default {
  data(){
    return{
      msg:'内容续集'
    }
  }
}
</script>
<template>
<!--  <SlotsBase>-->
<!--    <div>-->
<!--      <h3>插槽标题</h3>-->
<!--      <p>内容</p>-->
<!--    </div>-->
<!--  </SlotsBase>-->

<!--  <SlotsTwo>-->
<!--    <template #header>-->
<!--      <h3>{{msg}}</h3>-->
<!--    </template>-->
<!--    <template v-slot:end>-->
<!--      <h3>内容</h3>-->
<!--    </template>-->
<!--  </SlotsTwo>-->
    <SlostAttr>
      <template v-slot:mmm = "slotProps">
        <h3>{{msg}}-{{slotProps.msg}}</h3>
      </template>
      <template #main="slotmmm">
        <p>{{slotmmm.jop}}</p>
      </template>
    </SlostAttr>
  <SlostAttr v-slot="slotProps">
    <h3>{{msg}}-{{slotProps.msg}}</h3>
  </SlostAttr>
</template>

<style scoped>

</style>
