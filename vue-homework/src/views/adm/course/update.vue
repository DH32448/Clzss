<template>
  <Modal :visible="visible" @update:visible="updateVisible">
    <form @submit.prevent="handleUpdate">
      <h3>更新课程</h3>
      <div>
        <label for="cno">课程编号:</label>
        <input type="text" id="cno" v-model="cno" required readonly />
      </div>
      <div>
        <label for="cname">课程名称:</label>
        <input type="text" id="cname" v-model="cname" required />
      </div>
      <button type="submit">更新</button>
    </form>
  </Modal>
</template>

<script setup>
import { ref, defineProps, defineEmits, watch } from 'vue';
import Modal from '@/components/Modal.vue';
import { updateCourse } from '@/axios/index.js';

const props = defineProps({
  visible: Boolean,
  course: Object,
});

const emit = defineEmits(['update:visible', 'refreshCourses']);

const cno = ref(props.course ? props.course.cno : '');
const cname = ref(props.course ? props.course.cname : '');


watch(
    () => props.course,
    (newCourse) => {
      console.log('课程属性变化，更新表单字段:', newCourse);
      if (newCourse) {
        cno.value = newCourse.cno;
        cname.value = newCourse.cname;
      }
    },
    { immediate: true }
);

const handleUpdate = () => {
  console.log('开始更新课程，课程编号:', cno.value, '课程名称:', cname.value);
  updateCourse(cno.value, cname.value, () => {
    console.log('更新课程成功');
    emit('refreshCourses');
    emit('update:visible', false);
  }, (message, code, url) => {
    console.error(`更新课程失败: ${message}`);
  });
};

const updateVisible = (value) => {
  console.log('更新模态框可见性:', value);
  emit('update:visible', value);
};
</script>

<style scoped>

</style>
