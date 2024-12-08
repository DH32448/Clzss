<script setup>
import { ref, onMounted } from 'vue';
import { getTea, deleteTea, getImage } from '@/axios/index.js'; // 导入 deleteTea 和 getImage 函数
import Modal from '@/components/Modal.vue';
import AddTeacher from './add.vue';

const teachers = ref([]);
const showModal = ref(false);

onMounted(() => {
  console.log('开始获取教师信息');
  getTea((data) => {
    console.log('获取教师信息成功', data);
    // 获取图片 URL 并存储在每个教师对象中
    Promise.all(data.map(teacher => {
      return new Promise((resolve, reject) => {
        getImage(teacher.pic, (url) => {
          teacher.imageUrl = url;
          resolve();
        }, (message, code, url) => {
          console.error(`获取图片失败: ${message}`);
          reject(message);
        });
      });
    })).then(() => {
      teachers.value = data;
    }).catch(error => {
      console.error('获取图片过程中发生错误:', error);
    });
  }, (message, code, url) => {
    console.error(`获取教师信息失败: ${message}`);
  });
});

const handleAddTeacher = () => {
  console.log('点击添加教师按钮');
  showModal.value = true;
};

const handleCloseModal = () => {
  console.log('关闭模态框');
  showModal.value = false;
};

// 新增删除教师的处理函数
const handleDeleteTeacher = (uid) => {
  console.log(`点击删除教师按钮，教师编号: ${uid}`);
  deleteTea(uid, () => {
    console.log(`教师编号为 ${uid} 的教师删除成功`);
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
        <th>操作</th>
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
          <button class="delete" @click="handleDeleteTeacher(teacher.uid)">删除</button>
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

</style>
