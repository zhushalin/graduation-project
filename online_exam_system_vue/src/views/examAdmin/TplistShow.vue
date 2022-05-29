<template>
    <div>
        <el-breadcrumb separator="|" style="margin-bottom: 15px; margin-left: 5px">
            <el-breadcrumb-item :to="{ path: '/examAdmin/TpList' }">
                <i class="el-icon-back"></i>
                返回
            </el-breadcrumb-item>

            <el-breadcrumb-item class="testtypevo">{{this.tpName}}</el-breadcrumb-item>
            <el-breadcrumb-item class="testtypevo">单选题数量：{{testTypeVO.radioCount}}</el-breadcrumb-item>
            <el-breadcrumb-item class="testtypevo">多选题数量：{{testTypeVO.multiCount}}</el-breadcrumb-item>
            <el-breadcrumb-item class="testtypevo">判断题数量：{{testTypeVO.judgeCount}}</el-breadcrumb-item>
            <el-breadcrumb-item class="testtypevo">总分：{{testTypeVO.scoreTotal}}</el-breadcrumb-item>
            <el-breadcrumb-item></el-breadcrumb-item>

        </el-breadcrumb>
        <el-form :inline="true" :model="searchForm" class="demo-form-inline" clearable>
            <el-form-item>
                <el-select v-model="searchForm.testType" placeholder="题目类型" clearable>
                    <el-option label="单选题" value="1"></el-option>
                    <el-option label="多选题" value="2"></el-option>
                    <el-option label="判断题" value="3"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-input v-model="searchForm.testContent" placeholder="题目内容" clearable></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="getTestBank" >
                    <i class="el-icon-search" ></i>
                    搜索
                </el-button>
            </el-form-item>
        </el-form>
        <el-table
                ref="multipleTable"
                :data="tableData"
                tooltip-effect="dark"
                style="width: 100%"
                border
                stripe
                @selection-change="handleSelectionChange">
            <el-table-column
                    type="index"
                    width="55"
            >
            </el-table-column>

            <el-table-column
                    prop="subName"
                    label="科目"
                    sortable
                    show-overflow-tooltip>
            </el-table-column>
            <el-table-column
                    prop="testType"
                    label="试题类型"
                    sortable
            >
                <template slot-scope="scope">
                    <el-tag  v-if="scope.row.testType === 1" type="warning" effect="dark">单选题</el-tag>
                    <el-tag  v-else-if="scope.row.testType === 2" type="dark" effect="dark">多选题</el-tag>
                    <el-tag  v-else-if="scope.row.testType === 3" type="success" effect="dark">判断题</el-tag>
                </template>
            </el-table-column>
            <el-table-column
                    prop="testContent"
                    label="试题内容"
                    sortable
                    show-overflow-tooltip>
            </el-table-column>

            <el-table-column
                    prop="optionA"
                    label="选项A"
                    show-overflow-tooltip>
            </el-table-column>
            <el-table-column
                    prop="optionB"
                    label="选项B"
                    show-overflow-tooltip>
            </el-table-column>
            <el-table-column
                    prop="optionC"
                    label="选项C"
                    show-overflow-tooltip>
            </el-table-column>
            <el-table-column
                    prop="optionD"
                    label="选项D"
                    show-overflow-tooltip>
            </el-table-column>
            <el-table-column
                    prop="answer"
                    label="正确答案"
                    show-overflow-tooltip>
                <template slot-scope="scope">
                    <el-tag type="" effect="dark">{{scope.row.answer}}</el-tag>
                </template>
            </el-table-column>
            <el-table-column
                    prop="testType"
                    label="分值"
                    sortable
                    show-overflow-tooltip>
                <template slot-scope="scope">
                    <el-tag  v-if="scope.row.testType === 1" type="warning" effect="dark">3</el-tag>
                    <el-tag  v-else-if="scope.row.testType === 2" type="dark" effect="dark">5</el-tag>
                    <el-tag  v-else-if="scope.row.testType === 3" type="success" effect="dark">2</el-tag>
                </template>
            </el-table-column>
            <el-table-column
                    prop="icon"
                    label="操作"
                    width="220px">
                <template slot-scope="scope">
                    <template>
                        <el-popconfirm title="确定将该试题移出试卷吗？" @confirm="delHandle(scope.row.testId)">
                            <el-button type="text" slot="reference" size="small">
                                <i class="el-icon-close"></i> 移出
                            </el-button>
                        </el-popconfirm>
                    </template>
                </template>
            </el-table-column>
        </el-table>

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
                    :total="datatotal">
            </el-pagination>
        </el-card>
    </div>
</template>

<script>
    export default {
        inject:['reload'],
        name: "TplistShow",
        components: {

        },
        data() {
            return {
                tpName: null,
                editForm:{
                    answer: []
                },
                testTypeVO: {},
                subForm:{
                    testId: null,
                    tpId: localStorage.getItem('tpId'),
                },
                tableData: [],
                subData: [],
                multipleSelection: [],
                delBtlStatu: true,
                searchForm: {
                    subId: null,
                    testType: null,
                    teaId: null,
                    testContent: '',
                    size: 8,
                    current: 1,
                },
                datatotal: 0,
            }
        },
        created() {
            this.searchForm.subId = sessionStorage.getItem("subId")
            this.editForm.teaId = sessionStorage.getItem("id")
            this.searchForm.tpId = sessionStorage.getItem('tpId')
            this.tpName = sessionStorage.getItem('tpName')
            console.log("----------------<><><><")
            console.log(this.searchForm.tpId)
            console.log(this.searchForm.subId)
            console.log("----------------<><><><")
            this.getTestBank()
        },
        methods: {
            //查询所有相关科目的试题
            getTestBank() {
                console.log("搜索===>")
                this.$axios.post("/Tplist/aboutTplist",this.searchForm).then(res => {
                    console.log("搜索结果==>>")
                    console.log(res)
                    this.tableData = res.data.data.content
                    this.testTypeVO = res.data.data.testTypeVO
                    this.searchForm.size = res.data.data.size
                    this.searchForm.current = res.data.data.current
                    this.datatotal = res.data.data.datatotal
                })
            },
            //删除--删除关联表数据，达到撤销效果
            delHandle(testId){
                let ids = []
                if(testId){
                    ids.push(testId)
                }else{
                    this.multipleSelection.forEach(row =>{
                        ids.push(row.testId)
                    })
                }
                console.log("移除===========》")
                console.log(ids)
                this.searchForm.ids  = ids;
                console.log(this.searchForm)
                this.$axios.post('/Tplist/delete',this.searchForm).then(res =>{
                    this.$message({
                        showClose: true,
                        message: '你好，删除已完成',
                        type: 'success',
                    });
                    this.getTestBank()
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
                this.getTestBank()
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.searchForm.current = val
                this.getTestBank()
            },
            //重置表单
            resetForm() {
                this.reload();
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
    .testtypevo{
        font-weight: bold
    }
</style>
