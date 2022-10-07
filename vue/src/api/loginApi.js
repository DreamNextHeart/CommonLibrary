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

export function fetchPermission(){
  return request({
    url: '/getPermission',
    method: 'get'
  })
}


