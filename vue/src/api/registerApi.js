import request from "@/utils/request";

export function registerApi(registerForm){
  return request({
    url: '/register',
    method: 'post',
    params: registerForm
  })
}
