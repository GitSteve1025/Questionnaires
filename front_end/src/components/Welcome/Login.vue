<script setup>

import {Lock, User} from "@element-plus/icons-vue";
</script>

<template>
  <div style=" margin-top:160px" >
    <div style="font-size:25px;font-weight:bold"> 问卷星系统</div>
    <div style="font-size: 14px;color:gray">请输入用户名和密码进行登录</div>
  </div>

  <div style="margin-top:50px">
    <el-input type="text" placeholder="用户名/邮箱">
      <template #prefix>
        <el-icon><User /></el-icon>
      </template>
    </el-input>
  </div>

  <div style="margin-top:20px">
    <el-input type="password" placeholder="密码">
      <template #prefix>
        <el-icon><Lock /></el-icon>
      </template>
    </el-input>
  </div>
  <el-row style="margin-top:5px">
    <el-col :span="12" style="text-align:left">
      <el-checkbox v-model="checked1" label="记住密码" size="large" />
    </el-col>
    <el-col :span="12" style="text-align: right;margin-top:5px">
      <el-link>忘记密码？</el-link>
    </el-col>
  </el-row>

  <div style="margin-top:40px">
    <el-button style="width:270px" type="success" plain>
      立即登录
    </el-button>
  </div>
  <el-divider>
    <span style="color:grey;font-size:13px" >没有账号</span>
  </el-divider>
  <div>
    <el-button style="margin-top:20px;width:200px"type="warning" plain>注册账号</el-button>
  </div>
</template>

<style scoped>

</style>