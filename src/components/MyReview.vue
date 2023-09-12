<template>
<div class="MyReview">
  <div class="search">
    <el-input
        v-model="customerServeQuery.customer"
        class="w-50 m-2"
        placeholder="客户名"
        style="position: relative;width: 10%;margin: 1% 0 0 1%"
    />
    <el-select  class="m-2" placeholder="开发状态" v-model="customerServeQuery.serveType" style="margin: 1% 0 0 10px">
      <el-option label="无"     value="" />
      <el-option label="咨询"   value="6" />
      <el-option label="建议"   value="7" />
      <el-option label="投诉"   value="8" />
    </el-select>
    <el-select  class="m-2" placeholder="审核状态" v-model="customerServeQuery.auditStatus" style="margin: 1% 0 0 10px">
      <el-option label="无"      value="" />
      <el-option label="已通过"   value="1"/>
      <el-option label="未审核"   value="0"/>
    </el-select>
    &nbsp;&nbsp;
    <el-button type="primary"  style="margin: 1% 0 0 0" @click="queryServiceListByParams">搜  &nbsp;&nbsp;&nbsp; 索</el-button>
    <el-button size="small" type="success" style="float: right;margin: 2% 0 0 0" @click="handleDownload">导出EXCEL表格</el-button>
  </div>
  <div>
    <el-table :data="serveList"  class="tableMenu"
              max-height="460"  :default-sort="{ prop: 'createDate', order: 'descending' }"
              :header-cell-style="{ backgroundColor: '#eef5ff',   textAlign: 'center',  }"
              row-style="rowStyle"
    >
      <el-table-column fixed="left" sortable prop="id" label="编号" width="100" align="center"/>
      <el-table-column prop="customer" label="客户名" width="150" header-align="center"  align="center"/>
      <el-table-column label="服务类型" width="150" header-align="center"  align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.dicValue==='投诉'" type="danger">{{ scope.row.dicValue }}</el-tag>
          <el-tag v-else-if="scope.row.dicValue==='建议'" class="ml-2" >{{ scope.row.dicValue }}</el-tag>
          <el-tag v-else-if="scope.row.dicValue==='咨询'" class="ml-2" type="success" >{{ scope.row.dicValue }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="overview" label="概要信息" width="200" header-align="center"  align="center"/>
      <el-table-column prop="createPeople" label="创建人" width="135" header-align="center"  align="center"/>
      <el-table-column prop="createDate" sortable label="创建时间" width="210" header-align="center"  align="center"/>
      <el-table-column prop="label" label="分配给" width="135" header-align="center"  align="center"/>
      <el-table-column prop="assignTime" label="分配时间" width="200" header-align="center"  align="center"/>
      <el-table-column prop="updateDate" label="更新时间" width="200" header-align="center"  align="center"/>
      <el-table-column prop="updateDate" label="审核状态" width="200" header-align="center"  align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.auditStatus===0" class="ml-2" type="info">未审核</el-tag>
          <el-tag v-else-if="scope.row.auditStatus===1" class="ml-2" type="success">已通过</el-tag>
          <el-tag v-else-if="scope.row.auditStatus===2" type="danger">未通过</el-tag>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="150" header-align="center" align="center">
        <template #default="scope">
          <el-button link type="primary" size="small" @click="ArchivingVisible=true,updateServeInfo=JSON.parse(JSON.stringify(scope.row)),distribution(scope.row)"
          >查看详细信息</el-button
          >
        </template>
      </el-table-column>
      <template v-slot:empty>
        <p>暂无数据</p>
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
    <!--服务归档-->
    <el-dialog
        v-model="ArchivingVisible"
        title="服务管理-服务归档"
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
              <el-select  class="m-2" placeholder="(空)" disabled v-model="updateServeInfo.serveType" style="width: 80%">
                <el-option label="无"     value="" />
                <el-option label="咨询"   value="6" />
                <el-option label="建议"   value="7" />
                <el-option label="投诉"   value="8" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户" style="margin: 0 0 0 -50px">
              <el-input v-model="updateServeInfo.customer" disabled placeholder="(空)" style="width: 70%"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="服务内容">
          <el-input v-model="updateServeInfo.serviceRequest" disabled style="width: 70%;" type="textarea"
                    :autosize="{ minRows: 2, maxRows: 4 }"  placeholder="(空)"/>
        </el-form-item>
        <el-form-item label="服务概要">
          <el-input v-model="updateServeInfo.overview" disabled style="width: 70%;" type="textarea"
                    :autosize="{ minRows: 2, maxRows: 4 }" placeholder="(空)"/>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="指派人">
              <el-select  class="m-2" v-model="updateServeInfo.label" disabled placeholder="(空)"  style="width: 70%;">
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="指派时间" style="margin: 0 0 0 -50px">
              <el-date-picker
                  v-model="updateServeInfo.assignTime"
                  disabled
                  type="datetime"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="服务处理">
          <el-input v-model="updateServeInfo.serviceProce" disabled  style="width: 70%;" type="textarea"
                    :autosize="{ minRows: 2, maxRows: 4 }"  placeholder="(空)"/>
        </el-form-item>

        <el-form-item label="反馈结果">
          <el-input v-model="updateServeInfo.serviceProceResult"  disabled style="width: 70%;" type="textarea"
                    :autosize="{ minRows: 2, maxRows: 4 }"  placeholder="(空)"/>
        </el-form-item>

        <el-form-item label="满意度">
<!--          <el-select  class="m-2" placeholder="(空)" disabled v-model="updateServeInfo.myd" style="width: 80%">-->
<!--            <el-option label="☆"    value="☆" />-->
<!--            <el-option label="☆☆"    value="☆☆" />-->
<!--            <el-option label="☆☆☆"    value="☆☆☆" />-->
<!--            <el-option label="☆☆☆☆"    value="☆☆☆☆" />-->
<!--            <el-option label="☆☆☆☆☆"    value="☆☆☆☆☆" />-->
<!--          </el-select>-->
          <el-rate v-model="value2" :colors="colors" disabled />
        </el-form-item>
        <el-form-item label="审核结果">
          <el-tag v-if="updateServeInfo.auditStatus===0" class="ml-2" type="info" size="large">未审核</el-tag>
          <el-tag v-else-if="updateServeInfo.auditStatus===1" class="ml-2" type="success" size="large">已通过</el-tag>
          <el-tag v-else-if="updateServeInfo.auditStatus===2" type="danger" size="large">未通过</el-tag>
        </el-form-item>
      </el-form>
      <el-button type="success" @click="adopt" v-show="updateServeInfo.auditStatus!==1">点击通过</el-button>
        <el-button type="danger" @click="NoAdopt" v-show="updateServeInfo.auditStatus!==1">审核不通过</el-button>
      <template #footer>
        <span class="dialog-footer">
           <el-button type="primary" @click="ArchivingVisible=false,updateServeInfo={}"
           >
            取 消
          </el-button>
          <el-button type="primary" @click="ArchivingVisible=false"
          >
            确 定
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</div>
</template>

<script>
import {reactive, ref} from "@vue/reactivity";
import {ElMessage} from "element-plus";
// eslint-disable-next-line no-unused-vars
import {saveJsonToExcel} from "@/tools/utils";
// eslint-disable-next-line no-unused-vars
import Cookies from 'js-cookie'
export default {
  name: "MyReview",
  data(){
    let customerServeQuery = reactive({page:1,limit:10,customer:"",serveType:"",state:"fw_005",assigner:"",auditStatus:"",createPeople:""})
    let serveList = reactive([])
    let total = ref("")
    let ArchivingVisible = ref(false)
    let updateServeInfo = reactive({})
    const colors = ref(['#99A9BF', '#F7BA2A', '#FF9900'])
    const value2 = ref(null)
    return{
      customerServeQuery,serveList,total,ArchivingVisible,updateServeInfo,colors,value2
    }
  },
  methods:{
    handleDownload(){
      let customerServeQuery = reactive({page:1,limit:1000,customer:this.customerServeQuery.customer,serveType:this.customerServeQuery.serveType,state:"fw_005",assigner:this.customerServeQuery.assigner,auditStatus:this.customerServeQuery.auditStatus,createPeople:this.customerServeQuery.createPeople})
      customerServeQuery.createPeople=Cookies.get("userName")
      let serveList = []
      this.$api.CustomerServer.queryCustomerServeByParams("/customerServe/lists?flag=2",customerServeQuery).then(res=>{
        serveList=res.result.data
        for (let i = 0; i < serveList.length; i++) {
          for (let j = 0; j < this.customerManagers.length; j++) {
            if (serveList[i].assigner == this.customerManagers[j].id) {
              serveList[i].label=this.customerManagers[j].uname
              serveList[i].assigner = this.customerManagers[j].id
            }
          }
        }
        console.log("this.serveList:::::::::::::::::::::::::::::::",serveList)
        let json_fields = []
        for (let i = 0; i < serveList.length; i++) {
          if (serveList[i].auditStatus === 0) {
            serveList[i].result = '未审核'
          } else if (serveList[i].auditStatus === 1) {
            serveList[i].result = '已通过'
          } else if (serveList[i].auditStatus === 2) {
            serveList[i].result = '未通过'
          }
        }
        for (let i = 0; i < serveList.length; i++) {
          json_fields.push({
            "编号":serveList[i].id,
            "客户名":serveList[i].customer,
            "服务类型":serveList[i].dicValue,
            "概要信息":serveList[i].overview,
            "创建人":serveList[i].createPeople,
            "创建时间":serveList[i].createDate,
            "分配人":serveList[i].label,
            "分配时间":serveList[i].assignTime,
            "更新时间":serveList[i].updateDate,
            "管理员审核结果":serveList[i].result
          })
        }
        if (json_fields.length<1){
          this.common("info","当前无数据可导出")
        }else {
          saveJsonToExcel(json_fields, '我的审核信息.xlsx')
        }
      })

    },
    common(type,message){
      ElMessage({
        type:type,
        message:message
      })
    },
    //页面初始化（查找数据）
    paramsInitialization(){
      this.customerServeQuery.createPeople=Cookies.get("userName")
      this.$api.CustomerServer.queryCustomerServeByParams("/customerServe/lists?flag=2",this.customerServeQuery).then(res=>{
        this.serveList=res.result.data
        this.total = res.result.count
        // console.log(res)
        setTimeout(this.distribution, 50)
        console.log("this.serveList:::::::::::::::::::::::::::::::",this.serveList)
      })
    },
    distribution(msg) {
      // console.log(msg)
      for (let i = 0; i < this.serveList.length; i++) {
        for (let j = 0; j < this.customerManagers.length; j++) {
          if (this.serveList[i].assigner == this.customerManagers[j].id) {
            this.serveList[i].label=this.customerManagers[j].uname
            this.serveList[i].assigner = this.customerManagers[j].id
          }
        }
      }
      // console.log(msg.myd)
      if (msg.myd==='☆'){
        this.value2=1
      }else if (msg.myd==='☆☆'){
        this.value2=2
      }else if (msg.myd==='☆☆☆'){
        this.value2=3
      }else if (msg.myd==='☆☆☆☆'){
        this.value2=4
      }else if (msg.myd==='☆☆☆☆☆'){
        this.value2=5
      }
      // console.log(this.serveList)
    },
    handleCurrentChange(msg){
      this.customerServeQuery.page=msg
      this.paramsInitialization()
      setTimeout(this.distribution,50)
    },
    queryServiceListByParams(){
      console.log(this.customerServeQuery)
      this.$api.CustomerServer.queryCustomerServeByParams("/customerServe/lists?flag=2",this.customerServeQuery).then(res=>{
        this.serveList=res.result.data
        this.total = res.result.count
        if (res.code===200){
          this.common("success","查找成功!")
          // ElMessage({type:"success",message:"查找成功!"})
        }else {
          this.common("error","查找失败，请重试!")
        }
        setTimeout(this.distribution, 50)
      })
    },
    //审核通过
    adopt(){
      this.updateServeInfo.auditStatus=1
      console.log(this.updateServeInfo)
      this.$api.CustomerServer.updateCustomerServe("/customerServe/update",this.updateServeInfo).then(res => {
        if (res.code===200){
          this.common("success","操作成功!")
          this.ArchivingVisible=false
          this.paramsInitialization()
          // setTimeout(this.distribution,50)
          // this.distribution()
        }else {
          this.common("error","操作失败，请重试!")
        }
      })
    },
    //审核未通过
    NoAdopt(){
      this.updateServeInfo.auditStatus=2
      this.updateServeInfo.state="fw_002"
      console.log(this.updateServeInfo)
      this.$api.CustomerServer.updateCustomerServe("/customerServe/update",this.updateServeInfo).then(res => {
        if (res.code===200){
          this.common("success","操作成功!")
          this.ArchivingVisible=false
          this.paramsInitialization()
          setTimeout(this.distribution,50)
          // this.distribution()
        }else {
          this.common("error","操作失败，请重试!")
        }
      })
    }
  },
  created() {
    this.$api.CustomerServer.queryAllCustomerManagers("/user/queryAllCustomerManagers").then(res=>{
      this.customerManagers=res.result
      // console.log("this.customerManagers::::",this.customerManagers)
    })
    this.paramsInitialization()
  }
}
</script>

<style scoped>
.MyReview{
  height: 100%;
  width: 100%;
}
.tableMenu {
  margin: 1% 0 0 0;
  left: 2%;
  position: relative;
  width: 95%;
}
.search{
  width: 96%;
  height: 50px;
  margin: 0 0 0 1%;
}
.page{
  position: absolute;
  margin: 3% 0 0 1%;
  width: 60%;
  color: #ffffff;
}
</style>