//导入axios
import axios from 'axios'
import router from '../router/index.js'
import { ElMessage } from 'element-plus'

//创建axios实例对象
const request = axios.create({
  baseURL: '/api',
  timeout: 600000
})

//axios的请求 request 拦截器 - 获取localStorage中的token,在请求头中增加token请求头
request.interceptors.request.use(
  (config)=>{//成功回调
    const loginUser = JSON.parse(localStorage.getItem("loginUser"));
    if(loginUser && loginUser.token){
      config.headers.token = loginUser.token;
    }
    return config;
  }
)

//axios的响应 response 拦截器
request.interceptors.response.use(
  (response) => { //成功回调
    return response.data
  },
  (error) => { //失败回调
    if(error.response.status === 401){
      //提示信息
      ElMessage.error('登录超时，请重新登录')
      //跳转回登录页面
      router.push('/login')
    }else{
      ElMessage.error('接口访问异常')
    }
    return Promise.reject(error)
  }
)

//默认导出
export default request