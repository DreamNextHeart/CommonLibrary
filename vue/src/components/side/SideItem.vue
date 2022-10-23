<template>
  <div>
    <div v-for="(item, index) in $router.options.routes" :key="index">
      <!-- hidden=true且该路由又有子路由时 直接递归进入子路由 -->
      <div v-if="item.hidden && item.children">
        <SideItem :tree="item.children"></SideItem>
      </div>
      <div v-if="!item.hidden">
        <el-submenu :index="item.path" v-if="item.children">
          <template slot="title">
            <i :class="item.meta.icon || ''"></i>
            <span>{{ item.meta.title }}</span>
          </template>
          <SideItem :tree="item.children"></SideItem>
        </el-submenu>

        <el-menu-item :index="item.path" v-if="!item.children">
          <i :class="item.meta.icon || ''"></i>
          <span slot="title">
            {{ item.meta.title }}
          </span>
        </el-menu-item>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "SideItem"
}
</script>

<style scoped>

</style>
