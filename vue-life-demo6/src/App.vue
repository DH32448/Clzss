<script>
import User from "@/components/User.vue";
import A from "@/components/A.vue";
import B from "@/components/B.vue";
/**
 * 生命周期函数
 * 创建期：beforeCreate create
 * 挂载期：beforeMount mount
 * 更新期：beforeUpdate update
 * 销毁期：beforeUnmount unmount
 */
export default {
  components: {B, A, User},
  data() {
    return {
      msg: '更新之前',
      tabCon: 'A'
    }
  },
  beforeCreate() {
    console.log("组件创建期");
  },
  created() {
    console.log("组件创建完成");
  },
  beforeMount() {
    console.log("组件挂载期");
  },
  mounted() {
    console.log("组件挂载完成");
  },
  beforeUpdate() {
    console.log("组件更新期");
  },
  updated() {
    console.log("组件更新完成");
  },
  beforeUnmount() {
    console.log("组件销毁期");
  },
  unmounted() {
    console.log("组件销毁完成");
  },
  methods: {
    change() {
      this.tabCon = this.tabCon === 'A' ? 'B' : 'A'
    }
  }
}
</script>

<template>
<!--<p>{{msg}}</p>-->
<!--  <button @click="msg = '更新之后'">更新</button>-->

<!--  <User/>-->

  <A/>
  <B/>
  <keep-alive>
    <component :is="tabCon"></component>
  </keep-alive>
  <button @click="change">切换组件</button>
</template>
