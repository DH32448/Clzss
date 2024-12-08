import { createApp } from 'vue'
import App from './App.vue'
import axios from "axios";
import router from "@/router/index.js";
axios.defaults.baseURL='http://localhost:9000'

createApp(App).use(router).mount('#app')
