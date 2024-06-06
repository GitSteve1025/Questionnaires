<script setup>

import router from "@/router/index.js";
import {reactive} from "vue";
import {post} from "@/net/index.js";
import {ElMessage} from "element-plus";
import {Memo, Document, User} from "@element-plus/icons-vue";
import {useRoute} from "vue-router";

const route=useRoute()
const id=JSON.parse(route.query.params);

const create=()=>{
  post('/question/create-BlankQuestion', {
    questionnaireId: id,
    title:format.title,
    necessary:format.necessary,
    validation:format.validation,
    type:format.type,
  }, (message) => {
    ElMessage.success(message)
    router.push("/index/choicepage")
  })
}

const handleClick = () => {
  // eslint-disable-next-line no-alert
  alert('文本格式设置成功');
}

const format =reactive({
  title:'',
  necessary:[
    {
      required:false,
    }
  ],
  validation:{
    required:false,message:'是否需要进行文本验证',
  },
  type:'NULL',
})


</script>

<template>
  <el-button style="margin-top: 50px" >我是填空题</el-button>

  <div>
    <el-input v-model="format.title" :maxlength="20" type="text" placeholder="请输入题目标题" style="margin-top: 5px">
      <template #prefix>
        <el-icon><Memo /></el-icon>
      </template>
    </el-input>
  </div>

  <div class="flex flex-wrap items-center">

    <el-dropdown split-button type="primary" @click="handleClick" style="margin-top: 10px">
      文本验证
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item >无</el-dropdown-item>
          <el-dropdown-item>Phone</el-dropdown-item>
          <el-dropdown-item>Email</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
  <el-checkbox v-model="format.necessary" label="是否必答" size="large" span="20" /> <!--v-model="form.necessary"传入是否必答-->
  <div>

    <div v-for="(option, index) in options" :key="index" style="margin-top: 20px;display:flex;align-items:center">
      <el-form-item style="display: flex;" prop="">
        <!-- 删除按钮 -->
        <el-button type="danger" size="small" style="margin-right: 8px;" @click="removeOption(index)">
          删除
        </el-button>

        <!-- 输入框 -->
      </el-form-item>
    </div>

    <el-button @click="create()"  style="margin-top: 10px"  type="success">确认</el-button>
    <el-button @click="router.push('/index/choicepage')" style="margin-left: 20px;margin-top:10px" type="danger">返回</el-button>
  </div>


</template>

<style scoped>

</style>