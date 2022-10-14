import {loginInNotRemember, logout} from "@/api/user/userApi";
import {getUserInfo} from "@/api/user/userApi"
import {removeToken, setToken} from "@/assets/token/token";
import {resetRouter} from "@/router";

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
    SET_USERNAME: (state, username) => {
        state.username = username
    },
    SET_PHONE: (state, phone) => {
        state.phone = phone
    },
    SET_ROLES: (state, roles) => {
        state.roles = roles
    }
}

const actions={
    //用户登录
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

    //获取用户信息
    getInfo({commit,state}){
        return new Promise((resolve,reject)=>{
            getUserInfo(state.token).then(response=>{
                const{data}=response;
                //无data相当于并没有登录
                if(data===undefined){
                    reject("登陆已失效，请重新登录");
                }
                const{phone,roles,username}=data;
                //roles不允许为空，若为空即为没有进行配置角色
                if(roles===undefined||roles.length<=0){
                    reject("获取用户角色信息失败，请重新登录或联系管理员处理");
                }
                commit('SET_USERNAME',username);
                commit('SET_PHONE',phone);
                commit('SET_ROLES',roles);
                resolve(data);
            }).catch(error=>{
                reject(error);
            })
        })
    },

    //用户登出
    logout({commit,state}){
        return new Promise((resolve,reject)=>{
            logout(state.token).then(()=>{
                commit('SET_TOKEN','');
                commit('SET_USERNAME','');
                commit('SET_PHONE','')
                commit('SET_ROLES',[]);
                removeToken();
                resetRouter();
                resolve();
            }).catch(error=>{
                reject(error);
            })
        })
    },

    //重置token
    resetToken({commit}){
        return new Promise(resolve => {
            commit('SET_TOKEN','');
            commit('SET_USERNAME','');
            commit('SET_PHONE','')
            commit('SET_ROLES',[]);
        })
    }



}