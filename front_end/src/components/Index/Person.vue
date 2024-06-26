<template>
  <div class="person-info">
    <div class="user-info-box">
      <label for="username">用户名:</label>
      <input type="text" id="username" v-model="info.username" readonly>
    </div>
    <div class="user-info-box">
      <label for="email">邮箱:</label>
      <input type="text" id="email" v-model="info.email" readonly>
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const info = JSON.parse(route.query.params);

</script>

<style scoped>
.person-info {
  background-color: #f0f0f0;
  padding: 200px;
  border-radius: 5px;
  position: fixed;
  bottom: 200px;
  right: 400px;
  z-index: 1000;
}

.user-info-box {
  margin-bottom: 20px; /* 添加一些空白 */
}

label {
  display: inline-block; /* 将 label 元素设置为内联块级元素 */
  width: 100px; /* 设置宽度，根据实际需要调整 */
}

input {
  width: 200px; /* 设置宽度，根据实际需要调整 */
  margin-left: 10px; /* 添加一些空白 */
}
</style>
