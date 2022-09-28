<template>
  <div class="login">
    <el-form ref="loginForm" :rules="loginRules" :model="loginForm" class="login-form">
      <h3 class="title">CommonLibrary</h3>
      <!-- 输入账号-->
      <el-form-item prop="phone">
        <el-input
          v-model="loginForm.phone"
          type="text"
          auto-complete="off"
          placeholder="手机号码"
        >
        </el-input>
      </el-form-item>

      <!--  输入密码-->
      <el-form-item prop="password">
        <el-input
          v-model="loginForm.password"
          type="password"
          auto-complete="off"
          placeholder="密码"
          @keyup.enter.native="handleLogin"
        >
        </el-input>
      </el-form-item>

      <!--  验证码-->
      <el-form-item prop="code" v-if="captchaEnabled">
        <el-input
          v-model="loginForm.code"
          auto-complete="off"
          placeholder="验证码"
          style="width: 63%"
          @keyup.enter.native="handleLogin"
        >
        </el-input>
        <div class="login-code">
          <img :src="codeUrl" @click="getCodeImg" class="login-code-img"/>
        </div>
      </el-form-item>

      <!--  记住密码-->
      <el-checkbox v-model="loginForm.remember" label="记住密码" style="margin:0 0 25px 0;"></el-checkbox>


      <!--  登录、注册按钮-->
      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="medium"
          type="primary"
          style="width:100%;"
          @click.native.prevent="handleLogin"
        >
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
        <div style="float: right;" v-if="register">
          <router-link class="link-type" :to="'/register'">没有账号？立即注册</router-link>
        </div>
      </el-form-item>
    </el-form>

    <!--底部-->
    <div class="el-login-footer">
      <span>Copyright © 2022 CommonLibrary.</span>
    </div>
  </div>
</template>

<script>
import {checkCode, getCodeImg, loginIn, loginInNotRemember, loginInRemember} from '@/api/login'
import {clearCookie, getCookie, getCookieRemember, setCookie} from '@/assets/login/js/cookie'
import {getRandomPassword} from "@/assets/login/js/password";


export default {
  name: 'Login',
  data() {
    return {
      codeUrl: '',
      loginForm: {
        username: '',
        phone: '',
        password: '',
        remember: false,
        code: '',
        token: '',
        uuid: ''
      },
      TempForm: {
        remember: false
      },
      loginRules: {
        phone: [
          {required: true, trigger: 'blur', message: "请输入你的手机号码"},
          {required: true,max: 11,min: 11,message: "请输入11位的手机号码",trigger: 'blur'},
        ],
        password: [
          {require: true, trigger: 'blur', message: "请输入你的密码"},
          {pattern: /^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$)([^\u4e00-\u9fa5\s]){6,20}$/,
            required: true, message: "密码6~20位，并且字母、数字和标点符号至少包含两种", trigger: "blur"}
        ]
      },
      loading: false,
      //验证码开关
      captchaEnabled: true,
      //注册开关
      register: true
    }
  },
  created() {
    this.getCookieRemember();
    this.getCodeImg();
    console.log(this.TempForm.remember)
  },
  methods: {
    getCodeImg() {
      getCodeImg().then(res => {
        console.log(res);
        try {
          this.codeUrl = window.URL.createObjectURL(res.data);
        } catch (error) {
          this.codeUrl = window.URL.createObjectURL(res.data);
        }
      });
    },
    getCookieRemember() {
      getCookieRemember(this.TempForm);
      if (this.TempForm.remember === true) {
        this.getCookie();
        this.loginForm.password = getRandomPassword(10);
        this.captchaEnabled = false;
      }
    },
    checkCode() {
      const code = this.loginForm.code;
      checkCode(code).then(res => {
        if (res.data.code !== 200) {
          this.$message.error('验证码错误');
        } else {
          this.clearCookie();
        }
      })
    },
    getCookie() {
      getCookie(this.loginForm);
    },
    setCookie() {
      setCookie(this.loginForm);
    },
    clearCookie() {
      clearCookie();
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          if (this.TempForm.remember === false) {
            loginInNotRemember(this.loginForm).then(res => {
              if (res.data.code === 310) {
                this.$message.error('验证码错误');
                this.loginForm.code = '';
                this.getCodeImg();
              } else if (res.data.code === 311) {
                this.$message.error('用户名或密码错误');
                this.loginForm.code = '';
                this.loginForm.password = '';
                this.getCodeImg();
              } else if (res.data.code === 200) {
                this.$message.success('登陆成功，正在跳转');
                this.loginForm.token = res.data.data.token;
                this.setCookie();
              }
            });
          } else {
            loginInRemember(this.loginForm).then(res => {
              if (res.data.code === 200) {
                this.$message.success('登陆成功，正在跳转');
              } else {
                this.$message.error('用户信息已过期，请重新登录');
                this.loginForm.code = '';
                this.loginForm.username = '';
                this.loginForm.password = '';
                this.loginForm.remember = false;
                this.TempForm.remember = false;
                this.captchaEnabled = true;
                this.clearCookie();
                this.getCodeImg();
              }
            })
          }
          this.loading = false;
        }
      });
    }
  }
}

</script>

<style rel="stylesheet/scss" lang="scss">

.login {
  display: flex;
  //居中排列
  justify-content: center;
  //居中对齐
  align-items: center;
  height: 1080px;
  background-image: url("../assets/img/backgro.jpg");
  //背景图片尺寸，conver为扩展图片填满元素(保持像素的长宽比)
  background-size: cover;
}

.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
  //为元素添加圆角边框
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;

  .el-input {
    height: 38px;

    input {
      height: 38px;
    }
  }
}

.login-code {
  width: 33%;
  height: 38px;
  float: right;

  img {
    //鼠标放到图片上变成一个手指
    cursor: pointer;
    //把此元素放置在父元素的中部。
    vertical-align: middle;
  }
}

.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}

.login-code-img {
  height: 38px;
}
</style>
