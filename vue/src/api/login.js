// 获取验证码
import request from '@/utils/request'

export function getCodeImg () {
  return request({
    url: '/getCode',
    method: 'get',
    responseType: 'blob'
  })
}

// 获取验证码
export function checkCode (code) {
  return request({
    url: '/checkCode/' + code,
    method: 'get',
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


