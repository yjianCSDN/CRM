<template>
  <div class="CustomerOrder">
    <div class="search">
      <el-input
          class="w-50 m-2"
          v-model="customerQuery.customerName"
          placeholder="客户名"
          style="position: relative;width: 12%;margin: 1% 0 2% 3%"
      />
      <el-input
          class="w-50 m-2"
          v-model="customerQuery.customerNo"
          placeholder="客户编号"
          style="position: relative;width: 12%;margin: -1% 0 0 10px"
      />
      <el-select class="m-2" placeholder="请选择" v-model="customerQuery.level" style="width: 150px;margin: -1% 0 0 5px">
        <el-option label="无" value=""/>
        <el-option label="战略合作伙伴" value="战略合作伙伴"/>
        <el-option label="大 客 户" value="大客户"/>
        <el-option label="重点开发客户" value="重点开发客户"/>
      </el-select>
      &nbsp;&nbsp;
      <el-button type="primary" style="left: 10px;margin: -1% 0 0 0" @click="selectCustomer">搜 &nbsp;&nbsp;&nbsp; 索
      </el-button>
      <el-button type="primary" style="left: 10px;margin: -1% 0 0 12px" @click="addOrderVisible=true"
      >添加订单
      </el-button
      >
    </div>
    <div>
      <el-table :data="customerList" class="tableMenu"
                max-height="450" :default-sort="{ prop: 'createDate', order: 'descending' }"
                :header-cell-style="{ backgroundColor: '#eef5ff',   textAlign: 'center',  }"
      >
        <el-table-column fixed="left" prop="orderId" label="编号" width="100" align="center"/>
        <el-table-column prop="khno" label="客户编号" width="200" header-align="center" align="center"/>
        <el-table-column prop="name" label="客户名称" width="170" header-align="center" align="center"/>
        <el-table-column prop="level" label="客户级别" width="150" header-align="center" align="center"/>
        <el-table-column prop="cusManager" label="客户经理" width="100" header-align="center" align="center"/>
        <el-table-column prop="address" label="详细地址" width="300" header-align="center" align="center"/>
        <el-table-column prop="phone" label="电话" width="200" header-align="center" align="center"/>
        <!--        <el-table-column prop="orderId" label="订单编号" width="150" header-align="center"  align="center"/>-->
        <el-table-column prop="orderNo" label="订单编号" width="150" header-align="center" align="center"/>
        <el-table-column prop="orderDate" label="订单日期" width="200" header-align="center" align="center"/>
        <el-table-column prop="createDate" label="创建时间" width="200" header-align="center" align="center"/>
        <el-table-column prop="updateDate" label="更新时间" width="200" header-align="center" align="center"/>
        <el-table-column prop="state" label="状态" width="100" header-align="center" align="center">
          <template #default="scope">
            <scan v-if="scope.row.state===1" style="color:green;">已支付</scan>
            <scan v-else-if="scope.row.state===0" style="color:red;">未支付</scan>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操 作" width="150" header-align="center" align="center">
          <template #default="scope">
            <el-button link size="small" type="primary" @click="CustomerOrderVisible=true,addCustomerInfo=JSON.parse(JSON.stringify(scope.row)),
                        queryOrderList(scope.row),PaymentStatus=scope.row.state">订单查看
            </el-button>
            <el-button link size="small" type="primary" @click="updateCustomerInfo=JSON.parse(JSON.stringify(scope.row)),updateCustomerVisible=true,
                        updateCustomerId=scope.row.orderId,updateId=scope.row.orderId"
            >订单修改
            </el-button>
          </template>
        </el-table-column>
        <template v-slot:empty>
          <p>暂无记录</p>
        </template>
      </el-table>
    </div>
    <div class="page">
      <el-pagination
          v-model:current-page="customerQuery.page"
          v-model:page-size="customerQuery.limit"
          small="small"
          :disabled="false"
          :background="true"
          layout="prev,pager,next,jumper"
          :total="total"
          @current-change="handleCurrentChange"
      />
    </div>
    <!--添加客户订单-->
    <el-dialog
        v-model="addOrderVisible"
        title="客户订单添加"
        width="45%"
    >
      <el-form
          label-position="left"
          label-width="100px"
          :model="customerOrder"
      >
        <el-form-item label="客户名称">
          <el-input v-model="customerOrder.name" style="width: 200px;" placeholder="请输入客户名称"/>
        </el-form-item>
        <el-form-item label="订单编号">
          <el-input v-model="customerOrder.orderNo" style="width: 200px;" placeholder="请输入订单名称"/>
        </el-form-item>
        <el-form-item label="订单地址">
          <el-input v-model="customerOrder.address" style="width: 200px;" type="textarea"
                    :autosize="{ minRows: 2, maxRows: 4 }" placeholder="请输入订单地址"/>
        </el-form-item>
        <el-form-item label="电话号码">
          <el-input v-model="customerOrder.phone" style="width: 200px;" placeholder="请输入电话号码"/>
        </el-form-item>
        <el-form-item label="订单时间">
          <el-date-picker
              v-model="customerOrder.orderDate"
              type="datetime"
              placeholder="选择订单时间"
              format="YYYY-MM-DD HH:mm:ss"
              value-format="YYYY-MM-DD HH:mm:ss"
          />
        </el-form-item>
        <el-form-item label="订单支付状态">
          <el-select class="m-2" placeholder="请选择" v-model="customerOrder.state">
            <el-option label="已支付" value="1"/>
            <el-option label="未支付" value="0"/>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="addOrderVisible = false,this.customerOrder={}">取 消</el-button>
        <el-button type="primary" @click="addInfo">
          确认
        </el-button>
      </span>
      </template>
    </el-dialog>

    <!--查看订单页面-->
    <el-dialog
        v-model="CustomerOrderVisible"
        title="客户管理-订单信息展示"
        width="55%"
    >
      <el-form
          label-position="left"
          :inline="true"
          label-width="80px"
          :model="addCustomerInfo"
      >
        <el-form-item label="客户名称">
          <el-input v-model="addCustomerInfo.name" disabled placeholder="（空）"/>
        </el-form-item>
        <el-form-item label="法人">
          <el-input v-model="addCustomerInfo.fr" disabled placeholder="（空）"/>
        </el-form-item>
        <el-form-item label="客户地址">
          <el-input v-model="addCustomerInfo.address" disabled placeholder="（空）"/>
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="addCustomerInfo.phone" disabled placeholder="（空）"/>
        </el-form-item>
      </el-form>
      <el-button type="primary" style="margin: 10px 0 10px 10px"
                 @click="orderDetailsVisible=true,addOrUpdateResult=false">添加子项
      </el-button>
      <el-button type="primary" style="margin: 10px 0 10px 10px" v-show="PaymentStatus===0" @click="setState">设置为已支付
      </el-button>
      <el-table :data="orderDetailsList" max-height="450" :default-sort="{ prop: 'createDate', order: 'descending' }"
                :header-cell-style="{ backgroundColor: '#eef5ff',   textAlign: 'center',  }">
        <el-table-column prop="id" label="编号" width="100" fixed="left" align="center"/>
        <el-table-column prop="goodsName" label="物品名称" width="170" header-align="center" align="center"/>
        <el-table-column prop="goodsNum" label="物品数量" width="100" header-align="center" align="center"/>
        <el-table-column prop="unit" label="单位" width="70" header-align="center" align="center"/>
        <el-table-column prop="price" label="单价" width="70" header-align="center" align="center"/>
        <el-table-column prop="sum" label="总价" width="100" header-align="center" align="center"/>
        <el-table-column fixed="right" label="操 作" width="180" header-align="center" align="center">
          <template #default="scope">
            <el-button link size="small" type="primary" @click="addOrUpdateResult=true,orderDetailsVisible=true,
                                                        orderDetails=scope.row"
            >编 辑
            </el-button>
            <el-button link size="small" type="primary" @click="confirmEvent(scope.row)"
            >删 除
            </el-button>
          </template>
        </el-table-column>
        <template v-slot:empty>
          <p>该客户暂无订单</p>
        </template>
      </el-table>
      <el-pagination
          v-model:current-page="orderDetailsQuery.page"
          v-model:page-size="orderDetailsQuery.limit"
          small="small"
          :disabled="false"
          :background="true"
          layout="prev,pager,next,jumper"
          :total="total"
          @current-change="handleCurrentChange2"
      />
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="addCustomerVisible = false,this.addCustomerInfo={},this.vv=false,CustomerOrderVisible=false">取 消</el-button>
        <el-button type="primary" @click="addInfo">
          确认
        </el-button>
      </span>
      </template>
    </el-dialog>

    <!--添加服务-->
    <el-dialog
        v-model="orderDetailsVisible"
        title="订单管理-添加子项"
        width="30%"
    >
      <el-form
          label-position="right"
          label-width="90px"
          :model="orderDetails"
          style="width: 100%"
      >
        <el-form-item label="商品名称" style="width: 80%">
          <el-input v-model="orderDetails.goodsName" placeholder="请输入商品名称"/>
        </el-form-item>
        <el-form-item label="商品数量" style="width: 80%">
          <el-input v-model.number="orderDetails.goodsNum" type="number"
                    placeholder="请输入商品数量"/>
        </el-form-item>
        <el-form-item label="商品单价">
          <el-input v-model.number="orderDetails.price" type="number"
                    style="width: 80%" placeholder="请输入商品单价"/>
        </el-form-item>
        <el-form-item label="商品单位">
          <el-input v-model="orderDetails.unit" style="width: 80%" placeholder="请输入商品单位"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
           <el-button type="primary" @click="addServeVisible=false,addServeInfo={}"
           >
            取 消
          </el-button>
          <el-button type="primary" @click="addOrderDetails"
          >
            添 加
          </el-button>
        </span>
      </template>
    </el-dialog>

    <!--订单修改页面-->
    <el-dialog
        v-model="updateCustomerVisible"
        title="客户管理-订单信息修改"
        width="35%"
    >
      <el-form
          label-position="left"
          label-width="80px"
          :model="updateCustomerInfo"
      >
        <el-form-item label="客户名称">
          <el-input v-model="updateCustomerInfo.name" disabled placeholder="（空）"/>
        </el-form-item>
        <el-form-item label="订单编号">
          <el-input v-model="updateCustomerInfo.orderNo" placeholder="（空）"/>
        </el-form-item>
        <el-form-item label="订单地址">
          <el-input v-model="updateCustomerInfo.address" placeholder="（空）"/>
        </el-form-item>
        <el-form-item label="订单时间">
          <el-date-picker
              v-model="updateCustomerInfo.orderDate"
              type="datetime"
              placeholder="选择订单时间"
              format="YYYY-MM-DD HH:mm:ss"
              value-format="YYYY-MM-DD HH:mm:ss"
          />
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="updateCustomerInfo.phone" placeholder="（空）"/>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="updateCustomerVisible=false">取 消</el-button>
        <el-button type="primary" @click="updateOrderInfo">
          确认
        </el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import {reactive, ref} from "@vue/reactivity";
