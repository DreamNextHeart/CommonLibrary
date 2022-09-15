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

// 登录
export function loginIn (loginForm) {
  return request({
    url: '/loginIn',
    method: 'post',
    params: loginForm
  })

}


