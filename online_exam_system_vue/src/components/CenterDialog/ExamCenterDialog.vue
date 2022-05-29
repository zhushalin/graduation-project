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
            <el-card class="box-card" shadow="hover" style="border-radius: 4px;box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04)" >
                <div class="text">
                    <strong>考试名称：</strong>{{editForm.tpName}}
                </div>
                <div class="text">
                    <strong>考试时间：</strong>{{editForm.examTime}}
                </div>
                <div class="text">
                    <strong>考试时长：</strong>{{editForm.examLong}}分钟
                </div>
                <div class="text">
                    <strong>考试总分：</strong>{{editForm.examTotal}}分
                </div>
                <div class="text">
                    <strong>考试类型：</strong>
                    <a v-if="editForm.tpType === 0" >完全公开</a>
                    <a v-else-if="editForm.tpType === 1" >入考码进入</a>
                </div>
                <el-form ref="tpData" :model="tpData" :rules="tpDataRules" label-position="left">
                    <el-form-item prop="examPassword" v-if="editForm.tpType===1" placeholder="请输入入考码" >
                        <el-input v-model="tpData.examPassword" placeholder="请输入入考码"/>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="submitForm()">开始考试</el-button>
                        <el-button type="info" @click="handleClose">取消</el-button>
                    </el-form-item>
                </el-form>
            </el-card>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "ExamCenterDialog",
        props: ["editForm","subData"],

        data() {
            return {
                tpData: {
                    examPassword: ""
                },
                fullscreenLoading: false,
                submitForm() {
                    if (this.tpData.examPassword===this.editForm.examPassword){
                        this.openFullScreen2()
                        this.$message({
                            duration: 1000,
                            message: '试卷加载中，即将开始考试',
                            type: 'success',
                            onClose:() => {
                                sessionStorage.setItem("examTpId",this.editForm.tpId)
                                sessionStorage.setItem("examTpName",this.editForm.tpName)
                                console.log(sessionStorage.getItem("examTpName"))
                                this.$router.push({name: "OnlineExam"/*, params:{tpId:this.editForm.tpId,tpName:this.editForm.tpName}*/})
                            }
                        });

                        this.centerDialogVisible = false
                        this.$parent.resetForm()
                        /*sessionStorage.setItem("tpId",this.editForm.tpId)
                        sessionStorage.setItem("subId",this.editForm.subId)
                        sessionStorage.setItem("tpName",this.editForm.tpName)*/


                    }else{
                        this.$message({
                            showClose: true,
                            message: '入考码输入错误，请重新输入',
                            type: 'danger',
                        });
                    }
                },
                tpDataRules: {
                    examPassword: [
                        { required: true, message: '入考码不能为空！' }
                    ],
                },
                centerDialogVisible: false,
            };
        },
        created() {

        },
        methods:{
            openFullScreen2() {
                const loading = this.$loading({
                    lock: true,
                    text: '正在努力加载试卷中',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
                setTimeout(() => {
                    loading.close();
                }, 1000);
            },
            array(val){
                return true
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
    .input_number{
        margin-right: 40px;
    }

</style>
