<script setup>

import {Lock, User} from "@element-plus/icons-vue";
import {reactive} from "vue";
import {ElMessage} from "element-plus";
import {get, post} from "@/net"
import router from "@/router/index.js";
import {useStore} from "@/stores";

const store =useStore()

const form=reactive({
  username:'',
  password:'',
  remember:false
})

const login = () => {
  if(!form.username||!form.password){
    ElMessage.warning('请填写用户名和密码！')
  } else {
    post('/api/auth/login',{
        username: form.username,
        password: form.password,
        remember: form.remember
    }, (message)=> {
      ElMessage.success(message)
      get('/api/user/me',(message)=>{
        store.auth.user=message
        router.push('/index/homepage')
      },()=>{
        store.auth.user = null
      })
    })
  }
}
</script>

<template>
  <div style=" margin-top:160px" >
    <div style="font-size:25px;font-weight:bold"> 问卷星系统</div>
    <div style="font-size: 14px;color:gray">请输入用户名和密码进行登录</div>
  </div>

  <div style="margin-top:50px">
    <el-input v-model="form.username" type="text" placeholder="用户名/邮箱">
      <template #prefix>
        <el-icon><User /></el-icon>
      </template>
    </el-input>
  </div>

  <div style="margin-top:20px">
    <el-input v-model="form.password" type="password" placeholder="密码">
      <template #prefix>
        <el-icon><Lock /></el-icon>
      </template>
    </el-input>
  </div>
  <el-row style="margin-top:5px">
    <el-col :span="12" style="text-align:left">
      <el-checkbox v-model="form.remember" label="记住密码" size="large" />
    </el-col>
    <el-col :span="12" style="text-align: right;margin-top:5px">
      <el-link @click="router.push('/forget')">忘记密码？</el-link>
    </el-col>
  </el-row>

  <div style="margin-top:40px">
    <el-button @click="login()" style="width:270px" type="success" plain>
       立即登录
    </el-button>
  </div>
  <el-divider>
    <span style="color:grey;font-size:13px" >没有账号</span>
  </el-divider>
  <div>
    <el-button style="margin-top:20px;width:200px " type="warning" plain @click="router.push('/register')">
      注册账号</el-button>
  </div>
</template>

<style scoped>

</style>