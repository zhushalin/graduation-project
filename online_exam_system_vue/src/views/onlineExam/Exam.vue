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
      <el-form-item>
        <el-button type="primary" @click="getTestPaper">
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
      <el-table-column prop="examLong" label="考试时长">
        <template slot-scope="scope">{{ scope.row.examLong }}分钟</template>
      </el-table-column>
      <el-table-column
        prop="tpType"
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
      <el-table-column prop="icon" label="操作" sortable show-overflow-tooltip>
        <template slot-scope="scope">
          <el-button
            size="small"
            v-if="scope.row.examState === 0"
            type=""
            effect="dark"
            disabled
          >
            <i class="el-icon-bell"></i>
            还未开始
          </el-button>

          <el-button
            @click="GotoHandle(scope.row.tpId)"
            size="small"
            v-else-if="scope.row.examState === 1"
            type="primary"
            effect="dark"
          >
            <i class="el-icon-caret-right"></i>
            进入考试
          </el-button>

          <el-button
            size="small"
            v-else-if="scope.row.examState === 2"
            type="info"
            effect="dark"
            disabled
          >
            <i class="el-icon-s-unfold"></i>
            已结束
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <ExamCenterDialog
      ref="Dialog"
      v-bind:editForm="editForm"
      v-bind:subData="subData"
    ></ExamCenterDialog>
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
import ExamCenterDialog from "../../components/CenterDialog/ExamCenterDialog";
export default {
  name: "Exam",
  inject: ["reload"],
  components: {
    ExamCenterDialog,
  },

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
      },
      passExam: {},
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
    this.getTestPaper();
    this.getsubData();
  },
  methods: {
    //查询
    getTestPaper() {
      console.log("搜索===>");
      console.log(this.searchForm);
      this.$axios
        .post("/TestPaper/getTestPaper", this.searchForm)
        .then((res) => {
          console.log("搜索结果==>>");
          console.log(res);
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
    //进入考试
    GotoHandle(tpId) {
      this.passExam.tpId = tpId;
      this.passExam.stuId = localStorage.getItem("id");
      this.$axios.post("/TestPaper/getBystuId", this.passExam).then((res) => {
        console.log("考试描述====>");
        console.log(res);
        if (res.data.data == null) {
          this.$message({
            duration: 1500,
            message: "已经考过了，不能重复考试哦",
            type: "danger",
          });
        } else {
          this.editForm = res.data.data[0];
          console.log("编辑获取的选项内容==>");
          console.log(this.editForm);
          this.centerDialog();
        }
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
