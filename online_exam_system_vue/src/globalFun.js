import Vue from 'vue'

Vue.mixin({
    methods:{
        hasAuth(){
            if (localStorage.getItem("role") === "admin"){
                return true;
            }
            return false;
        }
    }
})
