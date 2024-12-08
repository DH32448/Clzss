<script setup>
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { addStu } from '@/axios';

const uname = ref('');
const phone = ref('');
const pwd = ref('');
const errorMessage = ref('');

const router = useRouter();

const handleSubmit = () => {
  addStu(
      uname.value,
      phone.value,
      pwd.value,
      (data) => {
        console.log('学生添加成功:', data);
        router.push({ name: 'ShowStudents' }); // 导航回学生列表页面
      },
      (msg, code) => {
        errorMessage.value = `错误 ${code}: ${msg}`; // 设置错误消息
      }
  );
};
</script>

<template>
  <div>
    <h1>添加学生</h1>
    <form @submit.prevent="handleSubmit">
      <div>
        <label for="uname">学生姓名:</label>
        <input id="uname" v-model="uname" type="text" required />
      </div>
      <div>
        <label for="phone">电话:</label>
        <input id="phone" v-model="phone" type="text" required />
      </div>
      <div>
        <label for="pwd">密码:</label>
        <input id="pwd" v-model="pwd" type="password" required />
      </div>
      <button type="submit">提交</button>
    </form>
    <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
  </div>
</template>

<style scoped>
.error {
  color: red;
  font-size: 1.2em;
}
</style>
