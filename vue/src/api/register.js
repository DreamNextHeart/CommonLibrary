import request from "@/utils/request";

export function register(registerForm){
  return request({
    url: '/register',
    method: 'post',
    params: registerForm
  })
}
