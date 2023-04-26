<template>
  <div class="CustomerChurn">
    <div class="search">
      <el-input
          v-model="customerLossQuery.customerNo"
          class="w-50 m-2"
          placeholder="客户编号"
          style="position: relative;width: 10%;margin: 1% 0 0 0"
      />
      <el-input
          v-model="customerLossQuery.customerName"
          class="w-50 m-2"
          placeholder="客户名"
          style="position: relative;width: 10%;margin: 1% 0 0 10px"
      />
      &nbsp;&nbsp;
      <el-button type="primary"  style="margin: 1% 0 0 0" @click="query">搜  &nbsp;&nbsp;&nbsp; 索</el-button>
      <el-button size="small" type="success" style="float: right;margin: 2% 2% 0 0" @click="handleDownload">导出EXCEL表格</el-button>
    </div>

    <div>
      <el-table :data="customerList"  class="tableMenu"
                max-height="550"  :default-sort="{ prop: 'createDate', order: 'descending' }"
                :header-cell-style="{ backgroundColor: '#eef5ff',   textAlign: 'center',  }"
                row-style="rowStyle"
      >
        <el-table-column fixed="left" prop="id" label="编号" width="100" align="center"/>
        <el-table-column prop="cusNo" label="客户编号" width="150" header-align="center"  align="center"/>
        <el-table-column prop="cusName" label="客户名称" width="200" header-align="center"  align="center"/>
        <el-table-column prop="cusManager" label="客户经理" width="200" header-align="center"  align="center"/>
        <el-table-column prop="lastOrderTime" label="最后下单时间" width="200" header-align="center"  align="center"/>
        <el-table-column prop="lossReason" label="流失原因" width="210" header-align="center"  align="center"/>
        <el-table-column prop="confirmLossTime" label="确认流失时间" width="200" header-align="center"  align="center"/>

        <template v-slot:empty>
          <p>暂时没有数据</p>
        </template>
      </el-table>
      <div class="page">
        <el-pagination
            v-model:current-page="customerLossQuery.page"
            v-model:page-size="customerLossQuery.limit"
            :small="true"
            :disabled="false"
            :background="true"
            layout="prev,pager,next,jumper"
            :total="total"
            @current-change="handleCurrentChange"
        />
      </div>
    </div>

  </div>
</template>

<script>
import {reactive, ref} from "@vue/reactivity";
import {ElMessage} from "element-plus";
import {saveJsonToExcel} from "@/tools/utils";

export default {
  name: "CustomerChurn",
  data(){
    let customerLossQuery = reactive({customerNo:"",customerName:"",state:1,page:1,limit:10})
    let customerList = reactive([])
    let total = ref("")
    return{
      customerLossQuery,customerList,total
    }
  },
  methods:{
    handleDownload(){
      let json_fields = []
      for (let i = 0; i < this.customerList.length; i++) {
        json_fields.push({
          "编号":this.customerList[i].id,
          "客户编号":this.customerList[i].cusNo,
          "客户名称":this.customerList[i].cusName,
          "客户经理":this.customerList[i].cusManager,
          "最后下单原因":this.customerList[i].lastOrderTime,
          "流失原因":this.customerList[i].lossReason,
          "确认六十时间":this.customerList[i].confirmLossTime
        })
      }
      // console.log("json_fields",json_fields)
      if (json_fields.length===0){
        ElMessage({type:"warning",message:"该条件下无内容,无数据可导出!"})
      }else {
        saveJsonToExcel(json_fields, '客户流失分析.xlsx')
      }
    },
    query(){
      this.$api.CustomerInformation.queryLossCustomer("/customerLoss/list",this.customerLossQuery).then(res=>{
        console.log(res)
        this.customerList=res.result.data
        this.total=res.result.count
        if (res.code===200){
          ElMessage({type:"success",message:"查找成功!"})
        }else {ElMessage({type:"error",message:"查找失败，请稍后再试"})}
      })
    },
    queryLossList(){
      this.$api.CustomerInformation.queryLossCustomer("/customerLoss/list",this.customerLossQuery).then(res=>{
        console.log(res)
        this.customerList=res.result.data
        this.total=res.result.count
      })
    },
    handleCurrentChange(msg){
      this.customerLossQuery.page=msg
      this.queryLossList()
      console.log(msg)
    }
  },
  mounted() {
    this.queryLossList()
  }
}
</script>

<style scoped>
.tableMenu{
  margin: 7px 0 0 0 ;
  left: 2%;
  position: relative;
  width: 96%;
}
.page{
  position: absolute;
  top: 85%;
  width: 60%;
  color: #ffffff;
}
.search{
  position: relative;
  width: 98%;
  height: 50px;
  display: block;
  left: 2%;
}
</style>