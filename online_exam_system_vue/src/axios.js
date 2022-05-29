import axios from "axios";
import Element from 'element-ui'
import router from "./router";
// http://localhost:8085
axios.defaults.baseURL = "http://localhost:8085"
//创建一个request对象，用于设置请求一般配置
//let 声明的变量只在 let 命令所在的代码块内有效。
// const 声明一个只读的常量，一旦声明，常量的值就不能改变
const  request = axios.create({
    timeout: 5000,
    headers: {
        'Content-Type': 'application/json; charset=utf-8'
    }
})
//拦截客户端到服务端的请求，将客户端本地token随机码封装再headers传给后端进行权限验证
request.interceptors.request.use(config =>{
    config.headers['Authorization'] = localStorage.getItem("token") //请求头带上token
    return config
})

//拦截服务端结果，对状态码进行校验分别处理
request.interceptors.response.use(response =>{
    let res = response.data;
    console.log("response")
    console.log(res)
    if (res.code === 200){
        return response
    }else{
        Element.Message.error(res.msg ? res.msg : '系统异常！', {duration: 3*1000})
        return Promise.reject(response.data.msg)
    }
},  //出现断言或权限异常，无法返回结果，只能通过状态码判断
 error => {
    console.log(error)
        if(error.response.data) {
            error.message = error.response.data.msg
        }
        if(error.response.status === 401) {
            router.push("/login")
        }
        Element.Message.error(error.message, {duration: 3 * 1000})
        return Promise.reject(error)
}
)
export default request
