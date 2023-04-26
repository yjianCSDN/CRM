<template>
<div class="SideBar">
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
        <span class="iconfont icon-caidanlan-kehu-kehulianxiren" style="margin: 0 0 0 10px"/>
        <span style="margin: 0 0 0 10px">客 户 管 理</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="/index/CustomerInformation" v-show="CustomerInfoVisible">
          <span class="iconfont icon-kehuxinxi" style="margin: 0 10px 0 0"/>
          <span>客户信息管理</span>
        </el-menu-item>
        <el-menu-item index="/index/CustomerLoss" v-show="CustomerLossVisible">
<!--          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0" :src="require('@/assets/防流失预警.png')"/>-->
          <span class="iconfont icon-fangliushiyujing" style="margin: 0 10px 0 0"/>
          <span>客户流失管理</span>
        </el-menu-item>
        <el-menu-item index="/index/CustomerOrders" v-show="CustomerOrderVisible">
<!--          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/客户订单.png')"/>-->
          <span class="iconfont icon-kehudingdan" style="margin: 0 10px 0 0"/>
          <span>客户订单管理</span>
        </el-menu-item>
      </el-menu-item-group>
    </el-sub-menu>
    <el-sub-menu index="2" v-if="UserManagementVisible">
      <template #title>
        <span class="iconfont icon-yonghuguanli1" style="margin: 0 0 0 10px"/>
        <span style="margin: 0 0 0 10px">个 人 中 心</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="/index/PersonalInfoManagement" v-show="updatePersonalInfoVisible">
<!--          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/257用户资料.png')"/>-->
          <span class="iconfont icon-kehuxinxi" style="margin: 0 10px 0 0"/>
          <span>个人信息管理</span>
        </el-menu-item>
      </el-menu-item-group>
    </el-sub-menu>
    <el-sub-menu index="3" v-if="saleChance" >
      <template #title>
<!--        <el-image style="width: 45px; height: 45px;top: -8px;margin: 0 10px 0 0" :src="require('@/assets/营销.png')"/>-->
        <span class="iconfont icon-yingxiao" style="margin: 0 0 0 10px"/>
        <span style="margin: 0 0 0 10px">营 销 管 理</span>
      </template>
      <el-menu-item-group>
        <el-menu-item  index="/index/SaleChance" v-show="select">
<!--          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/业务机会.png')"/>-->
          <span class="iconfont icon-yewujihui" style="margin: 0 10px 0 0"/>
          营销机会管理
        </el-menu-item>
        <el-menu-item  index="/index/CusDevPlan" v-show="CusDevPlan">
<!--          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/开发者.png')"/>-->
          <span class="iconfont icon-chuizi" style="margin: 0 10px 0 0"/>
          客户开发计划
        </el-menu-item>
      </el-menu-item-group>
    </el-sub-menu>
    <el-sub-menu index="4" v-if="ServerVisible">
      <template #title>
<!--        <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0" :src="require('@/assets/基础服务管理.png')"/>-->
        <span class="iconfont icon-1-jichufuwuguanli" style="margin: 0 0 0 10px"/>
        <span style="margin: 0 0 0 10px">客户意见管理</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="/index/ServiceCreation" v-show="ServerCreationVisible">
<!--          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/创建服务.png')"/>-->
          <span class="iconfont icon-chuangjianhuati" style="margin: 0 5px 0 -5px"/>
          <span style="margin: 0 0 0 13px">服务创建</span>
        </el-menu-item>
        <el-menu-item index="/index/ServiceAllocation" v-show="ServerAllocationVisible">
          <span class="iconfont icon-yuangongfenpei" style="margin: 0 5px 0 -5px"/>
          <span>服务分配</span>
        </el-menu-item>
        <el-menu-item index="/index/ServiceProcessing" v-show="ServerProcessVisible">
<!--          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/处理.png')"/>-->
          <span class="iconfont icon-chuli" style="margin: 0 5px 0 -5px"/>
          <span style="margin: 0 0 0 13px">服务处理</span>
        </el-menu-item>
        <el-menu-item index="/index/ServiceFeedback" v-show="ServerFeedBackVisible">
