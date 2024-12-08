<script setup>
import { ref } from 'vue';
import { addTea } from '@/axios/index.js';

const uname = ref('');
const phone = ref('');
const pwd = ref('');

const emit = defineEmits(['close']);

const handleSubmit = () => {
  addTea(
      uname.value,
      phone.value,
      pwd.value,
      () => {
        console.log('教师添加成功');
        emit('close'); // 关闭模态框
      },
      (message, code, url) => {
        console.error(`添加教师失败: ${message}`);
      }
  );
};
</script>

<template>
  <div>
    <h3>添加教师</h3>
    <form @submit.prevent="handleSubmit">
      <div>
        <label for="uname">教师名称:</label>
        <input type="text" id="uname" v-model="uname" required />
      </div>
      <div>
        <label for="phone">电话:</label>
        <input type="text" id="phone" v-model="phone" required />
      </div>
      <div>
        <label for="pwd">密码:</label>
        <input type="password" id="pwd" v-model="pwd" required />
      </div>
      <button type="submit">提交</button>
      <button type="button" @click="emit('close')">取消</button>
    </form>
  </div>
</template>

<style scoped>
form {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

label {
  font-weight: bold;
}

input {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

button[type="button"] {
  background-color: #dc3545;
}

button[type="button"]:hover {
  background-color: #c82333;
}
</style>
