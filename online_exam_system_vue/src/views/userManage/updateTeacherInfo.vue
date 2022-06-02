<template>
  <div>
    <el-row type="flex" class="row-bg" justify="center">
      <el-col :span="8">
        <el-card class="box-card" style="text-align: center">
          <div slot="header" class="clearfix">
            <span>个人资料</span>
          </div>
          <h2 style="margin-left: 15px">欢迎！</h2>
          <h2>
            {{ userInfo.role }}
          </h2>
        </el-card>
      </el-col>
      <el-col :span="1"></el-col>
      <el-col :span="12">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <el-button type="text" disabled style="color: #707070"
              >资料修改</el-button
            >
          </div>

          <el-form
            :model="passForm"
            status-icon
            :rules="rules"
            ref="passForm"
            label-width="80px"
          >
            <el-form-item label="用户账号">
              <el-input
                type="text"
                v-model="userName"
                disable
                autocomplete="off"
                readonly="readonly"
              ></el-input>
            </el-form-item>
            <el-form-item label="教师名称" prop="sex">
              <el-input
                type="text"
                v-model="passForm.teaName"
                disable
                autocomplete="off"
                readonly="readonly"
              ></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="sex">
              <el-input
                type="text"
                v-model="passForm.sex"
                disable
                autocomplete="off"
              ></el-input>
            </el-form-item>
            <el-form-item label="年龄" prop="age">
              <el-input
                type="text"
                v-model="passForm.age"
                autocomplete="off"
                placeholder="若要修改密码请输入旧密码"
                clearable
              ></el-input>
            </el-form-item>
            <el-form-item label="手机" prop="phone">
              <el-input
                type="text"
                v-model="passForm.phone"
                autocomplete="off"
                placeholder="若要修改密码请输入新密码"
                clearable
              ></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input
                type="text"
                v-model="passForm.email"
                autocomplete="off"
              ></el-input>
            </el-form-item>
            <el-form-item label="所属学院" prop="colName" label-width="100px">
              <el-select
                v-model="passForm.colName"
                autocomplete="off"
                placeholder="选择所属学院"
                clearable
              >
                <el-option
                  v-for="col in teaData"
                  :label="col.colName"
                  :value="col.colName"
                  :key="col.colName"
                ></el-option>
              </el-select>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="submitForm('passForm')"
                >提交</el-button
              >
              <el-button @click="resetForm('passForm')">重置</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
    <!--<el-calendar v-model="value">
        </el-calendar>-->
  </div>
</template>

<script>
import Background from "@/assets/t01f658bda8d6cbf70e.jpg";
export default {
  name: "UserCenter",
  Background: Background,

  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.passForm.currentPass) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      value: new Date(),
      tableData: [],
      userInfo: {},
      teaData: [],
      userName: "",
      searchForm: {
        colName: null,
        teaId: null,
        teaName: null,
        size: 8,
        current: 1,
      },
      passForm: {
        sex: "",
        age: "",
        phone: "",
        email: "",
        teaName: "",
        colName: "",
      },

      rules: {
        password: [
          { required: true, message: "请输入当前密码", trigger: "blur" },
        ],
        currentPass: [
          { required: true, message: "请输入新密码", trigger: "blur" },
          {
            min: 6,
            max: 12,
            message: "长度在 6 到 12 个字符",
            trigger: "blur",
          },
        ],
        checkPass: [
          { required: true, validator: validatePass, trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getUserInfo();
    this.getStudent();
    this.getTeacher();
  },
  methods: {
    //查询、添加
    getTeacher() {
      console.log("搜索===>");
      this.$axios.post("/Teacher/getTeacher", this.searchForm).then((res) => {
        console.log("搜索结果==>>");
        console.log(res);
        // this.tableData = res.data.data.content;
        // this.searchForm.size = res.data.data.size;
        // this.searchForm.current = res.data.data.current;
        // this.total = res.data.data.datatotal;
        this.getTeaData();
      });
    },
    //获取学院列表
    getTeaData() {
      this.$axios.get("/College/getCollegelist").then((res) => {
        this.teaData = res.data.data;
      });
    },
    //查询、添加
    getStudent() {
      this.userInfo.id = localStorage.getItem("id");
      console.log("搜索===>");
      this.$axios.get("/Teacher/getById/" + this.userInfo.id).then((res) => {
        console.log("搜索结果==>>");
        console.log(res.data.data[0].sex);
        this.passForm.sex = res.data.data[0].sex;
        this.passForm.age = res.data.data[0].age;
        this.passForm.phone = res.data.data[0].phone;
        this.passForm.email = res.data.data[0].email;
        this.passForm.teaName = res.data.data[0].teaName;
        this.passForm.colName = res.data.data[0].colName;

        // this.passForm.sex = res.data.data.sex;
      });
    },
    getUserInfo() {
      this.userInfo.name = localStorage.getItem("name");
      let r = localStorage.getItem("role");
      if (r === "admin") {
        this.userInfo.role = "管理员";
      } else if (r === "teacher") {
        this.userInfo.role = "教师";
      } else if (r === "student") {
        this.userInfo.role = "学生";
      }
      console.log(localStorage.getItem("userName"));
      this.userName = localStorage.getItem("userName");
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // this;

          let r = localStorage.getItem("role");
          if (r === "admin") {
            this.passForm.adminId = localStorage.getItem("id");
            this.$axios
              .post("/administrator/update", this.passForm)
              .then((res) => {
                this.$message({
                  type: "success",
                  message: "修改完成，请重新登录！",
                  duration: "1000",
                  onClose: () => {
                    this.logout();
                  },
                });
              });
          } else if (r === "teacher") {
            this.passForm.teaId = localStorage.getItem("id");
            this.$axios.post("/Teacher/update", this.passForm).then((res) => {
              this.$message({
                type: "success",
                message: "修改完成！",
                duration: "1000",
              });
              this.getStudent();
              this.$router.go(0);
            });
          } else if (r === "student") {
            this.passForm.stuId = localStorage.getItem("id");
            this.$axios.post("/Student/update", this.passForm).then((res) => {
              this.$message({
                type: "success",
                message: "修改完成！",
                duration: "1000",
              });
            });
            this.getStudent();
            this.$router.go(0);
          }
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    logout() {
      localStorage.clear();
      sessionStorage.clear();

      this.$store.commit("resetState");

      this.$router.push("/login");
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
};
</script>

<style scoped>
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}
.box-card {
  width: 480px;
}
.row-bg {
  display: flex;
  align-items: center;
  height: 100%;
  background-size: cover;
  margin: 2px;
  margin-top: 10px;
  margin-left: 50px;
}
</style>
