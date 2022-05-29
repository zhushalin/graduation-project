<template>
    <div>
        <el-row type="flex" class="row-bg" justify="center" >
            <el-col :span="8" >
                <el-card class="box-card" style="text-align: center">
                    <div slot="header" class="clearfix" >
                        <span>个人资料</span>
                    </div>
                    <h2 style="margin-left: 15px">欢迎！</h2>
                    <h2 >{{userInfo.role}}
                        <el-divider direction="vertical"></el-divider>
                        {{ userInfo.name }}
                        </h2>
                </el-card>
            </el-col>
            <el-col :span="1"></el-col>
            <el-col :span="12" >
                <el-card class="box-card" style="text-align: center">
                    <div slot="header" class="clearfix">
                        <el-button type="text" disabled style="color: #707070">资料修改</el-button>
                    </div>
                    <el-form :model="passForm"   status-icon :rules="rules" ref="passForm" label-width="80px">
                        <el-form-item label="用户账号" >
                            <el-input type="text" v-model="passForm.userName" disable autocomplete="off" readonly="readonly" ></el-input>
                        </el-form-item>
                        <el-form-item label="旧密码" prop="password">
                            <el-input type="password" v-model="passForm.password" autocomplete="off" placeholder="若要修改密码请输入旧密码" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="新密码" prop="currentPass">
                            <el-input type="password" v-model="passForm.currentPass" autocomplete="off" placeholder="若要修改密码请输入新密码" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="确认密码" prop="checkPass" >
                            <el-input type="password" v-model="passForm.checkPass" autocomplete="off" placeholder="若要修改密码请再次输入新密码" disabled v-if="!passForm.currentPass"></el-input>
                            <el-input type="password" v-model="passForm.checkPass" autocomplete="off" placeholder="若要修改密码请再次输入新密码" v-if="passForm.currentPass" clearable></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="submitForm('passForm')">提交</el-button>
                            <el-button @click="resetForm('passForm')">重置</el-button>
                        </el-form-item>
                    </el-form>
                </el-card>
            </el-col>
        </el-row>
        <!--<el-calendar v-model="value">
        </el-calendar>-->
        <el-steps :active="4" simple style="margin-top: 20px">
            <el-step title="1、输入旧密码" icon="el-icon-edit"></el-step>
            <el-step title="2、输入新密码" icon="el-icon-edit"></el-step>
            <el-step title="3、再次输入新密码" icon="el-icon-edit"></el-step>
            <el-step title="4、提交" icon="el-icon-upload"></el-step>
        </el-steps>

    </div>

</template>

<script>
    import Background from '@/assets/t01f658bda8d6cbf70e.jpg'
    export default {
        name: "UserCenter",
        Background: Background,

        data() {
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.passForm.currentPass) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                value: new Date(),
                userInfo: {

                },
                passForm: {
                    userName: '',
                    password: '',
                    checkPass: '',
                    currentPass: '',
                },
                rules: {
                    password: [
                        { required: true, message: '请输入当前密码', trigger: 'blur' },
                    ],
                    currentPass: [
                        { required: true, message: '请输入新密码', trigger: 'blur' },
                        { min: 6, max: 12, message: '长度在 6 到 12 个字符', trigger: 'blur' }
                    ],
                    checkPass: [
                        { required: true, validator: validatePass, trigger: 'blur' }
                    ],
                }
            }
        },
        created() {
            this.getUserInfo()
        },
        methods: {
            getUserInfo() {
                this.userInfo.name = localStorage.getItem("name")
                let r = localStorage.getItem("role")
                if (r === "admin"){
                    this.userInfo.role = "管理员"
                }else if(r === "teacher"){
                    this.userInfo.role = "教师"
                }else if(r === "student"){
                    this.userInfo.role = "学生"
                }
                console.log(localStorage.getItem("userName"))
                this.passForm.userName = localStorage.getItem("userName")
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this
                        this.passForm.role = localStorage.getItem("role")

                        console.log(this.passForm)
                        this.$axios.post('/User/updatePass', this.passForm).then(res => {
                                this.$message({
                                    type:"success",
                                    message:"修改完成，请重新登录！",
                                    duration:"1000",
                                    onClose:() =>{
                                        this.logout()
                                    }
                                })
                        })

                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            logout(){
                localStorage.clear()
                sessionStorage.clear()

                this.$store.commit("resetState")

                this.$router.push("/login")
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        }
    }
</script>

<style scoped>
    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }
    .clearfix:after {
        clear: both
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
        margin-top: 100px;
        margin-left: 50px;
    }
</style>
