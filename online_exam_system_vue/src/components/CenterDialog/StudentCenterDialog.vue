<template>
    <div>
        <el-dialog
                title="提示"
                width="30%"
                center
                :before-close="handleClose"
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
                <!--<el-form-item label="所属专业" prop="majorId" label-width="100px">
                    <el-select v-model="editForm.majorId" autocomplete="off"  placeholder="选择所属专业" clearable>
                        <el-option v-for="maj in majorData" :label="maj.majorName" :value="maj.majorId"></el-option>
                    </el-select>
                </el-form-item>-->

                <el-form-item label="学生账号" prop="stuUsername" >
                    <el-input v-model="editForm.stuUsername" autocomplete="off" placeholder="请输入学生学号" clearable ></el-input>
                </el-form-item>
                <el-form-item label="学生密码" prop="stuPassword" >
                    <el-input v-model="editForm.stuPassword" autocomplete="off" placeholder="若学生密码不设置，默认为123456" clearable type="password"></el-input>
                </el-form-item>

                <el-form-item label="学生名称" prop="stuName" >
                    <el-input v-model="editForm.stuName"  autocomplete="off" placeholder="请输入学生名称" clearable></el-input>
                </el-form-item>

                <el-form-item label="账号状态" prop="stuState" >
                    <el-radio-group v-model="editForm.stuState">
                        <el-radio :label=0>待审核-不可用</el-radio>
                        <el-radio :label=1>已审核-可用</el-radio>
                    </el-radio-group>
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
        name: "StudentCenterDialog",
        props: ['editForm','defaultParams','options'],
        data() {
            return {
                submitForm(formName) {
                    this.$refs[formName].validate((valid) => {
                        if (valid) {
                            this.$axios.post('/Student/' + (this.editForm.stuId?'update' : 'save'), this.editForm)
                                .then(res => {
                                    this.$parent.getStudent()
                                    this.$message({
                                        showClose: true,
                                        message: '恭喜你，操作成功',
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
                    stuState: [
                        {required: true, message: '请选择审核状态', trigger: 'blur'}
                    ],
                },
                /*pickerOptions: {
                    disabledDaste(time) {
                        return time.getTime() > Date.now();
                    },
                    shortcuts: [{
                        text: '今天',
                        onClick(picker) {
                            picker.$emit('pick', new Date());
                        }
                    }, {
                        text: '昨天',
                        onClick(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() - 3600 * 1000 * 24);
                            picker.$emit('pick', date);
                        }
                    }, {
                        text: '一周前',
                        onClick(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', date);
                        }
                    }]
                },*/
                centerDialogVisible: false,
            };
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
                this.$parent.resetForm('editForm')
            },
        },
    }
</script>

<style scoped>

</style>
