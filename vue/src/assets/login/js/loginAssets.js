import {checkCode} from "@/api/user/userApi";


export function checkCodeAssets(code){
  checkCode(code).then(res => {
    if (res.data.code !== 200) {
      this.$message.error('验证码错误');
    } else {
      this.clearCookie();
    }
  })
}

