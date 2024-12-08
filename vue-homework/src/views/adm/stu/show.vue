<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { getStuPage, deleteStu, getImage } from '@/axios';
import Modal from '@/components/Modal.vue';
import AddStudent from '@/views/adm/stu/add.vue';
import UpdateStudent from '@/views/adm/stu/update.vue';

const students = ref([]);
const errorMessage = ref('');
const currentPage = ref(1);
const pageSize = ref(10);
const totalStudents = ref(0);
const isModalVisible = ref(false);
const isUpdateMode = ref(false);
const selectedStudent = ref(null);

// 组件挂载时获取学生数据
const fetchStudents = (page, size) => {
  console.log('开始获取学生数据');
  getStuPage(
      page,
      size,
      (data) => {
        console.log('获取学生数据成功:', data);
        students.value = data.students;
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
        console.error(`获取学生数据失败: 错误 ${code}: ${msg}`);
        errorMessage.value = `错误 ${code}: ${msg}`;
      }
  );
};

onMounted(() => {
  fetchStudents(currentPage.value, pageSize.value);
});

const handlePageChange = () => {
  console.log('页码或每页条数改变，重新获取学生数据');
  fetchStudents(currentPage.value, pageSize.value);
};

const router = useRouter();

const openAddStudentModal = () => {
  console.log('打开添加学生模态框');
  isModalVisible.value = true;
  isUpdateMode.value = false; // 设置为添加模式
};

const openUpdateStudentModal = (student) => {
  console.log('打开更新学生模态框', student);
  isModalVisible.value = true;
  isUpdateMode.value = true; // 设置为更新模式
  selectedStudent.value = student; // 保存选中的学生信息
};

const closeAddStudentModal = () => {
  console.log('关闭添加/更新学生模态框');
  isModalVisible.value = false;
  isUpdateMode.value = false;
};

const handleSubmit = () => {
  console.log('提交学生信息');
  addStu(
      uname.value,
      phone.value,
      pwd.value,
      (data) => {
        console.log('学生添加成功:', data);
        fetchStudents(currentPage.value, pageSize.value);
        closeAddStudentModal(); // 关闭模态框
      },
      (msg, code) => {
        console.error(`添加学生失败: 错误 ${code}: ${msg}`);
        formErrorMessage.value = `错误 ${code}: ${msg}`;
      }
  );
};

const deleteStudent = (uid) => {
  console.log('删除学生，学号:', uid);
  deleteStu(
      uid,
      (data) => {
        console.log('学生删除成功:', data);
        fetchStudents(currentPage.value, pageSize.value);
      },
      (msg, code) => {
        console.error(`删除学生失败: 错误 ${code}: ${msg}`);
        errorMessage.value = `错误 ${code}: ${msg}`;
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
