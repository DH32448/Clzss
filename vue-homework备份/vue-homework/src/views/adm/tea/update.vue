<template>
  <div>
    <h3>更新教师信息</h3>
    <form @submit.prevent="handleSubmit">
      <div>
        <label for="uid">教师编号:</label>
        <input type="text" id="uid" v-model="formData.uid" readonly />
      </div>
      <div>
        <label for="uname">教师名称:</label>
        <input type="text" id="uname" v-model="formData.uname" required />
      </div>
      <div>
        <label for="phone">电话:</label>
        <input type="text" id="phone" v-model="formData.phone" required />
      </div>
      <div>
        <label for="role">角色:</label>
        <input type="text" id="role" v-model="formData.role" required />
      </div>
      <div>
        <label for="pic">头像:</label>
        <input type="file" id="pic" @change="handleFileChange" />
      </div>
      <button type="submit">更新</button>
      <button @click="handleCancel">取消</button>
    </form>
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
        avatarUrl.value = '/path/to/default/avatar.png'; // 设置默认头像
      });
    } else {
      avatarUrl.value = '/path/to/default/avatar.png'; // 设置默认头像
    }
  }, (message, code, url) => {
    console.error(`获取用户信息失败: ${message}`);
  });
});
</script>

<style scoped>
form {
  display: flex;
  flex-direction: column;
}

div {
  margin-bottom: 10px;
}

label {
  margin-bottom: 5px;
}

input[type="text"],
input[type="file"] {
  padding: 5px;
  font-size: 16px;
}

button {
  margin-top: 10px;
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

button.cancel {
  background-color: #dc3545;
}

button.cancel:hover {
  background-color: #c82333;
}
</style>
