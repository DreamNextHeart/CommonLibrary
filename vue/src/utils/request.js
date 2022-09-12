/*引入axios*/
import axios from 'axios'
const request = axios.create({
  baseURL: 'http://localhost:8082', // 基础路径,将统一的部分全部封装
})
//前端采用export.default，在写后端代码时用module.export
export default request
