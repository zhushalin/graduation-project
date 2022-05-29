<template>
    <div>
        <el-table
                ref="multipleTable"
                :data="tableData"
                tooltip-effect="dark"
                style="width: 100% ; border-radius: 5px"
                border
                stripe
                >
            <el-table-column
                    prop="roleId"
                    label="角色ID"
                    show-overflow-tooltip>
                <template slot-scope="scope" >
                    <el-tag  type="danger" effect="dark">{{scope.row.roleId}}</el-tag>
                </template>
            </el-table-column>
            <el-table-column
                    prop="roleId"
                    label="角色名称"
                    show-overflow-tooltip>
                <template slot-scope="scope" >
                    <el-tag  v-if="scope.row.roleId === '100X'"  type="warning" effect="dark">管理员</el-tag>
                    <el-tag  v-else-if="scope.row.roleId === '200X'" type="dark" effect="dark">教师</el-tag>
                    <el-tag  v-else-if="scope.row.roleId === '300X'" type="success" effect="dark">学生</el-tag>
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
                :page-sizes="[1,8, 30, 50, 100]"
                :current-page="current"
                :page-size="size"
                :total="total">
        </el-pagination>
        </el-card>
    </div>
</template>

<script>
    export default {
        name: "Role",
        data() {
            return {
                tableData: [
                    {
                        roleId: '100X',
                    }, {
                        roleId: '200X',
                    }, {
                        roleId: '300X',
                    },
                ],
                size: 8,
                total: 3,
                current: 1,
            }
        },
        created() {

        },
        methods: {
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
                this.size = val
                this.getTestBank()
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.current = val
                this.getTestBank()
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
