<template>
  <div>
    <el-dialog
      title="考试配置"
      width="40%"
      center
      :before-close="handleClose"
      :visible.sync="centerDialogVisible"
    >
      <el-card class="box-card" shadow="hover">
        <el-form
          ref="editForm"
          :model="editForm"
          :rules="editFormRules"
          label-position="left"
          label-width="120px"
        >
          <el-form-item label="考试科目" prop="subId">
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
                :key="sub.subId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="考试名称" prop="tpName">
            <el-input v-model="editForm.tpName" />
          </el-form-item>

          <el-form-item label="试卷类型" prop="tpType">
            <el-radio-group v-model="editForm.tpType">
              <el-radio :label="0">完全公开</el-radio>
              <el-radio :label="1">需要密码</el-radio>
            </el-radio-group>
          </el-form-item>

          <el-form-item
            label="入考码"
            prop="examPassword"
            v-if="editForm.tpType === 1"
          >
            <el-input v-model="editForm.examPassword" />
          </el-form-item>

          <el-form-item label="考试状态" prop="examState">
            <el-radio-group v-model="editForm.examState" disabled>
              <el-radio :label="0">未开始</el-radio>
              <el-radio :label="1">正在进行</el-radio>
              <el-radio :label="2">已结束</el-radio>
            </el-radio-group>
          </el-form-item>

          <!-- <el-form-item label="总分数" prop="examTotal">
                        <el-input-number :value="editForm.examTotal" />
                    </el-form-item>

                    <el-form-item label="及格线" prop="examPass">
                        <el-input-number v-model="editForm.examPass" :max="editForm.examTotal" />
                    </el-form-item>-->
          <el-form-item label="考试时长" prop="examLength">
            <el-time-select
              v-model="editForm.examLength"
              :picker-options="{
                start: '00:15',
                step: '00:15',
                end: '4:00',
              }"
              placeholder="设置考试时长"
              value-format="HH:mm"
            >
            </el-time-select>
          </el-form-item>

          <el-form-item label="考试时间" prop="examTime">
            <el-date-picker
              v-model="editForm.examTime"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetime"
              placeholder="选择考试时间"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="info" @click="handleClose">取消</el-button>
            <el-button type="primary" @click="submitForm('editForm')"
              >立即提交</el-button
            >
          </el-form-item>
        </el-form>
      </el-card>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "TestpaperCenterDialog",
  props: ["editForm", "subData"],
  data() {
    return {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.editForm.tpType === 0) {
              this.editForm.examPassword = "";
              console.log("11111111111111111111111111");
              console.log(this.editForm.examPassword);
            }
            this.$axios
              .post(
                "/TestPaper/" + (this.editForm.tpId ? "update" : "save"),
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
        subId: [{ required: true, message: "考试科目不能为空！" }],
        tpName: [{ required: true, message: "考试名称不能为空！" }],
        tpType: [{ required: true, message: "考试类型不能为空！" }],
        examPassword: [{ required: true, message: "入考码不能为空！" }],
        /*examTotal: [
                        { required: true, message: '总分不能为空！' }
                    ],
                    examPass: [
                        { required: true, message: '及格分不能为空！' }
                    ],*/
        examLength: [{ required: true, message: "考试时长不能为空！" }],
        examTime: [{ required: true, message: "考试时间不能为空" }],
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

<style scoped>
.text {
  font-size: 14px;
}

.item {
  padding: 18px 0;
}

.box-card {
  width: 100%;
  margin-bottom: 50px;
}
.input_number {
  margin-right: 40px;
}
</style>
