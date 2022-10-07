<template>
  <div class="register">
    <el-form ref="registerForm" :rules="loginRules" :model="registerForm" class="register-form">
      <h3 class="title">CommonLibrary</h3>
      <!-- 输入用户名-->
      <el-form-item prop="username">
        <el-input
          v-model="registerForm.username"
          type="text"
          auto-complete="off"
          placeholder="用户名"
        >
        </el-input>
      </el-form-item>

      <!-- 输入手机号码-->
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
      <el-form-item prop="currentPassword">
        <el-input
          v-model="registerForm.currentPassword"
          type="password"
          auto-complete="off"
          placeholder="确认密码"
        >
        </el-input>
      </el-form-item>

      <!--  验证码-->
      <el-form-item prop="code" v-if="captchaEnabled">
        <el-input
          v-model="registerForm.code"
          auto-complete="off"
          placeholder="验证码"
          style="width: 63%"
          @keyup.enter.native="handleLogin"
        >
        </el-input>
        <div class="register-code">
          <img :src="codeUrl" @click="getCodeImg" class="login-code-img"/>
        </div>
      </el-form-item>

      <!--  注册按钮-->
      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="medium"
          type="primary"
          style="width:100%;"
          @click.native.prevent="handleRegister"
        >
          <span v-if="!loading">注 册</span>
          <span v-else>注 册 中...</span>
        </el-button>
        <div style="float: right;" v-if="login">
          <router-link class="link-type" :to="'/'">已有账号？立即登录</router-link>
        </div>
      </el-form-item>
    </el-form>

    <!--底部-->
    <div class="el-register-footer">
      <span>Copyright © 2022 CommonLibrary.</span>
    </div>
  </div>
</template>

<script>
import {registerApi} from "@/api/registerApi";
import {getCodeImg} from "@/api/loginApi";

export default {
  name: "register",
  data() {
    //currentPWDCheck该回调函数是为了检验确认密码和密码是否一致
    const currentPWDCheck = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else if (value !== this.registerForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      codeUrl: '',
      registerForm: {
        username: '',
        phone: '',
        email: '',
        password: '',
        currentPassword: '',
        token: '',
        uuid: ''
      },
      loading: false,
      captchaEnabled: true,
      //登录开关
      login: true,
      loginRules: {
        username: [
          {required: true, trigger: 'blur', message: "请输入用户名"},
        ],
        phone: [
          {required: true, trigger: 'blur', message: "请输入手机号码"},
          {required: true,max: 11,min: 11,message: "请输入11位的手机号码",trigger: 'blur'},
        ],
        email: [
          {required: true, trigger: 'blur', message: "请输入邮箱"},
          {pattern: /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
            required: true, message: "请输入正确的邮箱地址", trigger: "blur"}
        ],
        password: [
          {required: true, trigger: 'blur', message: "请输入密码"},
          {pattern: /^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$)([^\u4e00-\u9fa5\s]){6,20}$/,
            required: true, message: "密码6~20位，并且字母、数字和标点符号至少包含两种", trigger: "blur"}
        ],
        currentPassword:[
          {required: true, trigger: 'blur', message: "请输入确认密码"},
          {pattern: /^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$)([^\u4e00-\u9fa5\s]){6,20}$/,
            required: true, message: "密码6~20位，并且字母、数字和标点符号至少包含两种", trigger: "blur"},
          {required: true, trigger: 'blur',validator: currentPWDCheck}
        ]
      },
    };

  },
  created() {
    this.getCodeImg();
  },
  methods:{
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
    handleRegister(){
      this.$refs.registerForm.validate(valid => {
        if(valid===false){
          this.$message.error('请按照提示输入');
        }else {
          registerApi(this.registerForm).then(res=>{
            console.log(res);
            if(res.data.code===200){
              this.$message.success('注册成功，请到邮箱激活账号');
            }else if(res.data.code===313){
              this.$message.error('该手机号已被注册');
              this.getCodeImg();
            }else if(res.data.code===310){
              this.$message.error('验证码错误');
            }
          })
        }
      })
    }
  }
}
</script>

<style  lang="scss">

.register {
  display: flex;
  //居中排列
  justify-content: center;
  //居中对齐
  align-items: center;
  height: 1080px;
  background-image: url("~@/assets/img/background.jpg");
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
