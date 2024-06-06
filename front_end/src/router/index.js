import { createRouter, createWebHistory } from 'vue-router'
import {useStore} from "@/stores/index.js";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'welcome',
      component:()=>import('@/views/WelcomeView.vue'),
      children:[//子路由
        {
          path:'',
          name:'welcome-login',
          component:()=>import('@/components/Welcome/Login.vue')
        },
        {
          path:'register',
          name:'welcome-register',
          component:()=>import('@/components/Welcome/Register.vue')
        },
        {
          path:'forget',
          name:'welcome-forget',
          component:()=>import('@/components/Welcome/Forget.vue')
        }
      ]
    },
    {
      path:'/index',
      name:'index',
      component:()=>import('@/views/IndexView.vue'),
      children:[
          {
        path:'mypage',
        name:'index-mypage',
        component:()=>import('@/components/Index/Mypage.vue')
      },
        {
          path:'homepage',
          name:'index-homepage',
          component:()=>import('@/components/Index/Homepage.vue')
        },
        {
          path:'choicepage',
          name:'index-choicepage',
          component:()=>import('@/components/Index/Choicepage.vue')
        },
        {
          path:'singlechoice',
          name:'index-singlechoice',
          component:()=>import('@/components/Index/Singlechoice.vue')
        },
        {
          path:'multiplechoice',
          name:'index-multiplechoice',
          component:()=>import('@/components/Index/MultipleChoice.vue')
        },
        {
          path:'blankchoice',
          name:'index-blankchoice',
          component:()=>import('@/components/Index/Blankchoice.vue')
        },
        {
          path:'ShowAllQuestion',
          name:'index-ShowAllQuestion',
          component:()=>import('@/components/Index/ShowAllQuestion.vue')
        }
      ]
    }
    ,{
       path:'/backlayout',
      name :'backlayout',
      component:()=>import('@/views/BackLayoutView.vue'),
      children:[//子路由
        {
          path:'InfoManage',
          name:'backlayout-InfoManage',
          component: ()=>import('@/components/BackLayout/InfoManage.vue'),
          children:[//子子路由
            {
              path:'QuestionnaireInfo',
              name:'InfoManage-QuestionnaireInfo',
              component:()=>import('@/components/BackLayout/QuestionnaireInfo.vue'),
            },
            {
              path:'QuestionInfo/:id',
              name:'InfoManage-QuestionInfo',
              component:()=>import('@/components/BackLayout/QuestionInfo.vue'),
            },
            {
              path:'AnswerInfo',
              name:'InfoManage-AnswerInfo',
              component:()=>import('@/components/BackLayout/AnswerInfo.vue'),
            }
          ]
        },
        {
          path:'UserManage',
          name:'backlayout-UserManage',
          component:( )=>import('@/components/BackLayout/UserManage.vue'),
          children:[
            {
              path:'ManagerInfo',
              name:'UserManage-ManagerInfo',
              component:()=>('@/components/BackLayout/ManagerInfo.vue'),
            },
            {
              path:'UserInfo',
              name:'UserManage-UserInfo',
              component:()=>import('@/components/BackLayout/UserInfo.vue'),
            }
          ]
        }
      ]
    }
  ]
})

//基本页面跳转拦截

// router.beforeEach((to, from, next) => {
//   const store = useStore()
//   if(store.auth.user != null && to.name.startsWith('Welcome-')) {
//     next('/index')
//   } else if(store.auth.user == null && to.fullPath.startsWith('/index')) {
//     next('/')
//   } else if(to.matched.length === 0){
//     next('/index')
//   } else {
//     next()
//   }
// })
 export default router
