import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import request from './axios'
//import './mock'
import  './globalFun'
//清空表单
import resetForm from '@/utils/resetForm'
Vue.prototype.$resetForm = resetForm;
//对象快速复制
import objCopy from '@/utils/objCopy'
Vue.prototype.$objCopy = objCopy;
//信息提示框
import myConfirm from '@/utils/myConfirm'
Vue.prototype.$myconfirm = myConfirm;
Vue.config.productionTip = false
//require("./mock.js") //引入mock数据，关闭则注释该行
Vue.use(ElementUI);
Vue.prototype.$axios = request

new Vue({
  router,
  store,

  render: h => h(App)
}).$mount('#app')
