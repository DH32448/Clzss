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

console.log('初始化班级信息:', { clzno: clzno.value, clzname: clzname.value });

const submitForm = () => {
  console.log('提交表单，班级信息:', { clzno: clzno.value, clzname: clzname.value });
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

</style>
