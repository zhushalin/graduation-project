<template>
    <div>
        <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                layout="total, sizes, prev, pager, next, jumper"
                background
                :page-sizes="[10, 30, 50, 100]"
                :current-page="current"
                :page-size="size"
                :total="total">
        </el-pagination>
    </div>
</template>

<script>
    export default {
        name: "pagination",
        size: 10,
        total: 0,
        current: 1,
        methods:{
            getPagination(pa){
                console.log("pa==========>")
                console.log(pa)
                this.size=pa.size,
                 this.total= pa.total,
                  this.current= pa.current
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
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
