<template>
  <div class="SaleChance">
    <div class="search">
      <el-input
          v-model="saleChanceQuery.customerName"
          class="w-50 m-2"
          placeholder="客户名"
          style="position: relative;width: 12%;margin: 0 0 0 0"
      />
      <el-input
          v-model="saleChanceQuery.createMan"
          class="w-50 m-2"
          placeholder="创建人"
          style="position: relative;width: 12%;margin: 5px"
      />
      <el-select v-model="saleChanceQuery.state" class="m-2" placeholder="分 配 状 态">
        <el-option label="全 部" value=""/>
        <el-option label="已 分 配" value="1"/>
        <el-option label="未 分 配" value="0"/>
      </el-select>
      &nbsp;&nbsp;
      <el-button type="primary" style="left: 30px" @click="selectQuery" v-show="selectVisible">点 击 查 询</el-button>
      <el-button type="primary" @click="dialogVisible=true" v-show="addVisible">添 加</el-button>
      <el-button size="small" type="success" style="float: right;margin: 2% 0 0 0" @click="handleDownload">导出EXCEL表格</el-button>
    </div>

    <div>
      <el-table :data="saleChance" class="tableMenu" max-height="510"
                :default-sort="{ prop: 'createDate', order: 'descending' }"
                :header-cell-style="{ backgroundColor: '#eef5ff',   textAlign: 'center',  }">
        <el-table-column prop="id" sortable label="编号" width="100" fixed="left" align="center"/>
        <el-table-column prop="chanceSource" label="机会来源" width="100" header-align="center" align="center"/>
        <el-table-column prop="customerName" label="客户名称" width="100" header-align="center" align="center"/>
        <el-table-column prop="cgjl" label="成功几率(%)" width="130" header-align="center" align="center"/>
        <el-table-column prop="overview" label="概要" width="150" header-align="center" align="center"/>
        <el-table-column prop="linkMan" label="联系人" width="100" header-align="center" align="center"/>
        <el-table-column prop="linkPhone" label="联系电话" width="130" header-align="center" align="center"/>
        <el-table-column label="描述" width="150" header-align="center" align="center">
          <template #default="scope">
            <el-popover
                placement="right-start"
                title="描述信息"
                :width="400"
                trigger="hover"
                :content="scope.row.description"
            >
              <template #reference>
                <el-button class="m-2" size="small">描述</el-button>
              </template>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column prop="createMan" label="创建人" width="100" header-align="center" align="center"/>
        <el-table-column prop="assignMan" label="分配人" width="100" header-align="center" align="center"/>
        <el-table-column prop="assignTime" label="分配时间" width="200" header-align="center" align="center"/>
        <el-table-column prop="createDate" sortable label="创建时间" width="200" header-align="center" align="center"/>
        <el-table-column prop="updateDate" label="修改时间" width="200" header-align="center" align="center"/>
        <el-table-column label="分配状态" width="100" header-align="center" align="center">
          <template #default="scope">
            <el-tag v-if="scope.row.state==='已分配'">{{ scope.row.state }}</el-tag>
            <el-tag v-else-if="scope.row.state==='未分配'" class="ml-2" type="danger">{{ scope.row.state }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="devResult" label="开发状态" width="100" header-align="center" align="center">
          <template #default="scope">
            <el-tag v-if="scope.row.devResult==='开发完成'" class="ml-2" type="success">{{ scope.row.devResult }}</el-tag>
            <el-tag v-else-if="scope.row.devResult==='开发中'">{{ scope.row.devResult }}</el-tag>
            <el-tag v-else-if="scope.row.devResult==='未开发'" class="ml-2" type="info">{{ scope.row.devResult }}</el-tag>
            <el-tag v-else-if="scope.row.devResult==='开发失败'" class="ml-2" type="danger">{{ scope.row.devResult }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="110" header-align="center" v-if="updateVisible||delVisible">
          <template #default="scope">
            <el-button link type="primary" size="small" @click="updateSC(scope.row)" v-if="updateVisible"
            >修 改
            </el-button
            >
            <el-button link type="primary" size="small" @click="deleteSC(scope.row)" v-if="delVisible"
            >
              删 除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="page">
      <el-pagination
          v-model:current-page="saleChanceQuery.page"
          v-model:page-size="saleChanceQuery.limit"
          small="small"
          :disabled="false"
          :background="true"
          layout="prev,pager,next,jumper"
          :total="total"
          @current-change="handleCurrentChange"
      />

    </div>
    <!--添加-->
    <el-dialog
        v-model="dialogVisible"
        title="营销机会信息添加"
        width="45%"
    >
      <el-form
          label-position="left"
          label-width="100px"
          :model="addSaleChanceList"
          :rules="RuleForm"
          ref="addSaleChanceList"
          style="max-width: 460px"
      >
        <el-form-item label="客户名称" prop="customerName">
          <el-input v-model="addSaleChanceList.customerName" placeholder="请输入客户名称"/>
        </el-form-item>
        <el-form-item label="机会来源">
          <el-input v-model="addSaleChanceList.chanceSource" placeholder="请输入机会来源"/>
        </el-form-item>
        <el-form-item label="联系人">
          <el-input v-model="addSaleChanceList.linkMan" placeholder="请输入联系人"/>
        </el-form-item>
        <el-form-item label="联系电话" prop="linkPhone">
          <el-input v-model="addSaleChanceList.linkPhone" placeholder="请输入联系电话"/>
        </el-form-item>
        <el-form-item label="概要">
          <el-input v-model="addSaleChanceList.overview" placeholder="请输入概要"/>
        </el-form-item>
        <el-form-item label="成功几率(%)">
          <el-input v-model="addSaleChanceList.cgjl" placeholder="请输入成功几率"/>
        </el-form-item>
        <el-form-item label="机会描述">
          <el-input v-model="addSaleChanceList.description" type="textarea" :autosize="{ minRows: 2, maxRows: 4 }"
                    placeholder="请输入机会描述"/>
        </el-form-item>
        <el-form-item label="指派给">
          <el-select v-model="addSaleChanceList.assignMan" placeholder="Select">
            <el-option
                v-for="a in AssignmentList"
                :key="a.id"
                :label="a.uname"
                :value="a.id"
            />
          </el-select>
        </el-form-item>

      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false,this.addSaleChanceList={}">取 消</el-button>
        <el-button type="primary" @click="addSaleChance">
          确认
        </el-button>
      </span>
      </template>
    </el-dialog>
    <!--修改-->
    <el-dialog
        v-model="updateDialogVisible"
        title="营销机会信息修改"
        width="45%"
    >
      <el-form
          label-position="left"
          label-width="100px"
          :model="updateSaleChanceList"
          style="max-width: 460px"
      >
        <el-form-item label="客户名称">
          <el-input v-model="updateSaleChanceList.customerName" placeholder="请输入客户名称"/>
        </el-form-item>
        <el-form-item label="机会来源">
          <el-input v-model="updateSaleChanceList.chanceSource" placeholder="请输入机会来源"/>
        </el-form-item>
        <el-form-item label="联系人">
          <el-input v-model="updateSaleChanceList.linkMan" placeholder="请输入联系人"/>
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model.number="updateSaleChanceList.linkPhone" placeholder="请输入联系电话"/>
        </el-form-item>
        <el-form-item label="概要">
          <el-input v-model="updateSaleChanceList.overview" placeholder="请输入概要"/>
        </el-form-item>
        <el-form-item label="成功几率(%)">
          <el-input v-model.number="updateSaleChanceList.cgjl" placeholder="请输入成功几率"/>
        </el-form-item>
        <el-form-item label="机会描述">
          <el-input v-model="updateSaleChanceList.description" type="textarea" :autosize="{ minRows: 2, maxRows: 4 }"
                    placeholder="请输入机会描述"/>
        </el-form-item>
        <el-form-item label="指派给">
          <el-select v-model="updateSaleChanceList.assignMan" :placeholder="select">
            <el-option
                v-for="list in AssignmentList"
                :key="list.id"
                :label="list.uname"
                :value="list.uname"
            />
          </el-select>
        </el-form-item>

      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="updateDialogVisible = false,this.updateSaleChanceList={}">取 消</el-button>
        <el-button type="primary" @click="update">
          确 认
        </el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import Cookies from "js-cookie";
import {toRaw} from '@vue/reactivity'
import {ElMessage, ElMessageBox} from "element-plus";
// eslint-disable-next-line no-unused-vars
import { saveJsonToExcel } from '../tools/utils.js'
export default {
  name: "SaleChance",
  data() {
    return {
      RuleForm : [{ linkPhone:[{required:true,message:"请输入联系电话",trigger:'blur'}],
        customerName:[{required:true,message:"请填写客户名称",trigger:'blur'}]
      }],
      AssignmentList: [],//指派列表
      dialogVisible: false,//添加对话框显示
      updateDialogVisible: false,//修改对话框显示
      addSaleChanceList: {//添加营销机会
        customerName: "",
        chanceSource: "",
        linkMan: "",
        linkPhone: "",
        overview: "",
        cgjl: "",
        description: "",
        assignMan: ""
      },
      updateSaleChanceList: {//更新营销机会
        customerName: "",
        chanceSource: "",
        linkMan: "",
        linkPhone: "",
        overview: "",
        cgjl: "",
        description: "",
        assignMan: ""
      },
      saleChance: [],//全部营销机会列表
      saleChanceQuery: {//多条件查询
        customerName: "",
        createMan: "",
        state: null,
        page: 1,
        limit: 10,
      },
      select: "",//指派名称回显
      total: "",
      permissionList: {},//角色权限码
      //根据权限码来显示增删查改操作
      selectVisible: false,
      addVisible: false,
      updateVisible: false,
      delVisible: false
    }
  },
  methods: {
    handleDownload(){
      let saleChance = []
      // eslint-disable-next-line no-unused-vars
      let json_fields = []
      let saleChanceQuery = {//多条件查询
            customerName: this.saleChanceQuery.customerName,
            createMan: this.saleChanceQuery.createMan,
            state: this.saleChanceQuery.state,
            page: 1,
            limit: 1000,
          }
      this.$api.SaleChance.querySaleChanceByParams("/SaleChance/lists",saleChanceQuery).then(res => {
        if (res.code == 200) {
          // console.log(toRaw(this.AssignmentList), 987)
          saleChance = res.result.data
          for (let i = 0; i < saleChance.length; i++) {
            if (saleChance[i].state === 0) {
              saleChance[i].state = "未分配"
            } else {
              saleChance[i].state = "已分配"
            }

            if (saleChance[i].devResult === 0) {
              saleChance[i].devResult = "未开发"
            } else if (saleChance[i].devResult === 1) {
              saleChance[i].devResult = "开发中"
            } else if (saleChance[i].devResult === 2){
              saleChance[i].devResult = "开发完成"
            } else if (saleChance[i].devResult === 3){
              saleChance[i].devResult = "开发失败"
            }

            for (let j = 0; j < this.AssignmentList.length; j++) {
              if (saleChance[i].assignMan == this.AssignmentList[j].id) {
                saleChance[i].assignMan = this.AssignmentList[j].uname
              }
            }
          }
          for (let i = 0; i < saleChance.length; i++) {
            json_fields.push({
              "编号": saleChance[i].id,
              "机会来源": saleChance[i].chanceSource,
              "客户名称": saleChance[i].customerName,
              "成功几率(%)": saleChance[i].cgjl,
              "概要": saleChance[i].overview,
              "联系人": saleChance[i].linkMan,
              "联系电话": saleChance[i].linkPhone,
              "描述": saleChance[i].description,
              "创建人":  saleChance[i].createMan,
              "分配人":  saleChance[i].assignMan,
              "分配时间": saleChance[i].assignTime,
              "创建时间": saleChance[i].createDate,
              "修改时间": saleChance[i].updateDate,
              "分配状态": saleChance[i].state === 1 ? '已分配' : '未分配',
              "开发状态": saleChance[i].devResult
            })
          }
          saveJsonToExcel(json_fields, '营销机会信息.xlsx')
        }
      })
    },
    updateSC(msg) {
      this.updateDialogVisible = true
      this.updateSaleChanceList = JSON.parse(JSON.stringify(msg))
    },
    //更新
    update() {
      let saleChance = this.updateSaleChanceList
      if (saleChance.state == "未分配") {
        saleChance.state = 0
      } else if (saleChance.state == "已分配") {
        saleChance.state = 1
      }
      if (saleChance.devResult == "未开发") {
        saleChance.devResult = 0
      } else if (saleChance.devResult == "开发中") {
        saleChance.devResult = 1
      } else if (saleChance.devResult == "开发完成") {
        saleChance.devResult = 2
      } else {
        saleChance.devResult = 3
      }
      if (!(/^1[34578]\d{9}$/.test(saleChance.linkPhone))){
        ElMessage({type: "warning", message: "电话号码格式不正确"})
      }else {
        // console.log("要更新的数据为：  ",saleChance)
        this.$api.SaleChance.updateSaleChance("/SaleChance/updateSaleChance", saleChance).then(res => {
          console.log(res)
          if (res.code === 200) {
            ElMessage({
              message: "修改成功！",
              type: "success"
            })
            this.updateDialogVisible = false
            this.handleCurrentChange(this.saleChanceQuery.page)
          } else {
            ElMessage({
              message: "修改失败，请重试",
              type: "error"
            })
          }
        })
      }
    },
    //删除
    deleteSC(msg) {
      ElMessageBox.confirm(
          '确认要删除此条记录吗？',
          'Warning',
          {
            confirmButtonText: "是的",
            cancelButtonText: "不,我再想想",
          }
      ).then(() => {
        let id = toRaw(msg).id
        // console.log(id,"id")
        this.$api.SaleChance.delSaleChance("/SaleChance/delete", id).then(res => {
          // console.log(res)
          if (res.code === 200) {
            ElMessage({
              type: "success",
              message: "删除成功!"
            })
          } else {
            ElMessage({
              type: "error",
              message: "删除失败，请重试!"
            })
          }
          this.handleCurrentChange(this.saleChanceQuery.page)
        })
      }).catch(() => {
        ElMessage({
          message: "取消操作",
          type: "info"
        })
      })
    },
    //查询
    selectQuery() {
      // console.log(toRaw(this.saleChanceQuery))
      let flag = 1
      this.$api.SaleChance.querySaleChanceByParams("/SaleChance/lists", toRaw(this.saleChanceQuery), flag).then(res => {
        // console.log(res)
        if (res.code === 200) {
          ElMessage({message: "查询成功", type: "success"})
          this.saleChance = res.result.data
          this.total = this.saleChance.length
          for (let i = 0; i < this.saleChance.length; i++) {
            if (this.saleChance[i].state === 0) {
              this.saleChance[i].state = "未分配"
            } else {
              this.saleChance[i].state = "已分配"
            }

            if (this.saleChance[i].devResult === 0) {
              this.saleChance[i].devResult = "未开发"
            } else if (this.saleChance[i].devResult === 1) {
              this.saleChance[i].devResult = "开发中"
            } else if (this.saleChance[i].devResult === 2) {
              this.saleChance[i].devResult = "开发完成"
            } else {
              this.saleChance[i].devResult = "开发失败"
            }

            for (let j = 0; j < this.AssignmentList.length; j++) {
              if (this.saleChance[i].assignMan == this.AssignmentList[j].id) {
                this.saleChance[i].assignMan = this.AssignmentList[j].uname
              }
            }
          }
        }
      })
    },
    //获取全部列表
    getAll() {
      this.$api.SaleChance.querySaleChanceByParams("/SaleChance/lists").then(res => {
        if (res.code == 200) {
          // console.log(toRaw(this.AssignmentList), 987)
          this.saleChance = res.result.data
          for (let i = 0; i < this.saleChance.length; i++) {
            if (this.saleChance[i].state === 0) {
              this.saleChance[i].state = "未分配"
            } else {
              this.saleChance[i].state = "已分配"
            }

            if (this.saleChance[i].devResult === 0) {
              this.saleChance[i].devResult = "未开发"
            } else if (this.saleChance[i].devResult === 1) {
              this.saleChance[i].devResult = "开发中"
            } else if (this.saleChance[i].devResult === 2){
              this.saleChance[i].devResult = "开发完成"
            }else if (this.saleChance[i].devResult === 3){
              this.saleChance[i].devResult = "开发失败"
            }

            for (let j = 0; j < this.AssignmentList.length; j++) {
              if (this.saleChance[i].assignMan == this.AssignmentList[j].id) {
                this.saleChance[i].assignMan = this.AssignmentList[j].uname
              }
            }
          }

          console.log(this.saleChance)
        } else {
          ElMessage({
            type: "error",
            message: "查询失败，请重试!"
          })
        }
      })
    },
    //添加营销机会
    addSaleChance() {
      if (this.addSaleChanceList.customerName === "" || this.addSaleChanceList.chanceSource === "" || this.addSaleChanceList.linkMan === "" ||
          this.addSaleChanceList.linkPhone === "" || this.addSaleChanceList.overview === "" || this.addSaleChanceList.cgjl === "" ||
          this.addSaleChanceList.description === "") {
        ElMessage({
          message: "信息没有输入完整！",
          type: "info",
        })
      } else {
        let saleChance = toRaw(this.addSaleChanceList)
        // console.log(saleChance)
        const phone = this.addSaleChanceList.linkPhone
        const SuccessProbability = this.addSaleChanceList.cgjl
        if (!(/^1[34578]\d{9}$/.test(phone))) {  //手机号正则校验
          if (phone === '') {//若手机号为空
            ElMessage({
              message: "请输入手机号",
              type: "error"
            })
          } else {//手机号非空的报错信息
            ElMessage({
              message: "手机号格式错误，请重试",
              type: "error"
            })
          }
        } else {//手机号正确则无错误信息,则进行成功几率为数字的校验
          var numReg = /^[0-9]+$/
          var numRe = new RegExp(numReg)
          if (!numRe.test(SuccessProbability)) {
            ElMessage({
              message: "成功几率格式错误，请重试",
              type: "error"
            })
          } else {
            // console.log(saleChance)
            this.$api.user.addSaleChance("/SaleChance/add", saleChance).then(res => {
              console.log(res)
              if (res.code === 200) {
                ElMessage({
                  message: "添加成功！",
                  type: "success"
                })
                this.dialogVisible = false
                this.addSaleChanceList = {}
                this.handleCurrentChange(this.saleChanceQuery.page)
              }
            })
          }
        }
      }
    },
    handleCurrentChange(msg) {
      this.saleChanceQuery.page = msg
      this.$api.SaleChance.querySaleChanceByParams("/SaleChance/lists", toRaw(this.saleChanceQuery)).then(res => {
        if (res.code === 200) {
          this.saleChance = res.result.data
          for (let i = 0; i < this.saleChance.length; i++) {
            if (this.saleChance[i].state === 0) {
              this.saleChance[i].state = "未分配"
            } else {
              this.saleChance[i].state = "已分配"
            }

            if (this.saleChance[i].devResult === 0) {
              this.saleChance[i].devResult = "未开发"
            } else if (this.saleChance[i].devResult === 1) {
              this.saleChance[i].devResult = "开发中"
            } else if (this.saleChance[i].devResult === 2){
              this.saleChance[i].devResult = "开发完成"
            }else if (this.saleChance[i].devResult === 3){
              this.saleChance[i].devResult = "开发失败"
            }

            for (let j = 0; j < this.AssignmentList.length; j++) {
              if (this.saleChance[i].assignMan == this.AssignmentList[j].id) {
                this.saleChance[i].assignMan = this.AssignmentList[j].uname
              }
            }
          }
        }
      })
    },
  },
  mounted() {
    document.cookie = Cookies.get("userIdStr")
    this.$api.SaleChance.getAll("/SaleChance/getAll").then(res => {
      if (res.code === 200) {
        this.total = res.result
      }
    })
    this.$api.user.queryAllSales("/user/queryAllSales").then(res => {
      this.AssignmentList = toRaw(res.result)
    })
    this.getAll()
    this.permissionList = this.$store.getters.getPermissionList
    if (JSON.stringify(this.permissionList).indexOf("101001") !== -1) {
      this.selectVisible = true
    }
    if (JSON.stringify(this.permissionList).indexOf("101002") !== -1) {
      this.addVisible = true
    }
    if (JSON.stringify(this.permissionList).indexOf("101003") !== -1) {
      this.delVisible = true
    }
    if (JSON.stringify(this.permissionList).indexOf("101004") !== -1) {
      this.updateVisible = true
    }
  }
}
</script>

<style scoped>
.SaleChance {
  width: 100%;
  height: 95%;
  position: relative;
}
.search{
  position: relative;
  width: 98%;
  height: 50px;
  display: block;
  left: 1%;
}
.tableMenu {
  header-align: center;
  left: 1%;
  position: relative;
  width: 98%;
}
.page{
  position: absolute;
  top: 99%;
  width: 60%;
  color: #ffffff;
}
</style>