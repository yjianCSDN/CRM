<template>
<div class="SideBar">
  <el-aside  :width="'100%'">
    <el-menu
      default-active="$router.path"
      router="router"
      class="el-menu-vertical-demo"
      active-text-color="black"
      :collapse="collapse"
      unique-opened="true"
  >
    <el-sub-menu index="1" v-if="CustomerManagementVisible">
      <template #title>
        <el-image style="width: 45px; height: 45px;top: -8px;margin: 0 10px 0 0" :src="require('@/assets/菜单栏-客户-客户联系人.png')"></el-image>
        <span>客 户 管 理</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="/index/CustomerInformation" v-show="CustomerInfoVisible">
          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0" :src="require('@/assets/客户信息.png')"/>
          客户信息管理
        </el-menu-item>
        <el-menu-item index="/index/CustomerLoss" v-show="CustomerLossVisible">
          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0" :src="require('@/assets/防流失预警.png')"/>
          客户流失管理
        </el-menu-item>
        <el-menu-item index="/index/CustomerOrders" v-show="CustomerOrderVisible">
          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/客户订单.png')"/>
          客户订单管理
        </el-menu-item>
      </el-menu-item-group>
    </el-sub-menu>
    <el-sub-menu index="2" v-if="UserManagementVisible">
      <template #title>
        <el-image style="width: 45px; height: 45px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/用户管理.png')"/>
        <span>个人中心</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="/index/PersonalInfoManagement" v-show="updatePersonalInfoVisible">
          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/257用户资料.png')"/>
          个人信息管理
        </el-menu-item>
      </el-menu-item-group>
    </el-sub-menu>
    <el-sub-menu index="3" v-if="saleChance" >
      <template #title>
        <el-image style="width: 45px; height: 45px;top: -8px;margin: 0 10px 0 0" :src="require('@/assets/营销.png')"/>
        <span>营 销 管 理</span>
      </template>
      <el-menu-item-group>
        <el-menu-item  index="/index/SaleChance" v-show="select">
          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/业务机会.png')"/>
          营销机会管理
        </el-menu-item>
        <el-menu-item  index="/index/CusDevPlan" v-show="CusDevPlan">
          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/开发者.png')"/>
          客户开发计划
        </el-menu-item>
      </el-menu-item-group>
    </el-sub-menu>
    <el-sub-menu index="4" v-if="ServerVisible">
      <template #title>
        <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0" :src="require('@/assets/基础服务管理.png')"/>
        <span>服 务 管 理</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="/index/ServiceCreation" v-show="ServerCreationVisible">
          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/创建服务.png')"/>
          服务创建
        </el-menu-item>
        <el-menu-item index="/index/ServiceAllocation" v-show="ServerAllocationVisible">
          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/员工分配.png')"/>
          服务分配
        </el-menu-item>
        <el-menu-item index="/index/ServiceProcessing" v-show="ServerProcessVisible">
          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/处理.png')"/>
          服务处理
        </el-menu-item>
        <el-menu-item index="/index/ServiceFeedback" v-show="ServerFeedBackVisible">
          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/用户反馈.png')"/>
          服务反馈
        </el-menu-item>
        <el-menu-item index="/index/ServiceArchiving" v-show="ServerArchivingVisible">
          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/归档.png')"/>
          服务归档
        </el-menu-item>
      </el-menu-item-group>
    </el-sub-menu>

    <el-sub-menu index="5" v-if="SystemVisible">
      <template #title>
        <el-image style="width: 45px; height: 45px;top: -8px;margin: 0 10px 0 0" :src="require('@/assets/系统管理.png')"/>
        <span>系 统 管 理</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="/index/DictionaryManagement">
          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/字典管理.png')"/>
          字 典 管 理
        </el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group>
        <el-menu-item index="/index/UserManagement" v-show="UserVisible">
          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/用户管理2.png')"/>
          用 户 管 理
        </el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group>
        <el-menu-item index="/index/RoleManagement" v-show="RoleManagementVisible">
          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/管理员_角色管理.png')"/>
          角 色 管 理
        </el-menu-item>
      </el-menu-item-group>
    </el-sub-menu>
    <el-sub-menu index="6" v-if="CountVisible">
      <template #title>
        <el-image style="width: 45px; height: 45px;top: -8px;margin: 0 10px 0 0" :src="require('@/assets/统计报表.png')"/>
        <span>统 计 报 表</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="/index/CustomerContribution" v-show="CustomerContributionVisible">
          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/贡献分析.png')"/>
          客户贡献分析
        </el-menu-item>
        <el-menu-item index="/index/CustomerComposition" v-show="CompositionVisible" >
          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/客户构成.png')"/>
          客户构成分析
        </el-menu-item>
        <el-menu-item index="/index/CustomerService" v-show="CustomerServerVisible">
          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/客户服务分析.png')"/>
          客户服务分析
        </el-menu-item>
        <el-menu-item index="/index/CustomerChurn" v-show="LossVisibleImage" >
          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/流失客户.png')"/>
          客户流失分析
        </el-menu-item>
      </el-menu-item-group>
    </el-sub-menu>
  </el-menu>
  </el-aside>
