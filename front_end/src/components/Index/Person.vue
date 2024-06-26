<script lang="ts" setup>
import { ref } from 'vue'
import { reactive } from 'vue';
import { useRoute } from 'vue-router';
import {RouteLocationNormalized} from "vue-router";

const route = useRoute();
const params=route.query.params as string;

const info=reactive(JSON.parse(params));

const radiusGroup = ref([
  {
    name: 'Large Radius',
    type: 'base',
  },
])

</script>

<template>
  <el-row :gutter="12" class="demo-radius">
    <el-col
        v-for="(radius, i) in radiusGroup"
        :key="i"
        :span="6"
        :xs="{ span: 12 }"
    >
      <div
          class="radius"
          :style="{
          borderRadius: radius.type
            ? `var(--el-border-radius-${radius.type})`
            : '',
        }"
      >
        <label for="username" class="custom-label username-label">用户名:</label>
        <input type="text" id="username" v-model="info.username" readonly class="custom-input">
        <br>
        <label for="email" class="custom-label">邮箱:</label>
        <input type="text" id="email" v-model="info.email" readonly class="custom-input">
      </div>
    </el-col>
  </el-row>
</template>

<style scoped>
.demo-radius .radius {
  height: 500px;
  width: 200%;
  border: 3px solid var(--el-border-color);
  border-radius: 0;
  margin-top: 150px;
  margin-left: 380px;
}

.custom-label {
  font-family: 'Arial', sans-serif; /* 更改字体 */
  font-size: 30px; /* 更改字体大小 */
  margin-right: 20px; /* 添加标签和输入框之间的间距 */
}

.username-label {
  margin-top: 50px;
}

.custom-input {
  font-family: 'Arial', sans-serif; /* 更改字体 */
  font-size: 30px; /* 更改字体大小 */
  margin-bottom: 151px; /* 添加输入框之间的间距 */
}
</style>