<!--          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/用户反馈.png')"/>-->
          <span class="iconfont icon-yonghufankui" style="margin: 0 5px 0 -5px"/>
          <span style="margin: 0 0 0 13px">服务反馈</span>
        </el-menu-item>
        <el-menu-item index="/index/ServiceArchiving" v-show="ServerArchivingVisible">
<!--          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/归档.png')"/>-->
          <span class="iconfont icon-guidang" style="margin: 0 5px 0 -5px"/>
          <span style="margin: 0 0 0 13px">服务归档</span>
        </el-menu-item>
        <el-menu-item index="/index/MyReview" v-show="MyReviewVisible" >
          <!--          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/流失客户.png')"/>-->
          <span class="iconfont icon-shenhe" style="margin: 0 5px 0 -5px"/>
          <span style="margin: 0 0 0 13px">我的审核</span>
        </el-menu-item>
      </el-menu-item-group>
    </el-sub-menu>
    <el-sub-menu index="5" v-if="SystemVisible">
      <template #title>
<!--        <el-image style="width: 45px; height: 45px;top: -8px;margin: 0 10px 0 0" :src="require('@/assets/系统管理.png')"/>-->
        <span class="iconfont icon-xitongguanli" style="margin: 0 0 0 10px"/>
        <span style="margin: 0 0 0 10px">系 统 管 理</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="/index/DictionaryManagement">
<!--          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/字典管理.png')"/>-->
          <span class="iconfont icon-zidianguanli" style="margin: 0 10px 0 0"/>
          字 典 管 理
        </el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group>
        <el-menu-item index="/index/UserManagement" v-show="UserVisible">
<!--          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/用户管理2.png')"/>-->
          <span class="iconfont icon-yonghuguanli" style="margin: 0 10px 0 0"/>
          用 户 管 理
        </el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group>
        <el-menu-item index="/index/RoleManagement" v-show="RoleManagementVisible">
<!--          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/管理员_角色管理.png')"/>-->
          <span class="iconfont icon-yonghuguanli1" style="margin: 0 10px 0 0"/>
          角 色 管 理
        </el-menu-item>
      </el-menu-item-group>
    </el-sub-menu>
    <el-sub-menu index="6" v-if="CountVisible">
      <template #title>
<!--        <el-image style="width: 45px; height: 45px;top: -8px;margin: 0 10px 0 0" :src="require('@/assets/统计报表.png')"/>-->
        <span class="iconfont icon-board-full" style="margin: 0 0 0 10px"/>
        <span style="margin: 0 0 0 10px">统 计 报 表</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="/index/CustomerContribution" v-show="CustomerContributionVisible">
<!--          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/贡献分析.png')"/>-->
          <span class="iconfont icon-fenxi" style="margin: 0 10px 0 0"/>
          客户贡献分析
        </el-menu-item>
        <el-menu-item index="/index/CustomerComposition" v-show="CompositionVisible" >
<!--          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/客户构成.png')"/>-->
          <span class="iconfont icon-yuangonggoucheng" style="margin: 0 10px 0 0"/>
          客户构成分析
        </el-menu-item>
        <el-menu-item index="/index/CustomerService" v-show="CustomerServerVisible">
<!--          <el-image style="width: 45px; height: 45px;margin: -15px 10px 0 0;position: relative" :src="require('@/assets/客户服务分析.png')"/>-->
          <span class="iconfont icon-kehufuwufenxi" style="margin: 0 10px 0 0"/>
          客户服务分析
        </el-menu-item>
        <el-menu-item index="/index/CustomerOrderAnalysis" v-show="CustomerOrderAnalysisVisible">
<!--          <el-image style="width: 45px; height: 45px;margin: -15px 10px 0 0;position: relative" :src="require('@/assets/数据分析-宝贝分析.png')"/>-->
          <span class="iconfont icon-shujufenxi-baobeifenxi" style="margin: 0 10px 0 0"/>
          客户订单分析
        </el-menu-item>
        <el-menu-item index="/index/CustomerChurn" v-show="LossVisibleImage" >
