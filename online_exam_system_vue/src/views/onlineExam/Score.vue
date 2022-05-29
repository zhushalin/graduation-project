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
            v-for="(sub, index) in subData"
            :label="sub.subName"
            :value="sub.subId"
            :key="index"
          ></el-option>
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
      <el-form-item>
        <el-button type="primary" @click="getExamScore">
          <i class="el-icon-search"></i>
          搜索
        </el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="success" @click="resetForm">
          <i class="el-icon-loading"></i>重置
        </el-button>
      </el-form-item>
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
      <el-table-column type="index" width="55"> </el-table-column>

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
      <el-table-column prop="examTime" label="考试时间" width="176px">
      </el-table-column>
      <el-table-column prop="examLong" label="考试时长" width="86px">
        <template slot-scope="scope">{{ scope.row.examLong }}分钟</template>
      </el-table-column>

      <el-table-column prop="examTotal" label="试卷总分" width="86px">
        <template slot-scope="scope"> {{ scope.row.examTotal }}分 </template>
      </el-table-column>

      <el-table-column prop="examScore" label="试卷成绩" width="86px">
        <template slot-scope="scope"> {{ scope.row.examScore }}分 </template>
      </el-table-column>
      <el-table-column prop="examScore" label="文件" width="176px">
        <template slot-scope="scope"> {{ scope.row.files }} </template>
      </el-table-column>

      <!--<el-table-column
                    prop="examRes"
                    label="考试结果"
                    width="176px">
                <template slot-scope="scope">
                    <el-tag  v-if="scope.row.examRes === 0" type="danger" effect="dark" >不及格</el-tag>
                    <el-tag  v-else-if="scope.row.examRes === 1" type="primary" effect="dark">及格</el-tag>
                </template>
            </el-table-column>-->

      <el-table-column prop="icon" label="操作" sortable show-overflow-tooltip>
        <template slot-scope="scope">
          <!-- <el-button
            @click="GotoHandle(scope.row.tpId, scope.row.tpName)"
            size="small"
            type="primary"
            effect="dark"
          >
            <i class="el-icon-caret-right"></i>
            查看考试详情
          </el-button> -->

          <el-button
            type="text"
            @click="GotoHandle(scope.row.tpId, scope.row.tpName)"
            size="small"
          >
            <i class="el-icon-search"></i>
            试卷详情
          </el-button>

          <el-button
            type="text"
            @click="DownLoad(scope.row.files)"
            size="small"
          >
            <i class="el-icon-download"></i>
            试卷下载
          </el-button>
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
        :total="datatotal"
      >
      </el-pagination>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "Score",
  inject: ["reload"],
  data() {
    return {
      name: "",
      tableData: [],
      editForm: {},
      subData: [],
      multipleSelection: [],
      delBtlStatu: true,
      searchForm: {
        examState: null,
        tpName: null,
        examTime: null,
        size: 8,
        current: 1,
        stuId: null,
      },
      datatotal: 0,

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
    this.getExamScore();
    this.getsubData();
  },
  methods: {
    //查询
    getExamScore() {
      console.log("搜索===>");
      console.log(this.searchForm);
      this.searchForm.stuId = localStorage.getItem("id");
      this.$axios
        .post("/Score/getById/" + this.searchForm.stuId)
        .then((res) => {
          console.log("搜索结果==>>");
          console.log(res);
          if (res.data.data == null) {
            this.$message({
              duration: 1500,
              message: "您暂时还没有考试信息",
              type: "info",
            });
          } else {
            this.tableData = res.data.data.content;
            this.searchForm.size = res.data.data.size;
            this.searchForm.current = res.data.data.current;
            this.datatotal = res.data.data.datatotal;
          }
        });
    },
    DownLoad(filename) {
      let link = document.createElement("a");
      link.style.display = "none";
      link.href = "http://localhost:8085/download?filename=" + filename;
      document.body.appendChild(link);
      link.click();

      // this.$axios
      //   .get("/download?filename=" + filename, { responseType: "blob" })
      //   .then((res) => {
      //     console.log("查询====>");
      //     console.log(URL.createObjectURL(res));
      //   });
    },
    //获取科目列表
    getsubData() {
      this.$axios.get("/TestBank/getSubData").then((res) => {
        this.subData = res.data.data;
      });
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
