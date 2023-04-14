<template>
  <div>
    <div>
      <el-avatar
          :src="src"
          style="margin: 5% 0 0 40%;width: 150px;height: 150px"
      />
      <el-form
          label-position="left"
          label-width="100px"
          :model="userinfo"
          style="max-width: 460px;margin: 2% 0 0 30%;position: relative"
      >

        <el-form-item label="用户名">
          <el-input v-model="userinfo.userName" style="width: 70%"/>
        </el-form-item>
        <el-form-item label="真实姓名">
          <el-input v-model="userinfo.trueName" style="width: 70%"/>
        </el-form-item>
        <el-form-item label="性别">
          <el-select class="m-2" placeholder="请选择" v-model="userinfo.sex" style="width: 70%">
            <el-option label="男" value="1"/>
            <el-option label="女" value="0"/>
          </el-select>
        </el-form-item>
        <el-form-item label="电话号码">
          <el-input v-model="userinfo.phone" style="width: 70%"/>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="userinfo.email" style="width: 70%"/>
        </el-form-item>
        <el-form-item>
          <el-bottom type="primary" label="修改个人信息"></el-bottom>
        </el-form-item>
      </el-form>
    </div>
    <el-button type="primary" style="position: relative;margin: 1% 0 0 41%" @click="checkInfoIsRight">修改个人资料</el-button>
  </div>
  <!--更新角色确认密码-->
  <el-dialog
      v-model="confirmUserPwdVisible"
      width="30%"
  >
    <el-form
        label-position="left"
        label-width="90px"
        :inline="true"
        :model="PwdModel"
        style="width: 100%"
    >
      <el-form-item label="用户密码">
        <el-input type="password" v-model="PwdModel.oldPwd" show-password placeholder="请输入该用户密码"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
         <el-button type="primary" @click="this.confirmUserPwdVisible = false,this.PwdModel={}">
          取 消
        </el-button>
        <el-button type="primary" @click="confirmUserPwd">
          确 定
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import Cookies from 'js-cookie'
import {reactive, ref} from "@vue/reactivity";
import {ElMessage} from "element-plus";

export default {
  name: "PersonalInfoManagement",
  data() {
    let PwdModel = reactive({userIdStr: "", oldPwd: ""})//确认密码是否正确
    let confirmUserPwdVisible = ref(false)
    let updateRoleId = reactive([])
    return {
      userinfo: {
        id: "",
        userName: "".trim(),
        trueName: "".trim(),
        sex: '',
        email: "",
        phone: "",
        userPwd: "".trim(),
        roleIds: []
      },
      userQuery: {},
      nameList: [],
      oldUserName: "",
      PwdModel,
      src: require("@/assets/头像.png"),
      confirmUserPwdVisible,
      updateRoleId
    }
  },
  methods: {
    checkInfoIsRight() {
      var e = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/
      if (this.userinfo.userName == "" || this.userinfo.trueName == "" || this.userinfo.email == "" || this.userinfo.phone == "") {
        ElMessage({type: "info", message: "信息没有填写完全"})
      } else if (!(/^1[34578]\d{9}$/.test(this.userinfo.phone))) {
        ElMessage({type: "warning", message: "电话号码格式不正确"})
      } else if (!e.test(this.userinfo.email)) {
        ElMessage({type: "warning", message: "邮箱格式不正确"})
      } else if (this.nameList.includes(this.userinfo.userName)) {
        if (this.userinfo.userName == this.oldUserName) {
          // console.log("修改信息后昵称与原来一致")
          this.confirmUserPwdVisible = true
        }
      } else {
        this.confirmUserPwdVisible = true
        // console.log("includes:", this.nameList.includes(this.userinfo.userName))
        // console.log(this.userinfo)
      }

    },
    //获取初始信息
    getInfo() {
      // eslint-disable-next-line no-unused-vars
      let userId = ""
      let userName = Cookies.get("userName")
      // console.log("userName:->",userName)
      this.oldUserName = userName
      this.$api.user.queryUserByName("/user/getUserByUserName", {userName}).then(res => {
        // console.log("userName", res)
        userId = res.result.id
        this.userinfo = res.result
        if (res.result.sex === 0) {
          this.userinfo.sex = '女'
        } else if (res.result.sex === 1) {
          this.userinfo.sex = '男'
        }
        // console.log(this.userinfo)
        this.PwdModel.userIdStr = res.result.id
      })
      this.$api.user.selectList("/user/lists", this.userQuery).then(res => {
        for (let i = 0; i < res.result.data.length; i++) {
          this.nameList.push(res.result.data[i].userName)
        }
      })
    },

    getRoleId() {
      let userId = ""
      console.log("this.userinfo.id:   ", this.userinfo.id)
      userId = this.userinfo.id
      this.$api.Role.queryAllRoles("/role/queryAllRoles", {userId}).then(res => {
        console.log("roId:      ", res)
        for (let i = 0; i < res.result.length; i++) {
          if (res.result[i].selected === 'selected') {
            this.updateRoleId.push(res.result[i].id)
          }
        }
        console.log("this.updateRoleId", this.updateRoleId)
      })
    },
    //确认密码是否正确
    confirmUserPwd() {
      this.userinfo.roleIds = this.updateRoleId.join(",")
      this.$api.user.confirmPwd("/user/confirmPwd", this.PwdModel).then(res => {
        if (res.code === 200) {
          // console.log("密码正确")
          if (this.userinfo.sex === '男') {
            this.userinfo.sex = 1
          } else {
            this.userinfo.sex = 0
          }
          console.log(this.userinfo)
          this.$api.user.updateUser("/user/updateUser?flag=1", this.userinfo).then(res => {
            if (res.code === 200) {
              ElMessage({
                type: "success",
                message: "修改成功,请重新登录!"
              })
              Cookies.remove("userIdStr")
              Cookies.remove("userName")
              Cookies.remove("trueName")
              this.$router.push("/")
            } else {
              ElMessage({
                type: "error",
                message: "修改失败，请重试!"
              })
            }
          })
        } else {
          ElMessage({
            type: "error",
            message: "密码错误，请重试!"
          })
        }
      })
      this.PwdModel = {}
    },
  },
  mounted() {
    this.getInfo()
    setTimeout(this.getRoleId, 50)
  }
}
</script>

<style scoped>

</style>