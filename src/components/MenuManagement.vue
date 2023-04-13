<template>
  <div class="MenuManagement">
    <div>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <el-input
          class="w-50 m-2"
          placeholder="菜单名称"
          v-model="moduleQuery.MenuName"
          style="position: relative;width: 17%;margin: 5px;top: 30px"
      />
      &nbsp;&nbsp;
      <el-select v-model="moduleQuery.grade" class="m-2" placeholder="类 型" style="position: relative;width: 17%;top: 30px">
        <el-option label="无"  value="" />
        <el-option label="目 录" value="0" />
        <el-option label="菜 单" value="1" />
        <el-option label="子 项" value="2" />
      </el-select>

      <el-button type="primary" style="position: relative; left: 5px;top: 30px" @click="selectMenuListByName">搜 索</el-button>
      <el-button type="primary" style="position: relative; top: 30px;left: 5px"
                  @click="addMenuModuleVisible=true,addMenuModule.grade='0',addMenuModule.parentId='-1'">添 加 目 录</el-button>
      <el-table :data="MenuList"  class="tableMenu"
                max-height="520"  :default-sort="{ prop: 'createDate', order: 'descending' }"
                :header-cell-style="{ backgroundColor: '#eef5ff',   textAlign: 'center',  }"
                row-style="rowStyle"
      >
        <el-table-column fixed="left" prop="id"  width="100" align="center"/>
        <el-table-column prop="moduleName" label="菜单名称" width="200" header-align="center"  align="center"/>
        <el-table-column prop="optValue" label="权限码" width="100" header-align="center"  align="center"/>
        <el-table-column prop="url" label="菜单路径" width="200" header-align="center"  align="center"/>
        <el-table-column prop="createDate" label="创建时间" width="200" header-align="center"  align="center"/>
        <el-table-column prop="updateDate" label="更新时间" width="200" header-align="center"  align="center"/>
        <el-table-column prop="type" label="类型" width="100" header-align="center"  align="center">
          <template #default="scope">
            <span v-if="scope.row.type==='目录'" style="color:skyblue">{{ scope.row.type }}</span>
            <span v-else-if="scope.row.type==='菜单'" style="color: #37B328">{{ scope.row.type }}</span>
            <span v-else-if="scope.row.type==='子项'" style="color: #8c6fd0">{{ scope.row.type }}</span>
          </template>
 
        </el-table-column>
        <el-table-column fixed="right" label="操 作" width="200" header-align="center"  align="center">
          <template #default="scope">
            <el-button link size="small" type="primary" @click="update(scope.row)">修改</el-button>
            <el-button link type="primary" size="small" style="left: 10px" @click="delModule(scope.row.id)">删 除</el-button>
            <el-button size="small" type="primary" @click="addMenuModuleVisible=true;
                       addMenuModule.parentId=scope.row.id;addMenuModule.grade=scope.row.grade+1" v-if="scope.row.type!=='子项'"
            >添加子项</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="page">
      <el-pagination
          v-model:current-page="moduleQuery.page"
          v-model:page-size="moduleQuery.limit"
          :small="true"
          :disabled="false"
          :background="true"
          layout="prev,pager,next,jumper"
          :total="total"
          @current-change="handleCurrentChange"
      />
    </div>
<!--添加目录-->
    <el-dialog
        v-model="addMenuModuleVisible"
        title="添加一级菜单(子项)信息"
        width="30%"
    >
      <el-form
          label-position="left"
          label-width="90px"
          :model="addMenuModule"
          style="width: 100%"
      >
        <el-form-item label="菜单名">
          <el-input  v-model="addMenuModule.moduleName" placeholder="请输入菜单名(必填)"/>
        </el-form-item>
        <el-form-item label="菜单路径">
          <el-input  v-model="addMenuModule.url" placeholder="请输入菜单路径(必填)"/>
        </el-form-item>
        <el-form-item label="排序值">
          <el-input  v-model="addMenuModule.orders" placeholder="请输入排序值（仅支持数字）"/>
        </el-form-item>
        <el-form-item label="权限码">
          <el-input  v-model="addMenuModule.optValue" placeholder="请输入权限码(必填)"/>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="addModule">
          确 定
        </el-button>
         <el-button type="primary" @click="this.addMenuModuleVisible = false,this.addMenuModule={}">
          取 消
        </el-button>
      </span>
      </template>
    </el-dialog>
