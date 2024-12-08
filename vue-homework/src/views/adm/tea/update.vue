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

const formData = ref({
  uid: '',
  uname: '',
  phone: '',
  role: '',
  pic: null
});
const userInfo = ref(null);
const avatarUrl = ref('');

// 获取用户信息和头像
onMounted(() => {
  console.log('开始获取用户信息');
  getUserInfo((data) => {
    console.log('获取用户信息成功', data);
    userInfo.value = data;
    formData.value = { ...data };

    if (data && data.pic) {
      getImage(data.pic, (imageUrl) => {
        console.log('获取头像成功', imageUrl);
        avatarUrl.value = imageUrl;
      }, (message, code, url) => {
        console.error(`获取头像失败: ${message}`);
      });
    } else {
      console.log('用户没有头像，设置默认头像');
    }
  }, (message, code, url) => {
    console.error(`获取用户信息失败: ${message}`);
  });
});

const handleSubmit = () => {
  console.log('提交表单', formData.value);
};

const handleCancel = () => {
  console.log('取消更新');
};

const handleFileChange = (event) => {
  const file = event.target.files[0];
  if (file) {
    console.log('选择了新的头像文件', file.name);
    formData.value.pic = file;
  }
};
</script>

<style scoped>

</style>
