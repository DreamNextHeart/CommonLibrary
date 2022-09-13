import Cookies from 'js-cookie'

export function setCookie (loginForm, day) {
  Cookies.set('username', loginForm.username, { expires: day })
  Cookies.set('password', loginForm.password, { expires: day })
  Cookies.set('remember', loginForm.remember, { expires: day })
}

export function getCookie (loginForm) {
  loginForm.username = Cookies.get('username')
  loginForm.password = Cookies.get('password')
  loginForm.remember=Boolean(Cookies.get('remember'))
  console.log(loginForm.remember)
}

export function clearCookie () {
  Cookies.remove('username')
  Cookies.remove('password')
  Cookies.remove('remember')
}

