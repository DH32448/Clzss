<template>
  <div>
    <h1>学生列表</h1>
    <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
    <table v-if="students.length > 0" border="1">
      <thead>
      <tr>
        <th>学号</th>
        <th>学生姓名</th>
        <th>电话</th>
        <th>班级</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="student in students" :key="student.uid">
        <td>{{ student.uid }}</td>
        <td>{{ student.uname }}</td>
        <td>{{ student.phone }}</td>
        <td>{{ student.clz.clzname }} ({{ student.clz.clzno }})</td>
        <td>
          <button @click="openUpdateStudentModal(student)">更新</button>
        </td>
      </tr>
      </tbody>
    </table>
    <div v-else>
      <p>暂无学生数据。</p>
    </div>
    <div>
      <label for="page">页码：</label>
      <input id="page" v-model="currentPage" type="number" min="1" />
      <label for="size">每页条数：</label>
      <input id="size" v-model="pageSize" type="number" min="1" />
      <button @click="handlePageChange">跳转</button>
    </div>
    <div>
      <p>总学生数：{{ totalStudents }}</p>
    </div>
    <button @click="openAddStudentModal">添加学生</button>
    <Modal :visible="isModalVisible" @update:visible="closeAddStudentModal">
      <!-- 添加学生模态框内容 -->
    </Modal>
    <Modal :visible="isUpdateModalVisible" @update:visible="closeUpdateStudentModal">
      <div>
        <h2>更新学生</h2>
        <form @submit.prevent="handleUpdateSubmit">
          <div>
            <label for="update-uname">学生姓名:</label>
            <input id="update-uname" v-model="updateUname" type="text" required />
          </div>
          <div>
            <label for="update-phone">电话:</label>
            <input id="update-phone" v-model="updatePhone" type="text" required />
          </div>
          <div>
            <label for="update-pwd">密码:</label>
            <input id="update-pwd" v-model="updatePwd" type="password" required />
          </div>
          <button type="submit">提交</button>
        </form>
        <div v-if="formErrorMessage" class="error">{{ formErrorMessage }}</div>
      </div>
    </Modal>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { getStuPage, addStu, updateStu } from '@/axios'; // 添加 updateStu 的导入
import Modal from '@/components/Modal.vue';

const students = ref([]);
const errorMessage = ref('');
const currentPage = ref(1);
const pageSize = ref(10);
const totalStudents = ref(0);
const isModalVisible = ref(false);
const isUpdateModalVisible = ref(false);

// 新增字段用于存储要更新的学生信息
const updateUid = ref('');
const updateUname = ref('');
const updatePhone = ref('');
const updatePwd = ref('');
const formErrorMessage = ref('');

// 组件挂载时获取学生数据
const fetchStudents = (page, size) => {
  getStuPage(
      page,
      size,
      (data) => {
        students.value = data.students; // 使用返回数据中的'students'数组
        totalStudents.value = data.total; // 设置学生总数
      },
      (msg, code) => {
        errorMessage.value = `错误 ${code}: ${msg}`; // 设置错误消息
      }
  );
};

onMounted(() => {
  fetchStudents(currentPage.value, pageSize.value);
});

const handlePageChange = () => {
  fetchStudents(currentPage.value, pageSize.value);
};

const router = useRouter();

const openAddStudentModal = () => {
  isModalVisible.value = true;
};

const closeAddStudentModal = () => {
  isModalVisible.value = false;
};

const openUpdateStudentModal = (student) => {
  updateUid.value = student.uid;
  updateUname.value = student.uname;
  updatePhone.value = student.phone;
  updatePwd.value = ''; // 密码通常不回显
  isUpdateModalVisible.value = true;
};

const closeUpdateStudentModal = () => {
  isUpdateModalVisible.value = false;
};

const uname = ref('');
const phone = ref('');
const pwd = ref('');

const handleSubmit = () => {
  addStu(
      uname.value,
      phone.value,
      pwd.value,
      (data) => {
        console.log('学生添加成功:', data);
        fetchStudents(currentPage.value, pageSize.value); // 刷新学生列表
        closeAddStudentModal(); // 关闭模态框
      },
      (msg, code) => {
        formErrorMessage.value = `错误 ${code}: ${msg}`; // 设置错误消息
      }
  );
};

const handleUpdateSubmit = () => {
  updateStu(
      updateUid.value,
      updateUname.value,
      updatePhone.value,
      updatePwd.value,
      (data) => {
        console.log('学生更新成功:', data);
        fetchStudents(currentPage.value, pageSize.value); // 刷新学生列表
        closeUpdateStudentModal(); // 关闭模态框
      },
      (msg, code) => {
        formErrorMessage.value = `错误 ${code}: ${msg}`; // 设置错误消息
      }
  );
};
</script>
