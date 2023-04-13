import { createApp } from 'vue'
import App from './App.vue'
import {router} from "@/router";
import ElementUI from 'element-plus';
import 'element-plus/dist/index.css'
import api from "@/http/index.js";
import store from "@/store";
import Cookies from 'js-cookie'
import * as echarts from 'echarts'
createApp(App).use(echarts).use(store).use(Cookies).use(router).use(api).use(ElementUI).use(ElementUI).mount('#app')
