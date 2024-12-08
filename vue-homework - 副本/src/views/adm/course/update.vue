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

// Watch for changes in the course prop to update the form fields
watch(
    () => props.course,
    (newCourse) => {
      if (newCourse) {
        cno.value = newCourse.cno;
        cname.value = newCourse.cname;
      }
    },
    { immediate: true }
);

const handleUpdate = () => {
  updateCourse(cno.value, cname.value, () => {
    emit('refreshCourses');
    emit('update:visible', false);
  }, (message, code, url) => {
    console.error(`更新课程失败: ${message}`);
  });
};

const updateVisible = (value) => {
  emit('update:visible', value);
};
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

input {
  padding: 5px;
  font-size: 16px;
}

input[readonly] {
  background-color: #f4f4f4;
  color: #666;
}

button {
  padding: 10px;
  font-size: 16px;
  background-color: #4CAF50;
  color: white;
  border: none;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}
</style>
