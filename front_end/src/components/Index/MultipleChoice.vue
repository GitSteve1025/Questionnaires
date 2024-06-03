<script setup>
import router from "@/router/index.js";
import {post} from "@/net/index.js";
import {ElMessage} from "element-plus";
import {reactive} from "vue";
import {Memo} from "@element-plus/icons-vue";

const format =reactive({
  title:[
    {
      required:true,message:'请输入标题',
      min:1
    }
  ],
  description:[
    {
      required:false,message:'请输入描述：',
    }
  ],
})

const create=()=>{
  post('/questionnaires/create', {
    title:format.title,
    description:format.description,
  }, (message) => {
    ElMessage.success(message)
    router.push("/index")
  })
}
</script>

<template>
  <el-button style="margin-top: 50px">我是多选题</el-button>

  <div>这里显示标题</div>
  <div style="margin-top: 20px">这里显示对问卷的说明</div>

  <el-form-item prop="">
    <el-input  :maxlength="20" type="text" placeholder="请输入选项">
      <template #prefix>
        <el-icon><Memo /></el-icon>
      </template>
    </el-input>
  </el-form-item>

  <el-button>添加选项</el-button>

  <el-button style="margin-top: 100px" type="primary" @click="create()">确认</el-button>
  <el-button style="margin-left: 30px;margin-top: 100px" type="primary" @click="router.push('/index/mypage')">返回</el-button>

</template>

<style scoped>

</style>