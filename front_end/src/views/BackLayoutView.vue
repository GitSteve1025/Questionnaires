<script setup>
import {Search} from "@element-plus/icons-vue";
import {ref} from 'vue';
import axios from 'axios';
import {get} from "@/net/index.js";
import router from "@/router/index.js"; // 确保已经安装并导入Axios

const keyword = ref(''); // 使用 ref 创建响应式数据
const currentPage=ref(1);//当前页码
const pageSize=ref(10);//每页显示的条数
const selectedRows=ref([]);//选中的行

// 表格对应的测试Data
const data=ref({
  arr:[
{questionnaireId:'a1',title:'大学生就业意向调查',description:'调查'},
{questionnaireId:'a2',title:'大学生就业意向调查2',description:'调查2'},
{questionnaireId:'a3',title:'大学生就业意向调查',description:'调查'},
{questionnaireId:'a4',title:'大学生就业意向调查',description:'调查'},
{questionnaireId:'a5',title:'大学生就业意向调查',description:'调查'},
{questionnaireId:'a6',title:'大学生就业意向调查',description:'调查'},
{questionnaireId:'a7',title:'大学生就业意向调查',description:'调查'},
{questionnaireId:'a8',title:'大学生就业意向调查',description:'调查'},
{questionnaireId:'a9',title:'大学生就业意向调查',description:'调查'},
{questionnaireId:'a10',title:'大学生就业意向调查',description:'调查'},
{questionnaireId:'a11',title:'大学生就业意向调查',description:'调查'},
{questionnaireId:'a12',title:'大学生就业意向调查',description:'调查'},
{questionnaireId:'a13',title:'大学生就业意向调查',description:'调查'},
{questionnaireId:'a14',title:'大学生就业意向调查',description:'调查'},
{questionnaireId:'a15',title:'大学生就业意向调查',description:'调查'}
  ]// 用于存储从后端获取的问卷信息
})
//用于测试的问卷信息


// 从后端获取所有问卷信息
const fetchAllQuestionnaires = async () => {

  try {
    const response = await axios.get('/questionnaires/display-all-questionnaires');
    data.value = response.data; // 将返回的问卷数组赋值给data数组
    console.log(response.data);
  } catch (error) {
    console.error('Failed to fetch all questionnaires:', error);
  }
};

// 分页事件处理函数
const handleCurrentChange = async (newPage) => {
  currentPage.value = newPage;
  // 调用API获取所有问卷数据
  await fetchAllQuestionnaires();
};


// 删除事件处理函数,执行批量删除操作
const dels = () => {
  // 可以根据selectedRows.value中的id来删除数据
  const questionnaireIds = selectedRows.value.map(row => row.questionnaireId);
  deleteQuestionnaires(questionnaireIds);
};
// 删除一行数据
const del = (questionnaireId) => {
  const index = data.value.findIndex(row => row.questionnaireId === questionnaireId);
  if (index !== -1) {
    data.value.splice(index, 1);
  }
  // 调用API删除问卷
  deleteQuestionnaires([questionnaireId]);
};
// 删除问卷API调用
const deleteQuestionnaires = async (questionnaireIds) => {
  try {
    const response = await axios.post('/questionnaires/delete-questionnaire', { questionnaireIds });
    if (response.data.success) {
      // 成功删除后更新表格数据
      data.value = data.value.filter(row => !questionnaireIds.includes(row.questionnaireId));
    }
  } catch (error) {
    console.error('Failed to delete questionnaires:', error);
  }
};
// 编辑事件处理函数
const edit = (index, row) => {
  // 执行编辑操作
  console.log('编辑第', index, '行数据', row);
};

// 选中行事件处理函数
const selected = (val) => {
  selectedRows.value = val;
};

// 从表格中查找模板介绍中含有xxx的数据
const searchFromData = () => {
  // 实现搜索逻辑
};

// 跳转到问卷ID为questionnaireId的问题信息页面
const goToAnswerPage = (questionnaireId) => {
  this.$router.push({ name: 'QuestionInfo', params: { questionnaireId: questionnaireId } });
};
</script>

<template>

  <div class="container">
    <header>
      <h1>问卷后台管理</h1>
    </header>
    <div class="columns" style="width:100vw;height:100vh;overflow:hidden;display:flex">
      <div class="column" style="background-color: aliceblue; width:100px;">
        <div>
          <!--   侧边栏内容    -->
        </div>
      </div>
      <div class="column" style="background-color: white">

        <!--批量删除-->
        <div>
          <el-row :gutter="10" style="position: absolute;top:160px;left:144px">
            <el-button type="danger" @click="dels">批量删除</el-button>
          </el-row>
        </div>
        <!--表格组件-->
        <el-table :data="data.arr.slice((currentPage-1)*pageSize,currentPage*pageSize)" @selection-change="selected" border style="top:110px ;left:40px">
          <el-table-column type="selection" width="55"/>

<!--          <el-table-column prop="id" label="编号" width="60" />-->

          <el-table-column prop="questionnaireId" label="问卷ID" width="100">
            <!--           问卷ID设置为链接形式，点击问卷ID后跳转到显示该问卷的问题的页面，并在该页面添加“显示回答的问卷内容信息”按钮-->
            <template #default="{ row }">
              <!-- 设置点击问卷ID时的跳转逻辑 -->
              <el-link type="primary" @click="goToAnswerPage(row.questionnaireId)">{{ row.questionnaireId}}</el-link>
            </template>
          </el-table-column>

          <el-table-column prop="title" label="问卷标题" width="160" />
          <el-table-column prop="description" label="问卷描述" width="130" />
          <el-table-column prop="state" label="问卷状态" width="100" />
          <el-table-column prop="createdTime" label="问卷创建时间" width="120" />
          <el-table-column prop="startTime" label="问卷开始时间" width="120" />
          <el-table-column prop="endTime" label="问卷结束时间" width="120" />

          <el-table-column label="操作" width="140">
            <template #default="scope">
              <el-button size="small" type="primary" @click="edit(scope.$index,scope.row)">
                编辑
<!--                跳转到 首页->我的问卷->相应问卷的编辑问卷 处进行处理-->
              </el-button>
              <el-button size="small" @click="del(scope.$index,scope.row)">
                删除
<!--                跳转到 首页->我的问卷->相应问卷的删除问卷 处进行处理-->
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--分页组件-->
        <div style="position:absolute;bottom:20px; left:140px;top:700px;">
          <el-pagination layout="prev,pager,next,jumper,total"
                         :page-size="pageSize" :current-page="currentPage" :total="50"
                         @current-change="handleCurrentChange"/>
        </div>

      </div>

    </div>
  </div>
  <!--请输入关键字查询那一行-->
  <div>
    <el-row :gutter="10" style="position: absolute;top:120px;">
      <el-col :span="18" style="position: absolute;left:140px;">
        <input type="text" v-model="keyword" placeholder="请输入关键字查询" style="height:24px;">
      </el-col>
      <el-col :span="6" style="position: absolute;left:320px;">
        <el-button type="success" plain @click="searchFromData()">
          <el-icon><Search/></el-icon>
          <span>搜索</span>
        </el-button>
      </el-col>
    </el-row>
  </div>


</template>

<script>

</script>

<style scoped>
.container{
  display:flex;
  flex-direction:column;/*垂直排列*/
  min-height:100vh;/*确保容器至少与视口一样高*/
}
header{
  background-color: rgba(255, 193, 127, 0.52);
  color:#fff;
  padding:10px 20px;
  text-align:left;
}

</style>
