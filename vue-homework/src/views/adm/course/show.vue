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
          <button @click="deleteCourseById(course.cno)">删除</button>
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
import { getCourse, deleteCourse } from '@/axios/index.js';
import AddCourse from '@/views/adm/course/add.vue';
import UpdateCourse from '@/views/adm/course/update.vue';

const courses = ref([]);
const route = useRoute();
const addCourseModal = ref(null);
const updateCourseModalVisible = ref(false);
const selectedCourse = ref(null);

const fetchCourses = () => {
  console.log('开始获取课程信息');
  getCourse((data) => {
    console.log('获取课程信息成功:', data);
    courses.value = data;  // 将课程数据存储到 courses 变量中
  }, (message, code, url) => {
    console.error(`获取课程信息失败: ${message}`);
  });
};

const openAddModal = () => {
  console.log('打开添加课程模态框');
  addCourseModal.value.openModal();
};

const openUpdateModal = (course) => {
  console.log('打开更新课程模态框，课程信息:', course);
  selectedCourse.value = course;
  updateCourseModalVisible.value = true;
};

const updateUpdateCourseModalVisible = (value) => {
  console.log('更新更新课程模态框可见性:', value);
  updateCourseModalVisible.value = value;
};

const deleteCourseById = (cno) => {
  console.log('开始删除课程，课程编号:', cno);
  deleteCourse(cno, () => {
    console.log('删除课程成功');
    fetchCourses(); // 刷新课程列表
  }, (message, code, url) => {
    console.error(`删除课程失败: ${message}`);
  });
};

onMounted(() => {
  console.log('组件挂载完成，开始获取课程信息');
  fetchCourses();
});

watch(
    () => route.query,
    (newQuery, oldQuery) => {
      console.log('路由查询参数变化，重新获取课程信息', { newQuery, oldQuery });
      fetchCourses();
    }
);
</script>

<style scoped>

</style>
