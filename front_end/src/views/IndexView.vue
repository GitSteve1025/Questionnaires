<template>
  <div>
<!--    {{store.auth.user.username}}-->
  </div>

  <el-menu
      :default-active="activeIndex2"
      class="el-menu-demo"
      mode="horizontal"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"

      style="margin-top: 20px;height: 8vh"
  >
    <div class="welcome-title"  style="margin-left:10px;margin-top:10px;font-size: 40px;font-weight:bold;display:flex ">问卷星系统</div>
    <el-menu-item index="1" style="margin-left: 30px" @click="Gohomepage">首页</el-menu-item>
    <el-sub-menu index="2">
      <template #title>我的问卷</template>
      <el-menu-item index="2-1" @click="Gomypage">创建问卷</el-menu-item>
      <el-menu-item index="2-2" @click="router.push('/index/showallquestion')">查看问卷</el-menu-item>
      <el-menu-item index="2-3">item three</el-menu-item>
      <el-sub-menu index="2-4">
        <template #title>item four</template>
        <el-menu-item index="2-4-1">item one</el-menu-item>
        <el-menu-item index="2-4-2">item two</el-menu-item>
        <el-menu-item index="2-4-3">item three</el-menu-item>
      </el-sub-menu>
    </el-sub-menu>
    <el-menu-item index="3" disabled>客服中心</el-menu-item>
    <el-menu-item index="4">个人资料</el-menu-item>
  </el-menu>
  <div>
    <el-container>
      <el-header>
        <el-menu mode="horizontal" style="margin-top: 10px">
        </el-menu>
      </el-header>
    <div>
      <router-view> </router-view>
    </div>

    </el-container>
  </div>

</template>

<script lang="ts" setup>
  import {get} from "@/net";
  import {ElMessage} from "element-plus";
  import router from "@/router";
  import {useStore} from "@/stores";
  import {reactive, ref} from 'vue'

  const activeIndex = ref('1')
  const activeIndex2 = ref('1')

  const store=useStore()

    // formRef.value.validate((isValid) => {
    //   if(isValid) {
    //
    //   } else {
    //     ElMessage.warning('请完整填写表单内容！')
    //   }
    // )
  const Gomypage=()=>{
    router.push('/index/mypage')
}
  const Gohomepage=()=>{
    router.push('/index/homepage')
  }
</script>

<style scoped>

</style>