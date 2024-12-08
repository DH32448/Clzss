<script setup>
import { useRouter, useRoute } from 'vue-router';
import clzShow from './clz/show.vue';
import courseShow from './course/show.vue';
import teaShow from './tea/show.vue';
import stuShow from './stu/show.vue';
import taskShow from './task/show.vue';
import headShow from './head/show.vue';

const router = useRouter();
const route = useRoute();

const navigateTo = (path) => {
  router.push(path);
};
</script>

<template>
  <div>
    <div style="float: left;width: 25%;border: 1px solid red;margin: 0" >
      <div @click="navigateTo('/adm/clz')">班级管理</div>
      <div @click="navigateTo('/adm/course')">课程管理</div>
      <div @click="navigateTo('/adm/tea')">教师管理</div>
      <div @click="navigateTo('/adm/stu')">学生管理</div>
      <div @click="navigateTo('/adm/task')">教学安排</div>
      <div @click="navigateTo('/adm/head')">更新头像</div>
    </div>
    <div style="border: 1px solid black;">
      <clz-show v-if="route.path === '/adm/clz'"/>
      <course-show v-if="route.path === '/adm/course'"/>
      <tea-show v-if="route.path === '/adm/tea'"/>
      <stu-show v-if="route.path === '/adm/stu'"/>
      <task-show v-if="route.path === '/adm/task'"/>
      <head-show v-if="route.path === '/adm/head'"/>
    </div>
  </div>
</template>

<style scoped>
div {
  cursor: pointer;
  padding: 10px;
  margin: 5px 0;
}
div:hover {
  background-color: #f0f0f0;
}
</style>