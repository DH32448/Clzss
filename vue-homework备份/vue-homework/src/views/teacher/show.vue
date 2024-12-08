<template>
  <div>
    <h2>教师主页</h2>
    <ul>
      <li>
        <router-link to="/tea/pwd">修改密码</router-link>
      </li>
      <li>
        <router-link to="/tea/mark">登分管理</router-link>
      </li>
    </ul>
  </div>
  <div>
    <router-view/>
  </div>
</template>

<script setup>
</script>

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
