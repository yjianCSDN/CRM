<template>
  <div>
    <div class="search">
      <el-input
          v-model="dictionaryQuery.dataDicName"
          class="w-50 m-2"
          placeholder="角色名"
          style="position: relative;width: 12%;margin: 1% 0 0 5%"
      />
      &nbsp;&nbsp;
      <el-button type="primary" style="margin: 1% 0 0 5px" @click="selectDictionaryByParams" v-show="selectVisible">搜 索</el-button>
      <el-button type="primary" style="margin: 1% 0 0 10px" @click="AddDictionaryVisible=true" v-show="addVisible">添 加</el-button>
    </div>
    <div>
      <el-table :data="dictionaryList" class="tableMenu"  max-height="520"
                :default-sort="{ prop: 'createDate', order: 'descending' }"
                :header-cell-style="{ backgroundColor: '#eef5ff',   textAlign: 'center',  }"
      >
        <el-table-column prop="id" label="编号" width="100" fixed="left" align="center"/>
        <el-table-column prop="dataDicName" label="字典名称" width="150" header-align="center" align="center"/>
        <el-table-column prop="dataDicValue" label="字典取值" width="150" header-align="center" align="center"/>
        <el-table-column prop="createDate" label="创建时间" width="200" header-align="center" align="center"/>
        <el-table-column prop="updateDate" label="更新时间" width="200" header-align="center" align="center"/>
        <el-table-column fixed="right" label="操作" width="150" header-align="center">
          <template #default="scope">
            <el-button link type="primary" size="small" @click="editDictionaryVisible=true,editDicInfo=JSON.parse(JSON.stringify(scope.row))"
                       v-show="updateVisible"
            >编 辑
            </el-button
            >
            <el-button link type="primary" size="small" @click="delDictionary(JSON.parse(JSON.stringify(scope.row)))" v-show="delVisible"
            >删 除
            </el-button>
          </template>
        </el-table-column>
        <template v-slot:empty>
          <p>暂无记录</p>
        </template>
      </el-table>
    </div>
    <div class=page>
      <el-pagination
          v-model:current-page="dictionaryQuery.page"
          v-model:page-size="dictionaryQuery.limit"
          :small="true"
          :disabled="false"
          :background="true"
          layout="prev,pager,next,jumper"
          :total="total"
          @current-change="handleCurrentChange"
      />
    </div>

    <!--添加字典-->
    <el-dialog
        v-model="AddDictionaryVisible"
        title="添加字典"
        width="32%"
    >
      <el-form
          label-position="left"
          label-width="90px"
          :model="insertDicInfo"
          style="width: 100%"
      >
        <el-form-item label="字典名称">
          <el-input v-model="insertDicInfo.dataDicName" style="width: 50%" placeholder="请输入字典名称"/>
        </el-form-item>

        <el-form-item label="字典值">
          <el-input v-model="insertDicInfo.dataDicValue" type="text"
                    style="width: 50%" placeholder="请输入字典值"/>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="AddDictionaryVisible = false,this.insertDicInfo={}">取 消</el-button>
        <el-button type="primary" @click="addDicInfo">
          添 加
        </el-button>
      </span>
      </template>
    </el-dialog>

    <!--修改字典-->
    <el-dialog
        v-model="editDictionaryVisible"
        title="编辑信息"
        width="32%"
    >
      <el-form
          label-position="left"
          label-width="90px"
          :model="editDicInfo"
          style="width: 100%"
      >
        <el-form-item label="字典名称">
          <el-input v-model="editDicInfo.dataDicName" style="width: 50%" placeholder="无"/>
        </el-form-item>

        <el-form-item label="字典值">
          <el-input v-model="editDicInfo.dataDicValue" type="text"
                    style="width: 50%" placeholder="无"/>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="editDictionaryVisible = false,this.editDicInfo={}">取 消</el-button>
        <el-button type="primary" @click="updateDicInfo">
          修 改
        </el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import {reactive, ref, toRaw} from "@vue/reactivity";
import {ElMessage, ElMessageBox} from "element-plus";

