<script>
import { login } from "@/axios/index.js";

export default {
  data() {
    return {
      username: '',
      password: ''
    };
  },
  methods: {
    login() {
      console.log(`尝试登录，用户名: ${this.username}, 密码: ${this.password}`);
      login(this.username, this.password, () => {
        console.log('登录成功');
        this.$emit('login-success');
      }, (error) => {
        console.error('登录失败', error);
      });
    }
  }
};
</script>

<template>
  <div>
    <form @submit.prevent="login">
      账号：<input type="text" placeholder="请输入手机号码" v-model="username"><br>
      密码：<input type="text" placeholder="请输入密码" v-model="password"><br>
      <button type="button" @click="login">登录</button>
    </form>
  </div>
</template>

<style scoped>
</style>
