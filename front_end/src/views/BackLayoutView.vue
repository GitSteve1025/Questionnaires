<script setup>
import { Search } from "@element-plus/icons-vue";
import { ref, reactive } from 'vue';
import { onMounted } from 'vue';
import {get, post} from "@/net";
import {ElMessage} from "element-plus";
import router from "@/router/index.js";

const questionnaires = reactive([]);// 创建一个响应式的问卷列表
const keyword = ref(''); // 使用 ref 创建响应式数据
const currentPage=ref(1);//当前页码
const pageSize=ref(3);//每页显示的条数
const selectedRows=ref([]);//选中的行


// 在组件挂载时或者需要时调用showData方法
 onMounted(() => {
   showData();
 });

const format = ({
  questionnaireId: '',
  title: '',
  description: '',
  state: '',
  createdTime: '',
  startTime: '',
  endTime: '',
})

// const transform = (time)=>{
//   return Date(time).toString({
//     year: 'numeric',
//     month: '2-digit',
//     day: '2-digit',
//     hour: '2-digit',
//     minute: '2-digit',
//     second: '2-digit'
//   });
// }

const showData=(page = currentPage.value)=>{
  // 清除现有的问卷列表
  questionnaires.length = 0;
  get('/questionnaires/display-all', (message) => {
    for (let temp of message) {
      // const newFormat = format
      // newFormat.questionnaireId = temp.questionnaireId
      // newFormat.title = temp.title
      // newFormat.description = temp.description
      // newFormat.state = temp.state
      // newFormat.createdTime = transform(temp.createdTime)
      // if (temp.startTime !== '') {
      //   newFormat.startTime = transform(temp.startTime)
      // }
      // if (temp.endTime !== '') {
      //   newFormat.endTime = transform(temp.endTime)
      // }
      // console.log(JSON.stringify(temp.createdTime))
      questionnaires.push(temp)
    }

  })
}


// 分页事件处理函数
const handleCurrentChange =  (newPage) => {
  currentPage.value = newPage;
};

// 删除事件处理函数,执行批量删除操作
const dels = () => {
  Promise.all(selectedRows.value.map(row => post('/questionnaires/delete', {
    questionnaireId: row.questionnaireId,
  }, (message) => {
    showData(currentPage.value);
    if (selectedRows.value.length === 0) {
      ElMessage.success(message)
    }
  })))
};
// 删除一行数据
const del = (index,row) => {
  post('/questionnaires/delete', {
    questionnaireId: row.questionnaireId,
  },(message)=>{
    ElMessage.success(message)
    showData(currentPage.value)
  })
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
//使用post会报"status": 405,"error": "Method Not Allowed",
//使用get则无法将问卷Id传到后端	"status": 400,"error": "Bad Request",
  // 实现搜索逻辑
  get('/questionnaires/find', (message)=>{
    console.log(message)
    //更新表格数据，只显示搜索到的问卷的信息
  },(message)=>{

  })
};

// 跳转到问卷ID为questionnaireId的问题信息页面
const goToAnswerPage = (questionnaireId) => {
  router.push({
    path:'QuestionInfo',
    query:{
      params:questionnaireId
    }
  })
};
</script>

<template>
<!--  <div>-->
<!--    <p>{{ formattedTime }}</p>-->
<!--  </div>-->
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
        <el-table :data="questionnaires" @selection-change="selected" border style="margin-top: 100px; margin-left: 40px;">
          <el-table-column type="selection" width="55"/>

          <el-table-column prop="questionnaireId" label="问卷ID" width="100">
            <!--           问卷ID设置为链接形式，点击问卷ID后跳转到显示该问卷的问题的页面，并在该页面添加“显示回答的问卷内容信息”按钮-->
            <template #default="{ row }">
              <!-- 设置点击问卷ID时的跳转逻辑 -->
              <el-link type="primary" @click="goToAnswerPage(row.questionnaireId)" >{{ row.questionnaireId }}</el-link>
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
<!--        分页组件-->
        <div style="margin-top: 30px; margin-left: 40px;">
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
