<template>
  <div class="CustomerComposition">
    <div id="main" ref="mainExpand" style="width: 800px;height: 400px;top: 10%;left: 40%"></div>
    <div id="main02" ref="mainExpand"
         style="width: 600px;height: 300px; top:-50%;left: 5%"></div>
  </div>
</template>

<script>
// eslint-disable-next-line no-unused-vars
import * as echarts from 'echarts';
import {toRaw} from "@vue/reactivity";


export default {
  name: "CustomerComposition",
  data() {
    return {
      x: [],
      y: [],
      xx: [],
      yy: [],
    }
  },
  methods: {
    initParams() {
      let myChart = echarts.init(document.getElementById('main'))
      let option = {
        //标题
        title: {
          text: "客户构成分析"
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
      this.$api.CustomerServer.countCustomerMake("/customer/countCustomerMake").then(res => {
        console.log(res)
        // console.log(this.x)
        let x = []
        let y = []
        for (let i = 0; i < res.result.dataFirst.length; i++) {
          this.x.push(res.result.dataFirst[i])
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
          //系列
          series: [{
            data: y,
            type: "line"
          }]
        })
      })
      myChart.setOption(option)
      window.onresize = myChart.resize
    },
    receiveParams() {
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
      let myChartRound = echarts.init(document.getElementById('main02'))
      this.$api.CustomerServer.countCustomerMake("/customer/countCustomerMake02").then(res => {
        // console.log("countCustomerMake02:",res)
        let x = []
        let y = []
        console.log("countCustomerMake02", res)
        for (let i = 0; i < res.result.data1.length; i++) {
          this.xx.push(res.result.data1[i])
        }
        x = this.xx
        for (let i = 0; i < res.result.data2.length; i++) {
          this.yy.push(res.result.data2[i])
        }
        y = this.yy
        console.log("x",x)
        console.log("y",y)
        myChartRound.setOption({
          legend: {
            data: x
          },
          series: [
            {
              data: y
            },
            {
              name:x,
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
.CustomerComposition {
  height: 100%;
  width: 100%;
}
</style>