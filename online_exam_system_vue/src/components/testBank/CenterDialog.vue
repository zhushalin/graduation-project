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
        <el-form-item v-if="role!='student'" label="科目" prop="subId" label-width="100px">
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
          <el-input :disabled="role=='student'"
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
          <el-input :disabled="role=='student'" v-model="editForm.optionA" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="选项B"
          prop="optionB"
          label-width="100px"
          v-if="!(editForm.testType === 4)"
        >
          <el-input :disabled="role=='student'" v-model="editForm.optionB" autocomplete="off"></el-input>
          <el-input :disabled="role=='student'"
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
          v-if="editForm.optionC && !(editForm.testType === 4)"
        >
          <el-input :disabled="role=='student'" v-model="editForm.optionC" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="选项D"
          prop="optionD"
          label-width="100px"
          v-if="editForm.optionD && !(editForm.testType === 4)"
        >
          <el-input :disabled="role=='student'" v-model="editForm.optionD" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item v-if="role!='student' || editForm.ctj" label="正确答案" prop="answer" label-width="100px">
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
        
        <el-form-item
          label="你的答案"
          label-width="100px"
          v-if="role=='student' && !editForm.ctj"
        >
          <el-input v-model="editForm.daan" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item  v-if="role=='admin'" label="审核结果" prop="status" label-width="100px">
          <el-select
            v-model="editForm.status"
            autocomplete="off"
            placeholder="审核结果"
            clearable
          >
            <el-option
              label="待审核"
              value="待审核"
            ></el-option>
            <el-option
              label="审核通过"
              value="审核通过"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item   label="知识类型" prop="type" label-width="100px">
          <el-select
          :disabled="role=='student'"
            v-model="editForm.type"
            autocomplete="off"
            placeholder="知识类型"
            clearable
          >
            <el-option
              label="简单"
              value="简单"
            ></el-option>
            <el-option
              label="重难"
              value="重难"
            ></el-option>
          </el-select>
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
          <el-button type="errot" v-if="editForm.scid" @click="qxshoucang('editForm')">取消收藏</el-button>
          <el-button type="warn" v-if="!editForm.scid"  @click="shoucang('editForm')">收藏</el-button>
          <el-button v-if="role=='student' && !editForm.ctj" type="primary" @click="lianxi('editForm')"
            >立即提交</el-button
          >
          <el-button v-if="role!='student'" type="primary" @click="submitForm('editForm')"
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
      role:localStorage.getItem("role"),
      shoucang(formName){
        var editForm = this.editForm;
          this.$axios
            .post(
              "/favorite/add",
              {studentId:localStorage.getItem("id"),type:2,questionId:editForm.testId}
            )
            .then((res) => {
              this.$message({
                showClose: true,
                message: "收藏成功!",
                type: "success",
              });
              this.centerDialogVisible = false;
              this.$parent.resetForm(formName);
            });

      },
      qxshoucang(formName){
        var editForm = this.editForm;
          this.$axios
            .post(
              "/favorite/delete",
              {ids:[this.editForm.scid]}
            )
            .then((res) => {
              this.$message({
                showClose: true,
                message: "取消收藏!",
                type: "success",
              });
              this.centerDialogVisible = false;
              this.$parent.resetForm(formName);
            });

      },
      lianxi(){
        var editForm = this.editForm;
        var stringAnswer = editForm.stringAnswer;
        var answer = editForm.answer;
        var bloe = false;
        debugger;
        //单选
        if(editForm.testType==1){
          if(editForm.daan ==stringAnswer ){bloe =true}
        }else if(editForm.testType==2){ // 多选
          if(answer.indexOf(editForm.daan)>=0){bloe =true}
        }else if(editForm.testType==3){ // 判断题
          if(editForm.daan ==stringAnswer ){bloe =true}
        }else if(editForm.testType==4){ // 填空题
          if(editForm.daan ==stringAnswer ){bloe =true}
        }
        debugger;
        if(bloe){
          this.$message({
            showClose: true,
            message: "恭喜你，回答正确!",
            type: "success",
          });
        }else{
          this.$message({
            showClose: true,
            message: "对不起，回答错误!",
            type: "error",
          });
          this.$axios
            .post(
              "/wrong/add",
              {studentId:localStorage.getItem("id"),questionId:editForm.testId}
            )
            .then((res) => {
            });
        }
      },
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
