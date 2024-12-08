<template>
  <div>
    <h1>任务管理</h1>
    <button @click="openModal">添加任务</button>
    <Modal :visible="isModalVisible" @update:visible="isModalVisible = $event">
      <form @submit.prevent="handleSubmit">
        <div>
          <label for="user-uid">用户ID:</label>
          <input id="user-uid" v-model="user.uid" type="number" required />
        </div>
        <div>
          <label for="user-uname">用户名:</label>
          <input id="user-uname" v-model="user.uname" type="text" required />
        </div>
        <div>
          <label for="clz-clzno">班级编号:</label>
          <input id="clz-clzno" v-model="clz.clzno" type="text" required />
        </div>
        <div>
          <label for="clz-clzname">班级名称:</label>
          <input id="clz-clzname" v-model="clz.clzname" type="text" required />
        </div>
        <div>
          <label for="course-cno">课程编号:</label>
          <input id="course-cno" v-model="course.cno" type="text" required />
        </div>
        <div>
          <label for="course-cname">课程名称:</label>
          <input id="course-cname" v-model="course.cname" type="text" required />
        </div>
        <button type="submit">提交</button>
      </form>
      <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
    </Modal>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { addTask } from '@/axios';
import Modal from '@/components/Modal.vue';

const isModalVisible = ref(false);
const user = ref({ uid: '', uname: '' });
const clz = ref({ clzno: '', clzname: '' });
const course = ref({ cno: '', cname: '' });
const errorMessage = ref('');

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
        // 可以在这里添加其他逻辑，例如重置表单或显示成功消息
      },
      (msg, code) => {
        errorMessage.value = `错误 ${code}: ${msg}`; // 设置错误消息
      }
  );
};
</script>

<style scoped>
.error {
  color: red;
  font-size: 1.2em;
}
</style>
