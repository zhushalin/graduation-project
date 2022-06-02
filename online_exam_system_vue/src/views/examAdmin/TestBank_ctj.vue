<template>
  <div>
    <el-form
    v-if="false"
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
          v-model="searchForm.testType"
          placeholder="题目类型"  
          clearable
        >
          <el-option label="单选题" value="1"></el-option>
          <el-option label="多选题" value="2"></el-option>
          <el-option label="判断题" value="3"></el-option>
        </el-select>
      </el-form-item>
      <!-- <el-form-item>
        <el-input
          v-model="searchForm.teaId"
          placeholder="教师Id"
          clearable
        ></el-input>
      </el-form-item> -->
      <el-form-item>
        <el-input
          v-model="searchForm.testContent"
          placeholder="题目内容"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getTestBank">
          <i class="el-icon-search"></i>
          搜索
        </el-button>
      </el-form-item>
      <!-- <el-form-item>
        <el-button type="primary" @click="centerDialog">
          <i class="el-icon-plus"></i>添加
        </el-button>
      </el-form-item> -->
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

      <!-- <el-table-column
        prop="teaId"
        label="教师编号"
        width="120"
        sortable
        show-overflow-tooltip
        v-if="hasAuth()"
      >
        <template slot-scope="scope">{{ scope.row.teaId }}</template>
      </el-table-column> -->
      <!-- <el-table-column
        prop="testDate"
        label="出题时间"
        width="120"
        sortable
        show-overflow-tooltip
      >
        <template slot-scope="scope">{{ scope.row.testDate }}</template>
      </el-table-column> -->
      <el-table-column
        prop="subName"
        label="科目"
        sortable
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column prop="testType" label="试题类型" sortable>
        <template slot-scope="scope">
          <el-tag v-if="scope.row.testType === 1" type="warning" effect="dark"
            >单选题</el-tag
          >
          <el-tag v-else-if="scope.row.testType === 2" type="dark" effect="dark"
            >多选题</el-tag
          >
          <el-tag
            v-else-if="scope.row.testType === 3"
            type="success"
            effect="dark"
            >判断题</el-tag
          >
          <el-tag
            v-else-if="scope.row.testType === 4"
            type="danger"
            effect="dark"
            >问答题</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column
        prop="testContent"
        label="试题内容"
        show-overflow-tooltip
      >
      </el-table-column>

      <el-table-column
        prop="optionA"
        label="选项A"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        prop="optionB"
        label="选项B"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        prop="optionC"
        label="选项C"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        prop="optionD"
        label="选项D"
        show-overflow-tooltip
      >
      </el-table-column>

      <!-- <el-table-column
        prop="answer"
        label="正确答案"
        width="150"
        show-overflow-tooltip
      >
        <template slot-scope="scope">
          <el-tag type="danger" effect="dark">{{ scope.row.answer }}</el-tag>
        </template>
      </el-table-column> -->
      <el-table-column
        prop="testType"
        label="分值"
        sortable
        show-overflow-tooltip
      >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.testType === 1" type="warning" effect="dark"
            >3</el-tag
          >
          <el-tag v-else-if="scope.row.testType === 2" type="dark" effect="dark"
            >5</el-tag
          >
          <el-tag
            v-else-if="scope.row.testType === 3"
            type="success"
            effect="dark"
            >2</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="icon" label="操作">
        <template slot-scope="scope">
          <el-button type="text" @click="editHandle(scope.row.testId)"
            >查看答案</el-button
          >
          <!-- <el-divider direction="vertical"></el-divider> -->

          <template>
            <el-popconfirm
              title="确定删除该数据吗？"
              @confirm="delHandle(scope.row.testId)"
            >
              <el-button v-if="searchForm.teaId==scope.row.teaId" type="text" slot="reference">删除</el-button>
            </el-popconfirm>
          </template>
        </template>
      </el-table-column>
    </el-table>
    <CenterDialog
      ref="Dialog"
      v-bind:editForm="editForm"
      v-bind:subData="subData"
    ></CenterDialog>

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
        :total="datatotal"
      >
      </el-pagination>
    </el-card>
  </div>
</template>

<script>
import CenterDialog from "../../components/testBank/CenterDialog";
export default {
  inject: ["reload"],
  name: "EATestBank_CTJ",
  components: {
    CenterDialog /*pagination*/,
  },
  data() {
    return {
      editForm: {
        answer: [],
        daan:""
      },
      tableData: [],
      subData: [],
      multipleSelection: [],
      delBtlStatu: true,
      searchForm: {
        subId: null,
        testType: null,
        teaId: null,
        testContent: "",
        size: 8,
        current: 1,
      },
      datatotal: 0,
    };
  },
  created() {
    this.editForm.teaId = localStorage.getItem("id");
    this.role = localStorage.getItem("role");
    debugger;
    if (localStorage.getItem("role") === "teacher") {
      this.searchForm.teaId = localStorage.getItem("id");
    }
    this.getTestBank();
    this.getsubData();
  },
  methods: {
    //查询、添加
    getTestBank() {
      console.log("搜索===>");
      this.$axios.post("/TestBank/getTestbankCtj", this.searchForm).then((res) => {
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
    //修改
    editHandle(testId) {
      this.$axios.get("/TestBank/getById/" + testId).then((res) => {
        console.log("修改====>");
        console.log(res);
        this.editForm = res.data.data[0];
        this.editForm.ctj = "1";
        if (res.data.data[0].testType === 2) {
          this.editForm.answer = res.data.data[0].answer.split("");
        }
        console.log("编辑获取的选项内容==>");
        console.log(this.editForm);
        this.centerDialog();
      });
    },

    //删除
    delHandle(testId) {
      let ids = [];
      if (testId) {
        ids.push(testId);
      } else {
        this.multipleSelection.forEach((row) => {
          ids.push(row.testId);
        });
      }
      console.log("删除===========》");
      console.log(ids);
      this.$axios.post("/TestBank/delete", ids).then((res) => {
        this.$message({
          showClose: true,
          message: "恭喜你，删除成功",
          type: "success",
        });
        this.getTestBank();
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
      this.getTestBank();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.searchForm.current = val;
      this.getTestBank();
    },
    //重置表单
    resetForm() {
      this.editForm = {};
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