</div>
</template>

<script>

import {toRaw} from "@vue/reactivity";
import Cookies from 'js-cookie'

export default {
  name: "SideBar",
  data(){
    return{
      isCollapse:true,
      list:[],
      saleChance:false,
      select:false,
      CusDevPlan:false,
      UserManagement:false,
      RoleManagement:false,
      CustomerManagementVisible:false,//客户管理
      CustomerInfoVisible:false, //客户信息管理
      CustomerLossVisible:false,//客户流失管理
      CustomerOrderVisible:false,//客户订单管理
      UserManagementVisible:false,  //个人中心
      updatePersonalInfoVisible:false,  //个人信息修改
      // SaleChanceManagementVisible: false,  //营销管理
      ServerVisible:false,  //服务管理
      ServerCreationVisible:false,  //服务创建
      ServerAllocationVisible:false,  //服务分配
      ServerProcessVisible:false, //服务处理
      ServerFeedBackVisible:false,  //服务反馈
      ServerArchivingVisible:false, //服务归档
      SystemVisible:false,  //系统管理
      UserVisible:false,  //用户管理
      RoleManagementVisible:false,  //角色管理
      CountVisible:false, //统计报表
      CustomerContributionVisible:false,  //客户贡献分析
      CompositionVisible:false, //客户构成分析
      CustomerServerVisible:false,  //客户服务分析
      LossVisibleImage:false, //客户流失分析
      collapse:false
    }
  },
  mounted() {
    console.log(Cookies.get("userIdStr"))
    this.$api.Permission.queryUserHasRoleHasPermissionByUserId("/permission/lists", {userIdStr:Cookies.get("userIdStr")   }).then(res=>{
      // console.log(res.result)
      if (res.code===200){
        this.list = toRaw(res.result)
        // for (let i = 0; i < res.result.length; i++) {
        //   // console.log(res.result[i])
        //   this.list.push(res.result[i])
        // }
        console.log("this.List:--->",this.list)
        // console.log(toRaw(this.list).includes("10"))//true
        this.$store.commit("setPermissionList",res.result)
      }

      this.saleChance = false
      if (JSON.stringify(toRaw(this.list)).includes("1000")){
        this.saleChance = true
        // console.log(JSON.stringify(this.list).includes("10"))
        // console.log(JSON.stringify(this.list).includes(10))
      }
      if (JSON.stringify(toRaw(this.list)).includes("1010")){
        this.select = true
        console.log("result:----->",JSON.stringify(toRaw(this.list)).includes("1010"))
      }
      if (JSON.stringify(toRaw(this.list)).includes("1020")){
        this.CusDevPlan = true
      }
      if (JSON.stringify(toRaw(this.list)).includes("6010")){
        this.UserManagement = true
      }
      if (JSON.stringify(toRaw(this.list)).includes("6020")){
        this.RoleManagement = true
      }

      this.CustomerManagementVisible=false
      if (JSON.stringify(this.list).includes("2000") ){
        this.CustomerManagementVisible=true
        console.log(this.CustomerManagementVisible)
        // console.log(JSON.stringify(this.list).includes(20))
        // console.log(JSON.stringify(this.list).includes("20"))
      }
      if (JSON.stringify(this.list).includes("2010")){
        this.CustomerInfoVisible=true
      }
      if (JSON.stringify(this.list).includes("2020")){
        this.CustomerLossVisible=true
      }
      if (JSON.stringify(this.list).includes("2030")){
        this.CustomerOrderVisible=true
      }
      this.UserManagementVisible=false
      if (JSON.stringify(this.list).includes("8010")){
        this.UserManagementVisible=true
        console.log(JSON.stringify(this.list).includes(8010))
        console.log(JSON.stringify(this.list).includes("8010"))
      }
      if (JSON.stringify(this.list).includes("801001")){
        this.updatePersonalInfoVisible=true
      }
      // var index = JSON.stringify(this.list).indexOf("10")
      // console.log("bbbbbbbbbbbbbbbbbbbbbbb",JSON.stringify(this.list).indexOf("20"))
      // console.log("ccccccccccccccc","20".length===JSON.stringify(this.list)[index])
      // console.log("bbbbbbbbbbbbbbbbbbbbbbb",JSON.stringify(this.list).indexOf("10"))
      // console.log("ccccccccccccccc","10".length===JSON.stringify(this.list)[index])
      // console.log('aaaaaaaaaaaaa',JSON.stringify(800020).length)

      this.ServerVisible=false
      if (JSON.stringify(this.list).includes("3000")){
        this.ServerVisible=true
        // console.log(JSON.stringify(this.list).includes("30"))
        // console.log(JSON.stringify(this.list).includes(30))
      }
      if (JSON.stringify(this.list).includes("3010")){
        this.ServerCreationVisible=true
      }
      if (JSON.stringify(this.list).includes("3020")){
        this.ServerAllocationVisible=true
      }
      if (JSON.stringify(this.list).includes("3030")){
        this.ServerProcessVisible=true
      }
      if (JSON.stringify(this.list).includes("3040")){
        this.ServerFeedBackVisible=true
      }
      if (JSON.stringify(this.list).includes("3050")){
        this.ServerArchivingVisible=true
      }
      this.SystemVisible=false
      if (JSON.stringify(this.list).includes("6000")){
        this.SystemVisible=true
        // console.log(JSON.stringify(this.list).includes("60"))
        // console.log(JSON.stringify(this.list).includes(60))
      }
      if (JSON.stringify(this.list).includes("6010")){
        this.UserVisible=true
      }
      if (JSON.stringify(this.list).includes("6020")){
        this.RoleManagementVisible=true
      }
      this.CountVisible=false
      if (JSON.stringify(this.list).includes("4000")){
        this.CountVisible=true
        // console.log(JSON.stringify(this.list).includes("40"))
        // console.log(JSON.stringify(this.list).includes(40))
      }
      if (JSON.stringify(this.list).includes("4010")){
        this.CustomerContributionVisible=true
      }
      if (JSON.stringify(this.list).includes("4020")){
        this.CompositionVisible=true
      }
      if (JSON.stringify(this.list).includes("4030")){
        this.CustomerServerVisible=true
      }
      if (JSON.stringify(this.list).includes("4060")){
        this.LossVisibleImage=true
      }
    })
    // this.list = this.$store.getters.getPermissionList
  }
}
</script>

<style scoped>
.SideBar{
  position: relative;
  /*display: flex;*/
  background-color: rgb(223, 239, 245);
  /*width: 13%;*/
  width: auto;
  height: 100%;
  float: left;
  color: white;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 100%;
  /*min-height: 400px;*/
}
</style>