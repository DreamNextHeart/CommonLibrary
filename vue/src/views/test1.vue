<template>
  <el-container style="height: 500px">
    <el-aside width="200px">
      <el-scrollbar>
        <el-menu router>
          <template v-for="(v,index) in finalRouter" :key="v.path" >
            <el-sub-menu  :index="index+ ' '" >
              <template #title>
                {{v.meta.title}}
              </template>
              <el-menu-item v-for="vitem in v.children" :key="vitem.path" >
                  {{vitem.meta.title}}
              </el-menu-item>
            </el-sub-menu>
          </template>
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
