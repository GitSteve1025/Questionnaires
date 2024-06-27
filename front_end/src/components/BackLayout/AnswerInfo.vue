<script setup>

import {useRoute} from "vue-router";
import {onMounted, reactive} from "vue";
import {post} from "@/net/index.js";
import router from "@/router/index.js";

const route=useRoute()
let questionnaireId=JSON.parse(route.query.params);
const questions =reactive([]);//存储问卷的所有问题信息
const fillAccount=reactive([]);//存储 填写问卷用户的账号和填写时间

//获取问题Id为questionId的每个选项被选择的次数
const getChoiceCounts = (questionId) => {
  return new Promise((resolve, reject) => {
    post('/info/choice', { questionId }, (message) => {
      resolve(message);
    }, () => {});
  });
};
//获取问题Id为questionId的填空题的填写信息
const getBlankAnswers = (questionId) => {
  return new Promise((resolve, reject) => {
    post('/info/blank', { questionId }, (message) => {
      resolve(message);
    }, () => {});
  });
};
const showQuestionData=()=>{
  questions.length=0;
  post('/questionnaires/find',{
    questionnaireId:questionnaireId
  }, (message)=> {
    console.log(message)
    for(let temp of message.choiceQuestions){
      let id = temp.sequenceId;
      questions.push(temp); // 将后端返回的问题数组存储到数组中
      promises.push(getChoiceCounts(temp.questionId).then((counts) => {
        temp.choiceCounts = counts;//choiceCounts为后端定义的每个题被选择的次数
      }));
    }
    for(let temp of message.blankQuestions){
      let id = temp.sequenceId;
      questions.push(temp);
      promises.push(getBlankAnswers(temp.questionId).then((answers) => {
        temp.answers = answers;
      }));
    }
    questions.sort((a, b)=>a.sequenceId - b.sequenceId);
  },()=>{})
}

const showAccountData =() =>{
  post('/info/questionnaire',{
     questionnaireId:questionnaireId
  },(message)=>{
    for(let temp of message){
      fillAccount.push(temp)
    }
   },()=>{})
}

// 在组件挂载时获取问卷的所有问题数据
onMounted(() => {
  showQuestionData();
  showAccountData();
});

</script>

<template>
  <div>
    <h1>问卷填写信息</h1>

    <!--批量删除-->
    <div>
      <el-row :gutter="10" style="position: absolute;top:100px;">
        <el-col :span="6" style="position: absolute;left:80px;">
          <el-button type="success" @click="router.push('/backlayout')">返回</el-button>
        </el-col>
      </el-row>
    </div>

    <!--表格组件-->
    <el-row :gutter="10">

      <el-col :span="80" style="height: 400px; overflow-y: auto;">
        <el-table :data="questions" border style="margin-top: 90px; margin-left: 60px; width:885px" >
          <!-- 问题填写信息表格 -->
          <el-table-column prop="sequenceId" label="问题顺序" width="160" />
          <el-table-column prop="title" label="题目" width="130" />
          <el-table-column prop="category" label="问题种类" width="100" />
          <el-table-column label="问题选项" width="150" >
            <template #default="{ row }">
              <div v-for="(choice, index) in row.choices" :key="index">
                {{ choice.content }}
              </div>
            </template>
          </el-table-column>
          <el-table-column label="问题选项被选择的次数" width="100" >
            <template #default="{ row }">
              <div v-for="(count, index) in row.answers" :key="index">
                {{ count }}
              </div>
            </template>
          </el-table-column>
          <el-table-column label="填空题填写内容" >
            <template #default="{ row }">
              <div v-for="(content, index) in row.choiceCounts" :key="index">
                {{ content }}
              </div>
            </template>
          </el-table-column>


        </el-table>
      </el-col>

      <el-col :span="6" style="height: 400px; overflow-y: auto;">
        <el-table :data="fillAccount" border style="margin-top: 90px; margin-left: 0px; width:350px" >
          <!-- 填写问卷用户信息表格 -->
          <el-table-column prop="Account" label="填写问卷用户的账号"/>
          <el-table-column prop="Date" label="用户填写时间"/>
        </el-table>
      </el-col>

    </el-row>

  </div>
</template>

<style scoped>

</style>