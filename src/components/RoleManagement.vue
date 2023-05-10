<template>
  <div class="RoleManagement">
    <div class="search">
      <el-input
          v-model="RoleQuery.roleName"
          class="w-50 m-2"
          placeholder="角色名"
          style="position: relative;width: 12%;margin: 1% 0 0 3%"
      />
      &nbsp;&nbsp;
      <el-button type="primary" style="margin: 1% 0 0 5px" @click="selectRoleListByName" v-if="selectVisible">搜 索</el-button>
      <el-button type="primary" style="margin: 1% 0 0 10px" @click="addRoleVisible=true" v-if="addVisible">添 加 角 色</el-button>
    </div>
    <div>
      <el-table :data="roleList"  class="tableMenu"
                max-height="520"  :default-sort="{ prop: 'createDate', order: 'descending' }"
                :header-cell-style="{ backgroundColor: '#eef5ff',   textAlign: 'center',  }"
                row-style="rowStyle"
      >
        <el-table-column fixed="left" sortable prop="id" label="编号" width="100" align="center"/>
        <el-table-column prop="roleName" label="角色" width="200" header-align="center"  align="center"/>
        <el-table-column prop="roleRemark" label="角色备注" width="200" header-align="center"  align="center"/>
        <el-table-column prop="createDate" sortable label="创建时间" width="300" header-align="center"  align="center"/>
        <el-table-column prop="updateDate" label="更新时间" width="300" header-align="center"  align="center"/>
        <el-table-column fixed="right" label="操 作" width="200" header-align="center"  align="center">
          <template #default="scope">
            <el-button link size="small" type="primary" @click="updateRoleVisible=true,updateRoleInfo=JSON.parse(JSON.stringify(scope.row)),
                       updateOldRoleName=scope.row.roleName" v-show="updateVisible"
            >编 辑</el-button
            >
            <el-button link size="small" type="danger" @click="delRole(JSON.parse(JSON.stringify(scope.row)))"
                       v-show="delVisible"
            >删 除</el-button>
            <el-button type="primary" size="small" style="left: 10px" @click="this.empowerVisible=true;addGrantModel.roleId=scope.row.id;PermissionEcho(JSON.parse(JSON.stringify(scope.row)))"
                       v-show="empower"
            >授 权</el-button>

          </template>
        </el-table-column>
      </el-table>
    </div>
  <!--添加用户-->
    <el-dialog
        v-model="addRoleVisible"
        title="添加用户"
        width="30%"
    >
      <el-form
          label-position="left"
          label-width="90px"
          :model="addRoleInfo"
          style="width: 100%"
      >
        <el-form-item label="角色名">
          <el-input v-model="addRoleInfo.roleName" placeholder="请输入角色名"/>
        </el-form-item>
        <el-form-item label="角色备注">
          <el-input v-model="addRoleInfo.roleRemark" placeholder="请输入角色备注"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
           <el-button type="primary" @click="this.addRoleVisible = false,this.addRoleList={}">
            取 消
          </el-button>
          <el-button type="primary" @click="addRole">
            添 加
          </el-button>
        </span>
      </template>
    </el-dialog>

  <!--更新用户-->
    <el-dialog
        v-model="updateRoleVisible"
        title="添加用户"
        width="30%"
    >
      <el-form
          label-position="left"
          label-width="90px"
          :model="updateRoleInfo"
          style="width: 100%"
      >
        <el-form-item label="角色名">
          <el-input v-model="updateRoleInfo.roleName" placeholder="请输入角色名"/>
        </el-form-item>
        <el-form-item label="角色备注">
          <el-input v-model="updateRoleInfo.roleRemark" placeholder="请输入角色备注"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
           <el-button type="primary" @click="this.updateRoleVisible = false,this.updateRoleInfo={}">
            取 消
          </el-button>
          <el-button type="primary" @click="updateRole">
            更新信息
          </el-button>
        </span>
      </template>
    </el-dialog>

  <!--用户授权  empower-->
    <el-dialog
        v-model="empowerVisible"
        title="用户授权"
        width="30%"
    >
      <el-tree
          :data="empowerList"
          node-key="id"
          :check-strictly="treeStrictly"
          :props="defaultProps"
          v-model="checkedKeys"
          :default-checked-keys="checkedKeys"
          show-checkbox
          highlight-current
          ref="tree">
      </el-tree>

      <template #footer>
        <span class="dialog-footer">
           <el-button type="primary" @click="empowerCancel">
            取 消
          </el-button>
          <el-button type="primary" @click="PermissionModification">
            确 认
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import {toRaw,reactive,ref} from "@vue/reactivity"
import {ElMessage, ElMessageBox} from "element-plus";
// eslint-disable-next-line no-unused-vars
import Cookies from 'js-cookie'

