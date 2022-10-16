import {loginInNotRemember, loginInRemember, logout} from "@/api/user/userApi";
import {getUserInfo} from "@/api/user/userApi"
import {removeToken, setToken} from "@/assets/token/token";

import {getToken} from "@/assets/token/token"



const user = {
    state: {
        token: getToken(),
        phone: '',
        roles: [],
        permissions: []
    },
    mutations: {
        SET_TOKEN: (state, token) => {
            state.token = token
        },
        SET_USERNAME: (state, username) => {
            state.username = username
        },
        SET_PHONE: (state, phone) => {
            state.phone = phone
        },
        SET_ROLES: (state, roles) => {
            state.roles = roles
        },
        SET_PERMISSIONS: (state, permissions) => {
            state.permissions = permissions
        }
    },
    actions: {
        //用户登录
        loginInNotRemember({commit}, userInfo) {
            // trim()：从字符串中删除开头和结尾的空格和行结束符。
            const phone = userInfo.phone.trim()
            const password = userInfo.password
            const code = userInfo.code
            return new Promise((resolve, reject) => {
                loginInNotRemember(phone, password, code).then(response => {
                    setToken(response.token)
                    commit('SET_TOKEN', response.token)
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },
        loginInRemember({commit}, userInfo) {
            // trim()：从字符串中删除开头和结尾的空格和行结束符。
            const phone = userInfo.phone.trim()
            const token = userInfo.token;
            return new Promise(((resolve, reject) => {
                loginInRemember(phone,token).then(response=>{
                    commit('SET_TOKEN', response.token)
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            }))
        },

        //获取用户信息
        getInfo({commit, state}) {
            return new Promise((resolve, reject) => {
                getUserInfo(state.token).then(response => {
                    console.log(response)
                    const user =response.user
                    //验证返回的roles是否是一个非空数组
                    if(response.roles&&response.roles.length>0){
                        commit('SET_ROLES', response.roles);
                        commit('SET_PERMISSIONS', response.permissions);
                    }else {
                        commit('SET_ROLES', ['ROLE_DEFAULT'])
                    }
                    commit('SET_USERNAME', user.username);
                    commit('SET_PHONE', user.phone);
                    resolve(response);
                }).catch(error => {
                    reject(error);
                })
            })
        },

        //用户登出
        logout({commit, state}) {
            return new Promise((resolve, reject) => {
                logout(state.token).then(() => {
                    commit('SET_TOKEN', '');
                    commit('SET_USERNAME', '');
                    commit('SET_PHONE', '')
                    commit('SET_ROLES', []);
                    removeToken();
                    resolve();
                }).catch(error => {
                    reject(error);
                })
            })
        },

        //重置token
        resetToken({commit}) {
            return new Promise(resolve => {
                commit('SET_TOKEN', '');
                commit('SET_USERNAME', '');
                commit('SET_PHONE', '')
                commit('SET_ROLES', []);
            })
        }
    }

}

export default user
