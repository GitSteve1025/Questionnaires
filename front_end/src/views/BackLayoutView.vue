<script setup>
import {Search} from "@element-plus/icons-vue";
import {ref} from 'vue';
const keyword = ref(''); // 使用 ref 创建响应式数据
const currentPage=ref(1);//当前页码
const pageSize=ref(10);//每页显示的条数
const selectedRows=ref([]);//选中的行

// 表格对应的测试Data
const data=ref({
  arr:[
    {id:'1',templateName:'大学生就业意向调查',templateInstruction:'调查'},
    {id:'2',templateName:'大学生就业意向调查2',templateInstruction:'调查2'},
    {id:'3',templateName:'大学生就业意向调查',templateInstruction:'调查'},
    {id:'4',templateName:'大学生就业意向调查',templateInstruction:'调查'},
    {id:'5',templateName:'大学生就业意向调查',templateInstruction:'调查'},
    {id:'6',templateName:'大学生就业意向调查',templateInstruction:'调查'},
    {id:'7',templateName:'大学生就业意向调查',templateInstruction:'调查'},
    {id:'8',templateName:'大学生就业意向调查',templateInstruction:'调查'},
    {id:'9',templateName:'大学生就业意向调查',templateInstruction:'调查'},
    {id:'10',templateName:'大学生就业意向调查',templateInstruction:'调查'},
    {id:'11',templateName:'大学生就业意向调查',templateInstruction:'调查'},
    {id:'12',templateName:'大学生就业意向调查',templateInstruction:'调查'},
    {id:'13',templateName:'大学生就业意向调查',templateInstruction:'调查'},
    {id:'14',templateName:'大学生就业意向调查',templateInstruction:'调查'},
    {id:'15',templateName:'大学生就业意向调查',templateInstruction:'调查'}
  ]
})

// 分页事件处理函数
const handleCurrentChange = (newPage) => {
  currentPage.value = newPage;
  // 这里可以调用一个API来获取分页数据
  // 例如：fetchData(newPage, pageSize.value);
};

// 删除事件处理函数,执行批量删除操作
const dels = () => {
  // 可以根据selectedRows.value中的id来删除数据
};
//删除一行数据
const del = (index, row) => {
  // 可以根据selectedRows.value中的id来删除数据
  console.log('删除第', index, '行数据', row);
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

//从表格中查找模板介绍中含有xxx的数据
const searchFromData = () =>{

}

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

          <el-table-column prop="id" label="编号" width="60" />

          <el-table-column prop="queid" label="问卷ID" width="100"/>
          <!--           问卷ID设置为链接形式，点击问卷ID后跳转到显示该问卷的问题的页面，并在该页面添加“显示回答的问卷内容信息”按钮-->

          <el-table-column prop="templateName" label="问卷标题" width="160" />
          <el-table-column prop="templateInstruction" label="问卷介绍" width="130" />
          <el-table-column prop="queState" label="问卷状态" width="100" />
          <el-table-column prop="queStartTime" label="问卷开始时间" width="120" />
          <el-table-column prop="queOverTime" label="问卷结束时间" width="120" />

          <el-table-column label="操作" width="140">
            <template #default="scope">
              <el-button size="small" type="primary" @click="edit(scope.$index,scope.row)">
                编辑
              </el-button>
              <el-button size="small" @click="del(scope.$index,scope.row)">删除</el-button>
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
import {} from "@element-plus/icons-vue";
export default {
  name: 'Layout',
};

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