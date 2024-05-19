<script setup>

import {Lock, User,Message,Bell} from "@element-plus/icons-vue";
import router from "@/router/index.js";
import {reactive} from "vue"

const form=reactive({
  username:'',
  password:'',
  password_repeat:'',
  email:'',
  code:''
})

</script>

<template>
  <div style="margin-top:100px">
    <div style="font-size:25px;font-weight:bold"> 注册新用户</div>
    <div style="font-size: 14px;color:gray">请在下方填写相关信息</div>
  </div>
  <div style="margin-top:50px">
    <el-input v-model="form.username" type="text" placeholder="用户名">
      <template #prefix>
        <el-icon><User /></el-icon>
      </template>
    </el-input>

    <el-input v-model="form.password" type="password" placeholder="密码" style="margin-top:10px">
      <template #prefix>
        <el-icon><Lock /></el-icon>
      </template>
    </el-input>

    <el-input v-model="form.password_repeat" type="password" placeholder="重置密码" style="margin-top:10px">
      <template #prefix>
        <el-icon><Lock /></el-icon>
      </template>
    </el-input>
  </div>

  <el-input v-model="form.email" type="email" placeholder="电子邮箱" style="margin-top:10px">
    <template #prefix>
      <el-icon><Message /></el-icon>
    </template>
  </el-input>

  <div style="margin-top:10px">
    <el-row>
      <el-col :span="18">
        <el-input v-model="form.code" type="email" placeholder="请输入验证码">
          <template #prefix>
            <el-icon><Bell /></el-icon>
          </template>
        </el-input>
      </el-col>
      <el-col :span="6">
        <el-button type="success">获取验证码</el-button>
      </el-col>
    </el-row>

    <div style="margin-top:100px">
      <el-button style="width:100px" type="warning">
        立即注册
      </el-button>
    </div>

    <div style="font-size:14px;line-height: 15px;margin-top: 20px">
      <span style="color:grey">已有账号？</span>
      <el-link type="primary" style="translate: 0 -2px" @click="router.push('/')">立即登录</el-link>
    </div>

  </div>
</template>

<style scoped>

</style>