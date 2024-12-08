<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { getStuPage, deleteStu, getImage } from '@/axios'; // 引入删除学生的API函数和获取图片的API函数
import Modal from '@/components/Modal.vue';
import AddStudent from '@/views/adm/stu/add.vue'; // 引入add.vue
import UpdateStudent from '@/views/adm/stu/update.vue'; // 引入update.vue

const students = ref([]);
const errorMessage = ref('');
const currentPage = ref(1);
const pageSize = ref(10);
const totalStudents = ref(0);
const isModalVisible = ref(false);
const isUpdateMode = ref(false); // 新增状态，判断是否为更新模式
const selectedStudent = ref(null); // 存储选中的学生信息

// 组件挂载时获取学生数据
const fetchStudents = (page, size) => {
  getStuPage(
      page,
      size,
      (data) => {
        students.value = data.students; // 使用返回数据中的'students'数组
        totalStudents.value = data.total; // 设置学生总数
        // 获取每个学生的图片URL
        students.value.forEach(student => {
          getImage(student.pic, (url) => {
            student.imageUrl = url; // 将图片URL存储在学生对象中
          }, (msg, code) => {
            console.error(`获取图片失败: 错误 ${code}: ${msg}`);
          });
        });
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
  isUpdateMode.value = false; // 设置为添加模式
};

const openUpdateStudentModal = (student) => {
  isModalVisible.value = true;
  isUpdateMode.value = true; // 设置为更新模式
  selectedStudent.value = student; // 保存选中的学生信息
};

const closeAddStudentModal = () => {
  isModalVisible.value = false;
  isUpdateMode.value = false; // 重置模式
};

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

const deleteStudent = (uid) => {
  deleteStu(
      uid,
      (data) => {
        console.log('学生删除成功:', data);
        fetchStudents(currentPage.value, pageSize.value); // 刷新学生列表
      },
      (msg, code) => {
        errorMessage.value = `错误 ${code}: ${msg}`; // 设置错误消息
      }
  );
};
</script>

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
        <th>图片</th>
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
          <img v-if="student.imageUrl" :src="student.imageUrl" alt="Student Image" width="50" height="50"/>
          <span v-else>无图片</span>
        </td>
        <td>
          <button @click="openUpdateStudentModal(student)">更新</button>
          <button @click="deleteStudent(student.uid)">删除</button>
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
      <AddStudent v-if="!isUpdateMode"/>
      <UpdateStudent v-else :uid="selectedStudent.uid"/>
    </Modal>
  </div>
</template>
