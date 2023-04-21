<template>
  <div class="CustomerChurn">
    <div class="search">
      <el-input
          v-model="customerLossQuery.customerNo"
          class="w-50 m-2"
          placeholder="客户编号"
          style="position: relative;width: 10%;margin: 2% 0 0 2%"
      />
      <el-input
          v-model="customerLossQuery.customerName"
          class="w-50 m-2"
          placeholder="客户名"
          style="position: relative;width: 10%;margin: 2% 0 0 10px"
      />
      &nbsp;&nbsp;
      <el-button type="primary"  style="margin: 2% 0 0 0" @click="query">搜  &nbsp;&nbsp;&nbsp; 索</el-button>
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
  top: 92%;
  width: 100%;
  color: #ffffff;
}
</style>