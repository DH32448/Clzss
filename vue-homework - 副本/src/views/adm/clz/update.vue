<script setup>
import { ref, defineProps, defineEmits } from 'vue';
import { updateClz } from '@/axios/index.js';

const props = defineProps({
  clzno: String,
  clzname: String,
});

const emit = defineEmits(['close', 'updated']);

const clzno = ref(props.clzno);
const clzname = ref(props.clzname);

const submitForm = () => {
  updateClz(clzno.value, clzname.value,
      (data) => {
        console.log('班级更新成功:', data);
        // 通知父组件关闭模态对话框
        emit('close');
        // 通知父组件班级已更新
        emit('updated');
      },
      (message, status, url) => {
        console.error('班级更新失败:', message, status, url);
      }
  );
};
</script>

<template>
  <form @submit.prevent="submitForm">
    <div class="form-group">
      <label for="clzno">班级编号:</label>
      <input type="text" id="clzno" v-model="clzno" required readonly />
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
