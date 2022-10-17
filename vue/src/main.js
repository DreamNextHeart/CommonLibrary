import axios from 'axios'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import request from "@/utils/request";
// import './permission'



createApp(App)
    .use(store)
    .use(router)
    .use(ElementPlus)
    .use("axios",axios)
    .use(request)
    .mount('#app')
