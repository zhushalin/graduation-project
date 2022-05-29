<template>
  <el-container>
    <el-aside width="200px" style=" border-radius: 5px">
      <SideMenu></SideMenu>
    </el-aside>

    <el-container
      :style="'background-image:url(' + Background + ')'"
      justify="center"
      type="flex"
      class="row-bg"
    >
      <el-header style="height: 55px ; border-radius: 5px">
        <strong style="color: snow">课程测评系统</strong>
        <div class="header-avatar block">
          <!-- <el-avatar class="el-avatar" size="medium" src="https://image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/5a9f48118166308daba8b6da7e466aab.jpg"></el-avatar>-->
          <el-dropdown>
            <span class="el-dropdown-link">
              {{ UserInfo.role }}
              <!-- <el-divider direction="vertical"></el-divider>

                          {{UserInfo.name}}
                          <i class="el-icon-arrow-down el-icon--right"></i> -->
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item
                style=" border-radius: 5px; width: 150px"
                @click.native="center"
                >个人中心
              </el-dropdown-item>

              <el-dropdown-item @click.native="logout">退出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-header>
      <el-main>
        <Tabs></Tabs>
        <div style="margin: 0 15px;">
          <router-view />
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Background from "@/assets/t01f658bda8d6cbf70e.jpg";
import SideMenu from "./inc/SideMenu";
import Tabs from "./inc/Tabs";
export default {
  name: "Home",
  components: {
    SideMenu,
    Tabs,
  },
  data() {
    return {
      Background: Background,
      UserInfo: {
        id: "",
        name: "",
        role: "",
      },
    };
  },
  created() {
    this.getUserInfo();
  },
  methods: {
    getUserInfo() {
      this.UserInfo.name = localStorage.getItem("name");
      let r = localStorage.getItem("role");
      if (r === "admin") {
        this.UserInfo.role = "管理员";
      } else if (r === "teacher") {
        this.UserInfo.role = "教师";
      } else if (r === "student") {
        this.UserInfo.role = "学生";
      }
    },
    logout() {
      localStorage.clear();
      sessionStorage.clear();

      this.$store.commit("resetState");

      this.$router.push("/login");
    },
    center() {
      this.$router.push("/center");
    },
  },
};
</script>

<style scoped>
.el-container {
  padding: 0;
  margin: 0;
  height: 100%;
}

.header-avatar {
  float: right;
  width: 230px;
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.el-dropdown-link {
  font-size: 20px;
  color: snow;
  cursor: pointer;
}

.el-header {
  font-size: 20px;
  background-color: #304152;
  color: #303133;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #d3dce6;
  color: #333;
  line-height: 200px;
}

.el-main {
  color: #333;
  padding: 0;
}
.row-bg {
  background-size: cover;
}

a {
  text-decoration: none;
}
</style>
