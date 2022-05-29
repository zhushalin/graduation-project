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
      <el-table-column type="index" width="55" label="序号"> </el-table-column>
      <el-table-column prop="testId" label="试题编号" width="176px">
      </el-table-column>
      <el-table-column prop="testContent" label="试题内容" width="176px">
      </el-table-column>
      <el-table-column prop="testId" label="试题编号" width="176px">
      </el-table-column>

      <el-table-column
        prop="accuracy"
        label="学生正确率"
        sortable
        show-overflow-tooltip
      >
        <template slot-scope="scope">{{ scope.row.accuracy }}%</template>
      </el-table-column>
      <el-table-column prop="correctCount" label="正确作答" width="176px">
      </el-table-column>
      <el-table-column prop="total" label="作答人数" width="176px">
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
        tpId: null,
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
    // this.getsubData();
  },
  methods: {
    //查询
    getExamScore() {
      this.searchForm.tpId = localStorage.getItem("tpId");

      this.$axios
        .get("/TestPaper/getById/" + this.searchForm.tpId)
        .then((res) => {
          console.log("查询===>");

          this.tableData = res.data.data;

          // this.searchForm.size = res.data.data.size;
          // this.searchForm.current = res.data.data.current;
          // this.datatotal = res.data.data.datatotal;
        });
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
