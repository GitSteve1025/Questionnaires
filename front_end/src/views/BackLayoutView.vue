<!--1、要确保 response.data 是一个 RestBean 对象，其中包含一个 questionnaires 属性，你需要确保从后端返回的数据格式与前端代码中预期的格式一致。-->
<!--2、因为没有登录，所以没有访问资源的权限的问题-->
<script setup>
import {Search} from "@element-plus/icons-vue";
import {ref} from 'vue';
import axios from 'axios';
import {onMounted } from 'vue';


const questionnaires = ref([]);// 创建一个响应式的问卷列表
const keyword = ref(''); // 使用 ref 创建响应式数据
const currentPage=ref(1);//当前页码
const pageSize=ref(10);//每页显示的条数
const selectedRows=ref([]);//选中的行


// 在组件挂载时或者需要时调用fetchQuestionnaires方法
onMounted(() => {
  fetchAllQuestionnaires();
});
const fetchAllQuestionnaires = async () => {
  try {
    console.log('Sending request to server...'); // 添加请求前的日志
    const response = await axios.get('/questionnaires/display-all-questionnaires');
    console.log('Response received:', response); // 添加响应后的日志
    questionnaires.value= response.data.questionnaires;
    console.log('Data assigned:', response.data.questionnaires); // 添加数据赋值后的日志
    } catch (error) {
    console.error('Failed to fetch all questionnaires:', error);
  }
};


// 分页事件处理函数
const handleCurrentChange = async (newPage) => {

  currentPage.value = newPage;
  // 调用API获取所有问卷数据
  //await fetchAllQuestionnaires();
};


// 删除事件处理函数,执行批量删除操作
const dels = () => {

};
// 删除一行数据
const del = (questionnaireId) => {

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
        <el-table :data="questionnaires" @selection-change="selected" border style="top:110px ;left:40px">
          <el-table-column type="selection" width="55"/>

<!--          <el-table-column prop="id" label="编号" width="60" />-->

          <el-table-column prop="questionnaireId" label="问卷ID" width="100"/>
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
