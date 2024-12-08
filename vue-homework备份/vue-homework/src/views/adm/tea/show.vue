<script setup>
import { ref, onMounted } from 'vue';
import { getTea, deleteTea, getImage } from '@/axios/index.js'; // 导入 deleteTea 和 getImage 函数
import Modal from '@/components/Modal.vue';
import AddTeacher from './add.vue';

const teachers = ref([]);
const showModal = ref(false);

onMounted(() => {
  getTea((data) => {
    // 获取图片 URL 并存储在每个教师对象中
    Promise.all(data.map(teacher => {
      return getImage(teacher.pic, (url) => {
        teacher.imageUrl = url;
      }, (message, code, url) => {
        console.error(`获取图片失败: ${message}`);
      });
    })).then(() => {
      teachers.value = data;
    });
  }, (message, code, url) => {
    console.error(`获取教师信息失败: ${message}`);
  });
});

const handleAddTeacher = () => {
  showModal.value = true;
};

const handleCloseModal = () => {
  showModal.value = false;
};

// 新增删除教师的处理函数
const handleDeleteTeacher = (uid) => {
  deleteTea(uid, () => {
    // 删除成功后重新获取教师列表
    getTea((data) => {
      teachers.value = data;
    }, (message, code, url) => {
      console.error(`删除教师信息失败: ${message}`);
    });
  }, (message, code, url) => {
    console.error(`删除教师信息失败: ${message}`);
  });
};
</script>

<template>
  <div>
    <h3>教师信息</h3>
    <button @click="handleAddTeacher">添加教师</button>
    <table border="1" cellspacing="0" cellpadding="5">
      <thead>
      <tr>
        <th>教师编号</th>
        <th>教师名称</th>
        <th>电话</th>
        <th>角色</th>
        <th>头像</th>
        <th>操作</th> <!-- 新增操作列 -->
      </tr>
      </thead>
      <tbody>
      <tr v-for="(teacher, index) in teachers" :key="index">
        <td>{{ teacher.uid }}</td>
        <td>{{ teacher.uname }}</td>
        <td>{{ teacher.phone }}</td>
        <td>{{ teacher.role }}</td>
        <td>
          <img :src="teacher.imageUrl" alt="Teacher Image" style="width: 50px; height: 50px;">
        </td>
        <td>
          <button @click="handleDeleteTeacher(teacher.uid)">删除</button> <!-- 新增删除按钮 -->
        </td>
      </tr>
      </tbody>
    </table>
    <!-- 模态框组件 -->
    <Modal :visible="showModal" @update:visible="handleCloseModal">
      <AddTeacher @close="handleCloseModal"/>
    </Modal>
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

button {
  margin-bottom: 10px;
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

/* 删除按钮样式 */
button.delete {
  background-color: #dc3545;
}

button.delete:hover {
  background-color: #c82333;
}
</style>
