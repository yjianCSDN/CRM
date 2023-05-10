<template>
  <div class="mainContent">
    <div class="message">
    <div>
      <el-avatar
          :src="img"
          class="avatar"
          style="margin: 5% 0 0 50%"
      />
      <el-form
          label-position="left"
          label-width="100px"
          :model="userinfo"
          class="form"
      >

        <el-form-item label="用户名">
          <el-input v-model="userinfo.userName" style="width: 70%"/>
        </el-form-item>
        <el-form-item label="真实姓名">
          <el-input v-model="userinfo.trueName" style="width: 70%"/>
        </el-form-item>
        <el-form-item label="性别">
          <el-select class="m-2" placeholder="请选择" v-model="userinfo.label" style="width: 70%">
            <el-option label="男" value="男"/>
            <el-option label="女" value="女"/>
          </el-select>
        </el-form-item>
        <el-form-item label="电话号码">
          <el-input v-model="userinfo.phone" style="width: 70%"/>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="userinfo.email" style="width: 70%"/>
        </el-form-item>
        <el-form-item label="上传头像">
          <el-upload
              v-model:file-list="fileList"
              class="upload-demo"
              action="https://www.zouhaihua.club:8080/api/fileupload/uploadP"
              multiple
              :on-success="onSuccess"
          >
            <el-button size="small" type="primary">点我上传</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
    </div>
    <el-button type="primary" style="position: relative;margin: 0 0 0 53%" @click="checkInfoIsRight">修改个人资料</el-button>
    </div>
    <div class="calendar">
      <el-calendar class="el-calendar">
        <template v-slot:dateCell="{data}">
          {{ data.day.split('-').slice(2).join('-') }}
          <div v-for="item in CalendarTime" :key="item">
            <el-badge is-dot class="item" v-if="(item).indexOf(data.day.split('-').slice(0).join('-'))!==-1" @click="details(item)"></el-badge>
          </div>
        </template>
      </el-calendar>
    </div>
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
         <el-button type="primary" @click="this.confirmUserPwdVisible = false,this.PwdModel.oldPwd=''">
          取 消
        </el-button>
        <el-button type="primary" @click="confirmUserPwd">
          确 定
        </el-button>
      </span>
    </template>
  </el-dialog>
