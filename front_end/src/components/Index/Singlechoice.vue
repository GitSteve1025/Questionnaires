<script setup>
import router from "@/router/index.js";
import {post} from "@/net/index.js";
import {ElMessage} from "element-plus";
import {reactive,inject} from "vue";
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
    router.push("/index/show")
  })
}

const options = reactive([]);

const Newoption = () => {
  options.push({ value: '' ,isDeleted:false});
}
const removeOption=(index)=>{
  options.splice(index,1);
}

const id=inject('formatId');//接收id值
</script>

<template>
  <el-button style="margin-top: 50px">我是单选题</el-button>
  <el-button style="margin-top: 50px" @click="Newoption">添加选项</el-button>

  <div>这里显示标题</div>
  <div style="margin-top: 20px">这里显示对问卷的说明</div>


  <div>
    <el-input :maxlength="20" type="text" placeholder="请输入题目标题">
      <template #prefix>
        <el-icon><Memo /></el-icon>
      </template>
    </el-input>
  </div>

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



  <el-button style="margin-top: 100px" type="primary" @click="create()">确认</el-button>
  <el-button style="margin-left: 30px;margin-top: 100px" type="primary" @click="router.push('/index/choicepage')">返回</el-button>
</template>

<style scoped>

</style>