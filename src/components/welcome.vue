<template>
  <div id="app">
    <div class="home">
      <download-excel
          class="export-excel-wrapper"
          :data="json_data"
          :fields="json_fields"
          type="xls"
          worksheet="My Worksheet"
          name="用户信息"
      >
        <el-button>导出EXCELsssss</el-button>
      </download-excel>
    </div>
    <i class="iconFont icon-caidanlan-kehu-kehulianxiren"></i>
    <el-button
        style="margin-left: 20px"
        size="small"
        icon="el-icon-download"
        @click="handleDownload"
    >导出Excel</el-button
    >
  </div>
</template>

<script>
// eslint-disable-next-line no-unused-vars
import { readExcelToJson, saveJsonToExcel } from '../tools/utils.js'
import axios from 'axios';
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "welcome",
  data() {
    return {
      json_fields: {
        年龄: "age", //常规字段
        姓名: "info.name", //支持嵌套属性
        密码: {
          field: "info.phone",
          //自定义回调函数
          callback: value => {
            return `+86 ${value}`;
          }
        }
      },
      tableDate:[
        {
          date: "2016-05-02",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1518 弄",
        },
        {
          date: "2016-05-04",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1517 弄",
        },
        {
          date: "2016-05-01",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1519 弄",
        },
        {
          date: "2016-05-03",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1516 弄",
        },
      ],
      json_data: [
        {
          age: 22,
          info: {
            name: "张三",
            phone: 12222222222
          },
          sex: "男"
        },
        {
          age: 23,
          info: {
            name: "李四",
            phone: 13333333333
          },
          sex: "女"
        }
      ]
    }
  },
  methods:{
    async fetchData(){
      const response = await axios.get('https://holidayapi.com/v1/holidays?key=a4b2083b-1577-4acd-9408-6e529996b129&country=US&year=2017&month=09');
      console.log(response);
      return response.data.holidays;
    },
    startDownload(){
      alert('show loading');
    },
    finishDownload(){
      alert('hide loading');
    },
    handleDownload() {
      saveJsonToExcel(this.tableDate, 'data.xlsx')
    },
  },
  // created() {
  //   this.list = this.tableDate
  // },
}
</script>

<style scoped>
</style>