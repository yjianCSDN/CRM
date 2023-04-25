<template>
  <div class="ServiceCreation">
    <div class="search">
      <el-input
          v-model="customerServeQuery.customer"
          class="w-50 m-2"
          placeholder="客户名"
          style="position: relative;width: 10%;margin: 1% 0 0 1%"
      />
      <el-select  class="m-2" placeholder="请选择服务类型" v-model="customerServeQuery.serveType" style="margin: 1% 0 0 10px">
        <el-option label="无"     value="" />
        <el-option label="咨询"   value="6" />
        <el-option label="建议"   value="7" />
        <el-option label="投诉"   value="8" />
      </el-select>
      &nbsp;&nbsp;
      <el-button type="primary"  style="margin: 1% 0 0 0" @click="queryServiceListByParams" v-show="selectVisible">搜  &nbsp;&nbsp;&nbsp; 索</el-button>
      <el-button type="primary"  style="margin: 1% 0 0 10px" @click="addServeVisible=true" v-show="addVisible">添  &nbsp;&nbsp;&nbsp; 加</el-button>
    </div>

    <div>
        <el-table :data="serveList"  class="tableMenu"
                  max-height="550"  :default-sort="{ prop: 'createDate', order: 'descending' }"
                  :header-cell-style="{ backgroundColor: '#eef5ff',   textAlign: 'center',  }"
                  row-style="rowStyle"
        >
          <el-table-column fixed="left" prop="id" label="编号" width="100" align="center"/>
          <el-table-column prop="customer" label="客户名" width="150" header-align="center"  align="center"/>
          <el-table-column prop="dicValue" label="服务类型" width="200" header-align="center"  align="center"/>
          <el-table-column prop="overview" label="概要信息" width="200" header-align="center"  align="center"/>
          <el-table-column prop="createPeople" label="创建人" width="150" header-align="center"  align="center"/>
          <el-table-column prop="createDate" label="创建时间" width="210" header-align="center"  align="center"/>
          <el-table-column prop="updateDate" label="更新时间" width="200" header-align="center"  align="center"/>

          <template v-slot:empty>
            <p>暂时没有数据</p>
          </template>
        </el-table>
      <div class="page">
        <el-pagination
            v-model:current-page="customerServeQuery.page"
            v-model:page-size="customerServeQuery.limit"
            :small="true"
            :disabled="false"
            :background="true"
            layout="prev,pager,next,jumper"
            :total="total"
            @current-change="handleCurrentChange"
        />
      </div>
      </div>

    <!--添加服务-->
    <el-dialog
        v-model="addServeVisible"
        title="服务管理-服务创建"
        width="40%"
    >
      <el-form
          label-position="right"
          label-width="90px"
          :model="addServeInfo"
          style="width: 100%"
      >
        <el-row>
          <el-col :span="12">
            <el-form-item label="服务类型">
              <el-select  class="m-2" placeholder="请选择" v-model="addServeInfo.serveType" style="width: 80%">
                <el-option label="无"     value="" />
                <el-option label="咨询"   value="6" />
                <el-option label="建议"   value="7" />
                <el-option label="投诉"   value="8" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户" style="margin: 0 0 0 -50px">
              <el-input v-model="addServeInfo.customer" placeholder="请输入客户名(必填)" style="width: 70%"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="服务内容">
          <el-input v-model="addServeInfo.serviceRequest"  style="width: 70%;" type="textarea"
                    :autosize="{ minRows: 2, maxRows: 4 }"  placeholder="请输入服务内容"/>
        </el-form-item>
        <el-form-item label="服务概要">
          <el-input v-model="addServeInfo.overview" style="width: 70%;" type="textarea"
                    :autosize="{ minRows: 2, maxRows: 4 }" placeholder="请输入服务信息"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
           <el-button type="primary" @click="addServeVisible=false,addServeInfo={}"
           >
            取 消
          </el-button>
          <el-button type="primary" @click="addServe"
          >
            添 加
          </el-button>
        </span>
      </template>
    </el-dialog>

  </div>
</template>

<script>
import {reactive, ref, toRaw} from "@vue/reactivity";
// eslint-disable-next-line no-unused-vars
import {ElMessage, ElMessageBox} from "element-plus";
export default {
  name: "ServiceCreation",
  data(){

    let customerServeQuery = reactive({page:1,limit:10,customer:"",serveType:"",state:"fw_002",assigner:""})
    let serveList = reactive([])
    let total = ref("")
    let addServeVisible = ref(false)
    let addServeInfo = reactive({serveType:"",customer:"",overview:"",serviceRequest:""})


    let list = reactive([])
    let selectVisible = ref(false)
    let addVisible = ref(false)
    return{
      customerServeQuery,serveList,total,addServeVisible,addServeInfo,list,selectVisible,addVisible
    }
  },
  methods:{
    queryServiceListByParams(){
      this.$api.CustomerServer.queryCustomerServeByParams("/customerServe/lists",this.customerServeQuery).then(res=>{
        this.serveList=res.result.data
        this.total = res.result.count
        if (res.code===200){
          ElMessage({type:"success",message:"查找成功"})
        }else {ElMessage({type:"error",message:"查找失败，请重试"})}
      })
    },
    //页面初始化（查找数据）
    paramsInitialization(){
      this.$api.CustomerServer.queryCustomerServeByParams("/customerServe/lists",this.customerServeQuery).then(res=>{
        this.serveList=res.result.data
        this.total = res.result.count
      })
    },
    //翻页
    handleCurrentChange(msg){
      this.customerServeQuery.page=msg
      this.paramsInitialization()
    },
    //添加服务
    addServe(){
      console.log(this.addServeInfo)
      let customer = this.addServeInfo.customer
      if (this.addServeInfo.customer==="" || this.addServeInfo.serveType==="" ||
          this.addServeInfo.overview==="" || this.addServeInfo.serviceRequest===""){
        ElMessage({type:"info",message:"内容填写不完整"})
      }else {
        this.$api.CustomerServer.getInfoByName("/customerServe/getInfoByName",{customer}).then(res=>{
          if (res.code===200){
            this.$api.CustomerServer.addCustomerServeInfo("/customerServe/add",this.addServeInfo).then(res=>{
              // console.log(res)
              if (res.code===200){
                ElMessage({type:"success",message:"添加成功"})
              }else {ElMessage({type:"error",message:"添加失败，请稍后再试"})}
              this.addServeInfo={}
              this.addServeVisible=false
              this.paramsInitialization()
            })
          }else {
            ElMessage({
              type:"info",
              message:"暂无用户数据，也可能该用户不存在!"
            })
          }
        })

      }
    }
  },
  mounted() {
   this.paramsInitialization()
    this.list = this.$store.getters.getPermissionList
    console.log("this.list:::::::",this.list)
    if (JSON.stringify(toRaw(this.list)).includes("301001")) {
      this.selectVisible = true
    }
    if (JSON.stringify(toRaw(this.list)).includes("301002")) {
      this.addVisible = true
    }
  }
}
</script>

<style scoped>
.ServiceCreation{
  height: 95%;
  width: 100%;
}
.search{
  width: 96%;
  height: 50px;
  margin: 0 0 0 1%;
}
.tableMenu {
  margin: 1% 0 0 0;
  left: 2%;
  position: relative;
  width: 95%;
}
.page{
  position: absolute;
  margin: 29% 0 0 1%;
  width: 60%;
  color: #ffffff;
}
</style>