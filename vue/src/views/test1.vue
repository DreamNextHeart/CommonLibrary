<template>
  <el-container style="height: 500px">
    <el-aside width="200px">
      <el-scrollbar>
        <el-menu router>
          <el-sub-menu v-for="(v,index) in $router.options.routes" :index="index+ ' '" >
            <template #title v-if="!v.hidden">{{v.title}}</template>
            <el-menu-item v-for="vTemp in v.children" :index="vTemp.path">{{vTemp.title}}</el-menu-item>
          </el-sub-menu>
        </el-menu>
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

    }
  },
  created() {
  }
}
</script>

<style scoped>

</style>
