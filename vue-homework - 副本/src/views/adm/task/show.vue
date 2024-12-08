<script setup>
import { ref, onMounted } from 'vue';
import { getTask } from '@/axios'; // 假设 getTask 函数放在 axios.js 文件中

// 创建一个 ref 来保存任务数据
const tasks = ref([]);

// 请求任务数据
onMounted(() => {
  getTask(
      (data) => {
        tasks.value = data;
      },
      (message, status) => {
        console.error(`获取任务失败: ${message}, 状态码: ${status}`);
      }
  );
});
</script>

<template>
  <div>
    <h2>任务列表</h2>
    <table border="1" cellpadding="10">
      <thead>
      <tr>
        <th>用户</th>
        <th>班级</th>
        <th>课程</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="task in tasks" :key="task.kid">
        <td>{{ task.user.uname }}</td>
        <td>{{ task.clz.clzname }}</td>
        <td>{{ task.course.cname }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
h2 {
  font-size: 1.5em;
  margin-bottom: 10px;
}
table {
  width: 100%;
  border-collapse: collapse;
}
th, td {
  text-align: left;
  padding: 8px;
}
th {
  background-color: #f2f2f2;
}
</style>