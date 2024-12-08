<template>
  <div>
    <h1>更新学生信息</h1>
    <form @submit.prevent="handleSubmit">
      <div>
        <label for="uname">学生姓名: </label>
        <input id="uname" v-model="uname" type="text" required />
      </div>
      <div>
        <label for="phone">电话:</label>
        <input id="phone" v-model="phone" type="text" required />
      </div>
      <div>
        <label for="pwd">密码:</label>
        <input id="pwd" v-model="pwd" type="password" required />
      </div>
      <button type="submit">提交</button>
    </form>
    <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { updateStu, getStu } from '@/axios';

const props = defineProps({
  uid: String,
});

const uname = ref('');
const phone = ref('');
const pwd = ref('');
const errorMessage = ref('');

// 根据uid获取学生信息并填充表单
onMounted(() => {
  console.log('开始获取学生信息');
  getStu(
      (data) => {
        console.log('获取学生信息成功:', data);
        const student = data.find((stu) => stu.uid === props.uid);
        if (student) {
          uname.value = student.uname;
          phone.value = student.phone;
          pwd.value = student.pwd;
        } else {
          console.log('未找到对应的学生信息');
        }
      },
      (msg, code) => {
        console.error(`获取学生信息失败: 错误 ${code}: ${msg}`);
        errorMessage.value = `错误 ${code}: ${msg}`;
      }
  );
});

const handleSubmit = () => {
  console.log('提交更新学生信息');
  updateStu(
      props.uid,
      uname.value,
      phone.value,
      pwd.value,
      (data) => {
        console.log('学生更新成功:', data);

      },
      (msg, code) => {
        console.error(`更新学生信息失败: 错误 ${code}: ${msg}`);
        errorMessage.value = `错误 ${code}: ${msg}`;
      }
  );
};
</script>

<style scoped>

</style>
