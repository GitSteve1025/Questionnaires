<template>
  <div>
    <h2>信息管理</h2>
    <nav>
      <router-link to="/backlayout/InfoManage/QuestionnaireInfo">问卷信息</router-link>
      <router-link to="/backlayout/InfoManage/QuestionInfo">问题信息</router-link>
      <router-link to="/backlayout/InfoManage/AnswerInfo">回答信息</router-link>
    </nav>
    <router-view></router-view>
  </div>
</template>