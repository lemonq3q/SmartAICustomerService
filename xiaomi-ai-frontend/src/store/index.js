import Vue from "vue";
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({

  state: {
    root: '',
    path: '',
    userId: 0,
    userheadshot: '未登陆',
    username: '未登录',
    isLogin: false
  },

  mutations: {
    updatePath(state,nowPath) {
      state.path = nowPath;
    },
    updateRoot(state,nowRoot) {
      state.root = nowRoot;
    },
    updateHeadShot(state,nowHeadShot){
      state.userheadshot = nowHeadShot;
    },
    updateUsername(state,nowUsername){
      state.username = nowUsername;
    },
    updateUserId(state,nowUserId){
      state.userId = nowUserId;
    },
    setLogin(state,flag){
      state.isLogin = flag;
    }
  }
})

export default store