<script>
export default {
  data(){
    return{
      child:'子数据',
      jop:'草间数据'
    }
  }
}
</script>

<template>
<h3>3续集</h3>
  <slot :msg="child" name="mmm"></slot>
  <slot name="main" :jop="jop"></slot>
</template>

<style scoped>

</style>