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
        ref="editForm"
        label-width="100px"
        class="demo-editForm"
        :rules="editFormRules"
      >
        <el-form-item label="公告标题" prop="teaUsername">
          <el-input
            v-model="editForm.title"
            autocomplete="off"
            placeholder="请输入公告标题"
            clearable
          ></el-input>
        </el-form-item>

        <el-form-item label="公告内容" prop="content">
          <el-input
            v-model="editForm.content"
            autocomplete="off"
            placeholder="请输入公告内容"
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
  name: "NoticeCenterDialog",
  props: ["editForm", "tableData"],
  data() {
    return {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$axios
              .post(
                "/notice/" + (this.editForm.adminId ? "update" : "add"),
                this.editForm
              )
              .then((res) => {
                this.$parent.getTeacher();
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
        adminId: [
          { required: true, message: "请选择公告标题", trigger: "blur" },
        ],
      },
      /*pickerOptions: {
                    disabledDate(time) {
                        return time.getTime() > Date.now();
                    },
                    shortcuts: [{
                        text: '今天',
                        onClick(picker) {
                            picker.$emit('pick', new Date());
                        }
                    }, {
                        text: '昨天',
                        onClick(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() - 3600 * 1000 * 24);
                            picker.$emit('pick', date);
                        }
                    }, {
                        text: '一周前',
                        onClick(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', date);
                        }
                    }]
                },*/
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
