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

                <el-form-item label="教师名称" prop="teaUsername" label-width="100px">
                    <el-input v-model="editForm.teaUsername" autocomplete="off"></el-input>
                </el-form-item>

                <el-form-item label="出题时间" prop="testDate" label-width="100px">
                    <el-date-picker
                            v-model="editForm.testDate"
                            align="right"
                            autocomplete="off"
                            type="date"
                            placeholder="选择日期"
                            :picker-options="pickerOptions">
                    </el-date-picker>
                </el-form-item>

                <el-form-item label="科目" prop="subName" label-width="100px">
                    <el-select v-model="editForm.subName" autocomplete="off"  placeholder="科目" >
                        <el-option label="计网" value="shanghai"></el-option>
                        <el-option label="算法" value="sss"></el-option>
                        <el-option label="操作系统" value="beijing"></el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="试题类型" prop="testType" label-width="100px">
                    <el-radio-group v-model="editForm.testType">
                        <el-radio :label=1>单选题</el-radio>
                        <el-radio :label=2>多选题</el-radio>
                        <el-radio :label=3>判断题</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="试题内容" prop="testContent">
                    <el-input v-model="editForm.testContent" autocomplete="off" ></el-input>
                </el-form-item>
                <el-form-item label="选项A" prop="optionA">
                    <el-input v-model="editForm.optionA" autocomplete="off" ></el-input>
                </el-form-item>
                <el-form-item label="选项B" prop="optionB" label-width="100px">
                    <el-input v-model="editForm.optionB" autocomplete="off" ></el-input>
                </el-form-item>
                <el-form-item label="选项C" prop="optionC" label-width="100px">
                    <el-input v-model="editForm.optionC" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="选项D" prop="optionD" label-width="100px">
                    <el-input v-model="editForm.optionD" autocomplete="off" ></el-input>
                </el-form-item>
                <el-form-item label="正确答案" prop="answer" label-width="100px">
                    <el-input v-model="editForm.answer" autocomplete="off" ></el-input>
                </el-form-item>
                <el-form-item label="分值" prop="score" label-width="100px">
                    <el-input v-model="editForm.score" autocomplete="off" ></el-input>
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
        name: "CenterDialog",
        props: ['editForm'],
        data() {
            return {
                submitForm(formName) {
                    this.$refs[formName].validate((valid) => {
                        if (valid) {
                            this.$axios.post('/TestBank/' + (this.editForm.id?'update' : 'save'), this.editForm)
                                .then(res => {
                                    this.$message({
                                        showClose: true,
                                        message: '恭喜你，操作成功',
                                        type: 'success',
                                        onClose: ()=>{
                                            this.$parent.getTestBank()
                                        }
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
                    teaUsername: [
                        {required: true, message: '请输入教师名称', trigger: 'blur'}
                    ],
                    testDate: [
                        {required: true, message: '请输入出题时间', trigger: 'blur'}
                    ],
                    subName: [
                        {required: true, message: '请选择科目', trigger: 'blur'}
                    ],
                    testType: [
                        {required: true, message: '请选择类型', trigger: 'blur'}
                    ],
                    testContent: [
                        {required: true, message: '请输入试题内容', trigger: 'blur'}
                    ],
                    optionA: [
                        {required: true, message: '请输入答案A', trigger: 'blur'}
                    ],
                    optionB: [
                        {required: true, message: '请输入答案B', trigger: 'blur'}
                    ],
                    answer: [
                        {required: true, message: '请输入正确答案', trigger: 'blur'}
                    ],
                },
                /*pickerOptions: {
                    disabledDate(time) {
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
            centerDialog(statu){
                this.centerDialogVisible = statu
            },
           /* resetForm() {
                /!*this.$refs[formName].resetFields();
                this.editForm = {}*!/
                this.centerDialogVisible = false
            },*/
            handleClose() {
                this.centerDialogVisible=false
                this.$parent.resetForm('editForm')
            },
        },



    }
</script>

<style scoped>

</style>
