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
      <el-option label="投诉"   value="7" />
      <el-option label="建议"   value="8" />
    </el-select>
    <el-select  class="m-2" placeholder="审核状态" v-model="customerServeQuery.auditStatus" style="margin: 1% 0 0 10px">
      <el-option label="无"      value="" />
      <el-option label="已通过"   value="1"/>
      <el-option label="未通过"   value="0"/>
    </el-select>
    &nbsp;&nbsp;
    <el-button type="primary"  style="margin: 1% 0 0 0" @click="queryServiceListByParams">搜  &nbsp;&nbsp;&nbsp; 索</el-button>
    <el-button size="small" type="success" style="float: right;margin: 2% 0 0 0" @click="handleDownload">导出EXCEL表格</el-button>
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
      <el-table-column prop="assigner" label="分配人" width="135" header-align="center"  align="center"/>
      <el-table-column prop="assignTime" label="分配时间" width="200" header-align="center"  align="center"/>
      <el-table-column prop="updateDate" label="更新时间" width="200" header-align="center"  align="center"/>
      <el-table-column prop="updateDate" label="更新时间" width="200" header-align="center"  align="center">
        <template #default="scope">
          <span v-if="scope.row.auditStatus===0" style="color: red">未审核</span>
          <span v-else-if="scope.row.auditStatus===1" style="color: green">已审核</span>
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
          <el-select  class="m-2" placeholder="(空)" disabled v-model="updateServeInfo.myd" style="width: 80%">
            <el-option label="☆"    value="☆" />
            <el-option label="☆☆"    value="☆☆" />
            <el-option label="☆☆☆"    value="☆☆☆" />
            <el-option label="☆☆☆☆"    value="☆☆☆☆" />
            <el-option label="☆☆☆☆☆"    value="☆☆☆☆☆" />
          </el-select>
        </el-form-item>
        <el-form-item label="审核状态" v-show="updateServeInfo.auditStatus===0">
          <el-button type="success" @click="adopt" >点击审核通过</el-button>
        </el-form-item>
      </el-form>
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
    return{
      customerServeQuery,serveList,total,ArchivingVisible,updateServeInfo
    }
  },
  methods:{
    handleDownload(){
      let json_fields = []
      for (let i = 0; i < this.serveList.length; i++) {
        json_fields.push({
          "编号":this.serveList[i].id,
          "客户名":this.serveList[i].customer,
          "服务类型":this.serveList[i].dicValue,
          "概要信息":this.serveList[i].overview,
          "创建人":this.serveList[i].createProple,
          "创建时间":this.serveList[i].createDate,
          "分配人":this.serveList[i].assigner,
          "分配时间":this.serveList[i].assignTime,
          "更新时间":this.serveList[i].updateDate,
          "管理员审核结果":this.serveList[i].auditStatus===1?'审核通过':'审核未通过'
        })
      }
      if (json_fields.length<1){
        this.common("info","当前无数据可导出")
      }else {
        // console.log("json_fields",json_fields)
        saveJsonToExcel(json_fields, '我的审核信息.xlsx')
      }
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
      console.log(this.customerServeQuery)
      this.$api.CustomerServer.queryCustomerServeByParams("/customerServe/lists?flag=2",this.customerServeQuery).then(res=>{
        this.serveList=res.result.data
        this.total = res.result.count
      })
    },
    distribution() {
      for (let i = 0; i < this.serveList.length; i++) {
        for (let j = 0; j < this.customerManagers.length; j++) {
          if (this.serveList[i].assigner == this.customerManagers[j].id) {
            this.serveList[i].label=this.customerManagers[j].uname
            this.serveList[i].assigner = this.customerManagers[j].uname
          }
        }
      }
      console.log(this.serveList)
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
          ElMessage({type:"success",message:"查找成功!"})
        }else {
          this.common("error","查找失败，请重试!")
        }
      })
    },
    //审核通过
    adopt(){
      this.updateServeInfo.auditStatus=1
      this.$api.CustomerServer.updateCustomerServe("/customerServe/update",this.updateServeInfo).then(res => {
        if (res.code===200){
          this.common("success","操作成功!")
          this.ArchivingVisible=false
          this.paramsInitialization()
          setTimeout(this.distribution,50)
          // this.distribution()
        }else {
          this.common("error","操作失败，请重试")
        }
      })
    }
  },
  created() {
    this.paramsInitialization()
    this.$api.CustomerServer.queryAllCustomerManagers("/user/queryAllCustomerManagers").then(res=>{
      this.customerManagers=res.result
      setTimeout(this.distribution,50)
    })
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
</style>