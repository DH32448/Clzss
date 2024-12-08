<template>
  <div>
    <h2>任务列表</h2>
    <button @click="openModal">添加任务</button>
    <Modal :visible="isModalVisible" @update:visible="isModalVisible = $event">
      <form @submit.prevent="handleSubmit">
        <!-- ... -->
      </form>
      <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
    </Modal>
    <table border="1" cellpadding="10">
      <thead>
      <tr>
        <th>用户</th>
        <th>班级</th>
        <th>课程</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="task in tasks" :key="task.kid">
        <td>{{ task.user.uname }}</td>
        <td>{{ task.clz.clzname }}</td>
        <td>{{ task.course.cname }}</td>
        <td><button @click="deleteTask(task.kid)">删除</button></td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getTask, addTask, deleteTask as apiDeleteTask } from '@/axios';
import Modal from '@/components/Modal.vue';

const isModalVisible = ref(false);
const user = ref({ uid: '', uname: '' });
const clz = ref({ clzno: '', clzname: '' });
const course = ref({ cno: '', cname: '' });
const errorMessage = ref('');
const tasks = ref([]);

// 请求任务数据
onMounted(() => {
  console.log('组件挂载，请求任务数据');
  getTask(
      (data) => {
        console.log('获取任务成功:', data);
        tasks.value = data;
      },
      (message, status) => {
        console.error(`获取任务失败: ${message}, 状态码: ${status}`);
      }
  );
});

const openModal = () => {
  console.log('打开模态框');
};

const handleSubmit = () => {
  console.log('提交任务', { user: user.value, clz: clz.value, course: course.value });
  addTask(
      user.value,
      clz.value,
      course.value,
      (data) => {
        console.log('任务添加成功:', data);
        // 关闭模态框
        isModalVisible.value = false;
        // 刷新任务列表
        getTask(
            (data) => {
              console.log('刷新任务列表成功:', data);
              tasks.value = data;
            },
            (message, status) => {
              console.error(`刷新任务列表失败: ${message}, 状态码: ${status}`);
            }
        );

      },
      (msg, code) => {
        console.error(`任务添加失败: 错误 ${code}: ${msg}`);
        errorMessage.value = `错误 ${code}: ${msg}`;
      }
  );
};

const deleteTask = (kid) => {
  console.log('删除任务，任务ID:', kid);
  apiDeleteTask(
      kid,
      (data) => {
        console.log('任务删除成功:', data);
        // 刷新任务列表
        getTask(
            (data) => {
              console.log('刷新任务列表成功:', data);
              tasks.value = data;
            },
            (message, status) => {
              console.error(`刷新任务列表失败: ${message}, 状态码: ${status}`);
            }
        );
      },
      (msg, code) => {
        console.error(`任务删除失败: 错误 ${code}: ${msg}`);
        errorMessage.value = `错误 ${code}: ${msg}`;
      }
  );
};
</script>

<style scoped>

</style>
