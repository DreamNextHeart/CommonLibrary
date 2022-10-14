import request from '@/utils/request'

// 获取验证码
export function getCodeImg () {
  return request({
    url: '/getCode',
    method: 'get',
    responseType: 'blob'
  })
}


// 登录不记住密码
export function loginInNotRemember (loginForm) {
  return request({
    url: '/loginInNotRemember',
    method: 'post',
    params: loginForm
  })
}

// 登录记住密码
export function loginInRemember (loginForm) {
  return request({
    url: '/loginInRemember',
    method: 'post',
    params: loginForm
  })
}

//匹配权限
export function fetchPermission(){
  return request({
    url: '/getPermission',
    method: 'get'
  })
}

//获取用户信息
export function getUserInfo(token){
  return request({
    url: '/getUserInfo',
    method: 'get',
    params: token
  })
}

//登出
export function logout(token){
  return request({
    url: '/logout',
    method: 'get',
    params: token
  })
}

//注册
export function registerApi(registerForm){
  return request({
    url: '/register',
    method: 'post',
    params: registerForm
  })
}

