<template>
  <el-row
    type="flex"
    class="row-bg"
    justify="center"
    :style="'background-image:url(' + Background + ');'"
  >
    <el-col :span="6"><div class="grid-content bg-purple"></div></el-col>
    <el-col :span="6" class="col6">
      <div class="grid-content bg-purple-light">
        <h3 class="title">
          欢迎使用课程测评系统
        </h3>
        <el-form
          :model="loginForm"
          :rules="rules"
          ref="loginForm"
          label-width="100px"
          class="demo-loginForm"
        >
          <el-form-item
            label="用户名"
            prop="userName"
            style="width: 380px"
            class="el-icon-delete"
          >
            <el-input v-model="loginForm.userName"></el-input>
          </el-form-item>

          <el-form-item label="密码" prop="password" style="width: 380px">
            <el-input v-model="loginForm.password"></el-input>
          </el-form-item>

          <el-form-item label="用户类型" prop="role" style="width: 380px">
            <el-select v-model="loginForm.role" placeholder="请选择用户类型">
              <el-option label="管理员" value="admin"></el-option>
              <el-option label="教师" value="teacher"></el-option>
              <el-option label="学生" value="student"></el-option>
            </el-select>
          </el-form-item>
          <!--<el-form-item label="验证码" prop="code"  style="width: 380px" >
                        <el-input v-model="loginForm.code" style="width: 172px; float: left;" maxlength="5" ></el-input>
                        <el-image :src="captchaImg" class="captchaImg" @click="getCaptcha"></el-image>
                    </el-form-item>
-->
          <el-form-item
            style="text-align: right; margin-top: -12px; margin-bottom: -8px"
          >
            <el-link type="primary" icon="el-icon-question" @click="forget()"
              >忘记密码</el-link
            >
          </el-form-item>

          <el-form-item class="elbutton">
            <el-button
              type="primary"
              @click="submitForm('loginForm')"
              style="width: 120px"
              >用户登录</el-button
            >
            <el-button @click="Register()" style="width: 120px"
              >学生注册</el-button
            >
          </el-form-item>
        </el-form>
      </div>
    </el-col>
    <el-col :span="6"><div class="grid-content bg-purple"></div></el-col>
    <RegisterCenterDialog
      ref="Dialog"
      v-bind:editForm="editForm"
    ></RegisterCenterDialog>
  </el-row>
  <!--    </div>-->
</template>

<script>
import Background from "@/assets/t01f658bda8d6cbf70e.jpg";
import RegisterCenterDialog from "../components/CenterDialog/RegisterCenterDialog";
import qs from "qs";
export default {
  name: "Login",
  components: { RegisterCenterDialog },
  data() {
    return {
      editForm: {},
      Background: Background,
      loginForm: {
        userName: "admin",
        password: "123456",
        role: "",
        /*  code: '',
                    key: ''*/
      },
      rules: {
        userName: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
        role: [
          { required: true, message: "请选择用户类型", trigger: "change" },
        ],
      },
    };
  },
  methods: {
    Register() {
      this.centerDialog();
    },
    //弹出--添加、修改--气泡框
    centerDialog() {
      this.$refs.Dialog.centerDialog(true);
    },
    forget() {
      this.$notify({
        title: "无用的建议",
        message: "请联系教师或管理员查看",
        offset: 200,
      });
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post("/User/login", this.loginForm).then((res) => {
            console.log("login===>");
            console.log(res);
            const jwt = res.data.data.token;
            // 将jwt存储到应用store的SET_TOKEN中
            this.$store.commit("SET_TOKEN", jwt);
            this.$store.commit("SET_USER", res.data.data);
            /*  this.$store.commit("SET_ROLE",res.data.data.role)*/
            this.$router.push("/index");
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },

    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    resetForm1() {
      this.editForm = {};
    },
  },
};
</script>

<style scoped>
.row-bg {
  display: flex;
  /*justify-content: center;*/
  align-items: center;
  height: 100%;
  background-size: cover;
  margin: 2px;
}
.bg-purple-light {
  border: 5px snow dashed;
  border-radius: 6px;
  background: #ffffff;
  width: 385px;
  padding: 25px 25px 5px 25px;
}
.title {
  margin: 0 auto 30px auto;
  text-align: center;
  color: #707070;
  font-size: 25px;
}
.demo-loginForm {
  margin-left: -30px;
}
.captchaImg {
  float: left;
  width: 100px;
  margin-left: 8px;
  border-radius: 5px;
}
.elbutton {
  margin-left: -30px;
}
</style>
