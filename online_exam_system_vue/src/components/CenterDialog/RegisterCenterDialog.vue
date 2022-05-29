<template>
    <div>
        <el-dialog
                title="考场描述"
                width="40%"
                center
                :before-close="handleClose"
                v-loading.fullscreen.lock="fullscreenLoading"
                :visible.sync="centerDialogVisible"
        >
            <el-form :model="editForm" :rules="editFormRules" ref="editForm" label-width="100px" class="demo-editForm">
                <!-- <el-form-item label="所属学院" prop="colName" label-width="100px">
                     <el-select v-model="editForm.colName" autocomplete="off"  placeholder="选择所属学院" clearable>
                         <el-option v-for="col in colData" :label="col.colName" :value="col.colName"></el-option>
                     </el-select>
                 </el-form-item>-->
                <el-form-item class="block" label="所属学院" prop="colId">
                    <el-cascader
                            v-model="editForm.majorId"
                            :options="options"
                            :props="defaultParams"
                            :clearable="true"
                            :show-all-levels="false"
                            @change="handleChange"
                            placeholder="选择学院、专业">
                    </el-cascader>
                </el-form-item>

                <el-form-item label="学生账号" prop="stuUsername" >
                    <el-input v-model="editForm.stuUsername" autocomplete="off" placeholder="请输入学生学号" clearable ></el-input>
                </el-form-item>

                <el-form-item label="学生密码" prop="stuPassword" >
                    <el-input v-model="editForm.stuPassword" autocomplete="off" placeholder="若学生密码不设置，默认为123456" clearable type="password"></el-input>
                </el-form-item>

                <el-form-item label="学生名称" prop="stuName" >
                    <el-input v-model="editForm.stuName"  autocomplete="off" placeholder="请输入学生名称" clearable></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="info" @click="handleClose">取消</el-button>
                    <el-button type="primary" @click="submitForm('editForm')">立即创建</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "RegisterCenterDialog",
        props: ["editForm"],

        data() {
            return {
                fullscreenLoading: false,
                options: [],
                defaultParams:{
                    label: 'majorName',
                    value: 'majorId',
                    checkStrictly: true
                },
                editFormRules: {
                    colId: [
                        {required: true, message: '请选择所属学院、专业', trigger: 'blur'}
                    ],
                    majorName: [
                        {required: true, message: '请选择所属专业', trigger: 'blur'}
                    ],
                    stuUsername: [
                        {required: true, message: '请输入学生账号', trigger: 'blur'}
                    ],
                    stuName: [
                        {required: true, message: '请输入学生名称', trigger: 'blur'}
                    ],
                },
                centerDialogVisible: false,
                submitForm(formName) {
                    this.$refs[formName].validate((valid) => {
                        if (valid) {
                            this.editForm.stuState = 0
                            this.$axios.post('/Student/save', this.editForm)
                                .then(res => {
                                    this.$message({
                                        showClose: true,
                                        message: '注册成功，请等待管理员审核通过',
                                        type: 'success',
                                    });
                                    this.centerDialogVisible = false
                                    this.$parent.resetForm(formName)
                                })
                        } else {
                            console.log('error submit!!');
                            return false;
                        }
                    });
                },
                //获取学院列表
                getColData(){
                    this.$axios.get("/College/getCollegelist").then(res =>{
                        this.options = res.data.data
                        console.log("==================>")
                        console.log(this.options)
                    })
                },
            };
        },
        created() {
            this.getColData()
        },
        methods:{
            handleChange(value) {
                if (value!=null){
                    this.editForm.colId = value[0]
                    this.editForm.majorId = value[1]
                }
                console.log(value);
            },
            centerDialog(statu){
                this.centerDialogVisible = statu
            },
            handleClose() {
                this.centerDialogVisible=false
                this.$parent.resetForm1('editForm')
            },

        },
    }
</script>

<style scoped>
    .text {
        font-size: 20px ;
        margin: 15px;
        font-family: Helvetica Neue,Helvetica,PingFang SC,Hiragino Sans GB,Microsoft YaHei,Arial,sans-serif;
    }

    .item {
        padding: 18px 0;
    }

    .box-card {
        width: 100%;
        margin-bottom: 50px;
    }
</style>
