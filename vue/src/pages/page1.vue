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
      <el-form-item prop="code" v-if="tempForm.captchaEnabled">
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
            :loading="tempForm.loading"
            size="medium"
            type="primary"
            style="width:100%;"
            @click.native.prevent="handleLogin"
        >
          <span v-if="!tempForm.loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
        <div style="float: right;" v-if="tempForm.register">
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
export default {
  name: 'page1',
  data() {
    return {
      n: 1, //用户选择的数字
    }
  },
  methods: {
    //加
    increment() {
      this.$store.commit('JIA', this.n)
    },
    //减
    decrement() {
      this.$store.commit('JIAN', this.n)
    },
    //奇数再加
    incrementOdd() {
      this.$store.dispatch('jiaOdd', this.n)
    },
    //等一等再加
    incrementWait() {
      this.$store.dispatch('jiaWait', this.n)
    },
  },
  mounted() {
    console.log('Count', this)
  },
}
</script>

<style lang="css">
button {
  margin-left: 5px;
}
</style>
