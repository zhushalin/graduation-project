<template>
  <div>
    <el-form
      :inline="true"
      :model="searchForm"
      class="demo-form-inline"
      clearable
    >
      <el-form-item>
        <el-select v-model="searchForm.subId" placeholder="选择科目" clearable>
          <el-option
            v-for="sub in subData"
            :label="sub.subName"
            :value="sub.subId"
            :key="sub.subId"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select
          v-model="searchForm.examState"
          placeholder="考试状态"
          clearable
        >
          <el-option label="未开始" value="0"></el-option>
          <el-option label="正在进行" value="1"></el-option>
          <el-option label="已结束" value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-input
          v-model="searchForm.tpName"
          placeholder="搜索试卷名称"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="searchForm.examTime"
          type="datetime"
          placeholder="选择某时间段之后的考试"
          align="right"
          :picker-options="pickerOptions"
        >
        </el-date-picker>
      </el-form-item>
      <br />
      <el-form-item>
        <el-button type="primary" @click="getTestPaper">
          <i class="el-icon-search"></i>
          搜索
        </el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="centerDialog">
          <i class="el-icon-plus"></i>添加
        </el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="success" @click="resetForm">
          <i class="el-icon-loading"></i>重置
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
      style="width: 100% ; border-radius: 5px"
      border
      stripe
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" v-if="hasAuth()">
      </el-table-column>
      <el-table-column type="index" width="55" v-if="!hasAuth()">
      </el-table-column>
      <el-table-column
        prop="teaId"
        label="所属教师id"
        sortable
        show-overflow-tooltip
        v-if="hasAuth()"
      >
        <template slot-scope="scope">{{ scope.row.teaId }}</template>
      </el-table-column>

      <el-table-column
        prop="subName"
        label="所属科目"
        sortable
        show-overflow-tooltip
      >
        <template slot-scope="scope">{{ scope.row.subName }}</template>
      </el-table-column>

      <el-table-column
        prop="tpName"
        label="考试名称"
        sortable
        show-overflow-tooltip
      >
        <template slot-scope="scope">{{ scope.row.tpName }}</template>
      </el-table-column>
      <el-table-column prop="examTime" label="考试时间" sortable width="176px">
      </el-table-column>
      <el-table-column prop="examLong" label="考试时长" sortable>
        <template slot-scope="scope">{{ scope.row.examLong }}分钟</template>
      </el-table-column>
      <el-table-column
        prop="tpType"
        slot-scope
        label="试卷类型"
        sortable
        show-overflow-tooltip
      >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.tpType === 0" type="primary" effect="dark"
            >完全公开</el-tag
          >
          <el-tag
            v-else-if="scope.row.tpType === 1"
            type="warning"
            effect="dark"
            >需要入考码</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="examPassword" label="入考码" width="176px">
      </el-table-column>
      <el-table-column prop="examState" label="状态" show-overflow-tooltip>
        <template slot-scope="scope">
          <el-tag v-if="scope.row.examState === 0" type="info" effect="dark"
            >未开始</el-tag
          >
          <el-tag
            v-else-if="scope.row.examState === 1"
            type="primary"
            effect="dark"
            >正在进行</el-tag
          >
          <el-tag
            v-else-if="scope.row.examState === 2"
            type="warning"
            effect="dark"
            >已结束</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="icon" label="操作" width="420px">
        <template slot-scope="scope">
          <el-button
            type="text"
            @click="editHandle(scope.row.tpId)"
            size="small"
          >
            <i class="el-icon-edit"></i>
            修改
          </el-button>
          <el-divider direction="vertical"></el-divider>
          <template>
            <el-popconfirm
              title="确定删除该试题吗？"
              @confirm="delHandle(scope.row.tpId)"
            >
              <el-button type="text" slot="reference" size="small">
                <i class="el-icon-close"></i> 删除
              </el-button>
            </el-popconfirm>
          </template>
          <el-divider direction="vertical"></el-divider>
          <el-button
            type="text"
            @click="
              addHandle(scope.row.tpId, scope.row.tpName, scope.row.subId)
            "
            size="small"
          >
            <i class="el-icon-download"></i>
            试题添加
          </el-button>
          <el-divider direction="vertical"></el-divider>
          <el-button
            type="text"
            @click="
              GotoHandle(scope.row.tpId, scope.row.tpName, scope.row.subId)
            "
            size="small"
          >
            <i class="el-icon-search"></i>
            试卷详情
          </el-button>
          <el-button
            type="text"
            @click="OpenDialog(scope.row.tpId)"
            size="small"
          >
            <i class="el-icon-search"></i>
            上传试卷
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <TestpaperCenterDialog
      ref="Dialog"
      v-bind:editForm="editForm"
      v-bind:subData="subData"
    ></TestpaperCenterDialog>
    <!-- 弹框定义 -->
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
        <!-- <el-form
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
        </el-form> -->
        <el-upload
          class="upload-demo"
          action="http://localhost:8085/upload"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :before-remove="beforeRemove"
          :on-success="handleSuccess"
          multiple
          :limit="3"
          :on-exceed="handleExceed"
          :file-list="fileList"
        >
          <el-button size="small" type="primary">点击上传</el-button>
          <div slot="tip" class="el-upload__tip">
            只能上传jpg/png文件，且不超过500kb
          </div>
        </el-upload>
      </template>
    </sys-dialog>
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
        :total="datatotal"
      >
      </el-pagination>
    </el-card>
  </div>
