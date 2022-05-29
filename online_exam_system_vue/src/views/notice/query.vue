<template>
  <div>
    <el-table
      ref="multipleTable"
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%; border-radius: 5px"
      border
      stripe
      @selection-change="handleSelectionChange"
    >
     </el-table-column>
        <el-table-column
        align="center"
        label="序号"
        type="index"
         sortable
         show-overflow-tooltip
      ></el-table-column>
      <el-table-column
        prop="title"
        label="公告标题"
        sortable
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        prop="content"
        label="公告内容"
        sortable
        show-overflow-tooltip
      >
      </el-table-column>
    </el-table>

    <!--  <pagination ref="Pagination"></pagination>-->
    <el-card class="box-card" style="height: 50px; margin-top: 10px;">
      <el-pagination
        style="margin-top: -10px"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        layout="total, sizes, prev, pager, next, jumper"
        background
        :page-sizes="[1, 5, 10, 50, 100]"
        :current-page="this.searchForm.page"
        :page-size="this.searchForm.limit"
      >
      </el-pagination>
    </el-card>
    <!-- 弹框定义 -->
    <sys-dialog
      :title="addDialog.title"
      :height="addDialog.height"
      :width="addDialog.width"
      :visible="addDialog.visible"
      @onClose="onClose"
      @onConfirm="onConfirm"
    >
      <template slot="content">
        <el-form
          :model="addModel"
          ref="addForm"
          :rules="rules"
          label-width="80px"
          :inline="false"
          size="small"
        >
          <el-form-item prop="patientNum" label="公告标题">
            <el-input v-model="addModel.title"></el-input>
          </el-form-item>

          <el-form-item prop="adviceText" label="公告内容">
            <el-input type="textarea" v-model="addModel.content"></el-input>
          </el-form-item>
        </el-form>
      </template>
    </sys-dialog>
  </div>
</template>

<script>
import SysDialog from "../../components/System/SysDialog.vue";
export default {
  name: "Teacher",
  components: {
    SysDialog,
  },
  data() {
    return {
      editForm: {},
      tableData: [],
      records: {},
      multipleSelection: [],
      delBtlStatu: true,
      searchForm: {
        title: null,
        content: null,
        page: 1,
        limit: 5,
      },

      total: 0,
      //表单绑定的数据域
      addModel: {
        editType: "",
        title: "",
        content: "",
        adminId: 1,
      },
      //设置弹框属性
      addDialog: {
        title: "",
        height: 200,
        width: 450,
        visible: false,
      },
      //表格高度
      tableHeight: 0,
      rules: {
        title: [
          {
            adviceText: "change",
            required: true,
            message: "请填标题",
          },
        ],
      },
    };
  },
  created() {
    this.getTeacher();
  },
  methods: {
    getByTitle() {
      this.$axios
        .get(
          "/notice/getByTitle?page=" +
            this.searchForm.page +
            "&limit=" +
            this.searchForm.limit +
            "&title=" +
            this.searchForm.title
        )
        .then((res) => {
          console.log("查询====>");
          console.log(res);
          this.tableData = res.data.data.records;
        });
    },
    getTeacher() {
      this.$axios
        .get(
          "/notice/page?page=" +
            this.searchForm.page +
            "&limit=" +
            this.searchForm.limit
        )
        .then((res) => {
          console.log("查询====>");
          console.log(res);
          this.tableData = res.data.data.records;
        });
    },

    //修改
    editHandle(row) {
      //清空表单
      this.$resetForm("addForm", this.addModel);
      //把当前要编辑的数据复制到表单数据域
      this.$objCopy(this.addModel, row);
      //设置编辑属性
      this.addModel.editType = "1";
      //设置弹框属性
      this.addDialog.title = "编辑公告";
      this.addDialog.visible = true;
    },
    onConfirm() {
      this.$refs.addForm.validate(async (valid) => {
        if (valid) {
          let res = null;
          if (this.addModel.editType === "0") {
            this.$axios.post("/notice/add", this.addModel).then((res) => {
              this.getTeacher();
              this.$message({
                showClose: true,
                message: "恭喜你，操作成功",
                type: "success",
              });
              this.addDialog.visible = false;
            });
          } else {
            this.$axios.post("/notice/update", this.addModel).then((res) => {
              this.getTeacher();
              this.$message({
                showClose: true,
                message: "恭喜你，操作成功",
                type: "success",
              });

              this.addDialog.visible = false;
            });
          }
        }
      });
    },
    //弹框关闭
    onClose() {
      this.addDialog.visible = false;
    },
    //删除
    delHandle(id) {
      let ids = [];
      if (id) {
        ids.push(id);
      } else {
        this.multipleSelection.forEach((row) => {
          ids.push(row.id);
        });
      }
      console.log("删除===========》");

      this.$axios.post("/notice/delete", ids).then((res) => {
        this.$message({
          showClose: true,
          message: "恭喜你，删除成功",
          type: "success",
        });
        this.getTeacher();
      });
    },
    //弹出--添加、修改--气泡框
    centerDialog() {
      this.addModel.editType = "0";
      this.addDialog.visible = true;
    },
    //判断批量删除条件
    handleSelectionChange(val) {
      this.multipleSelection = val;
      this.delBtlStatu = val.length == 0;
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.searchForm.limit = val;
      this.getTeacher();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.searchForm.page = val;
      this.getTeacher();
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
