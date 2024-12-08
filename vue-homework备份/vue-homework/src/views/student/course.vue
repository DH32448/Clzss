<template>
  <div>
    <h1>学生课程</h1>
    <table v-if="studentCourses.length > 0">
      <thead>
      <tr>
        <th>课程编号</th>
        <th>课程名称</th>
        <th>班级编号</th>
        <th>班级名称</th>
        <th>任课教师</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(course, index) in studentCourses" :key="index">
        <td>{{ course.course.cno }}</td>
        <td>{{ course.course.cname }}</td>
        <td>{{ course.clz.clzno }}</td>
        <td>{{ course.clz.clzname }}</td>
        <td>{{ course.user.uname }}</td>
      </tr>
      </tbody>
    </table>
    <p v-else>暂无课程数据</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getStudentCourses } from '@/axios/index.js';

// 定义一个响应式变量来存储学生课程数据
const studentCourses = ref([]);

// 在组件挂载时调用 getStudentCourses 函数获取学生课程信息
onMounted(() => {
  getStudentCourses((data) => {
    studentCourses.value = data;
  }, (message, status, url) => {
    console.error(`获取学生课程失败: ${message}`);
  });
});
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
