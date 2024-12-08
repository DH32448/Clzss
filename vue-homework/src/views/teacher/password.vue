<template>
  <div class="password-container">
    <h2>修改密码-功能未完成</h2>
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label for="old-password">旧密码:</label>
        <input
            type="password"
            id="old-password"
            v-model="oldPassword"
            required
            :class="{ 'error': errors.oldPassword }"
        />
        <span v-if="errors.oldPassword" class="error-message">{{ errors.oldPassword }}</span>
      </div>
      <div class="form-group">
        <label for="new-password">新密码:</label>
        <input
            type="password"
            id="new-password"
            v-model="newPassword"
            required
            :class="{ 'error': errors.newPassword }"
        />
        <span v-if="errors.newPassword" class="error-message">{{ errors.newPassword }}</span>
      </div>
      <div class="form-group">
        <label for="confirm-password">确认新密码:</label>
        <input
            type="password"
            id="confirm-password"
            v-model="confirmPassword"
            required
            :class="{ 'error': errors.confirmPassword }"
        />
        <span v-if="errors.confirmPassword" class="error-message">{{ errors.confirmPassword }}</span>
      </div>
      <button type="submit" :disabled="isLoading">修改密码</button>
      <span v-if="isLoading" class="loading">正在处理...</span>
      <span v-if="successMessage" class="success-message">{{ successMessage }}</span>
      <span v-if="errorMessage" class="error-message">{{ errorMessage }}</span>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { updatePassword } from '@/axios/index.js';

const oldPassword = ref('');
const newPassword = ref('');
const confirmPassword = ref('');
const isLoading = ref(false);
const successMessage = ref('');
const errorMessage = ref('');
const errors = ref({});

const validateForm = () => {
  errors.value = {};
  let isValid = true;

  if (newPassword.value !== confirmPassword.value) {
    errors.value.confirmPassword = '新密码和确认密码不一致';
    isValid = false;
  }
  console.log('表单验证结果:', isValid);
  return isValid;
};

const handleSubmit = () => {
  console.log('表单提交事件触发');
  if (!validateForm()) return;

  isLoading.value = true;
  successMessage.value = '';
  errorMessage.value = '';

  updatePassword(oldPassword.value, newPassword.value,
      (data) => {
        isLoading.value = false;
        successMessage.value = '密码修改成功!';
        console.log('密码修改成功:', data);
        oldPassword.value = '';
        newPassword.value = '';
        confirmPassword.value = '';
      },
      (message, status, url) => {
        isLoading.value = false;
        errorMessage.value = `密码修改失败: ${message}`;
        console.error('密码修改失败:', message, status, url);
      }
  );
};
</script>
