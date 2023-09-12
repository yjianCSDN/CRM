<template>
  <div class="CustomerInformationManagement">
    <div class="search">
      <el-input
          class="w-50 m-2"
          v-model="customerQuery.customerName"
          placeholder="客户名"
          style="position: relative;width: 12%;margin: 1% 0 0 0"
      />
      <el-input
          class="w-50 m-2"
          v-model="customerQuery.customerNo"
          placeholder="客户编号"
          style="position: relative;width: 12%;margin: 1% 0 0 10px"
      />
      <el-select  class="m-2" placeholder="请选择" style="width: 150px;margin: 1% 0 0 5px" v-model="customerQuery.level">
        <el-option label="无"    value="" />
        <el-option label="战略合作伙伴"    value="战略合作伙伴" />
        <el-option label="大 客 户"    value="大客户" />
        <el-option label="重点开发客户" value="重点开发客户" />
      </el-select>
      &nbsp;&nbsp;
      <el-button type="primary"  style="margin: 1% 0 0 10px" @click="selectCustomer" v-if="selectVisible">搜  &nbsp;&nbsp;&nbsp; 索</el-button>
      <el-button type="primary"  style="margin: 1% 0 0 10px" @click="addCustomerVisible=true,this.addCustomerInfo={},this.vv=false" v-if="addVisible"
      >添  &nbsp;&nbsp;&nbsp; 加</el-button>
      <el-button size="small" type="success" style="float: right;margin: 2% 0 0 0" @click="handleDownload">导出EXCEL表格</el-button>
    </div>
    <div>
      <el-table :data="customerList"  class="tableMenu"
                max-height="510"  :default-sort="{ prop: 'createDate', order: 'descending' }"
                :header-cell-style="{ backgroundColor: '#eef5ff',   textAlign: 'center',  }"
      >
        <el-table-column fixed="left" sortable prop="id" label="编号" width="100" align="center"/>
        <el-table-column prop="name" label="客户名称" width="170" header-align="center"  align="center"/>
        <el-table-column prop="fr" label="法人" width="100" header-align="center"  align="center"/>
        <el-table-column prop="khno" label="客户编号" width="200" header-align="center"  align="center"/>
        <el-table-column prop="area" label="地区" width="70" header-align="center"  align="center"/>
        <el-table-column prop="cusManager" label="客户经理" width="100" header-align="center"  align="center"/>
        <el-table-column prop="myd" label="满意度" width="150" header-align="center"  align="center">
          <template #default="scope">
            <el-rate v-model="scope.row.value2" :colors="colors" disabled />
          </template>
        </el-table-column>
        <el-table-column prop="level" label="客户级别" width="150" header-align="center"  align="center"/>
        <el-table-column prop="xyd" label="信用度" width="150" header-align="center"  align="center">
          <template #default="scope">
            <el-rate v-model="scope.row.value1" :colors="colors" disabled />
          </template>
        </el-table-column>
        <el-table-column prop="address" label="详细地址" width="300" header-align="center"  align="center"/>
        <el-table-column prop="postCode" label="邮编" width="100" header-align="center"  align="center"/>
        <el-table-column prop="phone" label="电话" width="200" header-align="center"  align="center"/>
        <el-table-column prop="webSite" label="网站" width="200" header-align="center"  align="center"/>
        <el-table-column prop="fax" label="传真" width="200" header-align="center"  align="center"/>
        <el-table-column prop="zczj" label="注册资金" width="120" header-align="center"  align="center"/>
        <el-table-column prop="yyzzzch" label="营业执照" width="300" header-align="center"  align="center"/>
        <el-table-column prop="khyh" label="开户行" width="100" header-align="center"  align="center"/>
        <el-table-column prop="khzh" label="开户账号" width="200" header-align="center"  align="center"/>
        <el-table-column prop="gsdjh" label="国税" width="150" header-align="center"  align="center"/>
        <el-table-column prop="dsdjh" label="地税" width="150" header-align="center"  align="center"/>
        <el-table-column prop="createDate" sortable label="创建时间" width="200" header-align="center"  align="center"/>
        <el-table-column prop="updateDate" label="更新时间" width="200" header-align="center"  align="center"/>
        <el-table-column fixed="right" label="操 作" width="200" header-align="center"  align="center">
          <template #default="scope">
            <el-button link size="small" type="primary" @click="addCustomerVisible=true,
                      addCustomerInfo=JSON.parse(JSON.stringify(scope.row)),vv=true,oldPhone=JSON.parse(JSON.stringify(scope.row.phone))"
                       v-if="updateVisible"
            >编 辑</el-button
            >
            <el-button link size="small" type="primary" @click="deleteCustomerById(scope.row)" v-if="delVisible">删 除</el-button>
            <el-button link size="small" type="primary"  style="left: 10px" @click="CustomerOrderVisible=true,addCustomerInfo=JSON.parse(JSON.stringify(scope.row)),
                        queryOrderList(scope.row)" v-if="selectVisible">订单查看</el-button>
          </template>
        </el-table-column>
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
    <!--添加客户-->
    <el-dialog
        v-model="addCustomerVisible"
        title="客户信息编辑"
        width="45%"
    >
      <el-form
          label-position="left"
          :inline="true"
          label-width="80px"
          :model="addCustomerInfo"
          :rules="formRules"
          :ref="addCustomerInfo"
      >
        <el-form-item label="客户名称" prop="name">
          <el-input v-model="addCustomerInfo.name" placeholder="请输入客户名称(必填)"/>
        </el-form-item>
        <el-form-item label="法人">
          <el-input v-model="addCustomerInfo.fr" placeholder="请输入法人"/>
        </el-form-item>
        <el-form-item label="区域">
          <el-input v-model="addCustomerInfo.area" placeholder="请输入区域"/>
        </el-form-item>
        <el-form-item label="客户经理">
          <el-input v-model="addCustomerInfo.cusManager" placeholder="请输入客户经理"/>
        </el-form-item>
        <el-form-item label="客户级别" prop="level">
          <el-select  class="m-2" placeholder="请选择" v-model="addCustomerInfo.level" style="width: 195px">
            <el-option label="战略合作伙伴"    value="战略合作伙伴" />
            <el-option label="大 客 户"    value="大客户" />
            <el-option label="重点开发客户" value="重点开发客户" />
          </el-select>
        </el-form-item>
        <el-form-item label="信用度">
          <el-rate v-model="addCustomerInfo.value1" :colors="colors"  />
