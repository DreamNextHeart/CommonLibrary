<template>
  <div class="login">
    <button @click="getMenuTree">a</button>
  </div>
</template>

<script>
import {getMenuTree} from "@/api/menu/menuApi";

export default {
  name: 'page1',
  data() {
    return {
      id: 2, //用户选择的数字
    }
  },
  methods: {
    getMenuTree(){
      getMenuTree().then(response=>{
        const sdata = JSON.parse(JSON.stringify(response.data))
        const rdata = JSON.parse(JSON.stringify(response.data))
        console.log("我是sdata:")
        console.log(sdata,"===")
        console.log(rdata)
      })
    }
  },
}
</script>

<style lang="css">
button {
  margin-left: 5px;
}
</style>
