<template>
  <div class="CusDevPlan">
    <div class="search">
      <el-input
          v-model="saleChanceQuery.customerName"
          class="w-50 m-2"
          placeholder="客户名"
          style="position: relative;width: 12%;margin: 0 0 0 1.5%"
      />
      <el-input
          v-model="saleChanceQuery.createMan"
          class="w-50 m-2"
          placeholder="创建人"
          style="position: relative;width: 12%;margin: 5px"
      />
      <el-select v-model="saleChanceQuery.devResult" class="m-2" placeholder="开发状态">
        <el-option label="未 开 发" value="0"/>
        <el-option label="开 发 中" value="1"/>
        <el-option label="开 发 完 成" value="2"/>
        <el-option label="开 发 失 败" value="3"/>
      </el-select>
      &nbsp;&nbsp;
      <el-button type="primary" style="left: 20px" @click="selectQuery" v-if="selectVisible">点 击 查 询</el-button>
      <el-button size="small" type="success" style="float: right;margin: 2% 0 0 0" @click="handleDownload">导出EXCEL表格</el-button>
    </div>
    <div>
      <el-table :data="saleChance" class="tableMenu" max-height="520"
                :default-sort="{ prop: 'createDate', order: 'descending' }"
                :header-cell-style="{ backgroundColor: '#eef5ff',   textAlign: 'center',  }"
                :row-style="rowStyle"
      >
        <el-table-column prop="id" label="编号" width="100" fixed="left" align="center"/>
        <el-table-column prop="chanceSource" label="机会来源" width="100" header-align="center" align="center"/>
        <el-table-column prop="customerName" label="客户名称" width="100" header-align="center" align="center"/>
        <el-table-column prop="cgjl" label="成功几率(%)" width="130" header-align="center" align="center"/>
        <el-table-column prop="overview" label="概要" width="150" header-align="center" align="center"/>
        <el-table-column prop="linkMan" label="联系人" width="100" header-align="center" align="center"/>
        <el-table-column prop="linkPhone" label="联系电话" width="130" header-align="center" align="center"/>
        <el-table-column prop="description" label="描述" width="200" header-align="center" align="center"/>
        <el-table-column prop="createMan" label="创建人" width="100" header-align="center" align="center"/>
        <el-table-column prop="createDate" label="创建时间" width="200" header-align="center" align="center"/>
        <el-table-column prop="devResult" label="开发状态" width="100" header-align="center" align="center">
          <template #default="scope">
            <span v-if="scope.row.devResult==='开发中'" style="color:skyblue">{{ scope.row.devResult }}</span>
            <span v-else-if="scope.row.devResult==='开发完成'" style="color: #37B328">{{ scope.row.devResult }}</span>
            <span v-else-if="scope.row.devResult==='开发失败'" style="color: #e81a5c">{{ scope.row.devResult }}</span>
            <span v-else-if="scope.row.devResult==='未开发'" style="color: #8c6fd0">{{ scope.row.devResult }}</span>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="70" header-align="center">
          <template #default="scope">
            <el-button link type="primary" size="small" @click="Detailed(scope.row)"
                       v-if="scope.row.devResult=='开发完成' && this.ViewDetailsVisible"
            >详 情
            </el-button
            >
            <el-button link type="primary" size="small" @click="Development(scope.row)"
                       v-if="scope.row.devResult=='开发中' || scope.row.devResult=='未开发'"
                       v-show="developVisible"
            >开 发
            </el-button>
          </template>
        </el-table-column>
        <template v-slot:empty>
          <p>暂无记录</p>
        </template>
      </el-table>
    </div>
    <div class=page>
      <el-pagination
          v-model:current-page="saleChanceQuery.page"
          v-model:page-size="saleChanceQuery.limit"
          :small="true"
          :disabled="false"
          :background="true"
          layout="prev,pager,next,jumper"
          :total="total"
          @current-change="handleCurrentChange"
      />
    </div>
    <!--开发完成-->
    <el-dialog
        v-model="DetailedVisible"
        title="计划项数据详情"
        width="70%"
    >
      <el-form
          label-position="left"
          label-width="90px"
          :inline="true"
          :model="DetailedInformation"
          style="width: 100%"
      >
        <el-form-item label="客户名称">
          <el-input v-model="DetailedInformation.customerName" disabled placeholder="客户名称"/>
        </el-form-item>
        <el-form-item label="机会来源">
          <el-input v-model="DetailedInformation.chanceSource" disabled placeholder="机会来源"/>
        </el-form-item>
        <el-form-item label="联系人">
          <el-input v-model="DetailedInformation.linkMan" disabled placeholder="联系人"/>
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="DetailedInformation.linkPhone" disabled placeholder="联系电话"/>
        </el-form-item>
        <el-form-item label="概要">
          <el-input v-model="DetailedInformation.overview" disabled placeholder="概要"/>
        </el-form-item>
        <el-form-item label="成功几率(%)">
          <el-input v-model="DetailedInformation.cgjl" disabled placeholder="成功几率"/>
        </el-form-item>
      </el-form>
      <el-button size="small" type="success" style="float: right;margin: 0 3.3% 0 0 " @click="handleDownloadItem">导出EXCEL表格</el-button>
      <el-table :data="cusDevPlan" max-height="520" :default-sort="{ prop: 'createDate', order: 'descending' }"
                :header-cell-style="{ backgroundColor: '#eef5ff',   textAlign: 'center',  }">
        <el-table-column prop="id" label="编号" width="100" fixed="left" align="center"/>
        <el-table-column prop="planItem" label="计划项" width="100" header-align="center" align="center"/>
        <el-table-column prop="exeAffect" label="执行效果" width="200" header-align="center" align="center"/>
        <el-table-column prop="planDate" label="执行时间" width="200" header-align="center" align="center"/>
        <el-table-column prop="createDate" label="创建时间" width="200" header-align="center" align="center"/>
        <el-table-column prop="updateDate" label="更新时间" width="200" header-align="center" align="center"/>
        <template v-slot:empty>
          <p>暂无记录</p>
        </template>
      </el-table>

      <el-pagination
          v-model:current-page="cusDevPlanQuery.page"
          v-model:page-size="cusDevPlanQuery.limit"
          :small="true"
          :disabled="false"
          :background="true"
          layout="prev,pager,next,jumper"
          :total="total"
          @current-change="CDPHandleCurrentChange"
      />

      <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="this.DetailedVisible=false">
          确 定
        </el-button>
      </span>
      </template>
    </el-dialog>

    <!--开发未完成-->
    <el-dialog
        v-model="DevelopmentVisible"
        title="计划项数据维护"
        width="70%"
    >

      <el-form
          label-position="left"
          label-width="90px"
          :inline="true"
          :model="DevelopmentInformation"
          style="width: 100%"
      >
        <el-form-item label="客户名称">
          <el-input v-model="DevelopmentInformation.customerName" disabled placeholder="暂无"/>
        </el-form-item>
        <el-form-item label="机会来源">
          <el-input v-model="DevelopmentInformation.chanceSource" disabled placeholder="暂无"/>
        </el-form-item>
        <el-form-item label="联系人">
          <el-input v-model="DevelopmentInformation.linkMan" disabled placeholder="暂无"/>
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="DevelopmentInformation.linkPhone" disabled placeholder="暂无"/>
        </el-form-item>
        <el-form-item label="概要">
          <el-input v-model="DevelopmentInformation.overview" disabled placeholder="暂无"/>
        </el-form-item>
        <el-form-item label="成功几率(%)">
          <el-input v-model="DevelopmentInformation.cgjl" disabled placeholder="暂无"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="margin: 10px 0 10px 10px" @click="AddScheduleItemVisible=true" v-show="addItemVisible"
          >添加计划项
          </el-button>
          <el-button type="success" style="margin: 10px 0 10px 10px" @click="success" v-model="SaleChanceSuccess">开发成功
          </el-button>
          <el-button type="danger" style="margin: 10px 0 10px 10px" @click="error" v-model="SaleChanceError">开发失败
          </el-button>
        </el-form-item>
        <el-button size="small" type="success" style="float: right;margin: 4% 0.5% 0 0 " @click="handleDownloadItem02">导出EXCEL表格</el-button>
        <el-table :data="cusDevPlan" max-height="520" :default-sort="{ prop: 'createDate', order: 'descending' }"
                  :header-cell-style="{ backgroundColor: '#eef5ff',   textAlign: 'center',  }">
          <el-table-column prop="id" label="编号" width="100" fixed="left" align="center"/>
          <el-table-column prop="planItem" label="计划项" width="100" header-align="center" align="center"/>
          <el-table-column prop="exeAffect" label="执行效果" width="200" header-align="center" align="center"/>
          <el-table-column prop="planDate" label="执行时间" width="200" header-align="center" align="center"/>
          <el-table-column prop="createDate" label="创建时间" width="200" header-align="center" align="center"/>
          <el-table-column prop="updateDate" label="更新时间" width="200" header-align="center" align="center"/>
          <el-table-column fixed="right" label="操作" width="110" header-align="center">
            <template #default="scope">
              <el-button link type="primary" size="small" @click="updateDVP(scope.row)" v-show="updateItemVisible"
              >编 辑
              </el-button
              >
              <el-button link type="primary" size="small" @click="deleteDVP(scope.row)" v-show="delItemVisible"
              >删 除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <el-pagination
          v-model:current-page="cusDevPlanQuery.page"
          v-model:page-size="cusDevPlanQuery.limit"
          :small="true"
          :disabled="false"
          :background="true"
          layout="prev,pager,next,jumper"
          :total="total"
          @current-change="CDPHandleCurrentChange"
      />
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="DevelopmentVisible = false">取 消</el-button>
        <el-button type="primary" @click="DevelopmentVisible = false">
          确认
        </el-button>
      </span>
      </template>
    </el-dialog>

    <!--添加计划项-->
    <el-dialog
        v-model="AddScheduleItemVisible"
        title="计划项管理——添加计划项"
        width="35%"
    >
      <el-form
          label-position="left"
          label-width="90px"
          :model="addCusDevPlan"
          style="width: 100%"
      >
        <el-form-item label="计划项内容">
          <el-input v-model="addCusDevPlan.planItem" style="width: 55%" placeholder="请输入计划项内容"/>
        </el-form-item>
        <el-form-item label="计划时间">
          <el-date-picker
              v-model.trim="addCusDevPlan.planDate"
              :picker-options="pickerOptions"
              type="datetime"
              placeholder="请选择"
              format="YYYY-MM-DD HH:mm:ss"
              value-format="YYYY-MM-DD HH:mm:ss"
          />
        </el-form-item>
        <el-form-item label="执行效果">
          <el-input v-model="addCusDevPlan.exeAffect" type="textarea" :autosize="{ minRows: 2, maxRows: 4 }"
                    style="width: 55%" placeholder="请输入执行效果"/>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false,this.addCusDevPlan={}">取 消</el-button>
        <el-button type="primary" @click="addCDP">
          确 认 添 加
        </el-button>
      </span>
      </template>
    </el-dialog>

    <!--更新计划项-->
    <el-dialog
        v-model="UpdateScheduleItemVisible"
        title="计划项管理——更新计划项"
        width="40%"
    >
      <el-form
          label-position="left"
          label-width="90px"
          :model="updateCusDevPlan"
          style="width: 100%"
      >
        <el-form-item label="计划项内容">
          <el-input v-model="updateCusDevPlan.planItem" placeholder="请输入计划项内容"/>
        </el-form-item>
        <el-form-item label="计划时间">
          <el-date-picker
              v-model.trim="updateCusDevPlan.planDate"
              :picker-options="pickerOptions"
              type="datetime"
              placeholder="请选择"
              format="YYYY-MM-DD HH:mm:ss"
              value-format="YYYY-MM-DD HH:mm:ss"
          />
        </el-form-item>
        <el-form-item label="执行效果">
          <el-input v-model="updateCusDevPlan.exeAffect" placeholder="请输入执行效果"/>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="UpdateScheduleItemVisible = false,this.updateCusDevPlan={}">取 消</el-button>
        <el-button type="primary" @click="updateCDP">
          确 认 更 新
        </el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import {toRaw, reactive} from '@vue/reactivity'