</template>

<script>
import TestpaperCenterDialog from "../../components/CenterDialog/TestpaperCenterDialog";
import SysDialog from "../../components/System/SysDialog.vue";
export default {
  name: "TpList",
  inject: ["reload"],
  components: {
    TestpaperCenterDialog /*pagination*/,
    SysDialog,
  },
  data() {
    return {
      fileList: [],
      //表单绑定的数据域
      addModel: {
        tpId: "",
        files: "",
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
      editForm: {
        // 总分数
        examTotal: 100,
        // 开放类型
        tpType: 1,
        //考试状态
        examState: 0,
      },

      tableData: [],
      subData: [],
      multipleSelection: [],
      delBtlStatu: true,
      searchForm: {
        examState: null,
        tpName: null,
        examTime: null,
        size: 8,
        current: 1,
      },
      datatotal: 0,
      rules: {
        title: [
          {
            adviceText: "change",
            required: true,
            message: "请填标题",
          },
        ],
      },
      pickerOptions: {
        shortcuts: [
          {
            text: "最近一周",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近一个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近三个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit("pick", [start, end]);
            },
          },
        ],
      },
    };
  },
  created() {
    this.editForm.teaId = localStorage.getItem("id");
    if (localStorage.getItem("role") === "teacher") {
      this.searchForm.teaId = localStorage.getItem("id");
    }
    this.getTestPaper();
    this.getsubData();
  },
  methods: {
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handleSuccess(file, fileList) {
      console.log("files====>");

      this.addModel.files = this.addModel.files + "，" + file.data;
      var s = this.addModel.files;
      if (s.substr(0, 1) == "，") s = s.substr(1);
      this.addModel.files = s;
    },
    handlePreview(file) {
      window.open(file.response.url);
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 3 个文件，本次选择了 ${
          files.length
        } 个文件，共选择了 ${files.length + fileList.length} 个文件`
      );
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    //查询、添加
    getTestPaper() {
      console.log("搜索===>");
      console.log(this.searchForm);
      this.$axios
        .post("/TestPaper/getTestPaper", this.searchForm)
        .then((res) => {
          console.log("搜索结果==>>");

          this.tableData = res.data.data.content;
          this.searchForm.size = res.data.data.size;
          this.searchForm.current = res.data.data.current;
          this.datatotal = res.data.data.datatotal;
        });
    },
    //获取科目列表
    getsubData() {
      this.$axios.get("/TestBank/getSubData").then((res) => {
        this.subData = res.data.data;
      });
    },
    //修改
    editHandle(tpId) {
      this.$axios.get("/TestPaper/getById/" + tpId).then((res) => {
        console.log("修改====>");
        console.log(res);
        this.editForm = res.data.data[0];
        console.log("编辑获取的选项内容==>");
        console.log(this.editForm);
        this.centerDialog();
      });
    },
    addHandle(tpId, tpName, subId) {
      sessionStorage.setItem("tpId", tpId);
      sessionStorage.setItem("subId", subId);
      sessionStorage.setItem("tpName", tpName);
      this.$router.push({ name: "TpListAdd" });
    },

    //试卷详情
    GotoHandle(tpId, tpName) {
      this.openFullScreen2();
      this.$message({
        duration: 1000,
        message: "试卷加载中，请稍等",
        type: "success",
        onClose: () => {
          sessionStorage.setItem("examTpId", tpId);
          sessionStorage.setItem("examTpName", tpName);
          console.log(sessionStorage.getItem("examTpName"));
          this.$router.push({ name: "ShowExam" });
        },
      });
    },
    openFullScreen2() {
      const loading = this.$loading({
        lock: true,
        text: "正在努力加载试卷中",
        background: "rgba(0, 0, 0, 0.7)",
      });
      setTimeout(() => {
        loading.close();
      }, 1000);
    },
    // showHandle(tpId, tpName, subId) {
    //   console.log("========================================>><><><><");
    //   console.log(tpId);
    //   console.log(tpName);
    //   console.log(subId);
    //   console.log("========================================>><><><><");
    //   sessionStorage.setItem("tpId", tpId);
    //   sessionStorage.setItem("subId", subId);
    //   sessionStorage.setItem("tpName", tpName);
    //   this.$router.push({ name: "ShowExam" });
    // },
    OpenDialog(tpId) {
      this.addModel.tpId = tpId;
      this.addDialog.visible = true;
    },
    onConfirm() {
      this.$axios.post("/TestPaper/update", this.addModel).then((res) => {
        this.$message({
          showClose: true,
          message: "恭喜你，上传成功",
          type: "success",
        });

        this.addDialog.visible = false;
      });
    },

    //弹框关闭
    onClose() {
      this.addDialog.visible = false;
    },
    //删除
    delHandle(tpId) {
      let ids = [];
      if (tpId) {
        ids.push(tpId);
      } else {
        this.multipleSelection.forEach((row) => {
          ids.push(row.tpId);
        });
      }
      console.log("删除===========》");
      console.log(ids);
      this.$axios.post("/TestPaper/delete", ids).then((res) => {
        this.$message({
          showClose: true,
          message: "恭喜你，删除成功",
          type: "success",
        });
        this.getTestPaper();
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
      this.getTestPaper();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.searchForm.current = val;
      this.getTestPaper();
    },
    //重置表单
    resetForm() {
      this.reload();
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
