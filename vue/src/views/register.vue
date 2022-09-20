<template>
  <div class="register">
    <el-form ref="registerForm" :model="registerForm" class="register-form">
      <h3 class="title">CommonLibrary</h3>
      <!-- 输入用户名（nickname，假用户名）-->
      <el-form-item prop="nickname">
        <el-input
          v-model="registerForm.nickname"
          type="text"
          auto-complete="off"
          placeholder="用户名"
        >
        </el-input>
      </el-form-item>

      <!-- 输入手机号码（用户登陆账号，真用户名）-->
      <el-form-item prop="phone">
        <el-input
          v-model="registerForm.phone"
          type="text"
          auto-complete="off"
          placeholder="手机号码"
        >
        </el-input>
      </el-form-item>

      <!--  输入邮箱-->
      <el-form-item prop="email">
        <el-input
          v-model="registerForm.email"
          type="text"
          auto-complete="off"
          placeholder="邮箱"
        >
        </el-input>
      </el-form-item>

      <!--  输入密码-->
      <el-form-item prop="password">
        <el-input
          v-model="registerForm.password"
          type="password"
          auto-complete="off"
          placeholder="密码"
        >
        </el-input>
      </el-form-item>

      <!--  输入确认密码-->
      <el-form-item prop="password">
        <el-input
          v-model="registerForm.currentPassword"
          type="password"
          auto-complete="off"
          placeholder="确认密码"
        >
        </el-input>
      </el-form-item>

<!--      &lt;!&ndash;  验证码&ndash;&gt;-->
<!--      <el-form-item prop="code" v-if="captchaEnabled">-->
<!--        <el-input-->
<!--          v-model="registerForm.code"-->
<!--          auto-complete="off"-->
<!--          placeholder="验证码"-->
<!--          style="width: 63%"-->
<!--          @keyup.enter.native="handleregister"-->
<!--        >-->
<!--        </el-input>-->
<!--        <div class="register-code">-->
<!--          <img :src="codeUrl" @click="getCodeImg" class="register-code-img"/>-->
<!--        </div>-->
<!--      </el-form-item>-->

<!--      &lt;!&ndash;  记住密码&ndash;&gt;-->
<!--      <el-checkbox v-model="registerForm.remember" label="记住密码" style="margin:0 0 25px 0;"></el-checkbox>-->


      <!--  注册按钮-->
      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="medium"
          type="primary"
          style="width:100%;"
          @click.native.prevent="handleregister"
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
    <div class="el-register-footer">
      <span>Copyright © 2022-2022 CommonLibrary.</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "register",
  data() {
    return {
      codeUrl: '',
      registerForm: {
        username: '',
        password: '',
        remember: false,
        code: '',
        token: '',
        uuid: ''
      },
      TempForm: {
        remember: false
      },
      loading: false,
      //验证码开关
      captchaEnabled: true,
      //注册开关
      register: false
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">

.register {
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

.register-form {
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

.register-code {
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

.el-register-footer {
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

.register-code-img {
  height: 38px;
}
</style>
