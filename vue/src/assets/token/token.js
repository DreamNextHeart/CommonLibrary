import Cookies from 'js-cookie'
import constant from "@/assets/globalvariable/constant";
export function getToken() {
    return Cookies.get(constant.tokenKey)
}

export function setToken(token) {
    return Cookies.set(constant.tokenKey, token)
}

export function removeToken() {
    return Cookies.remove(constant.tokenKey)
}
