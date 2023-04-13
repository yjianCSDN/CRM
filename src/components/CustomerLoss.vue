<template>
<div class="CustomerLoss">

  <div class="search">
    <el-input
        class="w-50 m-2"
        placeholder="客户编号"
        v-model="customerLossQuery.customerNo"
        style="position: relative;width: 12%;margin: 1% 0 0 3%"
    />
    <el-input
        class="w-50 m-2"
        placeholder="客户名"
        v-model="customerLossQuery.customerName"
        style="position: relative;width: 12%;margin: 1% 0 0 10px"
    />
    <el-select  class="m-2" placeholder="请选择" v-model="customerLossQuery.state" style="width: 150px;margin: 1% 0 0 5px">
      <el-option label="请选择"    value="" />
      <el-option label="暂缓流失"    value="0" />
      <el-option label="确认流失"    value="1" />
    </el-select>
    &nbsp;&nbsp;
    <el-button type="primary"  style="margin: 1% 0 0 5px" @click="queryListBySth">搜  &nbsp;&nbsp;&nbsp; 索</el-button>
    <el-button type="primary"  style="margin: 1% 0 0 5px" @click="insertVisible=true">添加流失客户</el-button>

  </div>

  <div>
    <el-table :data="customerLossList"  class="tableMenu"
              max-height="450"  :default-sort="{ prop: 'createDate', order: 'descending' }"
              :header-cell-style="{ backgroundColor: '#eef5ff',   textAlign: 'center',  }"
    >
      <el-table-column fixed="left" prop="id" label="编号" width="100" align="center"/>
      <el-table-column prop="cusNo" label="客户编号" width="170" header-align="center"  align="center"/>
      <el-table-column prop="cusName" label="客户名称" width="150" header-align="center"  align="center"/>
      <el-table-column prop="cusManager" label="客户经理" width="200" header-align="center"  align="center"/>
      <el-table-column prop="lastOrderTime" label="最后下单时间" width="200" header-align="center"  align="center"/>
      <el-table-column prop="lossReason" label="流失原因" width="100" header-align="center"  align="center"/>
      <el-table-column prop="confirmLossTime" label="确认流失时间" width="200" header-align="center"  align="center"/>
      <el-table-column prop="createDate" label="创建时间" width="200" header-align="center"  align="center"/>
      <el-table-column prop="updateDate" label="更新时间" width="200" header-align="center"  align="center"/>
      <el-table-column fixed="right" label="操 作" width="200" header-align="center"  align="center">
        <template #default="scope">
          <el-button  size="small" type="primary" v-if="scope.row.state===1" @click="watchDetail(scope.row)"
          >详 &nbsp; &nbsp;情</el-button
          >
          <el-button  size="small" type="warning" v-else-if="scope.row.state===0" @click="AddHold(scope.row)"
          >添加暂缓</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <div class="page">
      <el-pagination
          v-model:current-page="customerLossQuery.page"
          v-model:page-size="customerLossQuery.limit"
          small="small"
          :disabled="false"
          :background="true"
          layout="prev,pager,next,jumper"
          :total="total"
          @current-change="handleCurrentChange"
      />
    </div>
  </div>