export default {
  name: "DictionaryManagement",
  data(){
    let dictionaryQuery = reactive({page:1,limit:10,dataDicName:"",dataDicValue:""})
    let dictionaryList = reactive([])
    let total = ref("")
    let AddDictionaryVisible = ref(false)
    let insertDicInfo = reactive({dataDicName:"",dataDicValue:""})//添加字典信息
    let editDicInfo = reactive({})
    let editDictionaryVisible = ref(false)//修改字典信息

    let list = reactive([])
    let addVisible = ref(false)
    let selectVisible = ref(false)
    let updateVisible = ref(false)
    let delVisible = ref(false)
    return{
      dictionaryQuery,dictionaryList,total,AddDictionaryVisible,insertDicInfo,editDicInfo,editDictionaryVisible,
      list,addVisible,selectVisible,updateVisible,delVisible
    }
  },
  methods:{
    queryByParams(){
        this.$api.Dictionary.queryByParams("/Dictionary/lists",{}).then(res=>{
          // console.log(res)
          this.dictionaryList=res.result.data
          this.total=res.result.count
        })
    },
    selectDictionaryByParams(){
      // console.log(this.dictionaryQuery)
      this.$api.Dictionary.queryByParams("/Dictionary/lists",this.dictionaryQuery).then(res=>{
        // console.log("selectDictionaryByParams:  ",res)
        if (res.code===200){
          this.dictionaryList = res.result.data
          ElMessage({type:"success",message:"查找成功"})
        }else {
          ElMessage({type:"error",message:"查找失败，请稍后重试!"})
        }
      })
    },
    handleCurrentChange(msg){
      this.dictionaryQuery.page=msg
      this.$api.Dictionary.queryByParams("/Dictionary/lists",this.dictionaryQuery).then(res=>{
        // console.log("selectDictionaryByParamsssssssssss:---->  ",res)
        if (res.code===200){
          this.dictionaryList=res.result.data
        }else {
          ElMessage({type:"error",message:"似乎出了一些小问题..."})
        }
      })
    },
    delDictionary(msg){
      // console.log(msg)
      let id = msg.id
      ElMessageBox.confirm(
          '确认要删除此条记录吗？',
          'Warning',
          {
            confirmButtonText: "是的",
            cancelButtonText: "不",
          }
      ).then(() => {
        this.$api.Dictionary.delDictionary("/Dictionary/delDictionary",id).then(res=>{
          // console.log(res)
          if (res.code === 200) {
            ElMessage({
              message: "删除成功",
              type: "success"
            })
            this.handleCurrentChange(this.dictionaryQuery.page)
          }else {
            ElMessage({type:"error",message:"删除失败，请重试!"})
          }
        })
      }).catch(() => {
        ElMessage({
          message: "取消操作",
          type: "info"
        })
      })
    },
    addDicInfo(){
      if (this.insertDicInfo.dataDicValue===""||this.insertDicInfo.dataDicName===""){
        ElMessage({type:"info",message:"信息没有输入完全"})
      }else {
        // console.log(this.insertDicInfo)
        this.$api.Dictionary.addDictionaryInfo("/Dictionary/addDictionary",this.insertDicInfo).then(res=>{
          // console.log(res)
          if (res.code===200){
            ElMessage({type:"success",message:"添加成功"})
            this.AddDictionaryVisible=false
            this.insertDicInfo={}
            this.queryByParams()
          }else {
            ElMessage({type:"error",message:"添加失败，请重试"})
          }
        })
      }
    },
    updateDicInfo(){
      if (this.editDicInfo.dateDicName===""||this.editDicInfo.dataDicValue===""){
        ElMessage({type:"warning",message:"信息缺失"})
      }else {
        // console.log(this.editDicInfo)
        this.$api.Dictionary.updateDictionaryInfo("/Dictionary/updateDictionary",this.editDicInfo).then(res=>{
          // console.log(res)
          if (res.code===200){
            ElMessage({type:"success",message:"修改成功"})
            this.editDictionaryVisible=false
            this.handleCurrentChange(this.dictionaryQuery.page)
          }else {
            ElMessage({type:"error",message:"修改失败,请重试！"})
          }
        })
      }
    }
  },
  mounted() {
    this.queryByParams()
    this.list = this.$store.getters.getPermissionList
    if (JSON.stringify(toRaw(this.list)).includes("604001")) {
      this.addVisible = true
    }
    if (JSON.stringify(toRaw(this.list)).includes("604002")) {
      this.selectVisible = true
    }
    if (JSON.stringify(toRaw(this.list)).includes("604003")) {
      this.updateVisible = true
    }
    if (JSON.stringify(toRaw(this.list)).includes("604004")) {
      this.delVisible = true
    }
  }
}
</script>

<style scoped>
.tableMenu {
  left: 5%;
  top: 10px;
  position: relative;
  width: 960px;
}
.page {
  position: absolute;
  top: 88%;
  width: 100%;
  color: #ffffff;
}
</style>