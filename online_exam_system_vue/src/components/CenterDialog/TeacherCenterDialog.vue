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
        <el-form-item label="所属学院" prop="colName" label-width="100px">
          <el-select
            v-model="editForm.colName"
            autocomplete="off"
            placeholder="选择所属学院"
            clearable
          >
            <el-option
              v-for="col in teaData"
              :label="col.colName"
              :value="col.colName"
              :key="col.majorId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          label="教师账号"
          prop="teaUsername"
          v-if="!this.editForm.teaId"
        >
          <el-input
            v-model="editForm.teaUsername"
            autocomplete="off"
            placeholder="请输入教师账号"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item
          label="教师账号"
          prop="teaUsername"
          v-if="this.editForm.teaId"
        >
          <el-input
            v-model="editForm.teaUsername"
            autocomplete="off"
            readonly="readonly"
          ></el-input>
        </el-form-item>
        <el-form-item label="教师密码" prop="teaPassword">
          <el-input
            v-model="editForm.teaPassword"
            autocomplete="off"
            placeholder="若教师密码不设置，默认为123456"
            clearable
            type="password"
            readonly="readonly"
          ></el-input>
        </el-form-item>

        <el-form-item label="教师名称" prop="teaName">
          <el-input
            v-model="editForm.teaName"
            autocomplete="off"
            placeholder="请输入教师名称"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="账号状态" prop="teaState">
          <el-radio-group v-model="editForm.teaState">
            <el-radio :label="0">待审核-不可用</el-radio>
            <el-radio :label="1">已审核-可用</el-radio>
          </el-radio-group>
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
  name: "TeacherCenterDialog",
  props: ["editForm", "teaData"],
  data() {
    return {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$axios
              .post(
                "/Teacher/" + (this.editForm.teaId ? "update" : "save"),
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
        colName: [
          { required: true, message: "请选择所属学院", trigger: "blur" },
        ],
        teaUsername: [
          { required: true, message: "请输入教师账号", trigger: "blur" },
        ],
        teaName: [
          { required: true, message: "请输入教师名称", trigger: "blur" },
        ],
        teaState: [
          { required: true, message: "请选择审核状态", trigger: "blur" },
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
