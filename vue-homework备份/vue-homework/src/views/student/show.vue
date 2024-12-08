<script setup>

</script>

<template>
  <div style="margin-left: 40px;border: 1px solid black;height: 100%">
    <div style="float: left">
      <h2>学生主页</h2>
      <ul>
        <li>
          <router-link to="/stu/pwd">修改密码</router-link>
        </li>
        <li>
          <router-link to="/stu/course">我的课程</router-link>
        </li>
        <li>
          <router-link to="/stu/mark">我的分数</router-link>
        </li>
      </ul>
    </div>
    <div style="float: right;margin-right: 40px">
      <router-view/>
    </div>
  </div>

</template>

<style scoped>
ul {
  list-style-type: none;
  padding: 0;
}

li {
  margin: 10px 0;
}

a {
  text-decoration: none;
  color: blue;
}

a:hover {
  text-decoration: underline;
}
</style>
