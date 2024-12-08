<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { updatePassword } from '@/axios/index.js';

const oldPassword = ref('');
const newPassword = ref('');
const confirmPassword = ref('');

const router = useRouter();

const handleSubmit = () => {
  if (newPassword.value !== confirmPassword.value) {
    alert('新密码和确认密码不一致');
    return;
  }

  updatePassword(oldPassword.value, newPassword.value, (data) => {
    alert('密码修改成功');
    router.push('/');
  }, (message, status, url) => {
    alert(`密码修改失败: ${message}`);
  });
};
</script>

<template>
  <div class="password-container">
    <h2>修改密码-功能未完善</h2>
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label for="old-password">旧密码:</label>
        <input type="password" id="old-password" v-model="oldPassword" required />
      </div>
      <div class="form-group">
        <label for="new-password">新密码:</label>
        <input type="password" id="new-password" v-model="newPassword" required />
      </div>
      <div class="form-group">
        <label for="confirm-password">确认新密码:</label>
        <input type="password" id="confirm-password" v-model="confirmPassword" required />
      </div>
      <button type="submit">修改密码</button>
    </form>
  </div>
</template>

<style scoped>

</style>
