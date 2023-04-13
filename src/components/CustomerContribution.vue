<template>
  <div>
    <div style="width: 300px;height: 250px;margin: 0 0 0 70%;display: block;" ref="contribution"></div>
    <div style="width: 430px;height: 290px;margin: -2% 0 0 65%;position: relative" ref="contributionRound"></div>
    <div class="search">
      <el-input
          v-model="customerQuery.customerName"
          class="w-50 m-2"
          placeholder="客户名"
          style="position: relative;width: 10%;margin: -74% 0 0 3%"
      />
      <el-select  class="m-2" v-model="customerQuery.type" placeholder="选择客户金额区间"  style="margin: -74% 0 0 1%;width: 10%">
        <el-option label="无"     value="" />
        <el-option label="1--1000"      value="1" />
        <el-option label="1000--3000"   value="2" />
        <el-option label="3000--5000"   value="3" />
        <el-option label="5000以上"      value="4" />
      </el-select>
      <el-date-picker
          v-model="customerQuery.time"
          type="datetime"
          placeholder="起始时间"
          style="margin: -74% 0 0 1%"
      />
      &nbsp;&nbsp;
      <el-button type="primary"  style="margin: -74% 0 0 0" @click="paramsInitialization">搜  &nbsp;&nbsp;&nbsp; 索</el-button>
    </div>

    <el-table :data="customerList"  class="tableMenu"
              max-height="550"  :default-sort="{ prop: 'createDate', order: 'descending' }"
              :header-cell-style="{ backgroundColor: '#eef5ff',   textAlign: 'center',  }"
              row-style="rowStyle"
    >
      <el-table-column prop="name" label="名称"  align="center"/>
      <el-table-column prop="total" label="总金额(￥)"  header-align="center"  align="center"/>
      <template v-slot:empty>
        <p>暂无数据</p>
      </template>
    </el-table>
    <div class="page">
      <el-pagination
          v-model:current-page="customerQuery.page"
          v-model:page-size="customerQuery.limit"
          :small="true"
          :disabled="false"
          :background="true"
          layout="prev,pager,next,jumper"
          :total="total"
          @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script>
import {reactive, toRaw} from "@vue/reactivity";
import {ElMessage} from "element-plus";
// eslint-disable-next-line no-unused-vars
import * as echarts from 'echarts';

export default {
  name: "CustomerContribution",
  data(){
    let customerQuery = reactive({customerName:"",customerNo:"",level:"",time:"",type:"",page:1,limit:5})
    let total = ""
    let customerList = reactive([])
    return{
      customerQuery,total,customerList,
      x: [],
      y: [],
    }
  },
  methods:{
    //页面初始化（查找数据）
    paramsInitialization(){
      this.$api.CustomerServer.queryCustomerContributionByParams("/customer/queryCustomerContributionByParams",this.customerQuery).then(res=>{
        // console.log(res)
        this.customerList=res.result.data
        this.total = res.result.count
        if (res.code===200){
          ElMessage({type:"success",message:"查找成功"})
        }else {ElMessage({type:"error",message:"查找失败，请重试!"})}
      })
    },
    handleCurrentChange(msg){
      this.customerQuery.page=msg
      this.paramsInitialization()
    },
    initRound(){
      let x = []
      let y = []
      this.customerList.forEach(item=>{
        x.push(item.name)
        y.push(item.total)
      })
      let myChart = echarts.init(this.$refs.contribution)
      let option = {
        title: {
          text: "客户贡献分析"
        },
        tooltip: {},
        xAxis: {
          type: "category",
          data: toRaw([])
        },
        yAxis: {
          type: "value"
        },
        //系列
        series: [{
          data: [],
          type: "line"
        }]
      }
      myChart.setOption(option)
      myChart.setOption({
        xAxis: {
          data: x
        },
        //系列
        series: [{
          data: y,
          type: "bar"
        }]
      })
    },
    receiveParams(){
      let x = []
      let y = []
      let data=[]
      this.customerList.forEach(item=>{
        x.push(item.name)
        y.push(item.total)
        item.value=item.total
        data.push(item)
      })
      // console.log("x",x)
      // console.log("y",y)
      let myChartRound = echarts.init(this.$refs.contributionRound)
      let option = {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: [],
            type: 'pie',
            radius: '70%',
            data: [
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          },
          // {
          //   name: "面积模式",
          //   type: 'pie',
          //   radius: [30, 110],
          //   center: ['75%', '50%'],
          //   roseType: 'area',
          //   data: []
          // }
        ]
      }
      myChartRound.setOption(option)
      myChartRound.setOption({
        series: [
          {
            name:x,
            data: data
          },
        ]
      })
    }
  },
  mounted() {
    this.$api.CustomerServer.queryCustomerContributionByParams("/customer/queryCustomerContributionByParams",this.customerQuery).then(res=>{
      // console.log(res)
      this.customerList=res.result.data
      this.total=res.result.count
      this.customerList.forEach(item=>{
        this.x.push(item.name)
        this.y.push(item.total)
      })
    })
    setTimeout(this.initRound,50)
    setTimeout(this.receiveParams,50)
  }
}
</script>

<style scoped>
.tableMenu{
  margin: -35% 0 0 0;
  left: 3%;
  position: relative;
  width: 60%;
}
.page{
  position: absolute;
  top: 92%;
  width: 100%;
  color: #ffffff;
}
</style>