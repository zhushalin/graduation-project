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
        <el-form-item label="所属专业" prop="majorId" label-width="100px">
          <el-select
            v-model="editForm.majorId"
            autocomplete="off"
            placeholder="选择所属专业"
            clearable
          >
            <el-option
              v-for="maj in majorData"
              :label="maj.majorName"
              :value="maj.majorId"
              :key="maj.majorId"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="科目名称" prop="subName">
          <el-input
            v-model="editForm.subName"
            autocomplete="off"
            clearable
          ></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="info" @click="handleClose">取消</el-button>
          <el-button type="primary" @click="submitForm('editForm')"
            >立即创建</el-button
          >
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "SubCenterDialog.vue",
  props: ["editForm", "majorData"],
  data() {
    return {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$axios
              .post(
                "/Subject/" + (this.editForm.subId ? "update" : "save"),
                this.editForm
              )
              .then((res) => {
                this.$parent.getSubject();
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
        majorId: [
          { required: true, message: "请选择所属专业", trigger: "blur" },
        ],
        subName: [{ required: true, message: "请选择科目", trigger: "blur" }],
      },
      centerDialogVisible: false,
    };
  },
  methods: {
    centerDialog(statu) {
      this.centerDialogVisible = statu;
    },
    /* resetForm() {
                /!*this.$refs[formName].resetFields();
                this.editForm = {}*!/
                this.centerDialogVisible = false
            },*/
    handleClose() {
      this.centerDialogVisible = false;
      this.$parent.resetForm("editForm");
    },
  },
};
</script>

<style scoped></style>
