<script setup>import { ref } from 'vue';
import { addClz } from '@/axios/index.js';

const clzno = ref('');
const clzname = ref('');

const emit = defineEmits(['close', 'added']); // 添加一个新的事件 'added'

const submitForm = () => {
  addClz(clzno.value, clzname.value,
      (data) => {
        console.log('班级添加成功:', data);
        // 清空表单
        clzno.value = '';
        clzname.value = '';
        // 通知父组件关闭模态对话框
        emit('close');
        // 通知父组件班级添加成功
        emit('added');
      },
      (message, status, url) => {
        console.error('班级添加失败:', message, status, url);
      }
  );
};
</script>

<template>
  <form @submit.prevent="submitForm">
    <div class="form-group">
      <label for="clzno">班级编号:</label>
      <input type="text" id="clzno" v-model="clzno" required />
    </div>
    <div class="form-group">
      <label for="clzname">班级名称:</label>
      <input type="text" id="clzname" v-model="clzname" required />
    </div>
    <button type="submit">提交</button>
    <button type="button" @click="$emit('close')">取消</button>
  </form>
</template>

<style scoped>
.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
}

input[type="text"] {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}

button {
  padding: 10px 15px;
  margin-right: 5px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

button[type="button"] {
  background-color: #ccc;
}

button[type="button"]:hover {
  background-color: #aaa;
}
</style>
