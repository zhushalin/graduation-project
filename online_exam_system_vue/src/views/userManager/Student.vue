<template>
  <div>
    <el-form
      :inline="true"
      :model="searchForm"
      class="demo-form-inline"
      clearable
    >
      <!--<el-form-item>
                <el-select v-model="searchForm.colName" placeholder="选择专业" clearable>
                    <el-option v-for="col in colData" :label="col.colName" :value="col.colName">
                        <el-select v-model="searchForm.majorId" :placeholder="col.colName" clearable>
                            <el-option v-for="maj in col.children" :label="maj.majorName" :value="maj.majorId"></el-option>
                        </el-select>
                    </el-option>
                </el-select>
            </el-form-item>-->
      <el-form-item class="block">
        <el-cascader
          v-model="searchForm.majorId"
          :options="options"
          :props="defaultParams"
          :clearable="true"
          :show-all-levels="false"
          @change="handleChange"
          placeholder="选择学院、专业"
        >
        </el-cascader>
      </el-form-item>

      <el-form-item>
        <el-input
          v-model="searchForm.stuId"
          placeholder="学生编号"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-input
          v-model="searchForm.stuName"
          placeholder="学生名称"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getStudent">
          <i class="el-icon-search"></i>
          搜索
        </el-button>
      </el-form-item>
      <el-form-item v-if="hasAuth()">
        <el-button type="primary" @click="centerDialog">
          <i class="el-icon-plus"></i>添加
        </el-button>
      </el-form-item>
      <el-popconfirm
        title="确定删除所选内容吗？"
        @confirm="delHandle(null)"
        v-if="hasAuth()"
      >
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
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55px" v-if="hasAuth()">
      </el-table-column>
      <el-table-column type="index" width="55px" v-if="!hasAuth()">
      </el-table-column>

      <el-table-column
        prop="colName"
        label="学院名称"
        sortable
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        prop="majorName"
        label="专业名称"
        sortable
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        prop="stuId"
        label="学生编号"
        sortable
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        prop="stuUsername"
        label="学生账号"
        sortable
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        prop="stuPassword"
        label="学生密码"
        sortable
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        prop="stuName"
        label="学生名称"
        sortable
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        prop="stuState"
        label="学生状态"
        sortable
        show-overflow-tooltip
      >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.stuState === 0" type="info" effect="dark"
            >待审核-不可用</el-tag
          >
          <el-tag
            v-else-if="scope.row.stuState === 1"
            type="success"
            effect="dark"
            >已审核-可用</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="icon" label="操作" v-if="hasAuth">
        <template slot-scope="scope">
          <el-button
            type="text"
            @click="editHandle(scope.row.stuId)"
            v-if="hasAuth()"
            >编辑</el-button
          >
          <el-divider direction="vertical"></el-divider>

          <template>
            <el-popconfirm
              title="确定删除该数据吗？"
              @confirm="delHandle(scope.row.stuId)"
              v-if="hasAuth()"
            >
              <el-button type="text" slot="reference">删除</el-button>
            </el-popconfirm>
          </template>
        </template>
      </el-table-column>
    </el-table>
    <StudentCenterDialog
      ref="Dialog"
      v-bind:editForm="editForm"
      v-bind:options="options"
      v-bind:defaultParams="defaultParams"
    ></StudentCenterDialog>

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
        :total="total"
      >
      </el-pagination>
    </el-card>
  </div>
</template>

<script>
import StudentCenterDialog from "../../components/CenterDialog/StudentCenterDialog";
export default {
  name: "Student",
  components: {
    StudentCenterDialog,
  },
  data() {
    return {
      options: [],
      defaultParams: {
        label: "majorName",
        value: "majorId",
        checkStrictly: true,
      },
      editForm: {},
      tableData: [],
      majorData: {},
      colData: {},
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
    };
  },
  created() {
    this.getStudent();
  },
  methods: {
    handleChange(value) {
      if (value != null) {
        this.searchForm.colId = value[0];
        this.searchForm.majorId = value[1];
      }
      console.log(value);
    },
    //查询、添加
    getStudent() {
      console.log("搜索===>");
      this.$axios.post("/Student/getStudent", this.searchForm).then((res) => {
        console.log("搜索结果==>>");
        console.log(res);
        this.tableData = res.data.data.content;
        this.searchForm.size = res.data.data.size;
        this.searchForm.current = res.data.data.current;
        this.total = res.data.data.datatotal;
        this.getColData();
      });
    },
    //获取学院列表
    getColData() {
      this.$axios.get("/College/getCollegelist").then((res) => {
        this.options = res.data.data;
        console.log("==================>");
        console.log(this.options);
      });
    },
    //获取专业列表
    getmajorData() {
      this.$axios.get("/Subject/getMajorData").then((res) => {
        this.majorData = res.data.data;
      });
    },
    //修改
    editHandle(stuId) {
      this.$axios.get("/Student/getById/" + stuId).then((res) => {
        console.log("修改====>");
        console.log(res);
        this.editForm = res.data.data[0];
        console.log("编辑获取的选项内容==>");
        console.log(this.editForm);
        this.centerDialog();
      });
    },
    //删除
    delHandle(stuId) {
      let ids = [];
      if (stuId) {
        ids.push(stuId);
      } else {
        this.multipleSelection.forEach((row) => {
          ids.push(row.stuId);
        });
      }
      console.log("删除===========》");
      console.log(ids);
      this.$axios.post("/Student/delete", ids).then((res) => {
        this.$message({
          showClose: true,
          message: "恭喜你，删除成功",
          type: "success",
        });
        this.getStudent();
      });
    },
    //弹出--添加、修改--气泡框
    centerDialog() {
      this.$refs.Dialog.centerDialog(true);
    },
    //判断批量删除条件
    handleSelectionChange(val) {
      this.multipleSelection = val;
      this.delBtlStatu = val.length == 0;
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.searchForm.size = val;
      this.getStudent();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.searchForm.current = val;
      this.getStudent();
    },
    //重置表单
    resetForm() {
      this.editForm = {};
    },
  },
};
</script>

<style scoped>
.el-pagination {
  float: right;
  margin-top: 22px;
  margin-right: 10px;
}
</style>
