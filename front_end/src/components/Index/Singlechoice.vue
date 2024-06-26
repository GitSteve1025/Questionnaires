<script setup>
import router from "@/router/index.js";
import {post} from "@/net/index.js";
import {ElMessage} from "element-plus";
import {reactive} from "vue";
import {Memo} from "@element-plus/icons-vue";
import {useRoute} from "vue-router";

const route=useRoute()
const id=JSON.parse(route.query.params);

const choice=reactive({
  content: []
})

const format =reactive({
  title:[
    ''
  ],
  necessary:[
    {
      required:false,
    }
  ],
})

const create=()=>{
  for (let content of options.values()) {
    console.log(content)
    choice.content.push(content.value)
  }
  post('/question/create-SingleChoiceQuestion', {
    questionnaireId: id,
    title:format.title,
    necessary:format.necessary,
    content:choice.content
  }, (message) => {
    ElMessage.success(message)
    router.push({
      path: "/index/choicepage",
      query: {
        params: id
      }
    })
  })
}

const cancel=()=>{
  router.push({
    path:"/index/choicepage",
    query: {
      params: id
    }
  })
}

const options = reactive([]);

const Newoption = () => {
  options.push({ value: ''});
}

const removeOption=(index)=>{
  options.splice(index,1);
}

</script>

<template>
  <el-button style="margin-top: 50px">我是单选题</el-button>
  <el-button style="margin-top: 50px" @click="Newoption">添加选项</el-button>



  <div>

    <el-input v-model="format.title" :maxlength="20" type="text" placeholder="请输入题目标题">
      <template #prefix>
        <el-icon><Memo /></el-icon>
      </template>
    </el-input>
    <el-checkbox style="margin-left: 10px" v-model="format.necessary" label="是否必答" size="large" span="20" /> <!--v-model="form.necessary"传入是否必答-->
  </div>



  <div v-for="(option, index) in options" :key="index" style="margin-top: 20px;display:flex;align-items:center">
    <el-form-item style="display: flex;" prop="">
      <!-- 删除按钮 -->
      <el-button type="danger" size="small" style="margin-left: 8px;" @click="removeOption(index)">
        删除
      </el-button>
      <!-- 输入框 -->
      <el-input v-model="option.value" :maxlength="20" type="text" placeholder="请输入选项">
        <template #prefix>
          <el-icon><Memo /></el-icon>
        </template>

      </el-input>
    </el-form-item>
  </div>



  <el-button style="margin-top: 100px" type="success" @click="create()">确认</el-button>
  <el-button style="margin-left: 30px;margin-top: 100px" type="info" @click="cancel()">返回</el-button>
</template>

<style scoped>

</style>