<!--          <el-select  class="m-2" placeholder="请选择" v-model="addCustomerInfo.xyd" style="width: 195px">-->
<!--            <el-option label="☆"    value="☆" />-->
<!--            <el-option label="☆☆"    value="☆☆" />-->
<!--            <el-option label="☆☆☆"    value="☆☆☆" />-->
<!--            <el-option label="☆☆☆☆"    value="☆☆☆☆" />-->
<!--            <el-option label="☆☆☆☆☆"    value="☆☆☆☆☆" />-->
<!--          </el-select>-->
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="满意度">
              <el-rate v-model="addCustomerInfo.value2" :colors="colors"  />
              <!--          <el-select  class="m-2" placeholder="请选择" v-model="addCustomerInfo.myd" style="width: 195px">-->
              <!--            <el-option label="☆"    value="☆" />-->
              <!--            <el-option label="☆☆"    value="☆☆" />-->
              <!--            <el-option label="☆☆☆"    value="☆☆☆" />-->
              <!--            <el-option label="☆☆☆☆"    value="☆☆☆☆" />-->
              <!--            <el-option label="☆☆☆☆☆"    value="☆☆☆☆☆" />-->
              <!--          </el-select>-->
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮编">
              <el-input v-model="addCustomerInfo.postCode"  placeholder="请输入邮编"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="addCustomerInfo.phone" placeholder="请输入联系电话(必填)"/>
        </el-form-item>
        <el-form-item label="客户地址">
          <el-input v-model="addCustomerInfo.address" placeholder="请输入客户地址"/>
        </el-form-item>
        <el-form-item label="传真">
          <el-input v-model="addCustomerInfo.fax" placeholder="请输入传真"/>
        </el-form-item>
        <el-form-item label="营业执照">
          <el-input v-model="addCustomerInfo.yyzzzch" placeholder="请输入营业执照编号"/>
        </el-form-item>
        <el-form-item label="网站">
          <el-input v-model="addCustomerInfo.webSite" placeholder="请输入网站"/>
        </el-form-item>
        <el-form-item label="注册资金">
          <el-input v-model="addCustomerInfo.zczj" placeholder="请输入注册资金"/>
        </el-form-item>
        <el-form-item label="开户行">
          <el-input v-model="addCustomerInfo.khyh" placeholder="请输入开户行"/>
        </el-form-item>
        <el-form-item label="开户账号">
          <el-input v-model="addCustomerInfo.khzh" placeholder="请输入开户账号"/>
        </el-form-item>
        <el-form-item label="国税">
          <el-input v-model="addCustomerInfo.gsdjh" placeholder="请输入国税"/>
        </el-form-item>
        <el-form-item label="地税">
          <el-input v-model="addCustomerInfo.dsdjh" placeholder="请输入地税"/>
        </el-form-item>
        <el-form-item label="年营业额">
          <el-input v-model="addCustomerInfo.nyye" placeholder="请输入年营业额"/>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="addCustomerVisible = false,this.addCustomerInfo={},this.vv=false,oldPhone=''">取 消</el-button>
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
        width="45%"
    >
      <el-form
          label-position="left"
          :inline="true"
          label-width="80px"
          :model="addCustomerInfo"
      >
        <el-form-item label="客户名称">
          <el-input v-model="addCustomerInfo.name" placeholder="（空）"/>
        </el-form-item>
        <el-form-item label="法人">
          <el-input v-model="addCustomerInfo.fr" placeholder="（空）"/>
        </el-form-item>
        <el-form-item label="客户地址">
          <el-input v-model="addCustomerInfo.address" placeholder="（空）"/>
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="addCustomerInfo.phone" placeholder="（空）"/>
        </el-form-item>
      </el-form>
      <el-table :data="customerOrderLists" max-height="350" :default-sort="{ prop: 'createDate', order: 'descending' }"
                :header-cell-style="{ backgroundColor: '#eef5ff',   textAlign: 'center',  }" >
        <el-table-column prop ="id" label="编号" width="100" fixed="left" align="center"/>
        <el-table-column prop ="orderNo" label="订单编号" width="170" header-align="center"  align="center"/>
        <el-table-column prop ="orderDate" label="下单日期" width="200" header-align="center"  align="center"/>
        <el-table-column prop ="address" label="收货地址" width="200" header-align="center"  align="center"/>
        <el-table-column prop ="state" label="支付状态" width="100" header-align="center"  align="center">
          <template #default="scope">
            <el-tag v-if="scope.row.state===1" class="ml-2" type="success" >已支付</el-tag>
            <el-tag v-else-if="scope.row.state===0" type="danger">未支付</el-tag>
