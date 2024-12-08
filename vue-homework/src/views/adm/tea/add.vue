<script setup>
import { ref } from 'vue';
import { addTea } from '@/axios/index.js';

const uname = ref('');
const phone = ref('');
const pwd = ref('');

const emit = defineEmits(['close']);

const handleSubmit = () => {
  console.log('提交表单，准备添加教师');
  addTea(
      uname.value,
      phone.value,
      pwd.value,
      () => {
        console.log('教师添加成功');
        emit('close');
      },
      (message, code, url) => {
        console.error(`添加教师失败: 错误码 ${code}, 消息 ${message}, URL ${url}`);
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

</style>
