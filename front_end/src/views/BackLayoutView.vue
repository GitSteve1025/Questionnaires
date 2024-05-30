
<template>
  <div class="container">
    <header>
      <h1>问卷后台管理</h1>
    </header>
    <div class="columns" style="width:100vw;height:100vh;overflow:hidden;display:flex">
      <div class="column" style="background-color: aliceblue; width:100px;">
            <div>

            </div>
      </div>
      <div class="column" style="background-color: white">

      </div>
    </div>
  </div>
</template>

<script>
import {} from "@element-plus/icons-vue";
export default {
  name: 'Layout'
}
</script>

<style>
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