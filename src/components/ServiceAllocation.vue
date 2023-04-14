<template>
  <div>
    <div class="search">
      <el-input
          v-model="customerServeQuery.customer"
          class="w-50 m-2"
          placeholder="客户名"
          style="position: relative;width: 10%;margin: 2% 0 0 3%"
      />
      <el-select  class="m-2" placeholder="开发状态" v-model="customerServeQuery.serveType" style="margin: 2% 0 0 10px">
        <el-option label="无"     value="" />
        <el-option label="咨询"   value="6" />
        <el-option label="投诉"   value="7" />
        <el-option label="建议"   value="8" />
      </el-select>
      &nbsp;&nbsp;
      <el-button type="primary"  style="margin: 2% 0 0 0" @click="queryServiceListByParams" v-if="selectVisible">搜  &nbsp;&nbsp;&nbsp; 索</el-button>
    </div>

    <div>
      <el-table :data="serveList"  class="tableMenu"
                max-height="550"  :default-sort="{ prop: 'createDate', order: 'descending' }"
                :header-cell-style="{ backgroundColor: '#eef5ff',   textAlign: 'center',  }"
                row-style="rowStyle"
      >
        <el-table-column fixed="left" prop="id" label="编号" width="100" align="center"/>
        <el-table-column prop="customer" label="客户名" width="150" header-align="center"  align="center"/>
        <el-table-column prop="dicValue" label="服务类型" width="150" header-align="center"  align="center"/>
        <el-table-column prop="overview" label="概要信息" width="200" header-align="center"  align="center"/>
        <el-table-column prop="createPeople" label="创建人" width="135" header-align="center"  align="center"/>
        <el-table-column prop="createDate" label="创建时间" width="210" header-align="center"  align="center"/>
<!--        <el-table-column prop="assigner" label="分配人" width="135" header-align="center"  align="center"/>-->
<!--        <el-table-column prop="assignTime" label="分配时间" width="200" header-align="center"  align="center"/>-->
        <el-table-column prop="updateDate" label="更新时间" width="200" header-align="center"  align="center"/>
        <el-table-column fixed="right" label="操作" width="70" header-align="center" align="center">
          <template #default="scope">
            <el-button link type="primary" size="small" @click="updateServeVisible=true,updateServeInfo=JSON.parse(JSON.stringify(scope.row)),
                                                        distribution(JSON.parse(JSON.stringify(scope.row)))"
                            v-if="allocationVisible"
            >分 配</el-button
            >
          </template>
        </el-table-column>
        <template v-slot:empty>
          <p>暂无记录</p>
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
        v-model="updateServeVisible"
        title="服务管理-服务分配"
        width="40%"
    >
      <el-form
          label-position="right"
          label-width="90px"
          :model="updateServeInfo"
          style="width: 100%"
      >
        <el-row>
          <el-col :span="12">
            <el-form-item label="服务类型">
              <el-select  class="m-2" placeholder="请选择" disabled v-model="updateServeInfo.serveType" style="width: 80%">
                <el-option label="无"     value="" />
                <el-option label="咨询"   value="6" />
                <el-option label="建议"   value="7" />
                <el-option label="投诉"   value="8" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户" style="margin: 0 0 0 -50px">
              <el-input v-model="updateServeInfo.customer" disabled placeholder="空" style="width: 70%"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="服务内容">
          <el-input v-model="updateServeInfo.serviceRequest" disabled style="width: 70%;" type="textarea"
                    :autosize="{ minRows: 2, maxRows: 4 }"  placeholder="空"/>
        </el-form-item>
        <el-form-item label="服务概要">
          <el-input v-model="updateServeInfo.overview" disabled style="width: 70%;" type="textarea"
                    :autosize="{ minRows: 2, maxRows: 4 }" placeholder="空"/>
        </el-form-item>
        <el-form-item label="指派给">
          <el-select  class="m-2" v-model="updateServeInfo.assigner" placeholder="请选择"  style="width: 70%;">
            <el-option
                v-for="a in customerManagers"
                :key="a.id"
                :label="a.uname"
                :value="a.id"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
           <el-button type="primary" @click="updateServeVisible=false,updateServeInfo={}"
           >
            取 消
          </el-button>
          <el-button type="primary" @click="assign"
          >
            指 派
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import {reactive, ref, toRaw} from "@vue/reactivity";
import {ElMessage} from "element-plus";

export default {
  name: "ServiceAllocation",
  data(){
    let customerServeQuery = reactive({page:1,limit:10,customer:"",serveType:"",state:"fw_002",assigner:""})
    let serveList = reactive([])
    let total = ref("")
    let updateServeVisible = ref(false)
    let updateServeInfo = reactive({serveType:"",customer:"",overview:"",serviceRequest:"",assigner:""})
    let customerManagers = reactive([])


    let list = reactive([])
    let selectVisible = ref(false)
    let allocationVisible = ref(false)
    return{
      customerServeQuery,serveList,total,updateServeVisible,updateServeInfo,customerManagers,list,selectVisible,allocationVisible
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
      setTimeout(this.distribution,50)
    },
    //页面初始化（查找数据）
    paramsInitialization(){
      this.$api.CustomerServer.queryCustomerServeByParams("/customerServe/lists",this.customerServeQuery).then(res=>{
        this.serveList=res.result.data
        console.log(this.serveList)
        this.total = res.result.count
      })
    },
    //翻页
    handleCurrentChange(msg){
      this.customerServeQuery.page=msg
      this.paramsInitialization()
      setTimeout(this.distribution,50)

    },
    //分配给使用者进行处理
    // eslint-disable-next-line no-unused-vars
    distribution(msg){
      // console.log(msg)
      // console.log(this.serveList)
      // console.log(this.customerManagers)
      for (let i = 0; i < this.serveList.length; i++) {
        // console.log("77")
        for (let j = 0; j < this.customerManagers.length; j++) {
          // console.log("88")
          if (this.serveList[i].assigner==this.customerManagers[j].id){
            this.serveList[i].assigner=this.customerManagers[j].uname
            // console.log("有相同的🌶！！")
          }
        }
      }
    },
    assign(){
      for (let i = 0; i < this.customerManagers.length; i++) {
        if (this.updateServeInfo.assigner===this.customerManagers[i].uname){
          this.updateServeInfo.assigner=this.customerManagers[i].id
        }
      }
      console.log(this.updateServeInfo)
      this.$api.CustomerServer.updateCustomerServe("/customerServe/update",this.updateServeInfo).then(res=>{
        // console.log(res)
        if (res.code===200){
          ElMessage({type:"success",message:"指派成功!"})
        }else {ElMessage({type:"error",message:"指派失败，请稍后重试"})}
      })
      this.updateServeVisible=false
      this.updateServeInfo={}
      this.paramsInitialization()
      setTimeout(this.distribution,50)
    }
  },
  mounted() {
    this.paramsInitialization()
    this.$api.CustomerServer.queryAllCustomerManagers("/user/queryAllCustomerManagers").then(res=>{
      this.customerManagers=res.result
    })
    setTimeout(this.distribution,50)
    this.list = this.$store.getters.getPermissionList
    if (JSON.stringify(toRaw(this.list)).includes("302001")) {
      this.selectVisible = true
    }
    if (JSON.stringify(toRaw(this.list)).includes("302002")) {
      this.allocationVisible = true
    }
  }
}
</script>

<style scoped>
.tableMenu{
  margin: 2% 0 0 0 ;
  left: 3%;
  position: relative;
  width: 90%;
}
.page{
  position: absolute;
  top: 92%;
  width: 100%;
  color: #ffffff;
}
</style>