<!--            <span v-if="scope.row.state===1" style="color:green">已支付</span>-->
<!--            <span v-else-if="scope.row.state===0" style="color: red">未支付</span>-->
          </template>
        </el-table-column>
        <el-table-column prop ="createDate" label="创建时间" width="200" header-align="center"  align="center"/>
        <el-table-column prop ="updateDate" label="更新时间" width="200" header-align="center"  align="center"/>
        <el-table-column fixed="right" label="操 作" width="80" header-align="center" align="center">
          <template #default="scope">
            <el-button link size="small" type="primary" v-show="scope.row.state===1" @click="orderDetails(scope.row)">订单详情</el-button>
          </template>
        </el-table-column>
        <template v-slot:empty>
          <p>该客户暂无订单</p>
        </template>
      </el-table>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="CustomerOrderVisible = false,this.addCustomerInfo={},this.vv=false">取 消</el-button>
        <el-button type="primary" @click="CustomerOrderVisible = false">
          确认
        </el-button>
      </span>
      </template>
    </el-dialog>

    <!--订单详情-->
    <el-dialog
        v-model="OrderDetailsVisible"
        title="客户管理-订单信息详细展示"
        width="65%"
    >
      <el-form
          label-position="left"
          :inline="true"
          label-width="80px"
          :model="orderDetailsList"
      >
        <el-form-item label="订单编号">
          <el-input v-model="orderDetailsList.order_no" disabled placeholder="(空)"/>
        </el-form-item>
        <el-form-item label="总金额(￥)">
          <el-input v-model="orderDetailsList.total"  disabled placeholder="(空)"/>
        </el-form-item>
        <el-form-item label="物流地址">
          <el-input v-model="orderDetailsList.address" disabled placeholder="(空)"/>
        </el-form-item>
        <el-form-item label="支付状态">
          <el-tag v-if="orderDetailsList.status==='已支付'" class="ml-2" type="success" >已支付</el-tag>
          <el-tag v-else-if="orderDetailsList.status==='未支付'" type="danger">未支付</el-tag>
