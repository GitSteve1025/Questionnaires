<script setup>
import {get} from "@/net";
import {ElMessage} from "element-plus";
import router from "@/router";
import {useStore} from "@/stores";
import {reactive,provide} from 'vue'
import {post} from "@/net"
import {Document, Memo} from "@element-plus/icons-vue";


const format =reactive({
  title:'',
  description:'',
  id:'',//初始值为空
})


const logout = () => {
  get('/api/auth/logout', (message) => {
    ElMessage.success(message)
    store.auth.user=null
    router.push('/')
  })
}

const create=()=>{
  post('/questionnaires/create', {
    title:format.title,
    description:format.description,
  },(message)=>{
    ElMessage.success('问卷创建成功')
    format.id = JSON.parse(JSON.stringify(message)).questionnaireId;
    router.push({
      path: '/index/choicepage',
      query: {
        params: JSON.stringify(format.id)
      }
    }) //创建成功则进行跳转
  })
}

</script>

<template>



  <div style="margin-top: 20px">
    <el-form-item prop="">
      <el-input v-model="format.title" :maxlength="20" type="text" placeholder="请输入标题" style="margin-top: 100px">
        <template #prefix>
          <el-icon><Memo /></el-icon>
        </template>
      </el-input>
    </el-form-item>

    <el-form-item prop="">
      <el-input v-model="format.description" type="text" placeholder="描述" style="margin-top: 10px">
        <template #prefix>
          <el-icon><Document /></el-icon>
        </template>

      </el-input>
    </el-form-item>

    <el-button @click="create()"  style="text-align: center;margin-top: 10px"  type="success">确认</el-button>
    <el-button @click="" style="margin-left: 20px;margin-top:10px" type="danger">返回</el-button>
  </div>

</template>

<style scoped>

</style>