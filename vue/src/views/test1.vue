<template>
  <el-container style="height: 500px">
    <el-aside width="200px">
      <el-scrollbar>
        <el-menu>
          <el-sub-menu index="1">
            <template #title>第一个导航栏</template>
              <el-menu-item index="1-1">Option 1</el-menu-item>
              <el-menu-item index="1-2">Option 2</el-menu-item>
            <el-sub-menu index="1-3">
              <template #title>Option 3</template>
              <el-menu-item index="1-3-1">Option 3-1</el-menu-item>
            </el-sub-menu>
          </el-sub-menu>
        </el-menu>
      </el-scrollbar>
    </el-aside>
  </el-container>
</template>

<script>
export default {
  name: "test1"
}
</script>

<style scoped>

</style>
