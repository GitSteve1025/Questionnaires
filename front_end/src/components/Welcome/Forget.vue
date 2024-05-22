<script setup>

import {Bell, Lock, Message} from "@element-plus/icons-vue";
import {reactive,ref} from "vue";
import router from "@/router/index.js";
import {post} from "@/net/index.js";
import {ElMessage} from "element-plus";

const form =reactive({
  email:'',
  code:'',
  //password:''
})
const active=ref(0)

const validatePassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value === form.password) {
    callback()
  } else {
    callback(new Error("两次输入的密码不一致"))
  }
}

const formRef = ref()
const isEmailValid = ref(false)
const coldTime = ref(0)//是否已经点击发送验证码

const onValidate = (prop, isValid) => {
  if(prop === 'email')
    isEmailValid.value = isValid
}
const validateEmail = () => {
  coldTime.value = 60
  post('/api/auth/valid-register-email', {
    email: form.email
  }, (message) => {
    ElMessage.success(message)
    setInterval(() => coldTime.value--, 1000)
  }, (message) => {
    ElMessage.warning(message)
    coldTime.value = 0
  })
}


const rules={
  email: [
    { required: true, message: '请输入邮件地址', trigger: 'blur' },
    {type: 'email', message: '请输入合法的电子邮件地址', trigger: ['blur', 'change']}
  ],
  code: [
    { required: true, message: '请输入获取的验证码', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 3, max: 16, message: '密码的长度必须在3-16个字符之间', trigger: ['blur', 'change'] }
  ],
  password_repeat: [
    { validator: validatePassword, trigger: ['blur', 'change'] },
  ],
}
</script>

<template>
 <div style="margin:20px">
   <el-steps :active="active" finish-status="success" align-center>
     <el-step title="验证电子邮件"></el-step>
     <el-step title="重新设定密码"></el-step>
   </el-steps>
 </div>

 <transition name="el-fade-in-linear" mode="out-in">
   <div style ="text-align:center;margin:0 20px;height:100%" v-if="active===0">
     <div style=" margin-top:80px" >
       <div style="font-size:25px;font-weight:bold"> 重置密码</div>
       <div style="font-size: 14px;color:gray">请输入需要重置密码的电子邮件地址</div>
     </div>
     <div style="margin-top:50px">
       <el-form :model="form" :rules="rules" @validate="onValidate" ref="formRef">
         <el-form-item prop="email">
           <el-input v-model="form.email" type= "email" placeholder="电子邮箱" >
             <template #prefix>
               <el-icon><Message /></el-icon>
             </template>
           </el-input>
         </el-form-item>

         <el-form-item prop="code">
           <el-row :gutter="10" style="width:100%">
             <el-col :span="18">
               <el-input v-model="form.code" :maxlength="6" type="text" placeholder="请输入验证码">
                 <template #prefix>
                   <el-icon><Bell /></el-icon>
                 </template>
               </el-input>
             </el-col>
             <el-col :span="6">
               <el-button type="success" @click="validateEmail" :disabled="!isEmailValid||coldTime>0">
                 {{coldTime>0 ? '请稍后'+coldTime+'秒':'获取验证码'}}
               </el-button>
             </el-col>
           </el-row>
         </el-form-item>
       </el-form>
     </div>

     <div style="margin-top:70px">
       <el-button @click="active=1" style="width: 270px;"  type="danger" plain>开始重置密码</el-button>
     </div>
   </div>

 </transition>

  <transition name="el-fade-in-linear" mode="out-in">
    <div style ="text-align:center;margin:0 20px;height:100%" v-if="active===1">
      <div style=" margin-top:80px">
        <div style="font-size:25px;font-weight:bold"> 重置密码</div>
        <div style="font-size: 14px;color:gray">请填写您的新密码</div>
      </div>

      <div style="margin-top:50px">
        <el-form :model="form" :rules="rules" @validate="onValidate" ref="formRef">
          <el-form-item prop="password">
            <el-input v-model="form.password" :maxlength="16" type="password" placeholder="密码" >
              <template #prefix>
                <el-icon><Lock /></el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item prop="password_repeat">
            <el-input v-model="form.password_repeat" :maxlength="16" type="password" placeholder="重复密码" >
              <template #prefix>
                <el-icon><Lock /></el-icon>
              </template>
            </el-input>
          </el-form-item>
        </el-form>
      </div>
      <div style="margin-top:70px">
        <el-button @click="active=2" style="width: 270px;"  type="danger" plain>立即重置密码</el-button>
      </div>

    </div>
  </transition>

  <div style ="" v-if="active===2">
    <div style=" margin-top:100px">
      <div style="font-size:25px;font-weight:bold"> 成功重置密码</div>
      <div style="font-size: 14px;color:gray">请牢记您的新密码，以防丢失</div>
    </div>
    <div style="font-size:14px;line-height: 15px;margin-top: 50px">
      <span style="color:grey">已重置密码</span>
      <el-link type="primary" style="translate: 0 -2px" @click="router.push('/')">立即登录</el-link>
    </div>
  </div>

</template>



<style scoped>

</style>