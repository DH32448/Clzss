<script setup>
import { ref, onMounted } from 'vue';
import { getTea } from '@/axios/index.js';

const teachers = ref([]);

onMounted(() => {
  getTea((data) => {
    teachers.value = data;  // 将教师数据存储到 teachers 变量中
  }, (message, code, url) => {
    console.error(`获取教师信息失败: ${message}`);
  });
});
</script>

<template>
  <div>
    <h3>教师信息</h3>
    <table border="1" cellspacing="0" cellpadding="5">
      <thead>
      <tr>
        <th>教师编号</th>
        <th>教师名称</th>
        <th>电话</th>
        <th>角色</th>
        <th>头像</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(teacher, index) in teachers" :key="index">
        <td>{{ teacher.uid }}</td>
        <td>{{ teacher.uname }}</td>
        <td>{{ teacher.phone }}</td>
        <td>{{ teacher.role }}</td>
        <td><img :src="teacher.pic" alt="头像" width="50" height="50"/></td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
table {
  width: 100%;
  margin: 20px 0;
  border-collapse: collapse;
}

th, td {
  padding: 10px;
  text-align: left;
}

th {
  background-color: #f4f4f4;
}

tr:nth-child(even) {
  background-color: #f9f9f9;
}
</style>