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
    router.push("/index/show")
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

const options = reactive([]);

const Newoption = () => {
  options.push({ value: '' });
}
</script>

<template>
  <el-button style="margin-top: 50px">我是填空题</el-button>
  <el-button style="margin-top: 50px" @click="Newoption">添加问题</el-button>

  <div>这里显示标题</div>
  <div style="margin-top: 20px">这里显示对问卷的说明</div>
  <div>

    <div v-for="(option, index) in options" :key="index" style="margin-top: 20px;display:flex;align-items:center">
      <el-form-item style="display: flex;" prop="">
        <!-- 删除按钮 -->
        <el-button type="danger" size="small" style="margin-right: 8px;" @click="removeOption(index)">
          删除
        </el-button>
        <el-checkbox  label="是否必答" size="large" /> <!--v-model="form.necessary"传入是否必答-->
        <!-- 输入框 -->
        <el-input v-model="option.value" :maxlength="20" type="text" placeholder="请输入选项">
          <template #prefix>
            <el-icon><Memo /></el-icon>
          </template>

        </el-input>
      </el-form-item>
    </div>

    <el-form-item prop="">
      <el-input  type="text" placeholder="描述" style="margin-top: 10px"><!--记得加v-model="format.description"-->
        <template #prefix>
          <el-icon><Document /></el-icon>
        </template>

      </el-input>
    </el-form-item>

    <el-button @click="create()"  style="margin-top: 10px"  type="info">确认</el-button>
    <el-button @click="router.push('/index/choicepage')" style="margin-left: 20px;margin-top:10px" type="danger">返回</el-button>
  </div>


</template>

<style scoped>

</style>