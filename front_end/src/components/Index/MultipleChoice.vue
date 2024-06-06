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
  title:'',
  necessary:[
    {
      required:false,
    }
  ],
  minSelected:'',
  maxSelected:'',
})

const create=()=>{
  for (let content of options.values()) {
    // console.log(content)
    choice.content.push(content.value)
  }

  post('/question/create-MultipleChoiceQuestion', {
    questionnaireId: id,
    title:format.title,
    necessary:format.necessary,
    content:choice.content,
    minSelected:format.minSelected,
    maxSelected:format.maxSelected,
  }, (message) => {
    ElMessage.success(message)
    router.push("/index/choicepage")
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
  <el-button style="margin-top: 50px">我是多选题</el-button>
  <el-button style="margin-top: 50px" @click="Newoption">添加选项</el-button>

  <el-input v-model="format.title" placeholder="请输入标题" style="margin-top: 5px"></el-input>
  <el-checkbox v-model="format.necessary" label="是否必答" size="large" /> <!--v-model="form.necessary"传入是否必答-->

  <el-input v-model="format.minSelected" placeholder="请输入至少选择几个选项"></el-input>
  <el-input v-model="format.maxSelected" placeholder="请输入最多选择几个选项" style="margin-top: 10px"></el-input>


  <div v-for="(option, index) in options" :key="index" style="margin-top: 20px;display:flex;align-items:center">
    <el-form-item style="display: flex;" prop="">
      <!-- 删除按钮 -->
      <el-button type="danger" size="small" style="margin-right: 8px;" @click="removeOption(index)">
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

  <el-button style="margin-top: 100px" type="success" @click="create()" >确认</el-button>
  <el-button style="margin-left: 30px;margin-top: 100px" type="primary" @click="router.push('/index/choicepage')">返回</el-button>

</template>

<style scoped>

</style>