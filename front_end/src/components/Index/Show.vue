<script setup>
import router from "@/router/index.js";


const Gochoose=()=>{
  router.push('/index/choicepage')
}
</script>

<template>

  <div style="margin-left: 100px;margin-top: 30px">这里显示标题</div>
  <div style="margin-top: 20px;margin-left: 100px">这里显示对问卷的说明</div>

  <div>
    <el-button style="margin-top: 20px;margin-left: 100px" @click="Gochoose">添加题目</el-button>
  </div>
</template>

<style scoped>

</style>