<script setup>
import { ref, onMounted } from 'vue';
import { getClz, deleteClz } from '@/axios/index.js';
import Modal from '@/components/Modal.vue';
import AddClassForm from '@/views/adm/clz/add.vue';
import UpdateClassForm from '@/views/adm/clz/update.vue';

const classes = ref([]);
const showModal = ref(false);
const showUpdateModal = ref(false);
const selectedClass = ref(null);

onMounted(() => {
  console.log('开始获取班级信息');
  fetchClasses();
});

const fetchClasses = () => {
  console.log('开始获取班级信息');
  getClz((data) => {
    console.log('获取班级信息成功:', data);
    classes.value = data;  // 将返回的班级数据保存在classes中
  }, (message, code, url) => {
    console.error('获取班级信息失败:', message);
  });
};

const handleDelete = (clzno) => {
  console.log('开始删除班级，班级编号:', clzno);
  deleteClz(clzno, () => {
    console.log('删除班级成功，班级编号:', clzno);
    // 删除成功后，重新获取班级信息
    fetchClasses();
  }, (message, code, url) => {
    console.error('删除班级信息失败:', message);
  });
};

const openModal = () => {
  console.log('打开添加班级模态框');
  showModal.value = true;
};

const openUpdateModal = (clz) => {
  console.log('打开更新班级模态框，班级信息:', clz);
  selectedClass.value = clz;
  showUpdateModal.value = true;
};

const closeModal = () => {
  console.log('关闭模态框');
  showModal.value = false;
  showUpdateModal.value = false;
};

const handleAdded = () => {
  console.log('班级添加成功，重新获取班级信息');
  fetchClasses();
};

const handleUpdated = () => {
  console.log('班级更新成功，重新获取班级信息');
  fetchClasses();
};
</script>

<template>
  <div>
    <h3>班级信息</h3>
    <button @click="openModal">添加班级</button>
    <table border="1">
      <thead>
      <tr>
        <th>班级编号</th>
        <th>班级名称</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(clz, index) in classes" :key="index">
        <td>{{ clz.clzno }}</td>
        <td>{{ clz.clzname }}</td>
        <td>
          <button @click="handleDelete(clz.clzno)">删除</button>
          <button @click="openUpdateModal(clz)">更新</button>
        </td>
      </tr>
      </tbody>
    </table>
    <Modal :visible="showModal" @update:visible="closeModal">
      <AddClassForm @close="closeModal" @added="handleAdded" /> <!-- 监听 added 事件 -->
    </Modal>
    <Modal :visible="showUpdateModal" @update:visible="closeModal">
      <UpdateClassForm
          :clzno="selectedClass.clzno"
          :clzname="selectedClass.clzname"
          @close="closeModal"
          @updated="handleUpdated"
      /> <!-- 监听 updated 事件 -->
    </Modal>
  </div>
</template>
