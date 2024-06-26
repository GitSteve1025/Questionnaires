<script setup>
import router from "@/router/index.js";
import {useRoute} from "vue-router";
import {get} from "@/net/index.js";
import {ElMessage} from "element-plus";

const route=useRoute()
const id=JSON.parse(route.query.params);

const display=()=>{
  get('/questionnaires/find',(message)=>{
    ElMessage.success('展示问卷')
    format.id = JSON.parse(JSON.stringify(message)).questionnaireId;
  },()=>{

  })

}

</script>

<template>

  <div>Hello nice day</div>


</template>

<style scoped>

</style>