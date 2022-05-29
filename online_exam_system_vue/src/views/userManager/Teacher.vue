<template>
    <div>
        <el-form :inline="true" :model="searchForm" class="demo-form-inline" clearable>
            <el-form-item>

                <el-select v-model="searchForm.colName" placeholder="选择学院" clearable>
                    <el-option v-for="col in teaData" :label="col.colName" :value="col.colName"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-input v-model="searchForm.teaId" placeholder="教师编号" clearable></el-input>
            </el-form-item>
            <el-form-item>
                <el-input v-model="searchForm.teaName" placeholder="教师名称" clearable></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="getTeacher" >
                    <i class="el-icon-search" ></i>
                    搜索
                </el-button>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="centerDialog">
                    <i class="el-icon-plus" ></i>添加
                </el-button>
            </el-form-item>
            <el-popconfirm title="确定删除所选内容吗？" @confirm="delHandle(null)">
                <el-button type="danger" slot="reference" :disabled="delBtlStatu">
                    <i class="el-icon-delete"></i>
                    批量删除
                </el-button>
            </el-popconfirm>
        </el-form>

        <el-table
                ref="multipleTable"
                :data="tableData"
                tooltip-effect="dark"
                style="width: 100%; border-radius: 5px"
                border
                stripe
                @selection-change="handleSelectionChange">
            <el-table-column
                    type="selection"
                    width="55">
            </el-table-column>

            <el-table-column
                    prop="colName"
                    label="学院名称"
                    sortable
                    show-overflow-tooltip>
            </el-table-column>
            <el-table-column
                    prop="teaId"
                    label="教师编号"
                    sortable
                    show-overflow-tooltip>
            </el-table-column>
            <el-table-column
                    prop="teaUsername"
                    label="教师账号"
                    sortable
                    show-overflow-tooltip>
            </el-table-column>
            <el-table-column
                    prop="teaPassword"
                    label="教师密码"
                    sortable
                    show-overflow-tooltip>
            </el-table-column>
            <el-table-column
                    prop="teaName"
                    label="教师名称"
                    sortable
                    show-overflow-tooltip>
            </el-table-column>
            <el-table-column
                    prop="teaState"
                    label="教师状态"
                    sortable
                    show-overflow-tooltip>
                <template slot-scope="scope">
                    <el-tag  v-if="scope.row.teaState === 0" type="info" effect="dark">待审核-不可用</el-tag>
                    <el-tag  v-else-if="scope.row.teaState === 1" type="success" effect="dark">已审核-可用</el-tag>
                </template>
            </el-table-column>
            <el-table-column
                    prop="icon"
                    label="操作">

                <template slot-scope="scope">
                    <el-button type="text" @click="editHandle(scope.row.teaId)">编辑</el-button>
                    <el-divider direction="vertical"></el-divider>

                    <template>
                        <el-popconfirm title="确定删除该数据吗？" @confirm="delHandle(scope.row.teaId)">
                            <el-button type="text" slot="reference">删除</el-button>
                        </el-popconfirm>
                    </template>
                </template>
            </el-table-column>
        </el-table>
        <TeacherCenterDialog ref="Dialog" v-bind:editForm="editForm" v-bind:teaData="teaData"></TeacherCenterDialog>

        <!--  <pagination ref="Pagination"></pagination>-->
        <el-card class="box-card" style="height: 50px; margin-top: 10px;">
            <el-pagination
                    style="margin-top: -10px"
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    layout="total, sizes, prev, pager, next, jumper"
                    background
                    :page-sizes="[1, 8, 30, 50, 100]"
                    :current-page="this.searchForm.current"
                    :page-size="this.searchForm.size"
                    :total="total">
            </el-pagination>
        </el-card>
    </div>
</template>

<script>
    import TeacherCenterDialog from "../../components/CenterDialog/TeacherCenterDialog";
    export default {
        name: "Teacher",
        components:{
            TeacherCenterDialog
        },
        data() {
            return {
                editForm:{
                },
                tableData: [],
                teaData: {},
                multipleSelection: [],
                delBtlStatu: true,
                searchForm: {
                    colName: null,
                    teaId: null,
                    teaName: null,
                    size: 8,
                    current: 1,
                },
                total: 0,
            }
        },
        created() {
            this.getTeacher()

        },
        methods: {
            //查询、添加
            getTeacher() {
                console.log("搜索===>")
                this.$axios.post("/Teacher/getTeacher",this.searchForm).then(res => {
                    console.log("搜索结果==>>")
                    console.log(res)
                    this.tableData = res.data.data.content
                    this.searchForm.size = res.data.data.size
                    this.searchForm.current = res.data.data.current
                    this.total = res.data.data.datatotal
                    this.getTeaData()
                })
            },
            //获取学院列表
            getTeaData(){
                this.$axios.get("/College/getCollegelist").then(res =>{
                    this.teaData = res.data.data
                })
            },
            //修改
            editHandle(teaId){
                this.$axios.get('/Teacher/getById/'+teaId).then(res =>{
                    console.log("修改====>")
                    console.log(res)
                    this.editForm = res.data.data[0]
                    console.log("编辑获取的选项内容==>")
                    console.log(this.editForm)
                    this.centerDialog()
                })
            },
            //删除
            delHandle(teaId){
                let ids = []
                if(teaId){
                    ids.push(teaId)
                }else{
                    this.multipleSelection.forEach(row =>{
                        ids.push(row.teaId)
                    })
                }
                console.log("删除===========》")
                console.log(ids)
                this.$axios.post('/Teacher/delete',ids).then(res =>{
                    this.$message({
                        showClose: true,
                        message: '恭喜你，删除成功',
                        type: 'success',
                    });
                    this.getTeacher()
                })
            },
            //弹出--添加、修改--气泡框
            centerDialog(){
                this.$refs.Dialog.centerDialog(true)

            },
            //判断批量删除条件
            handleSelectionChange(val) {
                this.multipleSelection = val;
                this.delBtlStatu = val.length == 0
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
                this.searchForm.size = val
                this.getTeacher()
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.searchForm.current = val
                this.getTeacher()
            },
            //重置表单
            resetForm() {
                this.editForm = {}
            },
        }
    }
</script>

<style scoped>
    .el-pagination{
        float: right;
        margin-top: 22px;
        margin-right: 10px;
    }
</style>
