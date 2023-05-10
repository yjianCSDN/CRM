<template>
  <div class="UserManagement">
    <div class="search">
      <el-input
          v-model="userQuery.userName"
          class="w-50 m-2"
          placeholder="客户名"
          style="position: relative;width: 12%;margin: 1% 0 0 3%"
      />
      <el-input
          v-model="userQuery.email"
          class="w-50 m-2"
          placeholder="邮箱"
          style="position: relative;width: 12%;margin: 1% 0 0 5px"
      />
      <el-input
          v-model="userQuery.phone"
          class="w-50 m-2"
          placeholder="手机号"
          style="position: relative;width: 12%;margin: 1% 0 0 5px"
      />
      &nbsp;&nbsp;
      <el-button type="primary" style="margin: 1% 0 0 5px" @click="selectByParams" v-if="selectVisible">搜 &nbsp;&nbsp;&nbsp;
        索
      </el-button>
      <el-button type="primary" style="margin: 1% 0 0 10px" @click="this.addUserVisible=true" v-if="addVisible">添 加 用
        户
      </el-button>
    </div>
    <div style="margin: 5px 0 1% 3%">
    </div>
    <div>
      <el-table :data="userList" class="tableMenu"
                max-height="480" :default-sort="{ prop: 'createDate', order: 'descending' }"
                :header-cell-style="{ backgroundColor: '#eef5ff',   textAlign: 'center',  }"
                row-style="rowStyle"
      >
        <el-table-column fixed="left" sortable prop="id" label="编号" width="100" align="center"/>
        <el-table-column prop="userName" label="用户名" width="120" header-align="center" align="center"/>
        <el-table-column prop="email" label="用户邮箱" width="200" header-align="center" align="center"/>
        <el-table-column prop="trueName" label="真实姓名" width="120" header-align="center" align="center"/>
        <el-table-column label="性别" width="120" header-align="center" align="center">
          <template #default="scope">
            <span v-if="scope.row.sex===0">女</span>
            <span v-else-if="scope.row.sex===1">男</span>
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="手机号" width="150" header-align="center" align="center"/>
        <el-table-column prop="createDate" sortable label="创建时间" width="200" header-align="center" align="center"/>
        <el-table-column prop="updateDate" label="更新时间" width="200" header-align="center" align="center"/>
        <el-table-column label="用户头像" width="200" header-align="center" align="center">
          <template #default="scope">
            <el-image
                :src="scope.row.img"
                style="width: 50px;height: 50px"
                alt="图片加载失败"
            />
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操 作" width="130" header-align="center" align="center">
          <template #default="scope">
            <el-button link size="small" type="primary" @click="User(JSON.parse(JSON.stringify(scope.row)))"
                       v-show="updateVisible"
            >编 辑
            </el-button
            >
            <el-button link size="small" type="primary" @click="delUser(JSON.parse(JSON.stringify(scope.row)))"
                       v-show="delVisible"
            >删 除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <div class="page">
      <el-pagination
          v-model:current-page="userQuery.page"
          v-model:page-size="userQuery.limit"
          :small="true"
          :disabled="false"
          :background="true"
          layout="prev,pager,next,jumper"
          :total="total"
          @current-change="handleCurrentChange"
      />
    </div>

    <!--添加用户-->
    <el-dialog
        v-model="addUserVisible"
        title="添加用户"
        width="50%"
    >
      <el-form
          label-position="left"
          label-width="90px"
          :inline="true"
          :model="addUserInfo"
          style="width: 100%"
      >
        <el-form-item label="用户名">
          <el-input v-model="addUserInfo.userName" placeholder="请输入用户名"/>
        </el-form-item>
        <el-form-item label="真实姓名">
          <el-input v-model="addUserInfo.trueName" placeholder="请输入真实姓名"/>
        </el-form-item>
        <el-form-item label="性别">
          <el-select class="m-2" placeholder="请选择" v-model="addUserInfo.sex" style="width: 90%">
            <el-option label="男" value="1"/>
            <el-option label="女" value="0"/>
          </el-select>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="addUserInfo.email" placeholder="请输入邮箱" style="right: 23px"/>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="addUserInfo.phone" placeholder="请输入手机号"/>
        </el-form-item>
        <el-form-item label="用户密码">
          <el-input type="password" v-model="addUserInfo.userPwd" style="width: 90%" show-password
                    placeholder="请输入用户密码"/>
        </el-form-item>
        <el-row>
        </el-row>
        <el-form-item label="用户头像">
          <el-upload
              v-model:file-list="imgList"
              class="upload-demo"
              action="https://www.zouhaihua.club:8080/api/fileupload/uploadP"
              multiple
              :on-success="AddonSuccess"
          >
            <el-button size="small" type="primary">点我上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="用户角色">
          <el-select
              v-model="addRoleIds"
              multiple
              clearable
              placeholder="Select"
              style="width: 90%"
          >
            <el-option
                v-for="item in RoleList"
                :key="item.id"
                :label="item.roleName"
                :value="item.id"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
         <el-button type="primary" @click="this.addUserVisible = false,this.addUserInfo={},addRoleIds=[]">
          取 消
        </el-button>
        <el-button type="primary" @click="addUser">
          添 加
        </el-button>
      </span>
      </template>
    </el-dialog>

    <!--修改用户信息-->
    <el-dialog
        v-model="updateUserVisible"
        title="修改用户信息"
        width="50%"
    >
      <el-form
          label-position="left"
          label-width="90px"
          :inline="true"
          :model="addUserInfo"
          style="width: 100%"
      >
        <el-form-item label="用户名">
          <el-input v-model="updateUserInfo.userName" placeholder="请输入用户名"/>
        </el-form-item>
        <el-form-item label="真实姓名">
          <el-input v-model="updateUserInfo.trueName" placeholder="请输入真实姓名"/>
        </el-form-item>
        <el-form-item label="性别">
          <el-select class="m-2" placeholder="请选择" v-model="updateUserInfo.label" style="width: 90%">
            <el-option label="男" value="1"/>
            <el-option label="女" value="0"/>
          </el-select>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="updateUserInfo.email" placeholder="请输入邮箱" style="right: 23px"/>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="updateUserInfo.phone" placeholder="请输入手机号"/>
        </el-form-item>
        <el-form-item label="用户密码">
          <el-input type="password" v-model="updateUserInfo.userPwd" style="width: 90%" show-password
                    placeholder="请输入用户密码"/>
        </el-form-item>
        <el-form-item label="用户头像">
          <el-upload
              v-model:file-list="imgList"
              class="upload-demo"
              action="https://www.zouhaihua.club:8080/api/fileupload/uploadP"
              multiple
              :on-success="UpdateOnSuccess"
          >
            <el-button size="small" type="primary">点我上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item></el-form-item>
        <el-form-item label="用户角色">
          <el-select
              v-model="updateRoleId"
              multiple
              clearable
              placeholder="Select"
              style="width: 90%;"
          >
            <el-option
                v-for="item in RoleList"
                :key="item.id"
                :label="item.roleName"
                :value="item.id"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
         <el-button type="primary" @click="this.updateUserVisible = false,this.updateUserInfo={},this.updateRoleId=[]">
          取 消
        </el-button>
        <el-button type="primary" @click="updateUser">
          修 改
        </el-button>
      </span>
      </template>
    </el-dialog>

    <!--更新角色确认密码-->
    <el-dialog
        v-model="confirmUserPwdVisible"
        :title="title"
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
         <el-button type="primary" @click="this.confirmUserPwdVisible = false,this.PwdModel={},this.updateRoleId=[]">
          取 消
        </el-button>
        <el-button type="primary" @click="confirmUserPwd">
          确 定
        </el-button>
      </span>
      </template>
    </el-dialog>

    <!--删除角色的确认用户密码-->
    <el-dialog
        v-model="confirmUserPwdVisible2"
        title="请输入该用户密码"
        width="50%"
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
         <el-button type="primary" @click="this.confirmUserPwdVisible2 = false,this.PwdModel.oldPwd=''">
          取 消
        </el-button>
        <el-button type="primary" @click="delUserConfirm">
          确 定
        </el-button>
      </span>
      </template>
    </el-dialog>

  </div>