import {ElMessage, ElMessageBox} from "element-plus";

export default {
  name: "CustomerOrders",
  data() {
    let customerList = reactive([])
    let total = ref("")
    let customerQuery = reactive({page: 1, limit: 10, customerName: "", customerNo: "", level: "", time: "", type: ""})
    let customerOrder = reactive({name: "", cusId: "", orderDate: "", address: "", state: "", orderNo: "", phone: ""})
    let addOrderVisible = ref(false)
    let CustomerOrderVisible = ref(false)
    let addCustomerInfo = reactive({})
    let customerOrderLists = reactive([])
    let orderDetailsQuery = reactive({page: 1, limit: 5, orderId: ""})
    let orderDetailsList = reactive([])
    let orderDetails = reactive({orderId: "", goodsName: "", goodsNum: 0, unit: "", price: 0, sum: 0})
    let orderDetailsVisible = ref(false)//添加子项开启
    let addOrUpdateResult = ref(false)//默认设置开始更新子项则该项为true
    let PaymentStatus = ''
    let updateCustomerInfo = reactive({id: "", name: "", cusId: "", orderDate: "", address: "", orderNo: "", phone: ""})
    let updateCustomerVisible = ref(false)
    let updateId = ref("")
    return {
      customerList,
      total,
      customerQuery,
      customerOrder,
      addCustomerInfo,
      addOrderVisible,
      CustomerOrderVisible,
      updateCustomerInfo,
      customerOrderLists,
      orderDetailsQuery,
      orderDetailsList,
      orderDetails,
      orderDetailsVisible,
      addOrUpdateResult,
      PaymentStatus,
      updateCustomerVisible,
      updateId
    }
  },
  methods: {
    //条件查询
    selectCustomer() {
      // console.log(this.customerQuery)
      this.$api.CustomerServer.queryCustomerServeOrder("/customerServeOrder/lists", this.customerQuery).then(res => {
        // console.log(res)
        if (res.code === 200) {
          ElMessage({
            type: "success",
            message: "查询成功!"
          })
          this.customerList = res.result.data
          this.total = res.result.count
        } else {
          ElMessage({
            type: "error",
            message: "查询失败，请重试!"
          })
        }
      })
    },
    addInfo() {
      let customer = this.customerOrder.name
      this.$api.CustomerServer.getInfoByName("/customerServe/getInfoByName", {customer}).then(res => {
        if (res.code === 200) {
          // console.log("yejian",res)
          this.customerOrder.cusId = res.result.id
          // this.customerOrder.address=res.result.address
          // console.log(this.customerOrder)
          this.$api.CustomerInformation.addCustomerOrder("/customerServeOrder/addOrder", this.customerOrder).then(res => {
            // console.log(res)
            if (res.code === 200) {
              ElMessage({type: "success", message: "添加成功"})
            } else {
              ElMessage({type: "error", message: "添加失败，请稍后再试"})
            }
            this.customerOrder = {}
            this.addOrderVisible = false
            this.queryCustomer()
          })
        } else {
          ElMessage({
            type: "info",
            message: "暂无该用户数据，也可能该用户不存在!"
          })
        }
      })
    },
    //查询所有订单
    queryOrderList(msg) {
      // console.log(msg)
      // console.log(msg.orderId)
      this.orderDetails.orderId = msg.orderId
      this.orderDetailsQuery.orderId = msg.orderId
      this.updateId = msg.orderId
      this.$api.CustomerInformation.queryCustomerOrderByParams("/orderDetail/lists", this.orderDetailsQuery).then(res => {
        // console.log("/customerOrder/orderDetail:---------------->",res)
        this.orderDetailsList = res.result.data
      })
    },
    //订单翻页
    handleCurrentChange2(msg) {
      this.orderDetailsQuery.page = msg
      this.$api.CustomerInformation.queryCustomerOrderByParams("/orderDetail/lists", this.orderDetailsQuery).then(res => {
        // console.log("/customerOrder/orderDetail:---------------->",res)
        this.orderDetailsList = res.result.data
      })
    },
    //翻页
    handleCurrentChange(page) {
      this.customerQuery.page = page
      this.$api.CustomerServer.queryCustomerServeOrder("/customerServeOrder/lists", this.customerQuery).then(res => {
        this.customerList = res.result.data
      })
    },
    //初始数据渲染
    queryCustomer() {
      this.$api.CustomerServer.queryCustomerServeOrder("/customerServeOrder/lists").then(res => {
        // console.log("123456789", res)
        this.customerList = res.result.data
        this.total = res.result.count
      })
    },
    addOrderDetails() {
      this.orderDetails.orderId = this.orderDetailsQuery.orderId
      // console.log(this.addOrUpdateResult)
      if (this.addOrUpdateResult) {

        console.log("更新")
        this.orderDetails.sum = this.orderDetails.price * this.orderDetails.goodsNum
        this.$api.CustomerServer.updateOrderDetails("/orderDetail/updateOrderDetails", this.orderDetails).then(res => {
          // console.log(res)
          if (res.code === 200) {
            ElMessage({type: "success", message: "更新成功"})
            this.orderDetails = {}
            this.orderDetailsVisible = false
            this.commonMethod()
          } else {
            ElMessage({type: "error", message: "更新失败，请重试"})
          }
        })
      } else {

        console.log("添加")
        if (this.orderDetails.goodsName == "" || this.orderDetails.unit == "" || this.orderDetails.goodsNum === 0 || this.orderDetails.price === 0) {
          ElMessage({type: "info", message: "信息没有输入完全"})
        } else {
          this.orderDetails.sum = this.orderDetails.price * this.orderDetails.goodsNum
          // console.log(this.orderDetails)
          this.$api.CustomerServer.addOrderDetails("/orderDetail/addOrderInfo", this.orderDetails).then(res => {
            // console.log(res)
            this.orderDetails = {}
            this.orderDetailsVisible = false
            this.commonMethod()
            if (res.code === 200) {
              ElMessage({type: "success", message: "添加子项成功"})
            } else {
              ElMessage({type: "error", message: "添加失败，请稍后再试"})
            }
          })
        }
      }
    },
    //删除事件
    confirmEvent(msg) {
      ElMessageBox.confirm(
          '确认要删除此条记录吗？',
          'Warning',
          {
            confirmButtonText: "是的",
            cancelButtonText: "不,我再想想",
          }
      ).then(() => {
        // console.log(msg)
        let id = msg.id
        this.$api.CustomerServer.deleteOrderDetailsInfo("/orderDetail/deleteOrderDetails", id).then(res => {
          // console.log(res)
          if (res.code === 200) {
            ElMessage({type: "success", message: "删除成功"})
            this.commonMethod()
          } else {
            ElMessage({type: "error", message: "删除失败，请稍后重试!"})
          }
        })
      }).catch(() => {
        ElMessage({
          message: "取消操作",
          type: "info"
        })
      })
    },
    commonMethod() {
      this.$api.CustomerInformation.queryCustomerOrderByParams("/orderDetail/lists", this.orderDetailsQuery).then(res => {
        // console.log("/customerOrder/orderDetail:---------------->",res)
        this.orderDetailsList = res.result.data
      })
    },
    //更改支付状态
    setState() {
      // console.log(this.updateId)
      // eslint-disable-next-line no-unused-vars
      let id = this.updateId
      this.$api.CustomerInformation.setStateIsPay("/customerOrder/setStateIsPay", {id}).then(res => {
        // console.log(res)
        if (res.code === 200) {
          ElMessage({type: "success", message: "修改状态成功"})
          this.CustomerOrderVisible = false
          this.queryCustomer()
        } else {
          ElMessage({type: "error", message: "修改失败，请重试"})
        }
      })
    },
    updateOrderInfo() {
      this.updateCustomerInfo.id = this.updateCustomerInfo.orderId
      this.$api.CustomerInformation.updateOrderInfo("/customerOrder/updateInfo", this.updateCustomerInfo).then(res => {
        // console.log(res)
        if (res.code === 200) {
          ElMessage({type: "success", message: "修改成功"})
          this.updateCustomerVisible = false
          this.queryCustomer()
        } else {
          ElMessage({type: "error", message: "修改失败，请重试"})
        }
      })
    }
  },
  mounted() {
    this.queryCustomer()
  }
}
</script>

<style scoped>
.tableMenu {
  width: 95%;
  margin: -1.5% 0 0 3%;
  position: relative;
}

.page {
  position: absolute;
  top: 90%;
  width: 100%;
  color: #ffffff;
}
</style>