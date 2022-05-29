<template>
    <div>
    <el-form :inline="true" :model="searchForm" class="demo-form-inline" clearable>
        <el-form-item>
            <el-select v-model="searchForm.majorName" placeholder="专业名称" clearable>
                <el-option v-for="major in majorData" :label="major.majorName" :value="major.majorId"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item>
            <el-input v-model="searchForm.subId" placeholder="科目ID" clearable></el-input>
        </el-form-item>
        <el-form-item>
            <el-input v-model="searchForm.subName" placeholder="科目名称" clearable></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="getSubject" >
                <i class="el-icon-search" ></i>
                搜索
            </el-button>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="centerDialog" v-if="hasAuth()">
                <i class="el-icon-plus" ></i>添加
            </el-button>
        </el-form-item>
        <el-popconfirm title="确定删除所选内容吗？" @confirm="delHandle(null)" v-if="hasAuth()">
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
            style="width: 100% ; border-radius: 5px"
            border
            stripe
            @selection-change="handleSelectionChange">
        <el-table-column
                type="selection"
                width="55"
                v-if="hasAuth()">
        </el-table-column>
        <el-table-column
                type="index"
                width="55"
                v-if="!hasAuth()">
        </el-table-column>
        <el-table-column
                prop="majorName"
                label="所属专业"
                sortable
                show-overflow-tooltip>
        </el-table-column>
        <el-table-column
                prop="subId"
                label="科目ID"
                sortable
                show-overflow-tooltip>
            <template slot-scope="scope">{{ scope.row.subId }}</template>
        </el-table-column>
        <el-table-column
                prop="subName"
                label="科目名称"
                sortable
                show-overflow-tooltip>
        </el-table-column>

    </el-table>
    <SubCenterDialog ref="Dialog" v-bind:editForm="editForm" v-bind:majorData="majorData"></SubCenterDialog>


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
    import SubCenterDialog from "../../components/CenterDialog/SubCenterDialog";
    export default {
        name: "Subject",
        components: {
            SubCenterDialog,
        },
        data() {
            return {
                editForm:{},
                tableData: [],
                majorData: {},
                multipleSelection: [],
                delBtlStatu: true,
                searchForm: {
                    subName: '',
                    subId: null,
                    majorName: '',
                    size: 8,
                    current: 1,
                },
                total: 0,
            }
        },
        created() {
            this.getSubject()
        },
        methods: {
            //查询
            getSubject() {
                console.log("搜索===>")
                this.$axios.post("/Subject/getSubject",this.searchForm
                ).then(res => {
                    console.log("搜索结果==>>")
                    console.log(res)
                    this.tableData = res.data.data.content
                    this.searchForm.size = res.data.data.size
                    this.searchForm.current = res.data.data.current
                    this.total = res.data.data.datatotal
                    this.getmajorData()
                })
            },
            //获取专业列表
            getmajorData(){
                this.$axios.get("/Subject/getMajorData").then(res =>{
                    this.majorData = res.data.data
                })
            },
            //修改
            editHandle(subId){
                this.$axios.get('/Subject/getById/'+subId).then(res =>{
                    console.log("修改====>")
                    console.log(res)
                    this.editForm = res.data.data[0]
                    console.log("编辑获取的选项内容==>")
                    console.log(this.editForm)
                    this.centerDialog()
                })
            },
            //删除
            delHandle(subId){
                let ids = []
                if(subId){
                    ids.push(subId)
                }else{
                    this.multipleSelection.forEach(row =>{
                        ids.push(row.subId)
                    })
                }
                console.log("删除===========》")
                console.log(ids)
                this.$axios.post('/Subject/delete',ids).then(res =>{
                    this.$message({
                        showClose: true,
                        message: '恭喜你，删除成功',
                        type: 'success',
                    });
                    this.getSubject()
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
                this.getSubject()
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.searchForm.current = val
                this.getSubject()
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
