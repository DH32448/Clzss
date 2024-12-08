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
import { getTask, addTask, deleteTask as apiDeleteTask } from '@/axios'; // 假设 deleteTask 函数放在 axios.js 文件中
import Modal from '@/components/Modal.vue';

const isModalVisible = ref(false);
const user = ref({ uid: '', uname: '' });
const clz = ref({ clzno: '', clzname: '' });
const course = ref({ cno: '', cname: '' });
const errorMessage = ref('');
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

const openModal = () => {
  isModalVisible.value = true;
};

const handleSubmit = () => {
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
              tasks.value = data;
            },
            (message, status) => {
              console.error(`获取任务失败: ${message}, 状态码: ${status}`);
            }
        );
        // 可以在这里添加其他逻辑，例如重置表单或显示成功消息
      },
      (msg, code) => {
        errorMessage.value = `错误 ${code}: ${msg}`; // 设置错误消息
      }
  );
};
const deleteTask = (kid) => {
  apiDeleteTask(
      kid,
      (data) => {
        console.log('任务删除成功:', data);
        // 刷新任务列表
        getTask(
            (data) => {
              tasks.value = data;
            },
            (message, status) => {
              console.error(`获取任务失败: ${message}, 状态码: ${status}`);
            }
        );
      },
      (msg, code) => {
        errorMessage.value = `错误 ${code}: ${msg}`; // 设置错误消息
      }
  );
};
</script>

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
.error {
  color: red;
  font-size: 1.2em;
}
button.delete {
  background-color: red;
  color: white;
  border: none;
  padding: 5px 10px;
  cursor: pointer;
}
button.delete:hover {
  background-color: darkred;
}
</style>
