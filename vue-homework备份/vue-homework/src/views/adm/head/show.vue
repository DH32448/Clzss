<template>
  <div>
    <h2>当前登录用户信息</h2>
    <div v-if="userInfo">
      <p><strong>用户名:</strong> {{ userInfo.uname }}</p>
      <p><strong>电话:</strong> {{ userInfo.phone }}</p>
      <p><strong>角色:</strong> {{ userInfo.role }}</p>
      <p><strong>头像:</strong></p>
      <img v-if="avatarUrl" :src="avatarUrl" alt="头像" width="100" height="100" />
      <p v-else>暂无头像</p>
    </div>
    <div v-else>
      <p>加载中...</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getUserInfo, getImage } from '@/axios/index.js';

const userInfo = ref(null);
const avatarUrl = ref('');

// 获取用户信息和头像
onMounted(() => {
  getUserInfo((data) => {
    userInfo.value = data;

    if (data && data.pic) {
      getImage(data.pic, (imageUrl) => {
        avatarUrl.value = imageUrl;
      }, (message, code, url) => {
        console.error(`获取头像失败: ${message}`);
      });
    }
  }, (message, code, url) => {
    console.error(`获取用户信息失败: ${message}`);
  });
});
</script>