const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  // devServer: {
  //   open: true,
  //   port: 8081,
  //   //以上的ip和端口是我们本机的;下面为需要跨域的
  //   proxy: { //配置跨域
  //     '/apis': {
  //       target: 'http://localhost:8082/', //这里后台的地址模拟的;应该填写你们真实的后台接口
  //       ws: true,
  //       changOrigin: true, //允许跨域
  //       pathRewrite: {
  //         '^/apis': '' //请求的时候使用这个api就可以
  //       }
  //     }
  //   }
  // },
})