</template>

<script>
import {toRaw, reactive, ref} from "@vue/reactivity"
// eslint-disable-next-line no-unused-vars
import {ElMessage} from "element-plus";

export default {
  name: "UserManagement",
  data() {
    let userQuery = reactive({userName: "", email: "", phone: "", limit: 10, page: 1, total: "",})
    let addUserInfo = reactive({
      userName: "".trim(),
      trueName: "".trim(),
      sex: "",
      email: "",
      phone: "",
      userPwd: "".trim(),
      roleIds: "",
      img: ""
    })
    let updateUserInfo = reactive({
      label: "",
      id: "",
      userName: "".trim(),
      trueName: "".trim(),
      sex: "",
      email: "",
      phone: "",
      userPwd: "".trim(),
      roleIds: [],
      img: ""
    })
    let updateOldUserName = ref("")
    let updateRoleId = reactive([])
    let PwdModel = reactive({userIdStr: "", oldPwd: ""})//确认密码是否正确
    let userId = reactive({userId: ""})//根据用户id查询相应角色
    let title = reactive({})
    let addUserVisible = ref(false)
    let updateUserVisible = ref(false)
    let confirmUserPwdVisible = ref(false)
    let confirmUserPwdVisible2 = ref(false)
    let RoleList = reactive([])//初始角色列表
    let addRoleList = reactive([])
    let result = ref(true)
    let updateResult = ref(true)
    let addRoleIds = reactive([])
    let imgList = reactive([])


    let list = reactive([])
    let addVisible = ref(false)
    let updateVisible = ref(false)
    let delVisible = ref(false)
    let selectVisible = ref(false)
    let AllUserName = reactive([])
    return {
      userQuery,
      userList: [],
      limit: 10,
      page: 1,
      total: "",
      delId: "",
      addUserInfo,
      updateUserInfo,
      updateRoleId,
      PwdModel,
      userId,
      title,
      RoleList,
      addRoleList,
      addRoleIds,
      addUserVisible,
      updateUserVisible,
      confirmUserPwdVisible,
      confirmUserPwdVisible2,
      result,
      updateOldUserName,
      updateResult,
      list,
      addVisible,
      updateVisible,
      delVisible,
      selectVisible,
      imgList,
      AllUserName
    }
  },
  methods: {
    common(type, message) {
      ElMessage({
        type: type,
        message: message
      })
    },
    //多条件查询
    selectByParams() {
      this.$api.user.selectByParams("/user/lists", this.userQuery).then(res => {
        // console.log(res,88888)
        if (res.code === 200) {
          this.userList = toRaw(res.result.data)
          this.common("success", "查询成功")
        } else {
          this.common("error", "查询失败，请重试!")
        }
      })
      this.userQuery = {}
    },

    checkUserNameIsRepeat() {
      this.result = true
      for (let i = 0; i < this.AllUserName.length; i++) {
        if (this.addUserInfo.userName === this.AllUserName[i]) {
          this.common("info", "当前输入角色名称与原有角色名称重复!")
          this.result = false
        }
      }
    },
    //添加用户
    addUser() {
      var e = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/
      if (this.addUserInfo.userName === "" || this.addUserInfo.trueName === "" || this.addUserInfo.email === "" ||
          this.addUserInfo.phone === "" || this.addUserInfo.userPwd === "") {
        this.common("info", "信息没有输入完全!")
      } else if (!(/^1[34578]\d{9}$/.test(this.addUserInfo.phone))) {//手机号格式检验
        //手机号非空的报错信息
        this.common("warning", "手机号格式错误，请重试!")
      } else if (!e.test(this.addUserInfo.email)) {
        this.common("warning", "邮箱格式错误!")
      } else if (this.addUserInfo.userPwd.length < 6) {
        // console.log("密码长度少于六位")
        this.common("warning", "密码长度少于六位,请重新输入！")
      } else {
        console.log(this.addUserInfo)
        this.checkUserNameIsRepeat()
        if (this.result) {
          this.addUserInfo.roleIds = this.addRoleIds.join(",")
          let user = this.addUserInfo
          // console.log("user信息为: ->",user)
          this.$api.user.addUser("/user/addUser", user).then(res => {
            // console.log(res)
            if (res.code === 200) {
              this.common("success", "添加成功!")
              this.addUserVisible = false
              this.addUserInfo = {}
              this.addRoleIds = []
              this.imgList = []
            } else {
              this.common("error", "添加失败，请重试!")
            }
            this.selectList()
            this.addUserInfo.roleIds = ""
          })
        }
      }
    },
    //更新用户的中转方法
    User(msg) {
      // console.log("msg",msg)
      this.confirmUserPwdVisible = true
      this.title = "请输入该用户密码"
      this.PwdModel.userIdStr = toRaw(msg).id
      this.updateUserInfo.id = this.PwdModel.userIdStr
      this.updateUserInfo = JSON.parse(JSON.stringify(msg))
      this.updateRoleId = []
      if (this.updateUserInfo.sex === 0) {
        this.updateUserInfo.label = '女'
      } else if (this.updateUserInfo.sex === 1) {
        this.updateUserInfo.label = '男'
      }
      this.updateOldUserName = msg.userName
      console.log("原用户名为", this.updateOldUserName)
      // console.log("this.updateUserInfo.id: ",this.updateUserInfo)
    },
    //确认用户密码是否正确，正确就可以修改该用户信息，反之不行
    confirmUserPwd() {
      this.confirmUserPwdVisible = true
      // console.log("this.PwdModel.userIdStr:  ",this.PwdModel.userIdStr)
      this.userId.userId = this.PwdModel.userIdStr
      console.log("PwdModel", this.PwdModel)
      this.$api.user.confirmPwd("/user/confirmPwd", this.PwdModel).then(res => {
        if (res.code === 200) {
          this.PwdModel = {}
          // console.log("密码正确")
          this.confirmUserPwdVisible = false
          this.updateUserVisible = true
          // console.log("this.userId: ",this.userId)
          this.$api.Role.queryAllRoles("/role/queryAllRoles", this.userId).then(res => {
            // this.updateRoleId = res.result
            // console.log("roleId",res)
            for (let i = 0; i < res.result.length; i++) {
              if (res.result[i].selected === 'selected') {
                this.updateRoleId.push(res.result[i].id)
              }
            }
            // console.log("res.updateRoleId:  ",this.updateRoleId)
          })
        } else {
          this.common("error", "密码错误，请重试!")
        }
      })
    },

    checkUpdateRoleNameRepeat() {
      this.updateResult = true
      console.log("this.updateUserInfo.userName:  ", this.updateUserInfo.userName)
      console.log("updateOldUserName   :", this.updateOldUserName)
      for (let i = 0; i < this.AllUserName.length; i++) {
        if (this.updateUserInfo.userName === this.AllUserName[i].userName) {
          if (this.updateUserInfo.userName === this.updateOldUserName) {
            // console.log("更新后名字与原来名字一致")
          } else {
            this.updateResult = false
          }
        }
      }
    },
    //更新用户信息
    updateUser() {
      this.checkUpdateRoleNameRepeat()
      if (this.updateUserInfo.label === "1") {
        this.updateUserInfo.sex = 1
      } else if (this.updateUserInfo.label === "0") {
        this.updateUserInfo.sex = 0
      }
      if (this.updateResult) {
        // console.log(this.updateUserInfo)
        this.updateUserInfo.roleIds = this.updateRoleId.join(",")
        let user = this.updateUserInfo
        console.log("user:---------->", user)
        this.$api.user.updateUser("/user/updateUser", user).then(res => {
          // console.log(res)
          if (res.code === 200) {
            this.updateUserVisible = false
            this.common("success", "修改成功!")
            this.selectList()
            this.imgList = []
          } else {
            this.common("error", "修改失败，请重试!")
          }
        })
        this.selectList()
      } else {
        this.common("info", "更新后的角色名称与原有角色重复，请重新输入!")
        console.log(1)
      }
    },

    delUser(msg) {
      this.PwdModel.userIdStr = toRaw(msg).id
      this.delId = toRaw(msg).id
      // console.log(this.delId)
      this.confirmUserPwdVisible2 = true
    },
    delUserConfirm() {
      // console.log(this.PwdModel.userIdStr)
      this.$api.user.confirmPwd("/user/confirmPwd", this.PwdModel).then(res => {
        if (res.code === 200) {
          this.confirmUserPwdVisible2 = false
          this.deleteUser(this.delId)
        } else {
          this.common("error", "密码错误，请重试!")
        }
      })
      this.PwdModel = {}
    },
    //删除用户
    deleteUser(msg) {
      let id = msg
      this.$api.user.updatePassword("/user/deleteUser", id)
          .then(res => {
            if (res.code === 200) {
              this.common("success", "删除成功!")
            } else {
              this.common("error", "删除失败，请重试!")
            }
            // console.log(res)
            this.selectList()
          })
    },

    //初始查询用户列表
    selectList() {
      this.$api.user.selectList("/user/lists", {}).then(res => {
        console.log("初始用户列表： ", res)
        this.userList = toRaw(res.result.data)
        // console.log(toRaw(res.result.data.length))
        this.total = toRaw(res.result.data.length)
      })
    },
    //翻页
    handleCurrentChange(msg) {
      this.userQuery.page = msg
      this.$api.user.selectList("/user/lists", this.userQuery).then(res => {
        if (res.code === 200) {
          this.userList = toRaw(res.result.data)
        }
      })
    },
    AddonSuccess(res) {
      this.addUserInfo.img = res.url
      console.log(res)
    },
    UpdateOnSuccess(res) {
      console.log(res)
      this.updateUserInfo.img = res.url
    },
    getAllUserName() {
      this.$api.user.getAllPhoneInfo("/user/userName").then(res => {
        if (res.code === 200) {
          // console.log(res)
          this.AllUserName = res.result
        }
      })
    }
  },
  mounted() {
    this.selectList()
    this.getAllUserName()
    this.$api.user.queryAllRoles("/role/queryAllRoles").then(res => {
      this.RoleList = res.result
    })
    this.list = this.$store.getters.getPermissionList
    if (JSON.stringify(toRaw(this.list)).includes("601001")) {
      this.addVisible = true
    }
    if (JSON.stringify(toRaw(this.list)).includes("601002")) {
      this.selectVisible = true
    }
    if (JSON.stringify(toRaw(this.list)).includes("601003")) {
      this.updateVisible = true
    }
    if (JSON.stringify(toRaw(this.list)).includes("601004")) {
      this.delVisible = true
    }
  },
}
</script>

<style scoped>
.tableMenu {
  left: 3%;
  position: relative;
  width: 94%;
}

.UserManagement {
  width: 100%;
}

.page {
  position: absolute;
  /*margin: 10% 0 0 0;*/
  width: 60%;
  /*background-color: #8c6fd0;*/
}
</style>