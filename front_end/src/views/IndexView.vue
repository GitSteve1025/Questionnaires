<template>
  <div>
    <!--    {{store.auth.user.username}}-->
  </div>

  <el-menu
      :default-active="activeIndex2"
      class="el-menu-demo"
      mode="horizontal"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
      style="margin-top: 20px;height: 8vh"
  >
    <div class="welcome-title"  style="margin-left:10px;margin-top:10px;font-size: 40px;font-weight:bold;display:flex ">问卷星系统</div>
    <el-menu-item index="1" style="margin-left: 30px" @click="Gohome()">首页</el-menu-item>
    <el-sub-menu index="2">
      <template #title>我的问卷</template>
      <el-menu-item index="2-1" @click="goToCreate()" type="primary">创建问卷</el-menu-item>
      <el-menu-item index="2-2" @click="goToAdminMode()">查看问卷</el-menu-item>
    </el-sub-menu>
    <el-menu-item index="3" @click="open()">填写问卷</el-menu-item>
    <el-menu-item index="4" @click="goToPerson()" type="primary">个人资料</el-menu-item>
    <el-menu-item index="5" @click="logout()" type="danger" plain>退出登录</el-menu-item>
    <el-row class="demo-avatar demo-basic" style="margin-left: 600px">
      <el-col :span="12">
        <div class="sub-title">photo</div>
        <div class="demo-basic--circle">
          <div >
            <el-avatar shape="square"  :src="squareUrl" />
          </div>
        </div>
      </el-col>
    </el-row>
  </el-menu>

  <div>
    <router-view></router-view>
  </div>


</template>


<script lang="ts" setup>
import {get} from "@/net";
import router from "@/router";
import {useStore} from "@/stores";
import {ref, reactive, toRefs, onMounted} from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import type { Action } from 'element-plus'


const state = reactive({
  squareUrl:
      'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png',
  sizeList: ['small', '', 'large'] as const,
})

const { circleUrl, squareUrl, sizeList } = toRefs(state)

const activeIndex = ref('1')
const activeIndex2 = ref('1')

const store=useStore();

const Gohome=()=>{
  router.push('/index/homepage')
}

const mypage=()=>{
  get('',(message)=>{
    router.push('')
  })
}

const logout = () => {
  get('/api/auth/logout', (message) => {
    ElMessage.success(message)
    store.auth.user=null
    router.push('/')
  })
}

const goToAdminMode = () => {
  router.push({ name: 'backlayout' }); // 假设backLayout是您定义的路由名称
};


const goToCreate=()=>{
  router.push('/index/mypage');
};

const format=({
  username:'',
  email:''
})

const goToPerson=() => {
  router.push({path:'/index/Person',
    query: {
      params: JSON.stringify(format)
    }
  })
};

const open = () => {
  ElMessageBox.prompt('请输入你要填写的问卷ID', '填写问卷', {
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
          path: `/index/ShowAllQuestion`, // 将此路径替换为目标路径
          query: { params:id } // 如果你需要将文件ID作为查询参数传递
        });
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '返回',
        });
      });
}

const goToWrite=()=>{
  open
}

const showCurrentUser =()=>{
  get('/api/auth/currentUser', (account) => {
    format.username = account.username;
    format.email = account.email;
  })
};

onMounted(() => {
  showCurrentUser(); // 在组件挂载时调用 showCurrentUser 函数
});
</script>


<style scoped>

</style>