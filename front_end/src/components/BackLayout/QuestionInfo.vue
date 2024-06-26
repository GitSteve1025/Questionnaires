<script setup>
import router from "@/router/index.js";
//import {ElMessage} from "element-plus";
import {useRoute} from "vue-router";
import {onMounted, reactive, ref} from "vue";
import {get, post} from "@/net/index.js";
import {Search} from "@element-plus/icons-vue";
const selectedRows=ref([]);//选中的行

const route=useRoute()
const questionnaireId=JSON.parse(route.query.params);
const questions =reactive([]);
// const choiceQuestions = reactive([]);// 创建一个响应式的问卷选择问题列表
// const blankQuestions =reactive([]);//创建一个响应式的问卷填空问题列表
// const activeNames = ref([choiceQuestions, blankQuestions]); // 默认展开的选择题和填空题

// const question = ({
//   sequenceId: '',
//   choiceQuestion: '',
//   blankQuestion: '',
// })

const showData=()=>{
  // blankQuestions.length = 0;
  // choiceQuestions.length=0;
  post('/questionnaires/find',{
    questionnaireId:questionnaireId
  }, (message)=> {
    console.log(message)
    for(let temp of message.choiceQuestions){
      let id = temp.sequenceId;
      questions.push(temp); // 将后端返回的问题数组存储到数组中
      console.log(temp)
    }
    for(let temp of message.blankQuestions){
      let id = temp.sequenceId;
      questions.push(temp);
      console.log(temp)
    }
    questions.sort((a, b)=>a.sequenceId - b.sequenceId);
  },()=>{})

}

// 在组件挂载时获取问卷数据
onMounted(() => {
  showData();
});

const format = ({
  questionId: '',
  title: '',

})

// 删除事件处理函数,执行批量删除操作
const dels = () => {

};
// 删除一行数据
const del = (index,row) => {

};

// 编辑事件处理函数
const edit = (index, row) => {

};

// 选中行事件处理函数
const selected = (val) => {
  selectedRows.value = val;
};
</script>

<template>
  <div>
    <h1>问卷问题信息</h1>

    <!--批量删除-->
    <div>
      <el-row :gutter="10" style="position: absolute;top:100px;">
        <el-col :span="18" style="position: absolute;left:40px;">
          <el-button type="danger">批量删除</el-button>
        </el-col>
        <el-col :span="6" style="position: absolute;left:150px;">
          <el-button type="success" @click="router.push('/backlayout')">返回</el-button>
        </el-col>
      </el-row>
    </div>
    <!--表格组件-->
    <el-table :data="questions" @selection-change="selected" border style="margin-top: 90px; margin-left: 40px; width:885px" >
      <el-table-column type="selection" width="55"/>
      <el-table-column prop="sequenceId" label="问题顺序" width="160" />
      <el-table-column prop="title" label="题目" width="130" />
      <el-table-column prop="category" label="问题种类" width="100" />
      <el-table-column label="问题选项" width="300" >
        <template #default="{ row }">
          <div v-for="(choice, index) in row.choices" :key="index">
            {{ choice.content }}
          </div>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="140">
        <template #default="scope">
          <el-button size="small" type="primary" @click="edit(scope.$index,scope.row)">
            编辑
          </el-button>
          <el-button size="small" @click="del(scope.$index,scope.row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>



  </div>
</template>

<style scoped>
/* 样式根据需要添加 */
</style>