export default {
  name: "RoleManagement",
  data(){
    let RoleQuery = reactive({roleName:'',limit:10, page:1, total:""})
    let roleList = reactive([])
    let addRoleVisible = ref(false)
    let addRoleInfo = reactive({roleName:"",roleRemark:""})
    let result = ref(true)
    let updateResult = ref(true)
    let updateRoleInfo = reactive({roleName:"",roleRemark:""})
    let updateRoleVisible = ref(false)
    let updateOldRoleName = ""
    let empowerVisible = ref(false)
    let empowerList = reactive([])
    let addGrantModel = reactive({roleId:"",mids:{}})
    let checkedKeys = reactive([])
    let defaultProps = reactive(
              {children:"children",
                      label:"moduleName",
                    checked: function(node) {
                      return node.checked === true
                    }
              })
    let PermissionEchoList = reactive([])//权限列表回显
    let treeStrictly = false



    let list = reactive([])
    let addVisible = ref(false)
    let selectVisible =ref(false)
    let updateVisible = ref(false)
    let delVisible = ref(false)
    let empower = ref(false)
    return{
      RoleQuery,roleList,addRoleVisible,addRoleInfo,result,updateRoleInfo,updateRoleVisible,updateResult,updateOldRoleName,empowerVisible,
      defaultProps,empowerList,addGrantModel,PermissionEchoList,checkedKeys,treeStrictly,
      list,addVisible,selectVisible,updateVisible,delVisible,empower
    }
  },
  methods:{
    selectRoleListByName(){
      this.$api.Role.selectByParams("/role/lists",this.RoleQuery).then(res=>{
        // console.log(res)
        if (res.code==200){
          ElMessage({
            type:"success",
            message:"查找成功!"
          })
          // this.RoleQuery.roleName = ""
          this.roleList = res.result.data
        }else {
          ElMessage({
            type:"error",
            message:"查询失败，请重试!"
          })
        }
        // console.log("角色基本信息为: ",res.result.data)
      })
    },

    selectRoleList(){
      this.$api.Role.selectByParams("/role/lists").then(res=>{
        console.log(res)
          this.roleList = res.result.data
      })
    },

    checkAddRoleNameRepeat(){
      this.result = true
      for (let i = 0; i < this.roleList.length; i++) {
        if (this.addRoleInfo.roleName===this.roleList[i].roleName){
          ElMessage({
            type:"info",
            message:"添加的角色名称与原有角色重复，请重新输入!"
          })
          this.result = false
        }
      }
      console.log(this.result)
    },
    checkUpdateRoleNameRepeat(){
      this.updateResult = true
      for (let i = 0; i < this.roleList.length; i++) {
        if (this.updateRoleInfo.roleName===this.roleList[i].roleName){
          if (this.updateRoleInfo.roleName===this.updateOldRoleName){
            // console.log("更新后名字与原来名字一致")
          }else {
            this.updateResult = false
          }
        }
      }
    },

    addRole(){
      if (this.addRoleInfo.roleName==="" || this.addRoleInfo.roleRemark===""){
        ElMessage({
          type:"info",
          message:"信息没有输入完整!"
        })
      }else {
        this.checkAddRoleNameRepeat()
        console.log(this.result)
        if (this.result) {
          this.$api.Role.addRoleInfo("/role/addRole",toRaw(this.addRoleInfo)).then(res=>{
            if (res.code===200){
              ElMessage({
                type:"success",
                message:"添加成功!"
              })
            }else {
              ElMessage({
                type:"error",
                message:"添加失败，请重试!"
              })
            }
          this.addRoleInfo = {}
          this.addRoleVisible = false
          this.selectRoleList()
          })
        }
      }
    },

    delRole(msg){
      ElMessageBox.confirm(
          '确认要删除吗？',
          'Warning',
          {
            confirmButtonText:"是的",
            cancelButtonText:"不,我再想想",
          }
      ).then(()=>{
        // console.log("msg:  ",toRaw(msg).id)
        let id = toRaw(msg).id
        this.$api.Role.deleteRole("/role/deleteRole",id).then(res=>{
          console.log(res)
          if (res.code===200){
            ElMessage({
              type:"success",
              message:"删除成功!"
            })
          }else {
            ElMessage({
              type:"error",
              message:"删除失败，请重试!"
            })
          }
          this.selectRoleList()
        })
      }).catch(()=>{
        ElMessage({
          message:"取消操作",
          type:"info"
        })
      })
    },

    updateRole(){
      this.checkUpdateRoleNameRepeat()
      // console.log("this.updateResult: ",this.updateResult)
      // console.log("updateRoleInfo: ",this.updateRoleInfo)
      // console.log("updateOldRoleName:  ",this.updateOldRoleName)
      if (this.updateResult){
        this.$api.Role.UpdateRole("/role/updateRole",this.updateRoleInfo).then(res=>{
          // console.log(res)
          if (res.code===200){
            ElMessage({
              type:"success",
              message:"更新信息成功"
            })
          }else {
            ElMessage({
              type:"error",
              message:"更新失败，请重试!"
            })
          }
          this.updateRoleInfo = {}
          this.updateRoleVisible = false
          this.selectRoleList()
        })
      }else {
        ElMessage({
          type:"info",
          message:"更新后的角色名称与原有角色重复，请重新输入!"
        })
      }
    },
    //权限选择清空
    empowerCancel(){
      this.empowerVisible = false
      this.checkedKeys=[]
      // this.$nextTick(() => {
      //   this.$refs.tree.setCheckedKeys(this.empowerList)
      // })
    },
    //获取权限列表
    getAllEmpower(){
        this.$api.Role.getEmpowerList("/module/lists").then(res=>{
          let empowerList = res.result.data
          for (let i = 0; i < empowerList.length; i++) {
            empowerList[i].children = []
            for (let j = 0; j < empowerList.length; j++) {
              if (empowerList[i].id===empowerList[j].parentId){
                empowerList[i].children.push(empowerList[j])
              }
            }
          }
          for (let i = 0; i < empowerList.length; i++) {
            if (empowerList[i].parentId!==-1){
              empowerList.splice(i,1)
              i--
            }
          }
          this.empowerList = empowerList
          // console.log("empowerList:",empowerList)
        })
    },
    //授权
    PermissionModification(){
      var parentNodes = this.$refs.tree.getHalfCheckedKeys();
      var checkedNodes = this.$refs.tree.getCheckedNodes();
      // console.log("checkedNodes:  ",checkedNodes)
      var mids = []
      for (let i = 0; i < toRaw(checkedNodes).length; i++) {
          mids.push(toRaw(checkedNodes[i]).id)
      }
      for (let i = 0; i < parentNodes.length; i++) {
          mids.push(parentNodes[i])
      }
      this.addGrantModel.mids = mids
      console.log("roleId: ",this.addGrantModel.roleId)
      console.log("mids: ",mids)
      this.$api.Role.addGrant("/role/addGrant",this.addGrantModel).then(res=>{
        // console.log(res)
        if (res.code===200){
          ElMessage({
            type:"success",
            message:"授权成功"
          })
    this.$api.Permission.queryUserHasRoleHasPermissionByUserId("/permission/lists", {userIdStr:Cookies.get("userIdStr")   }).then(res=>{
      // console.log(res.result)
      if (res.code===200){
        this.list = toRaw(res.result)
        // console.log(this.list)
        // console.log(toRaw(this.list).includes("10"))//true
        this.$store.commit("setPermissionList",res.result)
      }
    })
        }else {
          ElMessage({
            type:"Error",
            message:"授权失败,请重试!"
          })
        }
      this.addGrantModel = {}
      this.checkedKeys = []
      this.empowerVisible = false

      })
    },
    //权限列表回显
    PermissionEcho(msg){
      this.treeStrictly=true
      // var checknode = this.$refs.tree.getCheckedNodes();
      // console.log("checknode",checknode)
      // console.log("msg",toRaw(msg))
      let roleId = toRaw(msg).id
      this.$api.Module.queryAllModulesById("/module/queryAllModules", {roleId}).then(res=>{
        console.log("res:",res)
        this.PermissionEchoList = res.result
        for (let i = 0; i < this.PermissionEchoList.length; i++) {
          this.PermissionEchoList[i].children = []
          for (let j = 0; j < this.PermissionEchoList.length; j++) {
            if (this.PermissionEchoList[i].id===this.PermissionEchoList[j].pid){
              // this.PermissionEchoList[j].moduleName = this.PermissionEchoList[j].name
              this.PermissionEchoList[i].children.push(this.PermissionEchoList[j])
              if (this.PermissionEchoList[j].checked===true){
                this.checkedKeys.push(this.PermissionEchoList[j].id)
              }
            }
          }
        }
        for (let i = 0; i < this.PermissionEchoList.length; i++) {
          this.PermissionEchoList[i].moduleName = this.PermissionEchoList[i].name
          if (this.PermissionEchoList[i].pid!==-1){
            this.PermissionEchoList.splice(i,1)
            i--
          }
          if (this.PermissionEchoList[i].checked===true){
            this.checkedKeys.push(this.PermissionEchoList[i].id)
          }
        }
        // this.PermissionEchoList.forEach(item=>{
        //   if (!this.$refs.tree.getNode(item.id).children || !this.$refs.tree.getNode(item.id).length){
        //     this.checkedKeys.push(item.id)
        //   }
        // })
        // console.log(this.checkedKeys)
        this.empowerList = this.PermissionEchoList
        console.log("this.empowerList",this.empowerList)
        // this.$refs.tree.setCheckedKeys(this.checkedKeys)
        // console.log("PermissionEchoList",toRaw(this.PermissionEchoList))
        // console.log("checkedKeys:   ",this.checkedKeys)
      })
      setTimeout(()=>{
        this.treeStrictly=false
      },50)
    },

  },
  mounted() {
    this.selectRoleList()
    this.getAllEmpower()
    this.list = this.$store.getters.getPermissionList
    if (JSON.stringify(toRaw(this.list)).includes("602001")) {
      this.addVisible = true
    }
    if (JSON.stringify(toRaw(this.list)).includes("602002")) {
      this.selectVisible = true
    }
    if (JSON.stringify(toRaw(this.list)).includes("602003")) {
      this.updateVisible = true
    }
    if (JSON.stringify(toRaw(this.list)).includes("602004")) {
      this.delVisible = true
    }
    if (JSON.stringify(toRaw(this.list)).includes("602005")) {
      this.empower = true
    }
  }
}
</script>

<style scoped>
.search{

  width: 100%;
  height: 40px;
  top: 1%;
  left: 3%;
}
.tableMenu{
  margin: 1.5% 0 0 0;
  left: 3%;
  position: relative;
  width: 80%;
}
</style>