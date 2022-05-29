<template>
  <div id="app">
    <router-view v-if="isRouterAlive"></router-view>
  </div>
</template>

<script>
  export default {
    name: "APP",
    provide () {    //父组件中通过provide来提供变量，在子组件中通过inject来注入变量。
      return {
        reload: this.reload
      }
    },
    data() {
      return{
        isRouterAlive: true                    //控制视图是否显示的变量
      }
    },
    methods: {
      reload () {
        this.isRouterAlive = false;            //先关闭，
        this.$nextTick(function () {
          this.isRouterAlive = true;         //再打开
        })
      }
    },
    watch: {
       $route(to,from){
         console.log("to==>")
        /* console.log(to)*/
         if(to.path != "/login"){
           let obj = {
             name: to.name,
             title: to.meta.title
           }
           console.log(obj)
           this.$store.commit("addTab",obj)
         }
       }
    }
  }
</script>

<style>
html, body, #app {
  font-family: 'Helvetica Neue', 'Hiragino Sans GB', 'WenQuanYi Micro Hei', 'Microsoft Yahei', sans-serif;
  height: 100%;
  padding: 0;
  margin: 0;
  font-size: 15px;
}
</style>
