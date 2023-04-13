<template>
<div class="personCenter">修改密码
  <div class="form">
    <el-radio-group v-model="labelPosition" label="label position">
      <el-radio-button label="left">Left</el-radio-button>
      <el-radio-button label="right">Right</el-radio-button>
      <el-radio-button label="top">Top</el-radio-button>
    </el-radio-group>
    <div style="margin: 20px" />
    <el-form
        :label-position="labelPosition"
        label-width="100px"
        :model="PwdModel"
        style="max-width: 460px"
    >
      <el-form-item label="旧的密码">
        <el-input type="password" v-model="PwdModel.oldPwd" show-password placeholder="请输入旧的密码"/>
      </el-form-item>
      <el-form-item label="新的密码">
        <el-input type="password" v-model="PwdModel.newPwd" show-password placeholder="请输入新密码"/>
      </el-form-item>
      <el-form-item label="新的密码">
        <el-input type="password" v-model="PwdModel.NewPwd" show-password placeholder="请再次输入新密码"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm()"
        >提 交</el-button
        >
        <el-button @click="resetForm()">重 置</el-button>
      </el-form-item>
    </el-form>
  </div>
</div>
</template>

<script>
import Cookies from "js-cookie";
import { ElMessage, ElMessageBox } from 'element-plus'
export default {
  name: "PersonCenter",
  data(){
    return{
      labelPosition:'',
      PwdModel:{
        oldPwd:''.trim(),
        newPwd:''.trim(),
        NewPwd:''.trim(),
        userIdStr:''.trim()
      }
    }
  },
  methods:{
    submitForm(){
      if (this.PwdModel.oldPwd == '' || this.PwdModel.newPwd == '' || this.PwdModel.NewPwd == ''){
        ElMessage({
          message:"还有信息未输入",
          type:"info",
        })
      }else if (this.PwdModel.newPwd !='' && this.PwdModel.newPwd != this.PwdModel.NewPwd){
        ElMessage({
          message:"两次输入的密码不一致",
          type:"info",
        })
      }else if (this.PwdModel.newPwd == this.PwdModel.NewPwd && this.PwdModel.newPwd==this.PwdModel.oldPwd){
        ElMessage({
          message:"新密码不能与当前密码一致",
          type:"warning"
        })
      }else {
        ElMessageBox.confirm(
            '确认要修改密码吗？',
            'Warning',
            {
              confirmButtonText:"是的",
              cancelButtonText:"不,我再想想",
            }
        ).then(()=>{
          this.PwdModel.userIdStr = Cookies.get("userIdStr")
          this.$api.user.updatePassword("/user/updatePassword",this.PwdModel)
              .then(res=>{
                if (res.code===200){
                  ElMessage({
                    message:"修改成功",
                    type:"success"
                  })
                  Cookies.remove("userName");
                  Cookies.remove("trueName");
                  Cookies.remove("userIdStr");
                  console.log(Cookies.get(),"删除Cookie后的cookie");
                  this.$router.push("/")
                }else {

                  ElMessage({
                    message:"修改失败，请重试",
                    type:"error"
                  })
                }
                console.log(res)
              })
        }).catch(()=>{
          ElMessage({
            message:"取消操作",
            type:"info"
          })
        })
      }
    },
    resetForm(){
      this.PwdModel = ''
    }
  }
}
</script>

<style scoped>
.personCenter{
  width: 100%;
  height: 100%;
}
.form{
  margin: 70px 10% 10% 10%;
  right: 20px;
  min-width: 100px;
}
</style>