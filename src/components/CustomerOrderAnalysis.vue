<template>
<div class="personCenter">
  <div class="search">
    <el-input
        v-model="userName"
        class="w-50 m-2"
        placeholder="请输入客户名称"
        style="position: relative;width: 20%;margin: 2.5% 0 0 0"
    />
    &nbsp;&nbsp;
    <el-button type="primary"  style="margin: 2.5% 0 0 0" @click="search">搜  &nbsp;&nbsp;&nbsp; 索</el-button>
  </div>
  <div style="width: 500px;height: 250px;margin: -3% 0 0 61%;display: block" ref="contribution"/>
  <div style="width: 500px;height: 290px;margin: 0 0 0 61%;position: relative" ref="contributionRound"/>
  <el-table :data="customerOrderLists"  class="tableMenu"
            max-height="550"  :default-sort="{ prop: 'createDate', order: 'descending' }"
            :header-cell-style="{ backgroundColor: '#eef5ff',   textAlign: 'center',  }"
            row-style="rowStyle"
  >
    <el-table-column prop="name" label="客户名称"  align="center"/>
    <el-table-column prop="value" sortable label="订单数量"  header-align="center"  align="center"/>
    <template v-slot:empty>
      <p>暂无数据</p>
    </template>
  </el-table>
</div>
</template>

<script>
import * as echarts from "echarts";
import {toRaw} from "@vue/reactivity";
import {ElMessage} from "element-plus";

export default {
  name: "PersonCenter",
  data(){
    return{
      x:[],
      y:[],
      customerOrderLists:[],
      userName:""
    }
  },
  methods:{
    common(type,message){
      ElMessage({
        type:type,
        message:message
      })
    },
    initRound(){
      let x = []
      let y = []
      this.customerOrderLists.forEach(item=>{
        x.push(item.name)
        y.push(item)
      })
      let myChart = echarts.init(this.$refs.contribution)
      let option = {
        title: {
          text: "客户订单分析"
        },
        tooltip: {},
        toolbox:{
          show: true,
          feature: {
            saveAsImage: {
              show:true,
              excludeComponents :['toolbox'],
              pixelRatio: 2
            }
          }
        },
        xAxis: {
          type: "category",
          data: toRaw([]),
          axisLabel: { interval: 0 }
        },
        dataZoom:[
          {
            show: true,
            start: 0,
            end: 50
          }
        ],
        yAxis: {
          type: "value",
          name:'（单位）件'
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
      this.customerOrderLists.forEach(item=>{
        x.push(item.name)
        y.push(item)
        data.push(item)
      })
      let myChartRound = echarts.init(this.$refs.contributionRound)
      let option = {
        tooltip: {
          trigger: 'item'
        },
        toolbox:{
          show: true,
          feature: {
            saveAsImage: {
              show:true,
              excludeComponents :['toolbox'],
              pixelRatio: 2
            }
          }
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
    },
    search(){
      let userName = this.userName
      this.$api.CustomerServer.countOrderByCustomer("/customer/countOrderByCustomer", {userName}).then(res=>{
        console.log(res)
        if (res.code===200){
          this.common("success","查找成功")
          this.customerOrderLists=res.result.data2
          setTimeout(this.initRound,50)
          setTimeout(this.receiveParams,50)
        }else {
          this.common("error","查找失败，请稍后重试!")
        }
      })
    }
  },
  created() {
    this.$api.CustomerServer.countOrderByCustomer("/customer/countOrderByCustomer").then(res=>{
      console.log(res)
      this.customerOrderLists=res.result.data2
      setTimeout(this.initRound,50)
      setTimeout(this.receiveParams,50)
    })
  }
}
</script>

<style scoped>
.personCenter{
  width: 100%;
  height: 95%;
}
.search{
  position: relative;
  width: 95%;
  height: 50px;
  display: block;
  /*background-color: #8c6fd0;*/
  left: 3%;
}
.tableMenu{
  margin: -37% 0 0 0;
  left: 3%;
  position: relative;
  width: 57%;
}
</style>