<script setup>

import {Lock, User} from "@element-plus/icons-vue";
</script>

<template>
  <div style="width:100vw;height:100vh;overflow:hidden;display:flex">

    <div style="flex: 1">
      <!--主页贴图-->
      <el-image style="width:100%;height:100%" fit="cover"
                src="https://ts1.cn.mm.bing.net/th/id/R-C.d07816d313cad5d2b53b30192443c4c5?rik=SUozltQgAs8%2bNQ&riu=http%3a%2f%2fn.sinaimg.cn%2fsinacn10119%2f600%2fw1920h1080%2f20190325%2f6449-hutwezf3366892.jpg&ehk=DH2hT8Ey9e3gfn%2fUBKrQFjRb3LPXUs9sEIOq4LRDZfQ%3d&risl=&pid=ImgRaw&r=0">
      </el-image>
    <div class="welcome-title"><!--左侧贴图进行装饰-->
      <div style="font-size:30px;font-weight: bold">一款智能的问卷星系统</div>
      <div style="margin-top:20px">我们能满足你所有的需求~</div>
    </div>

    </div>
    <div style="width:400px;margin-left:10px;text-align:center;background-color:white;z-index:1">
      <router-view v-slot="{Component}">
        <transition name="el-fade-in-linear">
          <component :is="Component"/>
        </transition>
      </router-view>

    </div>


  </div>
</template>

<style scoped>
.welcome-title{
  position: absolute;
  top:30px;
  left:30px;
  color:white;
  text-shadow:0 0 10px black;
}
</style>