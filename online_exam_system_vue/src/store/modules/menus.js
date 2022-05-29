import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)
//共享信息
export default {
    state: {
        menuList: [],  //菜单
        permList: [],  //权限

        hasRoutes: false,

        editableTabsValue: 'Index',
        editableTabs: [{
            title: '首页',
            name: 'Index',
        }],
    },
    mutations: {
        setMenuList(state, menus) {
            state.menuList = menus
        },
        setPermList(state, perms) {
            state.permList = perms
        },
        changeRouteStatus(state, hasRoutes) {
            state.hasRoutes = hasRoutes
        },
        addTab(state, tabs) {
            let res = state.editableTabs.findIndex(e => e.name === tabs.name);
            if (res === -1) {
                state.editableTabs.push({
                    title: tabs.title,
                    name: tabs.name,
                });
            }
            state.editableTabsValue = tabs.name;
        },
        resetState: (state) => {
            state.token = '',
            state.menuList = []  //菜单
            state.permList = [] //权限

            state.hasRoutes = false

            state.editableTabsValue = 'Index'
            state.editableTabs = [{
                title: '首页',
                name: 'Index',
            }]
        }
    },
    actions: {},

}
