import Cookies from 'js-cookie'
import constant from "@/assets/globalvariable/constant";

export function setCookie (loginForm) {
  if(loginForm.remember===true){
    Cookies.set('username', loginForm.username, { expires: constant.expires })
    Cookies.set('token', loginForm.token, { expires: constant.expires })
    Cookies.set('remember', loginForm.remember, { expires: constant.expires })
  }else {
    Cookies.set('token', loginForm.token, { expires: constant.expires })
  }
}

export function getCookie (loginForm) {
  loginForm.username = Cookies.get('username')
  loginForm.token = Cookies.get('token')
  loginForm.remember=Boolean(Cookies.get('remember'))
  console.log(loginForm.remember)
}


export function getCookieRemember(TempForm){
  TempForm.remember=Boolean(Cookies.get('remember'))
}

export function clearCookie () {
    Cookies.remove('username')
    Cookies.remove('token')
    Cookies.remove('remember')
}

