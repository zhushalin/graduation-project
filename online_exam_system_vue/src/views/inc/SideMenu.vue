<template>
  <el-menu
    active-text-color="#409EFF"
    background-color="#304152"
    class="el-menu-vertical-demo"
    :default-active="this.$store.state.menus.editableTabsValue"
    text-color="#fff"
  >
    <el-menu-item style="background-color: #2B2F3A ; font-size: 20px;">
      <template slot="title">
        <span slot="title">课程测评系统</span>
      </template>
    </el-menu-item>
    <router-link to="/index">
      <el-menu-item
        class="el-menu-index"
        index="Index"
        @click="SelectMenu({ name: 'Index', title: '首页' })"
      >
        <template slot="title">
          <i class="el-icon-s-home"></i>
          <span slot="title">首页</span>
        </template>
      </el-menu-item>
    </router-link>
    <!--动态遍历父菜单-->
    <el-submenu :index="menu.name" v-for="menu in menuList">
      <template slot="title">
        <i :class="menu.icon"></i>
        <span>{{ menu.title }}</span>
      </template>

      <!--动态遍历子菜单-->
      <router-link :to="item.path" v-for="item in menu.children">
        <el-menu-item :index="item.name" @click="SelectMenu(item)">
          <template slot="title">
            <i :class="item.icon"></i>
            <span slot="title">{{ item.title }}</span>
          </template>
        </el-menu-item>
      </router-link>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  name: "SideMenu",
  data() {
    return {};
  }, //动态渲染
  computed: {
    menuList: {
      get() {
        return this.$store.state.menus.menuList;
      },
      set(val) {
        this.$store.state.menus.menuList = val;
      },
    },
  },
  methods: {
    SelectMenu(item) {
      this.$store.commit("addTab", item);
    },
  },
};
</script>

<style scoped>
.el-menu-vertical-demo {
  height: 100%;
}
a {
  text-decoration: none;
}
</style>
