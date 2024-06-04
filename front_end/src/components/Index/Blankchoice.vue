<script setup>

import router from "@/router/index.js";
import {reactive} from "vue";
import {post} from "@/net/index.js";
import {ElMessage} from "element-plus";
import {Memo, Document, User} from "@element-plus/icons-vue";

const create=()=>{
  post('/questionnaires/create', {
    title:format.title,
    description:format.description,
  }, (message) => {
    ElMessage.success(message)
    router.push("/index")
  })
}



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
</script>

<template>
  <el-button style="margin-top: 50px">我是填空题</el-button>

  <div>这里显示标题</div>
  <div style="margin-top: 20px">这里显示对问卷的说明</div>
  <div>

    <el-form-item prop="">
      <el-input  :maxlength="20" type="text" placeholder="标题"> <!--记得加v-model="format.title"-->
        <template #prefix>
          <el-icon><Memo /></el-icon>
        </template>
      </el-input>
    </el-form-item>

    <el-form-item prop="">
      <el-input  type="text" placeholder="描述" style="margin-top: 10px"><!--记得加v-model="format.description"-->
        <template #prefix>
          <el-icon><Document /></el-icon>
        </template>

      </el-input>
    </el-form-item>

    <el-button @click="create()"  style="margin-top: 10px"  type="info">确认</el-button>
    <el-button @click="router.push('/index/mypage')" style="margin-left: 20px;margin-top:10px" type="danger">返回</el-button>
  </div>


</template>

<style scoped>

</style>