<!--          <el-image style="width: 40px; height: 40px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/流失客户.png')"/>-->
          <span class="iconfont icon-liushihuiyuan" style="margin: 0 10px 0 0"/>
          客户流失分析
        </el-menu-item>
      </el-menu-item-group>
    </el-sub-menu>
    <el-sub-menu index="7" v-show="AnnouncementManagement">
        <template #title>
<!--          <el-image style="width: 45px; height: 45px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/公告.png')"/>-->
          <span class="iconfont icon-gonggao" style="margin: 0 0 0 10px"/>
          <span style="margin: 0 0 0 10px">公 告 管 理</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="/index/AllAnnouncements" v-show="ViewAnnouncement">
<!--            <el-image style="width: 45px; height: 45px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/招展管理.png')"/>-->
            <span class="iconfont icon--zhanshiping" style="margin: 0 10px 0 0"/>
            公告展示
          </el-menu-item>
        </el-menu-item-group>
      <el-menu-item-group>
        <el-menu-item index="/index/Announcement" v-show="Announcement">
<!--          <el-image style="width: 45px; height: 45px;top: -8px;margin: 0 10px 0 0;position: relative" :src="require('@/assets/发布公告.png')"/>-->
          <span class="iconfont icon-fabugonggao" style="margin: 0 10px 0 0"/>
            发布公告
        </el-menu-item>
      </el-menu-item-group>
      </el-sub-menu>
  </el-menu>
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
      CustomerOrderAnalysisVisible:false,//客户订单分析
      MyReviewVisible:false,//我的审核
      collapse:false,
      ViewAnnouncement:false,
      Announcement:false,
      AnnouncementManagement:false,
    }
  },
  mounted() {
    console.log(Cookies.get("userIdStr"))
    this.$api.Permission.queryUserHasRoleHasPermissionByUserId("/permission/lists", {userIdStr:Cookies.get("userIdStr")   }).then(res=>{
      if (res.code===200){
        this.list = toRaw(res.result)
        console.log("this.List:--->",this.list)
        this.$store.commit("setPermissionList",res.result)
      }

      this.saleChance = false
      if (JSON.stringify(toRaw(this.list)).includes("1000")){
        this.saleChance = true
      }
      if (JSON.stringify(toRaw(this.list)).includes("1010")){
        this.select = true
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
      if (JSON.stringify(this.list).includes("2000") ){
        this.CustomerManagementVisible=true
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
      }
      if (JSON.stringify(this.list).includes("801001")){
        this.updatePersonalInfoVisible=true
      }
      if (JSON.stringify(this.list).includes("3000")){
        this.ServerVisible=true
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
      if (JSON.stringify(this.list).includes("3060")){
        this.MyReviewVisible=true
      }
      if (JSON.stringify(this.list).includes("6000")){
        this.SystemVisible=true
      }
      if (JSON.stringify(this.list).includes("6010")){
        this.UserVisible=true
      }
      if (JSON.stringify(this.list).includes("6020")){
        this.RoleManagementVisible=true
      }
      if (JSON.stringify(this.list).includes("4000")){
        this.CountVisible=true
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
      if (JSON.stringify(this.list).includes("4070")){
        this.CustomerOrderAnalysisVisible=true
      }
      if (JSON.stringify(this.list).includes("901001")){
        this.ViewAnnouncement=true
      }
      if (JSON.stringify(this.list).includes("902001")){
        this.Announcement=true
      }
      if (JSON.stringify(this.list).includes("901000")){
        this.AnnouncementManagement=true
      }
    })
  }
}
</script>

<style scoped>
.SideBar{
  position: relative;
  display: block;
  background-color: rgb(223, 239, 245);
  width: 13%;
  height: 100%;
  float: left;
  color: white;
  height: 100%;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 100%;
}
</style>