import {ElMessage, ElMessageBox} from "element-plus";
// eslint-disable-next-line no-unused-vars
import {saveJsonToExcel} from "@/tools/utils";

export default {
  name: "CusDevPlan",
  data() {
    let tableList = reactive({
      id: "",
      devResult: ""
    })
    return {
      saleChance: [],//全部营销机会列表
      cusDevPlan: [],
      saleChanceQuery: {//多条件查询营销机会
        customerName: "",
        createMan: "",
        devResult: "",
        page: 1,
        limit: 10,
      },
      //客户开发总条数
      cusDevPlanQueryTotal: "",
      //营销机会总条数
      total: "",

      //计划项数据详情
      DetailedVisible: false,
      DetailedInformation: [],

      //计划项数据开发
      DevelopmentVisible: false,
      DevelopmentInformation: [],
      //添加计划项
      AddScheduleItemVisible: false,
      addCusDevPlan: {saleChanceId: "", planItem: "", planDate: "", exeAffect: ""},
      pickerOptions: {
        disabledDate(time) {//设置计划时间格式、限制
          return time.getTime() > Date.now();
        },
        selectableRange: "00:00:00 - 23:59:59",
      },
      //更新计划项
      UpdateScheduleItemVisible: false,
      updateCusDevPlan: {},

      //开发状态
      SaleChanceSuccess: 2,
      SaleChanceError: 3,

      tableList,
      //查询条件封装
      cusDevPlanQuery: {page: 1, limit: 5, saleChanceId: ""},

      permissionList: {},//角色权限码
      //根据权限码来显示增删查改操作
      ViewDetailsVisible: false,
      //客户机会查询
      selectVisible: false,
      //添加子项
      addItemVisible: false,
      //修改子项
      updateItemVisible: false,
      //删除子项
      delItemVisible: false,
      //客户开发
      developVisible:false
    }
  },
  methods: {
    //excel导出
    handleDownload(){
      let json_fields = []
      for (let i = 0; i < this.saleChance.length; i++) {
        json_fields.push({
          "编号":this.saleChance[i].id,
          "机会来源":this.saleChance[i].chanceSource,
          "客户名称":this.saleChance[i].customerName,
          "成功几率(%)":this.saleChance[i].cgjl,
          "概要":this.saleChance[i].overview,
          "联系人":this.saleChance[i].linkMan,
          "联系电话":this.saleChance[i].linkPhone,
          "描述":this.saleChance[i].description,
          "创建人":this.saleChance[i].createMan,
          "分配人":this.saleChance[i].assignMan,
          "分配时间":this.saleChance[i].assignTime,
          "创建时间":this.saleChance[i].createDate,
          "修改时间":this.saleChance[i].updateDate,
          "开发状态":this.saleChance[i].devResult
        })
      }
      // console.log("json_fields",json_fields)
      saveJsonToExcel(json_fields, '客户开发计划信息.xlsx')
    },
    handleDownloadItem(){
      let json_fields = []
      for (let i = 0; i < this.cusDevPlan.length; i++) {
        json_fields.push({
          "编号":this.cusDevPlan[i].id,
          "计划项":this.cusDevPlan[i].planItem,
          "执行效果":this.cusDevPlan[i].exeAffect,
          "执行时间":this.cusDevPlan[i].planDate,
          "创建时间":this.cusDevPlan[i].createDate,
          "更新时间":this.cusDevPlan[i].updateDate
        })
      }
      if (json_fields.length===0){
        ElMessage({type:"warning",message:"该客户开发计划项子项无数据，无法导出!"})
      }else {
        // console.log("json_fields",json_fields)
        saveJsonToExcel(json_fields, '客户开发计划项子项信息.xlsx')
      }
    },
    handleDownloadItem02(){
      let json_fields = []
      for (let i = 0; i < this.cusDevPlan.length; i++) {
        json_fields.push({
          "编号":this.cusDevPlan[i].id,
          "计划项":this.cusDevPlan[i].planItem,
          "执行效果":this.cusDevPlan[i].exeAffect,
          "执行时间":this.cusDevPlan[i].planDate,
          "创建时间":this.cusDevPlan[i].createDate,
          "更新时间":this.cusDevPlan[i].updateDate
        })
      }
      if (json_fields.length===0){
        ElMessage({type:"warning",message:"该客户开发计划项子项无数据，无法导出!"})
      }else {
        // console.log("json_fields",json_fields)
        saveJsonToExcel(json_fields, '客户开发计划项子项信息.xlsx')
      }
    },

    //根据开发状态显示字体颜色
    rowStyle({row}) {
      let stylejson = {}
      if (toRaw(row).devResult === "开发完成") {
        stylejson.color = "green"
        return stylejson
      } else if (toRaw(row).devResult === "开发失败") {
        stylejson.color = "red"
        return stylejson
      } else {
        return stylejson
      }
    },
    //翻页触发此方法
    handleCurrentChange(msg) {
      this.saleChanceQuery.page = msg
      this.$api.SaleChance.querySaleChanceByParams("/SaleChance/lists?flag=1", toRaw(this.saleChanceQuery)).then(res => {
        if (res.code === 200) {
          this.saleChance = res.result.data
        }
      })
    },
    //客户开发计划中翻页触发方法
    CDPHandleCurrentChange(msg) {
      this.cusDevPlanQuery.page = msg
      this.$api.CusDevPlan.queryCusDevPlanByParams("/CusDevPlan/lists", toRaw(this.cusDevPlanQuery)).then(res => {
        console.log(res)
      })
    },
    //查询营销机会
    selectQuery() {
      // console.log(toRaw(this.saleChanceQuery))
      let flag = 1
      this.$api.SaleChance.querySaleChanceByParams("/SaleChance/lists?flag=1", toRaw(this.saleChanceQuery), flag).then(res => {
        // console.log(res)
        if (res.code === 200) {
          ElMessage({message: "查询成功", type: "success"})
          this.saleChance = res.result.data
          this.total = this.saleChance.length
          for (let i = 0; i < this.saleChance.length; i++) {
            if (this.saleChance[i].devResult === 0) {
              this.saleChance[i].devResult = "未开发"
            } else if (this.saleChance[i].devResult === 1) {
              this.saleChance[i].devResult = "开发中"
            } else if (this.saleChance[i].devResult === 2) {
              this.saleChance[i].devResult = "开发完成"
            } else {
              this.saleChance[i].devResult = "开发失败"
            }

          }
        }
      })
    },
    //计划项数据详情
    Detailed(msg) {
      this.DetailedVisible = true
      this.DetailedInformation = msg
      this.cusDevPlanQuery.saleChanceId = toRaw(msg.id)
      // console.log(toRaw(msg.id),777)
      this.cusDevPlanQuery.saleChanceId = toRaw(msg.id)
      this.queryByParams()
      this.getTotal(toRaw(msg.id))
    },
    //计划项数据操作
    Development(msg) {
      this.DevelopmentVisible = true
      this.DevelopmentInformation = msg
      this.cusDevPlanQuery.saleChanceId = toRaw(msg.id)
      this.addCusDevPlan.saleChanceId = toRaw(msg.id)
      this.queryByParams()
      this.getTotal(toRaw(msg.id))
    },
    //添加计划项
    addCDP() {
      this.addCusDevPlan.saleChanceId = this.DevelopmentInformation.id
      // console.log(toRaw(this.addCusDevPlan))
      if (this.addCusDevPlan.planItem===""||this.addCusDevPlan.planDate===""){
        ElMessage({type:"info",message:"信息缺失"})
      }else {
        this.$api.CusDevPlan.addCusDevPlan("/CusDevPlan/add", toRaw(this.addCusDevPlan)).then(res => {
          // console.log(res)
          if (res.code === 200) {
            ElMessage({
              type: "success",
              message: "添加成功!"
            })
          } else {
            ElMessage({
              type: "error",
              message: "添加失败，请稍后重试！"
            })
          }
          this.AddScheduleItemVisible = false
          this.addCusDevPlan = {}
          this.queryByParams()
        })
      }

    },
    //获取客户开发计划总数
    getTotal(msg) {
      this.cusDevPlanQuery.saleChanceId = msg
      this.$api.CusDevPlan.getTotal("/CusDevPlan/Total", this.cusDevPlanQuery).then(res => {
        this.cusDevPlanQueryTotal = res.result
      })
    },
    //根据id查询客户开发计划
    queryByParams() {
      this.$api.CusDevPlan.queryCusDevPlanByParams("/CusDevPlan/lists", this.cusDevPlanQuery).then(res => {
        this.cusDevPlan = res.result.data
        console.log(res, 4566541234567)
        console.log(res.result.data)
      })
    },
    //删除客户开发计划
    deleteDVP(msg) {
      // console.log(toRaw(msg).id)
      ElMessageBox.confirm(
          '确认要删除此条记录吗？',
          'Warning',
          {
            confirmButtonText: "是的",
            cancelButtonText: "不,我再想想",
          }
      ).then(() => {
        // console.log(toRaw(msg).id)
        let id = toRaw(msg).id
        // console.log(id, "id")
        this.$api.CusDevPlan.delCusDevPlan("/CusDevPlan/delete", id).then(res => {
          console.log(res)
          if (res.code === 200) {
            ElMessage({
              message: "删除成功",
              type: "success"
            })
            this.queryByParams()
          }
        })
      }).catch(() => {
        ElMessage({
          message: "取消操作",
          type: "info"
        })
      })
    },
    //修改客户开发计划页面
    updateDVP(msg) {
      this.UpdateScheduleItemVisible = true
      this.updateCusDevPlan =  JSON.parse(JSON.stringify(msg))
    },
    //实现更新操作
    updateCDP() {
      if(this.updateCusDevPlan.planItem===""||this.updateCusDevPlan.planItem===""){
        ElMessage({type:"info",message:"计划项信息不完整!"})
      }else {
        this.$api.CusDevPlan.updateCDP("/CusDevPlan/update", this.updateCusDevPlan).then(res => {
          if (res.code === 200) {
            ElMessage({
              type: "success",
              message: "更新成功！"
            })
          } else {
            ElMessage({
              message: "更新失败，请重试！",
              type: "error"
            })
          }
          this.UpdateScheduleItemVisible = false
          this.queryByParams()
          this.updateCusDevPlan = {}
        })
      }
    },
    //开发成功
    success() {
      // let id = this.cusDevPlanQuery.saleChanceId
      // let devResult = this.SaleChanceSuccess
      this.tableList.id = this.cusDevPlanQuery.saleChanceId
      this.tableList.devResult = this.SaleChanceSuccess
      // console.log(id, devResult)
      this.$api.SaleChance.updateSaleChanceDevResult("/SaleChance/updateSaleChanceDevResult", this.tableList).then(res => {
        // console.log(res)
        if (res.code === 200) {
          ElMessage({
            type: "success",
            message: "开发成功!"
          })
        } else {
          ElMessage({
            type: "error",
            message: "操作失败，请重试"
          })
        }
        this.DevelopmentVisible = false
        this.query()
      })
    },
    //开发失败
    error() {
      let id = this.cusDevPlanQuery.saleChanceId
      let devResult = this.SaleChanceError
      let formdata = new FormData()
      formdata.append("id", id)
      formdata.append("devResult", devResult)
      // console.log(id, devResult)
      this.$api.SaleChance.updateSaleChanceDevResult("/SaleChance/updateSaleChanceDevResult", formdata).then(res => {
        // console.log(res)
        if (res.code === 200) {
          ElMessage({
            type: "success",
            message: "操作成功!"
          })
        } else {
          ElMessage({
            type: "error",
            message: "操作失败，请重试!"
          })
        }
        this.DevelopmentVisible = false
        this.query()
      })
    },
    query() {
      this.$api.SaleChance.querySaleChanceByParams("/SaleChance/lists?flag=1").then(res => {
        if (res.code == 200) {
          this.saleChance = res.result.data
          this.total = this.saleChance.length
          // console.log("saleChance", this.saleChance)
          for (let i = 0; i < this.saleChance.length; i++) {
            if (this.saleChance[i].devResult === 0) {
              this.saleChance[i].devResult = "未开发"
            } else if (this.saleChance[i].devResult === 1) {
              this.saleChance[i].devResult = "开发中"
            } else if (this.saleChance[i].devResult === 2) {
              this.saleChance[i].devResult = "开发完成"
            } else {
              this.saleChance[i].devResult = "开发失败"
            }
          }
        }
      })
    }
  },
  mounted() {
    this.query()
    this.permissionList = this.$store.getters.getPermissionList
    if (JSON.stringify(toRaw(this.permissionList)).includes("102001")) {
      this.selectVisible = true
    }
    if (JSON.stringify(toRaw(this.permissionList)).includes("102003")) {
      this.ViewDetailsVisible = true
    }
    if (JSON.stringify(toRaw(this.permissionList)).includes("102004")) {
      this.developVisible = true
    }
    if (JSON.stringify(toRaw(this.permissionList)).includes("10200501")) {
      this.addItemVisible = true
    }
    if (JSON.stringify(toRaw(this.permissionList)).includes("10500503")) {
      this.delItemVisible = true
    }
    if (JSON.stringify(toRaw(this.permissionList)).includes("10200502")) {
      this.updateItemVisible = true
    }

  }
}
</script>

<style scoped>
.CusDevPlan {
  width: 100%;
  height: 95%;
  position: relative;
  /*background-color: seashell;*/
}
.search{
  position: relative;
  width: 98%;
  height: 50px;
  display: block;
}
.tableMenu {
  header-align: center;
  left: 1%;
  position: relative;
  width: 98%;
}
.page{
  position: relative;
  margin: 1% 0 0 1%;
  width: 60%;
  color: #ffffff;
}
</style>