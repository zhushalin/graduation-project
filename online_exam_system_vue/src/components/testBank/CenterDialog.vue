<template>
  <div>
    <el-dialog
      title="提示"
      width="30%"
      center
      :before-close="handleClose"
      :visible.sync="centerDialogVisible"
    >
      <el-form
        :model="editForm"
        :rules="editFormRules"
        ref="editForm"
        label-width="100px"
        class="demo-editForm"
      >
        <el-form-item label="科目" prop="subId" label-width="100px">
          <el-select
            v-model="editForm.subId"
            autocomplete="off"
            placeholder="科目"
            clearable
          >
            <el-option
              v-for="sub in subData"
              :label="sub.subName"
              :value="sub.subId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          label="试题类型"
          prop="testType"
          label-width="100px"
          v-if="this.editForm.testId"
        >
          <el-radio-group v-model="editForm.testType" disabled>
            <el-radio :label="1">单选题</el-radio>
            <el-radio :label="2">多选题</el-radio>
            <el-radio :label="3">判断题</el-radio>
            <el-radio :label="4">问答题</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item
          label="试题类型"
          prop="testType"
          label-width="100px"
          v-if="!this.editForm.testId"
        >
          <el-radio-group v-model="editForm.testType">
            <el-radio :label="1">单选题</el-radio>
            <el-radio :label="2">多选题</el-radio>
            <el-radio :label="3">判断题</el-radio>
            <el-radio :label="4">问答题</el-radio></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="试题内容" prop="testContent">
          <el-input
            v-model="editForm.testContent"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="选项A"
          prop="optionA"
          label-width="100px"
          v-if="!(editForm.testType === 4)"
        >
          <el-input v-model="editForm.optionA" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="选项B"
          prop="optionB"
          label-width="100px"
          v-if="!(editForm.testType === 4)"
        >
          <el-input v-model="editForm.optionB" autocomplete="off"></el-input>
          <el-input
            v-model="editForm.optionB"
            autocomplete="off"
            v-if="editForm.testType === 4"
            value="否"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="选项C"
          prop="optionC"
          label-width="100px"
          v-if="!(editForm.testType === 4)"
        >
          <el-input v-model="editForm.optionC" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="选项D"
          prop="optionD"
          label-width="100px"
          v-if="!(editForm.testType === 4)"
        >
          <el-input v-model="editForm.optionD" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="正确答案" prop="answer" label-width="100px">
          <template>
            <!--添加时-->
            <el-checkbox-group
              v-model="editForm.answer"
              v-if="editForm.testType === undefined"
            >
              <el-checkbox label="A" value="A"></el-checkbox>
              <el-checkbox label="B" value="B"></el-checkbox>
              <el-checkbox label="C" value="C"></el-checkbox>
              <el-checkbox label="D" value="D"></el-checkbox>
            </el-checkbox-group>
            <!--单选-->
            <el-radio-group
              v-model="editForm.stringAnswer"
              v-if="editForm.testType === 1"
              prop="stringAnswer"
            >
              <el-radio label="A" value="A"></el-radio>
              <el-radio label="B" value="B"></el-radio>
              <el-radio label="C" value="C"></el-radio>
              <el-radio label="D" value="D"></el-radio>
            </el-radio-group>
            <!--多选-->
            <el-checkbox-group
              v-model="editForm.answer"
              v-if="editForm.testType === 2"
              prop="answer"
            >
              <el-checkbox label="A"></el-checkbox>
              <el-checkbox label="B"></el-checkbox>
              <el-checkbox label="C"></el-checkbox>
              <el-checkbox label="D"></el-checkbox>
            </el-checkbox-group>
            <!--判断题-->
            <el-radio-group
              v-model="editForm.stringAnswer"
              v-if="editForm.testType === 3"
            >
              <el-radio label="A" value="A"></el-radio>
              <el-radio label="B" value="B"></el-radio>
            </el-radio-group>

            <el-input
              v-model="editForm.stringAnswer"
              v-if="editForm.testType === 4"
              placeholder="请输入内容"
              type="textarea"
            ></el-input>
          </template>
        </el-form-item>
        <el-form-item label-width="100px">
          <el-input
            v-model="editForm.teaId"
            autocomplete="off"
            type="hidden"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="info" @click="handleClose">取消</el-button>
          <el-button type="primary" @click="submitForm('editForm')"
            >立即提交</el-button
          >
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "CenterDialog",
  props: ["editForm", "subData"],

  data() {
    return {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            console.log(this.editForm.answer);
            // if (!(this.editForm.testType === 2)) {
            //   this.editForm.answer = this.editForm.answer;
            // }
            this.$axios
              .post(
                "/TestBank/" + (this.editForm.testId ? "update" : "save"),
                this.editForm
              )
              .then((res) => {
                this.$message({
                  showClose: true,
                  message: "恭喜你，操作成功",
                  type: "success",
                });
                this.centerDialogVisible = false;
                this.$parent.resetForm(formName);
              });
          } else {
            console.log("error submit!!");
            return false;
          }
        });
      },
      editFormRules: {
        teaUsername: [
          { required: true, message: "请输入教师名称", trigger: "blur" },
        ],
        testDate: [
          { required: true, message: "请输入出题时间", trigger: "blur" },
        ],
        subId: [{ required: true, message: "请选择科目", trigger: "blur" }],
        testType: [{ required: true, message: "请选择类型", trigger: "blur" }],
        testContent: [
          { required: true, message: "请输入试题内容", trigger: "blur" },
        ],
        optionA: [{ required: true, message: "请输入答案A", trigger: "blur" }],
        optionB: [{ required: true, message: "请输入答案B", trigger: "blur" }],
        stringAnswer: [
          { required: true, message: "请输入正确答案", trigger: "blur" },
        ],
      },
      centerDialogVisible: false,
    };
  },

  methods: {
    array(val) {
      return true;
    },
    centerDialog(statu) {
      this.centerDialogVisible = statu;
    },
    handleClose() {
      this.centerDialogVisible = false;
      this.$parent.resetForm("editForm");
    },
  },
};
</script>

<style scoped></style>