<!--            <span v-if="orderDetailsList.status==='已支付'" style="color:green">已支付</span>-->
<!--            <span v-else-if="orderDetailsList.status==='未支付'" style="color: red">未支付</span>-->
        </el-form-item>
      </el-form>
      <el-table :data="goodList" max-height="520" :default-sort="{ prop: 'createDate', order: 'descending' }"
                :header-cell-style="{ backgroundColor: '#eef5ff',   textAlign: 'center',  }" >
        <el-table-column prop ="id" label="编号" width="100" fixed="left" align="center"/>
        <el-table-column prop ="goodsName" label="物品名称" width="170" header-align="center"  align="center"/>
        <el-table-column prop ="goodsNum" label="物品数量" width="100" header-align="center"  align="center"/>
        <el-table-column prop ="unit" label="单位" width="100" header-align="center"  align="center"/>
        <el-table-column prop ="price" label="单价(￥)" width="100" header-align="center"  align="center"/>
        <el-table-column prop ="sum" label="总价格(￥)" width="100" header-align="center"  align="center"/>
        <el-table-column prop ="updateDate" label="更新时间" width="200" header-align="center"  align="center"/>
      </el-table>
      <template v-slot:empty>
        <p>该客户暂无订单</p>
      </template>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="OrderDetailsVisible=false,this.vv=false">取 消</el-button>
        <el-button type="primary" @click="OrderDetailsVisible=false">
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
// eslint-disable-next-line no-unused-vars
import { saveJsonToExcel } from '../tools/utils.js'
export default {
  name: "CustomerInformationManagement",
  components:{
  },
  data(){
    let total = ref("")
    let addCustomerInfo = reactive({})
    let customerList = reactive([])
    let customerOrderLists = reactive([])
    let orderDetailsList = reactive({})
    let goodList = reactive([])
    let addCustomerVisible = ref(false)
    let vv = ref(false)//用来判断用户更新还是添加
    let CustomerOrderVisible = ref(false)//客户订单展示
    let OrderDetailsVisible = ref(false)//订单详情
    let customerQuery = reactive({page:1,limit:10,customerName:"",customerNo:"",level:"",time:"",type:""})
    let customerOrderQuery = reactive({page:1,limit:10,cusId:""})
    let orderDetailsQuery = reactive({page:1,limit:10,orderId:""})
    let list = reactive([])
    let phoneList = reactive([])
    let oldPhone = ref("")
    const colors = ref(['#99A9BF', '#F7BA2A', '#FF9900'])

    //增删改查许可
    let selectVisible = ref(false)
    let addVisible = ref(false)
    let updateVisible = ref(false)
    let delVisible = ref(false)
    return{
      customerList,customerQuery,total,addCustomerVisible,addCustomerInfo,phoneList,
      vv,CustomerOrderVisible,customerOrderLists,customerOrderQuery,OrderDetailsVisible,oldPhone,
      orderDetailsList,orderDetailsQuery,goodList,list,selectVisible,addVisible,updateVisible,delVisible,colors,
      formRules:{name:[{required:true,message:"请输入客户名称",trigger:'blur'}],
                phone:[{required:true,message:"请输入联系电话",trigger:'blur'}],
                level:[{required:true,message:"请选择用户级别",trigger:'blur'}]},
    }
  },
  methods:{
    //导出excel表格
    handleDownload(){
      let customerList = []
      let customerQuery = reactive({page:1,limit:1000,customerName:this.customerQuery.customerName,customerNo:this.customerQuery.customerNo,level:this.customerQuery.level,time:this.customerQuery.time,type:this.customerQuery.type})
      this.$api.CustomerInformation.queryCustomerByParams("/customer/lists",customerQuery).then(res=>{
        customerList = res.result.data
        // console.log("customerList:",customerList)
        let json_fields = []
        for (let i = 0; i < customerList.length; i++) {
          json_fields.push({
            "编号":customerList[i].id,
            "客户名称":customerList[i].name,
            "法人":customerList[i].fr,
            "客户编号":customerList[i].khno,
            "地区":customerList[i].area,
            "客户经理":customerList[i].cusManager,
            "满意度":customerList[i].myd,
            "客户级别":customerList[i].level,
            "信用度":customerList[i].xyd,
            "详细地址":customerList[i].address,
            "邮编":customerList[i].postCode,
            "联系电话":customerList[i].phone,
            "网站":customerList[i].webSite,
            "传真":customerList[i].fax,
            "注册资金":customerList[i].zczj,
            "营业执照注册号":customerList[i].yyzzzch,
            "开户行":customerList[i].khyh,
            "开户账号":customerList[i].khzh,
            "国税":customerList[i].gsdjh,
            "地税":customerList[i].dsdjh,
            "创建时间":customerList[i].createDate,
            "更新时间":customerList[i].updateDate,
          })
          // console.log(customerList[i])
        }
        saveJsonToExcel(json_fields, '客户信息.xlsx')
      })

    },
    selectCustomer(){
      console.log(this.customerQuery)
      this.$api.CustomerInformation.queryCustomerByParams("/customer/lists",this.customerQuery).then(res=>{
        console.log(res)
        if (res.code===200){
          ElMessage({
            type:"success",
            message:"查询成功!"
          })
          this.customerList = res.result.data
          this.total = res.result.count
        }else {
          ElMessage({
            type:"error",
            message:"查询失败，请重试!"
          })
        }
      })
    },
    handleCurrentChange(page){
      this.customerQuery.page = page
      this.$api.CustomerInformation.queryCustomerByParams("/customer/lists",this.customerQuery).then(res=>{
        this.customerList = res.result.data
        this.customerList.forEach(item=>{
          if (item.xyd==='☆'){
            item.value1=1
          }else if (item.xyd==='☆☆'){
            item.value1=2
          }else if (item.xyd==='☆☆☆'){
            item.value1=3
          }else if (item.xyd==='☆☆☆☆'){
            item.value1=4
          }else if (item.xyd==='☆☆☆☆☆'){
            item.value1=5
          }

          if (item.myd==='☆'){
            item.value2=1
          }else if (item.myd==='☆☆'){
            item.value2=2
          }else if (item.myd==='☆☆☆'){
            item.value2=3
          }else if (item.myd==='☆☆☆☆'){
            item.value2=4
          }else if (item.myd==='☆☆☆☆☆'){
            item.value2=5
          }
          // console.log("customerList:",this.customerList)
        })
      })
    },
    queryCustomer(){
      this.$api.CustomerInformation.queryCustomerByParams("/customer/lists").then(res=>{
        this.customerList = res.result.data
        this.total = res.result.count
        this.customerList.forEach(item=>{
          if (item.xyd==='☆'){
            item.value1=1
          }else if (item.xyd==='☆☆'){
            item.value1=2
          }else if (item.xyd==='☆☆☆'){
            item.value1=3
          }else if (item.xyd==='☆☆☆☆'){
            item.value1=4
          }else if (item.xyd==='☆☆☆☆☆'){
            item.value1=5
          }

          if (item.myd==='☆'){
            item.value2=1
          }else if (item.myd==='☆☆'){
            item.value2=2
          }else if (item.myd==='☆☆☆'){
            item.value2=3
          }else if (item.myd==='☆☆☆☆'){
            item.value2=4
          }else if (item.myd==='☆☆☆☆☆'){
            item.value2=5
          }
          // console.log("customerList:",this.customerList)
        })
      })
      this.$api.CustomerInformation.getCusPhones("/customer/phone").then(res=>{
        for (let i = 0; i < res.result.length; i++) {
          if (res.result[i]!==null){
            this.phoneList.push(res.result[i])
          }
        }
        console.log("phoneList",this.phoneList)
      })
    },
    addInfo(){
      let result = true
      if (this.addCustomerInfo.value2===1){
        this.addCustomerInfo.myd = '☆'
      }else if (this.addCustomerInfo.value2===2){
        this.addCustomerInfo.myd = '☆☆'
      }else if (this.addCustomerInfo.value2===3){
        this.addCustomerInfo.myd = '☆☆☆'
      }else if (this.addCustomerInfo.value2===4){
        this.addCustomerInfo.myd = '☆☆☆☆'
      }else if (this.addCustomerInfo.value2===5){
        this.addCustomerInfo.myd = '☆☆☆☆☆'
      }

      if (this.addCustomerInfo.value1===1){
        this.addCustomerInfo.xyd = '☆'
      }else if (this.addCustomerInfo.value1===2){
        this.addCustomerInfo.xyd = '☆☆'
      }else if (this.addCustomerInfo.value1===3){
        this.addCustomerInfo.xyd = '☆☆☆'
      }else if (this.addCustomerInfo.value1===4){
        this.addCustomerInfo.xyd = '☆☆☆☆'
      }else if (this.addCustomerInfo.value1===5){
        this.addCustomerInfo.xyd = '☆☆☆☆☆'
      }
      // console.log(this.addCustomerInfo)
      if (this.addCustomerInfo.name===undefined||this.addCustomerInfo.phone===undefined||this.addCustomerInfo.level===undefined){
        ElMessage({type:"warning",message:"必填项未填写"})
      }else if (!(/^1[34578]\d{9}$/.test(this.addCustomerInfo.phone))){
        ElMessage({type:"warning",message:"联系电话格式错误"})
      }else {
        // console.log("addCustomerInfo:",this.addCustomerInfo)
        // console.log(this.vv)
        let customer = this.addCustomerInfo
        for (let i = 0; i < this.phoneList.length; i++) {
          if (this.addCustomerInfo.phone===this.phoneList[i]){
            if (this.addCustomerInfo.phone===this.oldPhone){
              console.log("有相同的")
              console.log("oldPhone",this.oldPhone)
              // result = true
            }else {
              console.log("没有")
              result=false
            }
          }
        }
        if (result){
          console.log("this.vv",this.vv)
          if (this.vv){
              this.$api.CustomerInformation.updateCustomer("/customer/updateCustomer",customer).then(res=>{
                console.log(res)
                if (res.code===200){
                  ElMessage({
                    type:"success",
                    message:"更新成功!"
                  })
                  this.addCustomerVisible = false
                  this.queryCustomer()
                  this.addCustomerInfo = {}
                  this.customerQuery.page=1
                  this.oldPhone=''
                }else if (res.msg==='用户名重复'){
                  ElMessage({
                    type:"warning",
                    message:"用户名重复!"
                  })
                }else {
                  ElMessage({
                    type:"error",
                    message:"更新失败，请重试!"
                  })
                }
              })
          }else {
            this.$api.CustomerInformation.addCustomer("/customer/addCustomer",customer).then(res=>{
              console.log(res)
              if (res.code===200){
                ElMessage({
                  type:"success",
                  message:"添加成功!"
                })
                this.addCustomerVisible = false
                this.queryCustomer()
                this.addCustomerInfo = {}
                this.customerQuery.page=1
              }else if (res.code===300){
                ElMessage({
                  type:"error",
                  message:"客户名称已存在，请重试!"
                })
              }else {
                ElMessage({
                  type:"error",
                  message:"添加失败，请重试!"
                })
              }
            })
          }
          this.vv = false
        }else {
          ElMessage({type:"warning",message:"联系电话与其他客户重复"})
        }
      }


    },
    deleteCustomerById(msg){
      ElMessageBox.confirm(
        "是否删除",
        "info",
          {
            confirmButtonText:"是的",
            cancelButtonText:"不,我再想想",
          }
      ).then(()=>{
        console.log("msg:",msg)
        let id = msg.id
        this.$api.CustomerInformation.deleteCustomer("/customer/deleteCustomer",id).then(res=>{
          console.log(res)
          if (res.code===200){
            ElMessage({
              type:"success",
              message:"删除成功!"
            })
          }else {
            ElMessage({
              type:"error",
              message:"删除失败，请重试!"
            })
          }
          this.queryCustomer()
          this.customerQuery.page=1
        }).catch(()=>{
          ElMessage({
            message:"取消操作",
            type:"info"
          })
        })
      })
    },
    queryOrderList(msg){
      this.customerOrderQuery.cusId = msg.id
      // console.log(msg.id)
      this.$api.CustomerInformation.queryCustomerOrderByParams("/customerOrder/lists",this.customerOrderQuery).then(res=>{
        // console.log("res为:---->",res.result.data)
        this.customerOrderLists = res.result.data
      })
    },
    orderDetails(msg){
      this.OrderDetailsVisible=true
      // console.log("msg----->",msg)
      let orderId = msg.id
      this.orderDetailsQuery.orderId = msg.id
      this.$api.CustomerInformation.scanOrderDetails("/customerOrder/orderDetail", {orderId}).then(res=>{
        console.log(res)
        this.orderDetailsList=res.result
      })
      this.$api.CustomerInformation.queryOrderDetailsByParams("/orderDetail/lists",this.orderDetailsQuery).then(res=>{
          this.goodList = res.result.data
      })
    },
  },
  mounted() {
    this.queryCustomer()
    this.list=this.$store.getters.getPermissionList
    if (JSON.stringify(this.list).includes("201001")){
      this.addVisible=true
    }
    if (JSON.stringify(this.list).includes("201002")){
      this.updateVisible=true
    }
    if (JSON.stringify(this.list).includes("201003")){
      this.delVisible=true
    }
    if (JSON.stringify(this.list).includes("201004")){
      this.selectVisible=true
    }
  }
}
</script>

<style scoped>
.CustomerInformationManagement{
  height: 100%;
  width: 100%;
}
.search{
  width: 98%;
  height: 50px;
  margin: 0 0 0 1%;
}
.tableMenu{
  width: 98%;
  margin: 0 0 0 1%;
  position: relative;
}
.page{
  position: absolute;
  top: 82%;
  width: 60%;
  color: #ffffff;
}
</style>