<!--更新-->
    <el-dialog
        v-model="updateMenuModuleVisible"
        title="更新菜单信息"
        width="30%"
    >
      <el-form
          label-position="left"
          label-width="90px"
          :model="updateMenuModule"
          style="width: 100%"
      >
        <el-form-item label="菜单名">
          <el-input  v-model="updateMenuModule.moduleName" placeholder="请输入菜单名(必填)"/>
        </el-form-item>
        <el-form-item label="菜单路径">
          <el-input  v-model="updateMenuModule.url" placeholder="请输入菜单路径(必填)"/>
        </el-form-item>
        <el-form-item label="排序值">
          <el-input  v-model="updateMenuModule.orders" placeholder="请输入排序值（仅支持数字）"/>
        </el-form-item>
        <el-form-item label="权限码">
          <el-input  v-model="updateMenuModule.optValue" placeholder="请输入权限码(必填)"/>
        </el-form-item>
        <el-form-item label="菜单级别" placeholder="菜单级别">
          <el-select v-model="updateMenuModule.type" disabled="disabled">
            <el-option label="目 录" value="0" />
            <el-option label="菜 单" value="1" />
            <el-option label="子 项" value="2" />
          </el-select>
<!--          <el-input  v-model="updateMenuModule.grade" placeholder="请输入权限码(必填)"/>-->
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="updateModule">
          确 定
        </el-button>
         <el-button type="primary" @click="this.updateMenuModuleVisible = false,this.updateMenuModule={}">
          取 消
        </el-button>
      </span>
      </template>
    </el-dialog>

  </div>
</template>

<script>
// eslint-disable-next-line no-unused-vars
import {toRaw,reactive,ref} from "@vue/reactivity"
import {ElMessage,ElMessageBox} from "element-plus";

