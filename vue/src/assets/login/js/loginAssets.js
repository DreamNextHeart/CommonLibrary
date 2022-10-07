import {checkCode} from "@/api/loginApi";


export function checkCodeAssets(code){
  checkCode(code).then(res => {
    if (res.data.code !== 200) {
      this.$message.error('验证码错误');
    } else {
      this.clearCookie();
    }
  })
}

// export function handleLoginAssets(tempForm,loginForm){
//   tempForm.loading = true;
//   if (tempForm.remember === false) {
//     loginInNotRemember(loginForm).then(res => {
//       if (res.data.code === 310) {
//         console.log(1)
//         this.$message.error('验证码错误');
//         loginForm.code = '';
//         this.getCodeImg();
//       } else if (res.data.code === 311) {
//         console.log(2)
//         this.$message.error('用户名或密码错误');
//         loginForm.code = '';
//         loginForm.password = '';
//         this.getCodeImg();
//       } else if (res.data.code === 200) {
//         console.log(3)
//         this.$message.success('登陆成功，正在跳转');
//         loginForm.token = res.data.data.token;
//         this.setCookie();
//       }
//     });
//   }else {
//     loginInRemember(loginForm).then(res => {
//       if (res.data.code === 200) {
//         console.log(4)
//         this.$message.success('登陆成功，正在跳转');
//       } else {
//         console.log(5)
//         this.$message.error('用户信息已过期，请重新登录');
//         loginForm.code = '';
//         loginForm.username = '';
//         loginForm.password = '';
//         loginForm.remember = false;
//         tempForm.remember = false;
//         tempForm.captchaEnabled = true;
//         this.clearCookie();
//         this.getCodeImg();
//       }
//     })
//   }
//   tempForm.loading = false;
// }
