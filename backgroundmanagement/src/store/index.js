import Vue from 'vue'
import Vuex from 'vuex'
import request from "@/utils/request";

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token:'',
    account:'',
    id:'',
    avatar:''
  },
  getters: {
    //get方法
    getUser: state => {
      return state.account
    }
  },
  mutations: {
    //set方法
    SET_TOKEN: ((state, token) => {
      state.token = token
      localStorage.setItem("token",token)
    }),
    SET_ACCOUNT: ((state, account) => {
      state.account = account
      localStorage.setItem("account",account)
    }),
    SET_ID: ((state, id) => {
      state.id = id
      localStorage.setItem("id",id)
    }),
    SET_AVATAR: ((state, avatar) => {
      state.avatar = avatar
      localStorage.setItem("avatar",avatar)
    }),
    REMOVE_INFO:(state => {
      state.token=''
      state.account=''
      state.id=''
      state.avatar=''
      localStorage.setItem("token",'')
      localStorage.setItem("account",'')
      localStorage.setItem("id",'')
      localStorage.setItem("avatar",'')
    })
  },
  actions: {
  },
  modules: {
  }
})