export default {
  name: "MenuManagement",
  data(){
    let MenuList = reactive([])
    let moduleQuery = reactive({MenuName:"",grade:"",limit:10,page:1})
    let total = ref("")
    let addMenuModule = reactive({moduleName:"",url:"",parentId:"",grade:"",optValue:"",orders:"",})
    let addMenuModuleVisible = ref(false)
    let updateMenuModuleVisible = ref(false)
    let updateMenuModule = reactive({moduleName:"",url:"",parentId:"",grade:"",optValue:"",orders:""})
    let permissionList = reactive([])
    let temp = reactive({moduleName:"",url:"",parentId:"",grade:"",optValue:"",orders:""})//更新菜单内容备份
    let result1 = ref(false)
    let result2 = ref(false)
    let result3 = ref(false)
    return{
      MenuList,moduleQuery,total,addMenuModule,addMenuModuleVisible,updateMenuModule,
      updateMenuModuleVisible,permissionList,result1,result2,result3,temp
    }
  },
  methods:{
    //查找菜单列表
    queryModuleList(){
      this.$api.Module.queryModuleList("/module/lists").then(res=>{
        // console.log(res)
        this.MenuList = res.result.data
        this.total = res.result.count
        for (let i = 0; i < this.MenuList.length; i++) {
            if (this.MenuList[i].grade === 0){
              this.MenuList[i].type = "目录"
            }else if (this.MenuList[i].grade === 1){
              this.MenuList[i].type = "菜单"
            }else if (this.MenuList[i].grade === 2){
              this.MenuList[i].type = "子项"
            }
        }
      })
    },
    //多条件查询
    selectMenuListByName(){
      console.log(this.moduleQuery)
      this.$api.Module.queryModuleList("/module/lists",this.moduleQuery).then(res=>{
        console.log(res,336699)
        if (res.code===200){
          ElMessage({
            type:"success",
            message:"查找成功!"
          })
          this.MenuList = res.result.data
          this.total = res.result.count
          for (let i = 0; i < this.MenuList.length; i++) {
            if (this.MenuList[i].grade === 0){
              this.MenuList[i].type = "目录"
            }else if (this.MenuList[i].grade === 1){
              this.MenuList[i].type = "菜单"
            }else if (this.MenuList[i].grade === 2){
              this.MenuList[i].type = "子项"
            }
          }
        }else {
          ElMessage({
            type:"error",
            message:"查询失败，请重试!"
          })
        }
      })
    },
    //翻页
    handleCurrentChange(msg){
      this.moduleQuery.page = msg
      this.$api.Module.queryModuleList("/module/lists",this.moduleQuery).then(res=>{
        console.log(res)
        this.MenuList = res.result.data
        for (let i = 0; i < this.MenuList.length; i++) {
          if (this.MenuList[i].grade === 0){
            this.MenuList[i].type = "目录"
          }else if (this.MenuList[i].grade === 1){
            this.MenuList[i].type = "菜单"
          }else if (this.MenuList[i].grade === 2){
            this.MenuList[i].type = "子项"
          }
        }
      })
    },
    addModule(){
      console.log(this.addMenuModule)
      let module = this.addMenuModule
      this.permissionList = this.$store.getters.getPermissionList
      // console.log(this.permissionList.includes(this.addMenuModule.optValue))
      if (this.addMenuModule.moduleName===""||this.addMenuModule.optValue===""||this.addMenuModule.url===""){
          ElMessage({
            type:"info",
            message:"必要输入信息没有填写完全"
          })
      }else{
          this.queryModuleByGradeAndModuleName(module)
          this.queryModuleByGradeAndUrl(module)
          this.queryModuleByOptValue(module)
          console.log("this.result1:  ",this.result1)
          console.log("this.result2:  ",this.result2)
          console.log("this.result3:  ",this.result3)
      }
      if (this.result1 && this.result2 && this.result3){
        this.$api.Module.addModuleInfo("/module/addModule",module).then(res=>{
          console.log(res)
          if (res.code===200){
            ElMessage({
              type:"success",
              message:"添加成功！"
            })
            this.addMenuModule = {}
            this.result1 = false
            this.result2 = false
            this.result3 = false
            this.moduleQuery.page=1
            this.addMenuModuleVisible = false
            this.queryModuleList()
          }else {
            ElMessage({
              type:"error",
              message:"添加失败，请重试!"
            })
          }
        })
      }
    },
    delModule(msg){
      ElMessageBox.confirm(  '确认要删除吗？',
          'Warning',
          {
            confirmButtonText:"是的",
            cancelButtonText:"不,我再想想",
          }
      ).then(()=>{
        let id = msg
        this.$api.Module.delModuleById("/module/deleteModule",id).then(res=>{
          if (res.code===200){
            ElMessage({
              type:"success",
              message:"删除成功!"
            })
            this.queryModuleList()
            // this.moduleQuery.page=1
          }else {
            ElMessage({
              type:"error",
              message:"删除失败，请重试!"
            })
          }
        })
      }).catch(()=>{
        ElMessage({
          message:"取消操作",
          type:"info"
        })
      })
    },
    update(msg){
      this.updateMenuModuleVisible=true;this.updateMenuModule=msg;this.temp=msg
    },
    updateModule(){
      console.log(this.updateMenuModule)
      console.log(this.temp)
      if (this.updateMenuModule.moduleName !== this.temp.moduleName){
        this.queryModuleByGradeAndModuleName(this.updateMenuModule)
      }else {
        this.result1 = true
      }
      if (this.updateMenuModule.url !== this.temp.url){
        this.queryModuleByGradeAndUrl(this.updateMenuModule)
      }else {
        this.result2 = true
      }
      if (this.updateMenuModule.optValue !== this.temp.optValue){
        this.queryModuleByOptValue(this.updateMenuModule)
      }else {
        this.result3 = true
      }
      console.log(this.result2)
      if (this.result1 && this.result2 && this.result3){
        this.$api.Module.updateMenuInfo("/module/updateModule",this.updateMenuModule).then(res=>{
          console.log(res)
          if (res.code===200){
            ElMessage({
              type:"success",
              message:"更新信息成功!"
            })
            this.result1 = false
            this.result2 = false
            this.result3 = false
            this.updateMenuModule = {}
            this.temp = {}
            this.queryModuleList()
          }else {
            ElMessage({
              type:"error",
              message:"更新失败，请重试!"
            })
          }
        this.updateMenuModuleVisible = false
        })
      }
    },
    queryModuleByGradeAndModuleName(module){
      this.$api.Module.queryModuleByGradeAndModuleName("/module/queryModuleByGradeAndModuleName",module).then(res=>{
        console.log(res)
        if (res.code===200){
          this.result1 = true
        }else {
          ElMessage({
            type:"info",
            message:"当前模块下已经有该模块名称,请重新输入!"
          })
          this.result1 = false
        }
      })
    },
    queryModuleByGradeAndUrl(module){
      this.$api.Module.queryModuleByGradeAndUrl("/module/queryModuleByGradeAndUrl",module).then(res=>{
        console.log(res)
        if (res.code===200){
          this.result2 = true
        }else {
          ElMessage({
            type:"info",
            message:"该路径与当前模块下子项路径一致，请重新输入!"
          })
          this.result2 = false
        }
      })
    },
    queryModuleByOptValue(module){
      this.$api.Module.queryModuleByOptValue("/module/queryModuleByOptValue",module).then(res=>{
        console.log(res)
        if (res.code===200){
          this.result3 = true
        }else {
          ElMessage({
            type:"info",
            message:"该权限码与已有权限码冲突，请重新输入!"
          })
          this.result3 = false
        }
      })
    }
  },
  mounted() {
    this.queryModuleList()
  }
}
</script>

<style scoped>
.MenuManagement{
  position: relative;
  width: 100%;
  height: 100%;
}
.tableMenu{
  top: 40px;
  left: 3%;
  position: relative;
  width: 80%;
}
.page{
  position: absolute;
  top: 90%;
  width: 100%;
  color: #ffffff;
}
</style>