<!--事项表具体内容-->
  <el-drawer v-model="drawer"
             :with-header="false">
    <span>当天计划</span>
    <table class="detail" v-for="item in list" :key="item" >
      <tr>
        <th colspan="2">计划项信息</th>
      </tr>
      <tr v-show="item.planItem">
        <td>计划项标题</td>
        <td>{{item.planItem}}</td>
      </tr>
      <tr/>
      <tr v-show="item.planDate">
        <td>计划项时间</td>
        <td>{{item.planDate}}</td>
      </tr>
      <tr/>
      <tr v-show="item.exeAffect">
        <td>计划项内容</td>
        <td>{{item.exeAffect}}</td>
      </tr>
      <tr/>
      <tr v-show="item.createDate">
        <td>创建时间</td>
        <td>{{item.createDate}}</td>
      </tr>
      <tr/>
      <tr v-show="item.updateDate">
        <td>计划项更新时间</td>
        <td>{{item.updateDate}}</td>
      </tr>
    </table>
    <el-button type="primary" @click="drawer=false" style="float: left;margin-top: 25%">确定</el-button>
  </el-drawer>
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
    let phoneList = reactive([])
    let oldPhone = ref("")
    let SaleItem = reactive([])
    let detailsInfo = reactive({})
    let drawer = ref(false)
    let direction = ref("rtl")
    let CalendarTime = reactive([])
    let list = reactive([])

    return {
      userinfo: {
        id: "",
        userName: "".trim(),
        trueName: "".trim(),
        sex: '',
        email: "",
        phone: "",
        userPwd: "".trim(),
        imgUrl:"",
        roleIds: []
      },
      userQuery: {},
      nameList: [],
      oldUserName: "",
      PwdModel,
      src: require("@/assets/头像.png"),
      confirmUserPwdVisible,
      updateRoleId,
      phoneList,
      oldPhone,
      img:Cookies.get("img"),
      SaleItem,detailsInfo,drawer,direction,CalendarTime,list,
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
      }
    },
    //获取初始信息
    getInfo() {
      // eslint-disable-next-line no-unused-vars
      let userId = ""
      let userName = Cookies.get("userName")
      this.oldUserName = userName
      this.$api.user.queryUserByName("/user/getUserByUserName", {userName}).then(res => {
        // console.log("userName", res)
        userId = res.result.id
        this.userinfo = res.result
        this.oldPhone=res.result.phone
        if (res.result.sex === 0) {
          this.userinfo.sex = '女'
          this.userinfo.label = '女'
        } else if (res.result.sex === 1) {
          this.userinfo.label = '男'
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
    //获取权限id
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
      let result = true
      console.log("this.PwdModel",this.PwdModel)
      this.userinfo.roleIds = this.updateRoleId.join(",")
      this.$api.user.confirmPwd("/user/confirmPwd", this.PwdModel).then(res => {
        if (res.code === 200) {
          // console.log("密码正确")
          if (this.userinfo.label === '男') {
            this.userinfo.sex = 1
          } else {
            this.userinfo.sex = 0
          }
          console.log(this.userinfo)
          if (this.phoneList.indexOf(this.userinfo.phone)!==-1 &&this.userinfo.phone===this.oldPhone){
            // console.log("电话号码没有改变")
          }else {
            // console.log("电话修改了")
            // console.log("电话号码重复了")
            result = false
          }
          // console.log(result)
          if (result){
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
          }else {
           ElMessage({type:"warning",message:"电话号码与其他用户重复啦!"})
          }
        } else {
          ElMessage({
            type: "error",
            message: "密码错误，请重试!"
          })
        }
      })
      this.PwdModel.oldPwd = ""
    },
    getAllPhone(){
      this.$api.user.getAllPhoneInfo("/user/phone").then(res=>{
        this.phoneList=res.result
        console.log(res.result)
      })
    },
    onSuccess(res){
      console.log(res.url)
      this.userinfo.imgUrl=res.url
      this.img=res.url
    },
    getServeInit(){
      this.$api.CustomerServer.getServeByAssigner("/CusDevPlan/myItem").then(res=>{
        if (res.code===200){
          // console.log(res)
          res.result.forEach(item=>{
            if (item!==null){
              // console.log("calendar:::::::::::",item.planDate.split(" ").splice(0,1).toString())
              item.calendar = item.planDate.split(" ").splice(0,1).toString()
              this.SaleItem.push(item)
              if (this.CalendarTime.indexOf(item.calendar)===-1){
                this.CalendarTime.push(item.calendar)
              }
            }
          })
          console.log("asdasdasdasd:::::",this.SaleItem)
          console.log("this.CalendarTime:",this.CalendarTime)
        }
      })
      console.log(new Date().toLocaleString())
    },
    details(msg){
      this.list = []
      console.log("msg:",msg)
      this.SaleItem.forEach(item=>{
        if (item.calendar.indexOf(msg)!==-1){
          this.list.push(item)
        }
      })
      this.drawer=true
      this.detailsInfo = JSON.parse(JSON.stringify(msg))
      console.log("this.list::::",this.list)
    }
  },
  mounted() {
    this.getInfo()
    this.getAllPhone()
    setTimeout(this.getRoleId, 50)
    this.getServeInit()
  }
}
</script>

<style scoped>
.mainContent{
  width: 100%;
  height: 100%;
  /*background-color: #8c6fd0;*/
}
.avatar{
  margin: 3% 0 0 40%;
  width: 150px;
  height: 150px;
}
.form{
  max-width: 460px;
  margin: 0 0 0 30%;
  position: relative;
  /*background-color: #8c6fd0;*/
}
.message{
  /*background-color: #c1c1c1;*/
  height: 100%;
  width: 50%;
  float: left;
  /*background-color: #8c6fd0;*/
}
.el-calendar{
  height: 50%;
  width: 35%;
  /*background-color: skyblue;*/
  float: right;
  --el-calendar-cell-width:55px;
  margin: 5% 10% 0 0;
}
.detail{
  width: 100%;
  border: solid 2px goldenrod;
  margin: 30% 0 0 0;
}
.detail td{
  border: solid 1px goldenrod;
}
</style>