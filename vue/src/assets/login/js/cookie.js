import Cookies from 'js-cookie'
import constant from "@/assets/globalvariable/constant";

export function setCookie (loginForm) {
  if(loginForm.remember===true){
    Cookies.set('phone', loginForm.phone, { expires: constant.expires })
    Cookies.set('token', loginForm.token, { expires: constant.expires })
    Cookies.set('remember', loginForm.remember, { expires: constant.expires })
  }else {
    Cookies.set('token', loginForm.token, { expires: constant.expires })
  }
}

export function getCookie (loginForm) {
  loginForm.phone = Cookies.get('phone')
  loginForm.token = Cookies.get('token')
  loginForm.remember=Boolean(Cookies.get('remember'))
  console.log(loginForm.remember)
}


export function getCookieRemember(TempForm){
  TempForm.remember=Boolean(Cookies.get('remember'))
}

export function clearCookie () {
    Cookies.remove('phone')
    Cookies.remove('token')
    Cookies.remove('remember')
}

export function getMessage (tempForm) {
  tempForm.message = Cookies.get('message')
}

