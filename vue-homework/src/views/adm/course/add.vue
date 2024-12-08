<template>
  <Modal :visible="isVisible" @update:visible="handleModalVisibility">
    <div>
      <h3>添加课程</h3>
      <form @submit.prevent="submitForm">
        <div>
          <label for="cno">课程编号:</label>
          <input type="text" id="cno" v-model="cno" required />
        </div>
        <div>
          <label for="cname">课程名称:</label>
          <input type="text" id="cname" v-model="cname" required />
        </div>
        <button type="submit">添加课程</button>
      </form>
    </div>
  </Modal>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { addCourse } from '@/axios/index.js';
import Modal from '@/components/Modal.vue';

const cno = ref('');
const cname = ref('');
const router = useRouter();
const isVisible = ref(false);

const emit = defineEmits(['refreshCourses']);

const handleModalVisibility = (visibility) => {
  console.log('模态框可见性更新:', visibility);
  isVisible.value = visibility;
};

const openModal = () => {
  console.log('打开模态框');
  isVisible.value = true;
};

const submitForm = () => {
  console.log('提交表单，课程信息:', { cno: cno.value, cname: cname.value });
  addCourse(cno.value, cname.value,
      (data) => {
        // alert('课程添加成功!');
        console.log('课程添加成功:', data);
        // 通知父组件刷新课程列表
        emit('refreshCourses');
        // 关闭模态框
        isVisible.value = false;
      },
      (message, code, url) => {
        console.error(`课程添加失败: ${message}`);
        alert('课程添加失败: ' + message);
      }
  );
};

defineExpose({
  openModal
});
</script>

<style scoped>

</style>
