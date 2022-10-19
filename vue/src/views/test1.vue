<template>
  <el-container style="height: 500px">
    <el-aside width="200px">
      <el-scrollbar>
        <el-menu router>
          <el-sub-menu v-for="(v,index) in $router.options.routes" :index="index+ ' '">
            <template #title>{{v.name}}</template>
            <el-menu-item v-for="vTemp in v.children" :index="vTemp.path">{{vTemp.name}}</el-menu-item>
          </el-sub-menu>
        </el-menu>

<!--        <el-menu router>-->
<!--          <template v-for="(v,index) in $router.options.routes" :key="v.path" >-->
<!--            <el-sub-menu  :index="index+ ' '" >{{v.name}}-->
<!--              <template>-->
<!--                {{v.name}}-->
<!--              </template>-->
<!--              <el-menu-item v-for="vitem in v.children" :key="vitem.path" >-->
<!--                  {{vitem.meta.title}}-->
<!--              </el-menu-item>-->
<!--            </el-sub-menu>-->
<!--          </template>-->
<!--        </el-menu>-->
      </el-scrollbar>
    </el-aside>
  </el-container>
</template>

<script>
import router from "@/router";

export default {
  name: "test1",
  data(){
    return{
      finalRouter: []
    }
  },
  created() {
    console.log("create")
    console.log(router.options.routes)
    this.finalRouter=router.getRoutes();
    console.log("进入首页")
    console.log(router.options.routes)
    console.log(router.getRoutes())
  }
}
</script>

<style scoped>

</style>
