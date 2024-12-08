<script setup>
import { ref } from 'vue';
import { addClz } from '@/axios/index.js';

const clzno = ref('');
const clzname = ref('');

const emit = defineEmits(['close', 'added']);

const submitForm = () => {
  console.log('开始提交表单');
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
  console.log('结束提交表单');
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

</style>
