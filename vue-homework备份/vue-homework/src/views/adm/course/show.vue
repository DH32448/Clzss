<template>
  <div>
    <h3>课程信息</h3>
    <button @click="openAddModal">添加课程</button>
    <table border="1" cellspacing="0" cellpadding="5">
      <thead>
      <tr>
        <th>课程编号</th>
        <th>课程名称</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(course, index) in courses" :key="index">
        <td>{{ course.cno }}</td>
        <td>{{ course.cname }}</td>
        <td>
          <button @click="openUpdateModal(course)">更新</button>
        </td>
      </tr>
      </tbody>
    </table>
    <AddCourse ref="addCourseModal" @refreshCourses="fetchCourses" />
    <UpdateCourse
        :visible="updateCourseModalVisible"
        :course="selectedCourse"
        @update:visible="updateUpdateCourseModalVisible"
        @refreshCourses="fetchCourses"
    />
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRoute } from 'vue-router';
import { getCourse } from '@/axios/index.js';
import AddCourse from '@/views/adm/course/add.vue';
import UpdateCourse from '@/views/adm/course/update.vue';

const courses = ref([]);
const route = useRoute();
const addCourseModal = ref(null);
const updateCourseModalVisible = ref(false);
const selectedCourse = ref(null);

const fetchCourses = () => {
  getCourse((data) => {
    courses.value = data;  // 将课程数据存储到 courses 变量中
  }, (message, code, url) => {
    console.error(`获取课程信息失败: ${message}`);
  });
};

const openAddModal = () => {
  addCourseModal.value.openModal();
};

const openUpdateModal = (course) => {
  selectedCourse.value = course;
  updateCourseModalVisible.value = true;
};

const updateUpdateCourseModalVisible = (value) => {
  updateCourseModalVisible.value = value;
};

onMounted(() => {
  fetchCourses();
});

watch(
    () => route.query,
    () => {
      fetchCourses();
    }
);
</script>

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

button {
  padding: 10px;
  font-size: 16px;
  background-color: #4CAF50;
  color: white;
  border: none;
  cursor: pointer;
  margin-bottom: 20px;
}

button:hover {
  background-color: #45a049;
}
</style>
