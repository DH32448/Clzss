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
  fetchClasses();
});

const fetchClasses = () => {
  getClz((data) => {
    classes.value = data;  // 将返回的班级数据保存在 `classes` 中
  }, (message, code, url) => {
    console.error('获取班级信息失败:', message);
  });
};

const handleDelete = (clzno) => {
  deleteClz(clzno, () => {
    // 删除成功后，重新获取班级信息
    fetchClasses();
  }, (message, code, url) => {
    console.error('删除班级信息失败:', message);
  });
};

const openModal = () => {
  showModal.value = true;
};

const openUpdateModal = (clz) => {
  selectedClass.value = clz;
  showUpdateModal.value = true;
};

const closeModal = () => {
  showModal.value = false;
  showUpdateModal.value = false;
};

const handleAdded = () => {
  // 班级添加成功后，重新获取班级信息
  fetchClasses();
};

const handleUpdated = () => {
  // 班级更新成功后，重新获取班级信息
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
