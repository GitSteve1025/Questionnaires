<script setup>
import router from "@/router/index.js";
import {post} from "@/net/index.js";
import {ElMessage} from "element-plus";
import {useRoute} from "vue-router";


const route=useRoute()
const id=route.query.params;

const Gosingle=()=>{
  console.log(id)
  router.push({
    path:'/index/singlechoice',
    query: {
      params: id
    }
  })
}

const Gomultiple=()=>{
  router.push({
    path:'/index/singlechoice',
    query: {
      params: JSON.stringify(format.id)
    }
  })
}

const Goblank=()=>{
  router.push('/index/blankchoice')
}

</script>

<template>
  <div style="margin-top: 40px">
    <el-button style="margin-left: 30px" @click="Gosingle">
      单选题
    </el-button>
    <el-button style="margin-left: 30px" @click="Gomultiple">
      多选题
    </el-button>

    <el-button style="margin-left: 30px" @click="Goblank">
      填空题
    </el-button>

  </div>

  <el-button style="margin-left: 100px;margin-top: 30px" @click="router.push('/index/show')">
    返回
  </el-button>


  <div>

  </div>

</template>

<style scoped>

</style>