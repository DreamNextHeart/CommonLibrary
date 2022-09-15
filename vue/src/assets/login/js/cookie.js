import Cookies from 'js-cookie'
import cookie from "@/assets/globalvariable/cookie";

export function setCookie (loginForm) {
  Cookies.set('username', loginForm.username, { expires: cookie.expires })
  Cookies.set('token', loginForm.token, { expires: cookie.expires })
  Cookies.set('remember', loginForm.remember, { expires: cookie.expires })
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

