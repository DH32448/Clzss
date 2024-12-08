<template>
  <div>
    <h1>学生成绩</h1>
    <table v-if="studentScores.length > 0">
      <thead>
      <tr>
        <th>学生编号</th>
        <th>学生姓名</th>
        <th>班级编号</th>
        <th>班级名称</th>
        <th>课程编号</th>
        <th>课程名称</th>
        <th>课程成绩</th>
        <th>录入时间</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(score, index) in studentScores" :key="index">
        <td>{{ score.sno }}</td>
        <td>{{ score.user.uname }}</td>
        <td>{{ score.user.clz.clzno }}</td>
        <td>{{ score.user.clz.clzname }}</td>
        <td>{{ score.course.cno }}</td>
        <td>{{ score.course.cname }}</td>
        <td>{{ score.score }}</td>
        <td>{{ formatDate(score.tpost) }}</td>
      </tr>
      </tbody>
    </table>
    <p v-else>暂无成绩数据</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getStudentMarks } from '@/axios/index.js';

// 定义一个响应式变量来存储学生成绩数据
const studentScores = ref([]);

// 在组件挂载时调用 getStudentMarks 函数获取学生成绩
onMounted(() => {
  getStudentMarks((data) => {
    studentScores.value = data;
  }, (message, status, url) => {
    console.error(`获取学生成绩失败: ${message}`);
  });
});

// 格式化日期
const formatDate = (dateString) => {
  const date = new Date(dateString);
  return date.toLocaleDateString() + ' ' + date.toLocaleTimeString();
};
</script>

<style scoped>
/* 可以在这里添加样式 */
table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
}

th {
  background-color: #f2f2f2;
  text-align: left;
}
</style>
