import {loginInNotRemember} from "@/api/loginApi";
import {setToken} from "@/assets/token/token";

const {getToken} = require("@/assets/token/token");

const state={
    token: getToken(),
    name: '',
    roles: []
}

const mutations={
    SET_TOKEN: (state, token) => {
        state.token = token
    },
    SET_NAME: (state, name) => {
        state.name = name
    },
    SET_ROLES: (state, roles) => {
        state.roles = roles
    }
}

const actions={
    //user login
    loginInNotRemember({commit},userInfo){
        const {username,password}=userInfo

        return new Promise((resolve, reject)=>{
            // trim()：从字符串中删除开头和结尾的空格和行结束符。
            loginInNotRemember({ username: username.trim(), password: password }).then(response => {
                const { data } = response
                commit('SET_TOKEN', data.token)
                setToken(data.token)
                resolve()
            }).catch(error => {
                reject(error)
            })
        })
    },
    getInfo({commit,state}){

    }
}