<!--客户流失数据展示-->
  <el-dialog
      v-model="CustomerLossExpandVisible"
      :title="title"
      width="50%"
  >
    <el-form
        label-position="left"
        :inline="true"
        label-width="100px"
        :model="CustomerLossExpandList"
    >
      <el-form-item label="客户名称">
        <el-input v-model="CustomerLossExpandList.cusName" placeholder="(空)"/>
      </el-form-item>
      <el-form-item label="客户编号">
        <el-input v-model="CustomerLossExpandList.cusNo" placeholder="(空)"/>
      </el-form-item>
      <el-form-item label="确认流失时间" v-if="title==='流失管理-暂缓措施维护'">
        <el-input v-model="CustomerLossExpandList.confirmLossTime" placeholder="(空)"/>
      </el-form-item>
      <el-form-item label="流失原因" v-if="title==='流失管理-暂缓措施维护'">
        <el-input v-model="CustomerLossExpandList.lossReason" placeholder="(空)"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="margin: 10px 0 10px 10px" v-if="title==='流失管理-暂缓措施维护'" @click="AddCustomerReprieveVisible=true">添加暂缓</el-button>
        <el-button type="primary" style="margin: 10px 0 10px 10px" v-if="title==='流失管理-暂缓措施维护'"
                    @click="ConfirmLossVisible=true">确认流失</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="customerReprieveList" max-height="520" :default-sort="{ prop: 'createDate', order: 'descending' }"
              :header-cell-style="{ backgroundColor: '#eef5ff',   textAlign: 'center',  }" >
      <el-table-column prop ="id" label="编号" width="100" fixed="left" align="center"/>
      <el-table-column prop ="measure" label="暂缓措施" width="170" header-align="center"  align="center"/>
      <el-table-column prop ="createDate" label="创建时间" width="200" header-align="center"  align="center"/>
      <el-table-column prop ="updateDate" label="更新时间" width="200" header-align="center"  align="center"/>
      <el-table-column fixed="right" label="操 作" width="200" header-align="center"  align="center">
        <template #default="scope">
          <el-button  size="small" type="success" @click="UpdateCustomerReprieveVisible=true,customerReprieve=JSON.parse(JSON.stringify(scope.row))"
          >修 改</el-button
          >
          <el-button  size="small" type="danger" @click="delCustomerReprieve(scope.row.id)"
          >删 除</el-button
          >
        </template>
      </el-table-column>
          <template v-slot:empty>
            <p>暂无历史记录</p>
          </template>
    </el-table>

    <div>
      <el-pagination
          v-model:current-page="customerReprieveQuery.page"
          v-model:page-size="customerReprieveQuery.limit"
          small="small"
          :disabled="false"
          :background="true"
          layout="prev,pager,next,jumper"
          :total="customerReprieveQueryTotal"
          @current-change="customerReprieveHandleCurrentChange"
      />
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="CustomerLossExpandVisible=false">取 消</el-button>
        <el-button type="primary" >
          确认
        </el-button>
      </span>
    </template>
  </el-dialog>


<!--暂缓措施添加-->
  <el-dialog
      v-model="AddCustomerReprieveVisible"
      title="暂缓管理-添加暂缓措施"
      width="50%"
  >
    <el-form
        label-position="left"
        :inline="true"
        label-width="100px"
        :model="CustomerLossExpandList"
    >
      <el-form-item label="暂缓措施">
        <el-input v-model="customerReprieve.measure" placeholder="请输入暂缓措施"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button >取 消</el-button>
        <el-button type="primary" @click="addCustomerReprieve">
          确认
        </el-button>
      </span>
    </template>
  </el-dialog>

  <!--暂缓措施修改-->
  <el-dialog
      v-model="UpdateCustomerReprieveVisible"
      title="暂缓管理-修改暂缓措施"
      width="50%"
  >
    <el-form
        label-position="left"
        :inline="true"
        label-width="100px"
        :model="CustomerLossExpandList"
    >
      <el-form-item label="暂缓措施">
        <el-input v-model="customerReprieve.measure" placeholder="(空)"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button >取 消</el-button>
        <el-button type="primary" @click="updateCustomerReprieve">
          确认
        </el-button>
      </span>
    </template>
  </el-dialog>
<!--流失原因填写-->
  <el-dialog
      v-model="ConfirmLossVisible"
      title="流 失 原 因"
      width="40%"
  >
    <el-form
        label-position="left"
        :inline="true"
        label-width="100px"
        :model="lossReason"
    >
      <el-form-item label="暂缓措施">
        <el-input v-model="lossReason" type="textarea" autosize="{ minRows: 2, maxRows: 4 }" placeholder="请输入流失原因!"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="ConfirmLossVisible=false">取 消</el-button>
        <el-button type="primary" @click="submitLossReason">
          确认
        </el-button>
      </span>
    </template>
  </el-dialog>
<!--  添加流失客户-->
  <el-dialog
      v-model="insertVisible"
      title="添加流失客户"
      width="40%"
  >
    <el-form
        label-position="left"
        :inline="true"
        label-width="100px"
        :model="insertLossInfo"
    >
      <el-form-item label="客户名称">
        <el-input v-model="insertLossInfo.cusName" type="text" placeholder="请输入客户名称"/>
      </el-form-item>
      <el-form-item label="最后订单时间">
        <el-date-picker
            v-model="insertLossInfo.lastOrderTime"
            type="datetime"
            placeholder="选择订单时间"
            format="YYYY-MM-DD HH:mm:ss"
            value-format="YYYY-MM-DD HH:mm:ss"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="insertVisible=false,insertLossInfo={}">取 消</el-button>
        <el-button type="primary" @click="addLoss">
          确认
        </el-button>
      </span>
    </template>
  </el-dialog>
