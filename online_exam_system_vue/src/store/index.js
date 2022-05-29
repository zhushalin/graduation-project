import Vue from 'vue'
import Vuex from 'vuex'
import menus from "./modules/menus";
import th from "element-ui/src/locale/lang/th";

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: '',
    /*user: {
      name: '',
      role: '',
      username: '',
      token: '',
      id: '',
    },*/
  },
  mutations: {
    SET_TOKEN: (state,token) =>{
        state.token = token
        localStorage.setItem("token",token)
    },
    SET_USER: (state,user) =>{
/*      state.user.name = user.name;
      state.user.role = user.role;
      state.user.username = user.username;
      state.user.token = user.token;
      state.user.id = user.id;*/
      localStorage.setItem("name",user.name)
      localStorage.setItem("role",user.role)
      localStorage.setItem("userName",user.userName)
      localStorage.setItem("token",user.token)
      localStorage.setItem("id",user.id)
    },
    /*tpId: (state , tpId) =>{
      localStorage.setItem("tpId",tpId)
    },
    subId: (state , subId) =>{
      localStorage.setItem("subId",subId)
    },
    tpName: (state , tpName) =>{
      localStorage.setItem("tpName",tpName)
    }*/
    /*SET_ROLE: (state,role) =>{
      state.role = role;
      localStorage.setItem("role",role)
    },*/


  },
  actions: {
  },
  modules: {
    menus
  }
})
