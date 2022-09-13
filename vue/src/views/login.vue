<template>
  <div class="login">
    <el-form ref="loginForm" :model="loginForm" class="login-form">
      <h3 class="title">CommonLibrary</h3>
      <!-- 输入账号-->
      <el-form-item prop="username">
        <el-input
          v-model="loginForm.username"
          type="text"
          auto-complete="off"
          placeholder="账号"
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
          <router-link class="link-type" :to="'/register'">立即注册</router-link>
        </div>
      </el-form-item>
    </el-form>

    <!--底部-->
    <div class="el-login-footer">
      <span>Copyright © 2022-2022 CommonLibrary.</span>
    </div>
  </div>
</template>

<script>
import {checkCode, getCodeImg, handleLogin} from '@/api/login'
import {clearCookie, getCookie, setCookie} from '@/assets/login/js/cookie'

export default {
  name: 'Login',
  data() {
    return {
      codeUrl: '',
      loginForm: {
        username: '',
        password: '',
        remember: true,
        code: '',
        uuid: ''
      },
      loading: false,
      //验证码开关
      captchaEnabled: true,
      //注册开关
      register: false
    }
  },
  created() {
    this.getCodeImg();
    this.getCookie();
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
    checkCode() {
      const code = this.loginForm.code;
      console.log(code);
      checkCode(code).then(res => {
        if (res.data.code !== 200) {
          this.$message.error('验证码错误');
        } else {

        }
      })
    },
    getCookie() {
      getCookie(this.loginForm);
    },
    setCookie(day) {
      setCookie(this.loginForm, day);
    },
    clearCookie() {
      clearCookie();
    },
    test() {
      console.log(this.loginForm);
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          if (this.loginForm.remember) {
            this.setCookie(30);
          } else {
            this.clearCookie();
          }
          console.log("来了")
          handleLogin(this.loginForm).then(res => {
            console.log(res);
          });
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
