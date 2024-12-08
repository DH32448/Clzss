<script>
export default {
  data(){
    return{
      banner:[]
    }
  },
  beforeMount() {
    console.log(this.$refs.name)
  },
  mounted(){
    console.log(this.$refs.name)
  },
  mounted() {
    //模拟网络请求
    this.banner = [
        {
      title:'标题',
      content:'内容'
    },
        {
      title:'标题',
      content:'内容'
    }
    ]
  }
}
</script>

<template>
  <h3>组件生命周期应用</h3>
  <p ref="name">it</p>
  <ul>
    <li v-for="(item,index) in banner" :key="index">
      <h3>{{item.title}}</h3>
      <h3>{{item.content}}</h3>
    </li>
  </ul>
</template>

<style scoped>

</style>