</div>
</template>

<script>
import {reactive, ref, toRaw} from "@vue/reactivity";
import {ElMessage,ElMessageBox} from "element-plus";

export default {
  name: "CustomerLoss",
  data(){
    let customerLossList = reactive([])
    let customerLossQuery = reactive({customerNo:"",customerName:"",state:"",page:1,limit:10})
    let total = ref("")
    let CustomerLossExpandVisible = ref(false)
    let CustomerLossExpandList = reactive([])
    let title = ""
    let customerReprieveQuery = reactive({page:1,limit:3,lossId:""})
    let customerReprieveList = reactive([])
    let AddCustomerReprieveVisible = ref(false)
    let customerReprieve = reactive({measure:"",lossId:""})
    let UpdateCustomerReprieveVisible = ref(false)
    let customerReprieveQueryTotal = ref("")
    let lossReason = ""
    let id = ref("")
    let ConfirmLossVisible = ref(false)
    let insertLossInfo = reactive({cusName:"",lastOrderTime:""})
    let insertVisible = ref(false)
    let customerList = reactive([])//未流失客户名单，用来判断名称是否正确
    return{
      customerLossList,customerLossQuery,total,CustomerLossExpandVisible,CustomerLossExpandList,title,customerReprieveQueryTotal,
      customerReprieveQuery,customerReprieveList,AddCustomerReprieveVisible,
      customerReprieve,UpdateCustomerReprieveVisible,lossReason,id,ConfirmLossVisible,insertLossInfo,insertVisible,customerList
    }
  },
  methods:{
    queryLossList(){
      this.$api.CustomerInformation.queryCustomerLossByParams("/customerLoss/list",this.customerLossQuery).then(res=>{
        // console.log(res)
        this.customerLossList = res.result.data
        this.total = res.result.count
      })
    },
    queryListBySth(){
      console.log(this.customerLossQuery)
      this.$api.CustomerInformation.queryCustomerLossByParams("/customerLoss/list",this.customerLossQuery).then(res=>{
        console.log(res)
        if (res.code===200){
            ElMessage({type:"success",message:"查询成功!"})
        }else {ElMessage({type:"error",message:"查询失败,请重试"})}
        this.customerLossList = res.result.data
        this.total = res.result.count
      })
    },
    handleCurrentChange(msg){
      this.customerLossQuery.page = msg
      this.queryLossList()
    },
    //根据id查询暂缓措施
    commonFunction(msg){
      this.id = msg.id
      this.CustomerLossExpandList=toRaw(msg)
      this.CustomerLossExpandVisible=true
      this.customerReprieveQuery.lossId = msg.id
      this.customerReprieve.lossId = msg.id
      this.$api.CustomerInformation.queryCustomerReprieveByParams("/customerReprieve/lists",this.customerReprieveQuery).then(res=>{
        console.log(res)
        this.customerReprieveList = res.result.data
        this.customerReprieveQueryTotal = res.result.count
      })
    },
    // 暂缓措施维护
    AddHold(msg){
      this.commonFunction(msg)
      this.title='流失管理-暂缓措施维护'
    },
    // 暂缓数据查看
    watchDetail(msg){
      this.commonFunction(msg)
      this.title = '流失管理-暂缓数据查看'
    },
    // 添加暂缓措施
    addCustomerReprieve(){
      console.log(this.customerReprieve)
      this.$api.CustomerInformation.addCustomerRepr("/customerReprieve/add",this.customerReprieve).then(res=>{
        console.log(res)
        if (res.code===200){
          ElMessage({type:"success",message:"添加成功"})
          this.AddCustomerReprieveVisible=false
          this.customerReprieve={}
          //重新渲染列表
          console.log("渲染列表中。。。")
          this.$api.CustomerInformation.queryCustomerReprieveByParams("/customerReprieve/lists",this.customerReprieveQuery).then(res=>{
            // console.log(res)
            // console.log(this.customerReprieveList,999999999999)
            this.customerReprieveList = res.result.data
            this.customerReprieveQueryTotal = res.result.count
          })
        }else {ElMessage({type:"error",message:"添加失败，请重试"})}
      })

    },
    updateCustomerReprieve(){
      console.log(this.customerReprieve)
      this.$api.CustomerInformation.updateCustomerRepr("/customerReprieve/update",this.customerReprieve).then(res=>{
        console.log(res)
        if (res.code===200){
          ElMessage({type:"success",message:"修改成功"})
          this.UpdateCustomerReprieveVisible=false
        }else {ElMessage({type:"error",message:"修改失败，请重试!"})}
      })
    },
    //删除暂缓条目
    delCustomerReprieve(msg){
      let id = msg
      ElMessageBox.confirm(
          "是否删除",
          "info",
          {confirmButtonText:"是的", cancelButtonText:"不，我再想想"}
      ).then(()=>{
        this.$api.CustomerInformation.updateCustomerRepr("/customerReprieve/delete",id).then(res=>{
          if (res.code===200){
            ElMessage({
              type:"success",message:"删除成功"
            })
            this.$api.CustomerInformation.queryCustomerReprieveByParams("/customerReprieve/lists",this.customerReprieveQuery).then(res=>{
              console.log(res)
              this.customerReprieveList = res.result.data
              this.customerReprieveQueryTotal = res.result.count
            })
          }else {ElMessage({type:"error",message:"删除失败，请重试"})}
        })
      }).catch(()=>{
        ElMessage({type:"info",message:"取消操作"})
      })
    },
    customerReprieveHandleCurrentChange(msg){
      this.customerReprieveQuery.page=msg
      this.$api.CustomerInformation.queryCustomerReprieveByParams("/customerReprieve/lists",this.customerReprieveQuery).then(res=>{
        console.log(res)
        this.customerReprieveList = res.result.data
        this.customerReprieveQueryTotal = res.result.count
      })
      console.log(msg)
    },
    //确认流失
    submitLossReason(){
      let id = this.id
      let lossReason = this.lossReason
      if (this.lossReason===''){
        ElMessage({type:"info",message:"该项为必填项"})
      }else {
        this.$api.CustomerInformation.updateCustomerLossStateById("/customerLoss/updateCustomerLossStateById",{id,lossReason}).then(res=>{
          console.log(res)
          if (res.code===200){
            ElMessage({type:"success",message:"修改成功"})
            this.queryLossList()
            console.log(this.customerLossList)
          }else {
            ElMessage({type:"error",message:"修改失败，请重试"})
          }
          this.lossReason=""
          this.ConfirmLossVisible=false
          this.CustomerLossExpandVisible=false
        })
      }
    },

    //添加流失客户
    addLoss(){
      let result = false
      let result2 = false
      for (let i = 0; i < this.customerLossList.length; i++) {
          if (this.insertLossInfo.cusName===this.customerLossList[i].cusName){
            result = true
            console.log("该客户已在流失名单上")
          }
      }

      for (let i = 0; i < this.customerList.length; i++) {
        if (this.insertLossInfo.cusName===this.customerList[i].name){
          result2 = true
          console.log("该客户在客户名单上")
        }
      }


      if (this.insertLossInfo.cusName===""||this.insertLossInfo.lastOrderTime===""){
        ElMessage({type:"info",message:"信息没有填写完全"})
      }
      else if (result){
        ElMessage({type:"warning",message:"该客户已在流失名单上"})
      }else if (!result2){
        ElMessage({type:"warning",message:"该客户名称不在客户中"})
      }else {
        // console.log(this.customerLossList)
        console.log(this.insertLossInfo)
        this.$api.CustomerInformation.addLossCustomerInfo("/customerLoss/addLossInfo",this.insertLossInfo).then(res=>{
          console.log(res)
          if (res.code===200){
            ElMessage({type:"success",message:"添加成功"})
            this.insertVisible=false
            this.queryLossList()
          }else {
            ElMessage({type:"error",message:"添加失败，请重试"})
          }
        })
      }
    },
    queryCustomer(){
      this.$api.CustomerInformation.queryCustomerByParams("/customer/lists").then(res=>{
        // console.log(res)
        this.customerList = res.result.data
      })
    },
  },
  mounted() {
    this.queryLossList()
    this.queryCustomer()
  }
}
</script>

<style scoped>
.tableMenu{
  width: 95%;
  margin: 0 0 0 3%;
  position: relative;
}
.CustomerLoss{
  width: 100%;
  height: 100%;
}
.search{
  position: relative;
  width: 100%;
  height: 50px;
}
.page{
  position: absolute;
  top: 90%;
  width: 100%;
  color: #ffffff;
}
</style>