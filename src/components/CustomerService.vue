<template>
  <div class="CustomerService">
    <div class="main" ref="mainExpand" style="width: 600px;height: 300px;
                                      position: relative;margin: 0 0 0 50%;top: 20%"
    ></div>
    <div class="mian02" ref="mainExpandRound" style="width: 600px;height: 300px;margin: -13% 0 0 3%"
    ></div>
  </div>
</template>

<script>

import * as echarts from "echarts";
import {toRaw} from "@vue/reactivity";

export default {
  name: "CustomerService",
  data(){
    return{
      x: [],
      y: [],
      xx: [],
      yy: [],
    }
  },
  methods:{
    //折线图
    initParams() {
      let myChart = echarts.init(this.$refs.mainExpand)
      let option = {
        //标题
        title: {
          text: "客户服务分析",
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
      this.$api.CustomerServer.countCustomerMake("/customerServe/countCustomerMake02").then(res=>{
        console.log(res)
        console.log(this.x)
        let x = []
        let y = []
        for (let i = 0; i < res.result.dataFirst.length; i++) {
          if (res.result.dataFirst[i]==="6"){
            this.x.push("咨询")
          }
          if (res.result.dataFirst[i]==="7"){
            this.x.push("投诉")
          }
          if (res.result.dataFirst[i]==="8"){
            this.x.push("建议")
          }
          // this.x.push(res.result.dataFirst[i])
        }
        x = this.x
        for (let i = 0; i < res.result.dataSecond.length; i++) {
          this.y.push(res.result.dataSecond[i])
        }
        y = this.y

        myChart.setOption({
          xAxis: {
            data: x
          },
          //y值
          series: [{
            data: y,
            type: "line"
          }]
        })
      })
      myChart.setOption(option)
    },
    //饼状图
    receiveParams() {
      let option = {
        title: {
          text: "客户服务分析",
          left: "40%"
        },
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
            radius: '65%',
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
      let myChartRound = echarts.init(this.$refs.mainExpandRound)
      this.$api.CustomerServer.countCustomerMake("/customerServe/countCustomerMake").then(res=>{
        console.log(res)
        console.log(this.x)
        let x = []
        let y = []
        console.log("countCustomerMake02", res)
        for (let i = 0; i < res.result.data1.length; i++) {
          if (res.result.data1[i]==="6"){
            this.xx.push("咨询")
          }
          if (res.result.data1[i]==="7"){
            this.xx.push("投诉")
          }
          if (res.result.data1[i]==="8"){
            this.xx.push("建议")
          }
          // this.x.push(res.result.dataFirst[i])
        }
        x=this.xx
        for (let i = 0; i < res.result.data2.length; i++) {
          if (res.result.data2[i].name==="6"){
            res.result.data2[i].name="咨询"
          }
          if (res.result.data2[i].name==="7"){
            res.result.data2[i].name="投诉"
          }
          if (res.result.data2[i].name==="8"){
            res.result.data2[i].name="建议"
          }
          // console.log(res.result.data2[i])
          this.yy.push(res.result.data2[i])
        }
        y = this.yy
        myChartRound.setOption({
          legend: {
            data: x
          },
          series: [
            {
              name:x,
              data: y
            },
            {
              data: y
            }
          ]
        })
      })

      myChartRound.setOption(option)
    },
  },
  mounted() {
    this.initParams()
    this.receiveParams()
  }
}
</script>

<style scoped>
.CustomerService{
  width: 100%;
  height: 100%;
}
</style>