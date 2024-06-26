<script setup>
import router from "@/router/index.js";
//import {ElMessage} from "element-plus";
import {useRoute} from "vue-router";
import {onMounted, reactive, ref} from "vue";
import {get, post} from "@/net/index.js";
import {Search} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";
const selectedRows=ref([]);//选中的行

const route=useRoute()
let Id=JSON.parse(route.query.params);
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
  post('/questionnaires/get',{
    questionnaireId:Id
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

const Questionnaire = reactive({
  questionnaireId:0,
  title:'',
  description:'',
  choiceQuestions:[],
  blankQuestions:[],
  state:null,
  createdTime:null,
  starTime:null,
  endTime:null
})

const commit=()=>{
  Questionnaire.questionnaireId = Id;
  for (let temp of questions) {
    let val = temp;
    console.log(val);
    if (val.category === "BLANK_QUESTION") {
      if (val.blank.content.length > 0) {
        val.state = true;
        val.blank.state = true;
      }
      Questionnaire.blankQuestions.push(val);
    } else {
      if (val.selectedCount > 0) {
        val.state = true;
      }
      Questionnaire.choiceQuestions.push(val);
    }
  }
  post('/questionnaires/fill',{
    questionnaire: JSON.stringify({
      questionnaireId: Questionnaire.questionnaireId,
      choiceQuestions: Questionnaire.choiceQuestions,
      blankQuestions: Questionnaire.blankQuestions
    })
  },(message)=>{
    ElMessage.success('提交成功')
    router.push('/index')
  })
  //执行向后端的提交操作
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

const updateSelectedCount = (question, choice) => {
  if (choice.state) {
    question.selectedCount++;
  } else {
    question.selectedCount--;
  }
};
</script>

<template>
  <div>
    <h1>问卷问题信息</h1>

    <!--批量删除-->
    <div>
      <el-row :gutter="10" style="position: absolute;top:100px;">
        <el-col :span="6" style="position: absolute;left:150px;">
          <el-button style="margin-top: 265px" type="success" @click="router.push('/index/choicepage')">返回</el-button>
        </el-col>
      </el-row>
    </div>
    <!--表格组件-->
    <el-table :data="questions"  border style="margin-top: 90px; margin-left: 40px; width:885px" >
      <el-table-column prop="sequenceId" label="问题顺序" width="160" />
      <el-table-column prop="title" label="题目" width="130"/>
      <el-table-column prop="category" label="问题种类" width="100" />
      <el-table-column label="问题选项"  >
        <template #default="{ row }">
          <div>
            是否必填: {{ row.necessary ? '是' : '否' }}
          </div>

          <div v-if="row.category === 'SINGLE_CHOICE_QUESTION'">
            <div v-for="(choice, index) in row.choices" :key="index">
              {{ choice.content }}
              <el-checkbox v-model="choice.state" @change="updateSelectedCount(row, choice)"/>
            </div>
          </div>

          <div v-else-if="row.category === 'MULTIPLE_CHOICE_QUESTION'">
            <div>
              至少填：{{ row.minSelected }}
            </div>

            <div>
              最多填：{{row.maxSelected}}
            </div>

            <div v-for="(choice, index) in row.choices" :key="index">
              {{ choice.content }}
              <el-checkbox v-model="choice.state" @change="updateSelectedCount(row, choice)" />
            </div>
            <div>
              已选：{{ row.selectedCount }}
            </div>
          </div>

          <div v-else-if="row.category === 'BLANK_QUESTION'">
            <el-input v-model="row.blank.content" placeholder="请输入您的答案"></el-input>
          </div>

        </template>
      </el-table-column>

    </el-table>

<el-button style="margin-top: 20px;margin-left: 40px" type="primary" @click="commit()">确认提交</el-button>

  </div>
</template>

<style scoped>
/* 样式根据需要添加 */
</style>
