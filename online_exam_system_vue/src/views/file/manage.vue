<template>
  <div>
    <el-form
      :inline="true"
      :model="searchForm"
      class="demo-form-inline"
      clearable
    >
      <el-form-item v-if="false">
        <el-input
          v-model="searchForm.title"
          placeholder="标题"
          clearable
        ></el-input>
      </el-form-item>

      <el-form-item v-if="false">
        <el-button type="primary" @click="getByTitle">
          <i class="el-icon-search"></i>
          搜索
        </el-button>
      </el-form-item>
      <el-form-item v-if="role!='student'">
        <el-button type="primary" @click="centerDialog">
          <i class="el-icon-plus"></i>添加
        </el-button>
      </el-form-item>
      <el-popconfirm v-if="false" title="确定删除所选内容吗？" @confirm="delHandle(null)">
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
      <el-table-column type="selection" width="55"> </el-table-column>

      <el-table-column
        prop="titile"
        label="标题"
        sortable
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        prop="content"
        label="内容"
        sortable
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        prop="fileurl"
        label="文件"
        sortable
        show-overflow-tooltip
      >
      </el-table-column>

      <el-table-column prop="icon" label="操作">
        <template slot-scope="scope">
          <el-button type="text" @click="editHandle(scope.row)">查看</el-button>
          <el-divider direction="vertical"></el-divider>

          <template v-if="role!='student'">
            <el-popconfirm
              title="确定删除该数据吗？"
              @confirm="delHandle(scope.row.id)"
            >
              <el-button type="text" slot="reference">删除</el-button>
            </el-popconfirm>
          </template>
        </template>
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
      :width="addDialog.width"
      :height="addDialog.height"
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
          <el-form-item prop="patientNum" label="资料名称">
            <el-input v-model="addModel.titile"></el-input>
          </el-form-item>

          <el-form-item prop="adviceText" label="资料内容">
            <el-input type="textarea" v-model="addModel.content"></el-input>
          </el-form-item>
          
          <el-form-item label="文件">
              <el-upload
                ref="upload"
                :limit="1"
                :action="upload.url"
                :disabled="upload.isUploading"
                :on-progress="handleFileUploadProgress"
                :on-remove="handleRemove"
                :on-success="handleFileSuccess"
                :file-list="fileList"
                drag
              >
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              <div class="el-upload__tip text-center" slot="tip">
              </div>
            </el-upload>
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
      role:localStorage.getItem("role"),
      fileList:[],
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
        type: "2"
      },
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 上传的地址
        url: "http://localhost:8085/file/upload"
      },

      total: 0,
      //表单绑定的数据域
      addModel: {
        editType: "",
        title: "",
        content: "",
        fileurl: "",
        type: "1"
      },
      //设置弹框属性
      addDialog: {
        title: "",
        height: 400,
        width: 650,
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
    // 上传成功回调
    handleUploadSuccess(res) {
      debugger;
      this.fileList2.push({ name:res, url: res });
      this.form.img = JSON.stringify(this.fileList2);
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      debugger;
      this.fileList.push({ name:response.data, url: response.data });
      this.upload.open = false;
      this.upload.isUploading = false;
      this.addModel.fileurl = response.data;
      this.$refs.upload.clearFiles();
      // this.$modal.msgSuccess("导入成功");
      // this.getList();
    },
    newSubmitForm(){//确定上传
        this.$refs.newupload.submit();
    },
    getTeacher() {
      this.$axios
        .get(
          "/file/getAll/page?current="+this.searchForm.page+"&page=" + this.searchForm.page + "&limit=" + this.searchForm.limit
        )
        .then((res) => {
          console.log("查询====>");
          console.log(res);
          this.tableData = res.data.data.content;
        });
    },

    //修改
    editHandle(row) {
      //清空表单
      this.$resetForm("addForm", this.addModel);
      //把当前要查看的数据复制到表单数据域
      this.$objCopy(this.addModel, row);
      //设置查看属性
      this.addModel.editType = "1";
      //设置弹框属性
      this.addDialog.title = "查看";
      this.addDialog.visible = true;
      this.fileList=[];
      this.fileList.push({ name:row.fileurl, url: row.fileurl });
    },
    onConfirm() {
      this.$refs.addForm.validate(async (valid) => {
        if (valid) {
          let res = null;
          if (this.addModel.editType === "0") {
            this.addModel.type="1";
            this.$axios.post("/file/add", this.addModel).then((res) => {
              this.getTeacher();
              this.$message({
                showClose: true,
                message: "恭喜你，操作成功",
                type: "success",
              });
              this.addDialog.visible = false;
            });
          } else {
            this.addModel.type="1";
            this.$axios.post("/file/update", this.addModel).then((res) => {
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
      // let ids = [];
      // if (id) {
      //   ids.push(id);
      // } else {
      //   this.multipleSelection.forEach((row) => {
      //     ids.push(row.id);
      //   });
      // }
      console.log("删除===========》");
      this.$axios.get("/file/delete/"+id).then((res) => {
        debugger;
        this.$message({
          showClose: true,
          message: "恭喜你，操作成功",
          type: "success",
        });
        this.subData = res.data.data;
              this.getTeacher();
      });
    },
    //弹出--添加、修改--气泡框
    centerDialog() {
      this.addModel={};
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
