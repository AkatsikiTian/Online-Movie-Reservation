import Vue from 'vue'
import VueRouter from 'vue-router'
import Manger from '../views/BackgroundManagement/Manger.vue'


Vue.use(VueRouter)

const routes = [
  {
    path: '/manger',
    name: 'manger',
    component: Manger,
    redirect: "/manger/home",
    meta:{
      requireAuth: true
    },
    children: [
      {
      path:'home',
      name:'home',
      component: () => import(/* webpackChunkName: "about" */ '../views/BackgroundManagement/Home.vue'),
      meta: {title: '首页'}
    },
    {
      path:'employee',
      name:'employee',
      component: () => import(/* webpackChunkName: "about" */ '../views/BackgroundManagement/Employee.vue'),
      meta: {title: '员工管理'}
    },
    {
      path:'category',
      name:'category',
      component: () => import(/* webpackChunkName: "about" */ '../views/BackgroundManagement/Category.vue'),
      meta: {title: '分类管理'}
    },
    {
      path:'film',
      name:'film',
      component: () => import(/* webpackChunkName: "about" */ '../views/BackgroundManagement/Film.vue'),
      meta: {title: '电影管理'}
    },
    {
      path:'news',
      name:'news',
      component: () => import(/* webpackChunkName: "about" */ '../views/BackgroundManagement/News.vue'),
      meta: {title: '热门资讯'}
    },
    {
      path:'user',
      name:'user',
      component: () => import(/* webpackChunkName: "about" */ '../views/BackgroundManagement/User.vue'),
      meta: {title: '用户管理'}
    },
    {
      path:'house',
      name:'house',
      component: () => import(/* webpackChunkName: "about" */ '../views/BackgroundManagement/House.vue'),
      meta: {title: '影厅管理'}
    },
    {
      path:'arrangement',
      name:'arrangement',
      component: () => import(/* webpackChunkName: "about" */ '../views/BackgroundManagement/Arrangement.vue'),
      meta: {title: '放映管理'}
    },
    {
      path:'order',
      name:'order',
      component: () => import(/* webpackChunkName: "about" */ '../views/BackgroundManagement/Order.vue'),
      meta: {title: '订单管理'}
    },
    ]
  },
  ,{
    path: '/login',
    name: 'login',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/BackgroundManagement/Login.vue')
  },
  {
    path: '/Login1',
    name: 'login',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/front/Login.vue')
  },
  {
    path: '/register',
    name: 'register',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/front/Register.vue')
  },
  {
    path: '/changepassword1',
    name: 'changepassword1',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/BackgroundManagement/ChangePassword.vue')
  },
  {
    path: '/',
    name: 'index',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/front/Index.vue'),
    children: [
      {
        path:'',
        name:'home',
        component: () => import(/* webpackChunkName: "about" */ '../views/front/Home.vue'),
        meta: {title: '首页'}
      },
      {
        path: 'person',
        name: 'person',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/front/Person.vue'),
        meta: {title: '个人中心'},
        children:[
          {
            path: 'changepassword',
            name: 'changepassword',
            // route level code-splitting
            // this generates a separate chunk (about.[hash].js) for this route
            // which is lazy-loaded when the route is visited.
            component: () => import(/* webpackChunkName: "about" */ '../views/front/ChangePassword.vue')
          },
          {
            path: '/',
            name: 'userinfo',
            // route level code-splitting
            // this generates a separate chunk (about.[hash].js) for this route
            // which is lazy-loaded when the route is visited.
            component: () => import(/* webpackChunkName: "about" */ '../views/front/UserInfo.vue')
          },
          {
            path: 'order',
            name: 'order',
            // route level code-splitting
            // this generates a separate chunk (about.[hash].js) for this route
            // which is lazy-loaded when the route is visited.
            component: () => import(/* webpackChunkName: "about" */ '../views/front/Order.vue')
          },
        ]
      },
      {
        path:'category',
        name:'category',
        component: () => import(/* webpackChunkName: "about" */ '../views/front/Category.vue'),
        meta: {title: '电影分类'}
      },
      {
        path:'filmdetial/:id',
        name:'filmdetial',
        component: () => import(/* webpackChunkName: "about" */ '../views/front/FilmDetail.vue'),
        meta: {title: '电影详情'}
      },
      {
        path:'select',
        name:'select',
        component: () => import(/* webpackChunkName: "about" */ '../views/front/Select.vue'),
        meta: {title: '选票'}
      },
      {
        path:'pay',
        name:'pay',
        component: () => import(/* webpackChunkName: "about" */ '../views/front/Pay.vue'),
        meta: {title: '付款'}
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
