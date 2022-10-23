<template>
  <el-container style="height: 100%">
    <el-aside width="200px">
      <el-scrollbar>
        <el-menu router>
          <el-sub-menu v-for="(v,index) in menuList" :index="v.path" >
            <template #title v-if="!v.hidden">{{v.title}}</template>
            <el-menu-item v-for="(vTemp,inTemp) in v.children" :index="vTemp.path">{{vTemp.title}}</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-scrollbar>
    </el-aside>
    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</template>

<script>
import router from "@/router";

export default {
  name: "index",
  data(){
    return{
      menuList: []
    }
  },
  created() {
    this.menuList=router.options.routes;
  }
}
</script>

<style scoped>

</style>
