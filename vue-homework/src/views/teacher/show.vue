<template>
  <div>
    <h2>教师主页</h2>
    <ul>
      <li>
        <router-link to="/tea/pwd" @click="logClick('修改密码')">修改密码</router-link>
      </li>
      <li>
        <router-link to="/tea/mark" @click="logClick('登分管理')">登分管理</router-link>
      </li>
    </ul>
  </div>
  <div>
    <router-view/>
  </div>
</template>

<script setup>
import { onMounted } from 'vue';

const logClick = (action) => {
  console.log(`点击了 ${action}链接`);
};

onMounted(() => {
  console.log('show.vue 组件');
});
</script>

<style scoped>

</style>
