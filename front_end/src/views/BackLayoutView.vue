<script setup>
import { Search } from "@element-plus/icons-vue";
import { ref, reactive } from 'vue';
import { onMounted } from 'vue';
import {get, post} from "@/net";
import {ElMessageBox,ElMessage} from "element-plus";
import router from "@/router/index.js";

const questionnaires = reactive([]);// 创建一个响应式的问卷列表
const keyword = ref(); // 使用 ref 创建响应式数据
const selectedRows=ref([]);//选中的行
const startTime = ref(''); // 用于存储开始时间
const endTime = ref(''); // 用于存储截止时间

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

const showData=()=>{
  // 清除现有的问卷列表
  questionnaires.length = 0;
  get('/questionnaires/display-all', (message) => {
    for (let temp of message) {
      questionnaires.push(temp)
    }
  })
}
// 删除事件处理函数,执行批量删除操作
const dels = () => {
  Promise.all(selectedRows.value.map(row => post('/questionnaires/delete', {
    questionnaireId: row.questionnaireId,
  }, (message) => {
    showData();
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
    showData()
  })
};

const check = () => {
  ElMessageBox.prompt('请输入你要查看的问卷ID', '查看问卷填写情况', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    inputErrorMessage: 'ID',
  })
      .then(({ value }) => {
        ElMessage({
          type: 'success',
          message: `页面检索成功`
        });
        let id=value;
        // 使用 router.push 跳转页面
        router.push({
          path: `AnswerInfo`, // 将此路径替换为目标路径
          query: { params:id } // 如果你需要将文件ID作为查询参数传递
        });
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '返回',
        });
      });
};
const shareId = (index, row) => {
  ElMessageBox.alert(row.questionnaireId,'问卷ID', {
    // if you want to disable its autofocus
    // autofocus: false,
    confirmButtonText: 'OK',

  })
};
// 编辑事件处理函数
const edit = (index, row) => {
  goToAnswerPage(row.questionnaireId)
};
//发布该问卷
const publish = (index, row) => {
  // 弹出开始时间消息框
  ElMessageBox.prompt('请输入问卷的开始发布时间', '开始时间', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    inputPattern: /(\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2})/,
    inputErrorMessage: '时间格式不正确，请使用YYYY-MM-DD HH:MM:SS的格式'
  }).then(({ value }) => {
    startTime.value = value;
    // 弹出截止时间消息框
    ElMessageBox.prompt('请输入问卷的截止时间', '截止时间', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      inputPattern: /(\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2})/,
      inputErrorMessage: '时间格式不正确，请使用YYYY-MM-DD HH:MM:SS的格式'
    }).then(({ value }) => {
      endTime.value = value;
      // 发送数据到后端
      post('/questionnaires/publish', {
        questionnaireId: row.questionnaireId,
        startTime: startTime.value,
        endTime: endTime.value,
      }, (message) => {
        ElMessage.success(message);
        showData();
      });
    }).catch(() => {
      ElMessage.error('时间输入不正确，请重新输入');
    });
  }).catch(() => {
    ElMessage.info('取消开始时间输入');
  });
};
const returnl =()=>{
  router.push('/index')
}
// 选中行事件处理函数
const selected = (val) => {
  selectedRows.value = val;
};

// 从表格中查找模板介绍中含有xxx的数据
const searchFromData = () => {
//使用post会报"status": 405,"error": "Method Not Allowed",
//使用get则无法将问卷Id传到后端	"status": 400,"error": "Bad Request",
  // 实现搜索逻辑
  post('/questionnaires/find', {
    questionnaireId : keyword,
  },(message)=>{
    console.log(message)
    //更新表格数据，只显示搜索到的问卷的信息
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
    <div class="columns" style="width:100vw;height:100vh;display:flex">

      <div class="column" style="background-color: white">
        <!--批量删除-->
        <div>
          <el-row :gutter="10" style="position: absolute;top:160px;left:65px">
            <el-col :span="18" style="position: absolute;left:-5px;">
              <el-button type="danger" @click="dels">批量删除</el-button>
            </el-col>
            <el-col :span="6" style="position: absolute;left:130px;">
              <el-button type="success" @click="returnl">返回</el-button>
            </el-col>
            <el-col :span="6" style="position: absolute;left:240px;">
              <el-button type="success" @click="check">查看问卷填写情况</el-button>
            </el-col>
          </el-row>
        </div>
        <!--表格组件-->
        <el-table :data="questionnaires" @selection-change="selected" border style=" overflow-y: auto; margin-top: 100px; margin-left: 60px;width:1065px">
          <el-table-column type="selection" width="55"/>

          <el-table-column prop="title" label="问卷标题" width="160" />
          <el-table-column prop="description" label="问卷描述" width="130" />
          <el-table-column prop="state" label="问卷状态" width="100" />
          <el-table-column prop="createdTime" label="问卷创建时间" width="120" />
          <el-table-column prop="startTime" label="问卷开始时间" width="120" />
          <el-table-column prop="endTime" label="问卷结束时间" width="120" />

          <el-table-column label="操作" width="260">
            <template #default="scope">
              <el-button size="small" type="primary" @click="edit(scope.$index,scope.row)">
                编辑
<!--                跳转到 首页->我的问卷->相应问卷的编辑问卷 处进行处理-->
              </el-button>
              <el-button size="small" type="primary" @click="del(scope.$index,scope.row)">
                删除
<!--                跳转到 首页->我的问卷->相应问卷的删除问卷 处进行处理-->
              </el-button>
              <el-button size="small" type="primary" @click="publish(scope.$index,scope.row)">
                发布
              </el-button>
              <el-button size="small" type="primary" @click="shareId(scope.$index,scope.row)">
                分享
              </el-button>
            </template>
          </el-table-column>
        </el-table>


      </div>

    </div>
  </div>
  <!--请输入关键字查询那一行-->
  <div>
    <el-row :gutter="10" style="position: absolute;top:120px;">
      <el-col :span="18" style="position: absolute;left:60px;">
        <input type="text" v-model="keyword" placeholder="请输入关键字查询" style="height:24px;">
      </el-col>
      <el-col :span="6" style="position: absolute;left:250px;">
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
  position:relative;
}
header{
  background-color: rgba(255, 193, 127, 0.52);
  color:#fff;
  padding:10px 20px;
  text-align:left;
}

</style>
