<template>
  <div>
    <h2>用户管理</h2>
    <nav>
      <router-link to="/backlayout/UserManage/ManagerInfo">管理员信息</router-link>
      <router-link to="/backlayout/UserManage/Userinfo">用户信息</router-link>
    </nav>
    <router-view></router-view>
  </div>
</template>
<script setup lang="ts">
</script>