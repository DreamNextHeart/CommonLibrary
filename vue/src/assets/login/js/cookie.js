import Cookies from 'js-cookie'
import constant from "@/assets/globalvariable/constant";



export function setCookie (loginForm) {
  if(loginForm.remember===true){
    Cookies.set(constant.phone, loginForm.phone, { expires: constant.expires })
    Cookies.set(constant.tokenKey, loginForm.token, { expires: constant.expires })
    Cookies.set(constant.remember, loginForm.remember, { expires: constant.expires })
  }else {
    Cookies.set(constant.tokenKey, loginForm.token, { expires: constant.expires })
  }
}

export function getCookie (loginForm) {
  loginForm.phone = Cookies.get(constant.phone)
  loginForm.token = Cookies.get(constant.tokenKey)
  loginForm.remember=Boolean(Cookies.get(constant.remember))
}


export function getCookieRemember(TempForm){
  TempForm.remember=Boolean(Cookies.get(constant.remember))
}

export function clearCookie () {
    Cookies.remove(constant.phone)
    Cookies.remove(constant.tokenKey)
    Cookies.remove(constant.remember)
}

export function getMessage (tempForm) {
  tempForm.message = Cookies.get('message')
}

