<template>
  <div class="Header">
    <div style="width: 12%;height:100%;float: left">
          <el-image :src="require('@/assets/crm.jpeg')" style="position: relative;height: 100%;width: 100%"/>
    </div>
    <div style="width: 400px;height: 100%;position: relative;float: left;margin: 1.5% 0 0 0 ">
      <span style="font-size: 30px;font-family: 'Comic Sans MS';color: white">&nbsp; 客户关系管理系统</span>
    </div>
    <div style="float: right;width: 100px;height: 100%;position: relative">
      <el-image style="width: 40px; height: 40px;margin: 20px 0 0 0;position: relative" :src="imgurl" v-show="imgurl" fit="cover" />
      <br>
      <el-dropdown style="margin: 0 0 10px -30px;position: relative">
        <span style="color: #faeaea;margin: 0 0 0 27px;font-size: 17px">{{userName}}</span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="PersonalCenter">点我查看资料</el-dropdown-item>
            <el-dropdown-item @click="exit">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import Cookies from "js-cookie";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Header",
  data(){
    return{
      userName:Cookies.get("userName"),
      imgurl:require("@/assets/头像2.png")
    }
  },
  methods:{
    exit(){
      Cookies.remove("userIdStr")
      Cookies.remove("userName")
      Cookies.remove("trueName")
      this.$router.push("/")
    },
    PersonalCenter(){
      this.$router.push("/index/PersonalInfoManagement")
    },
  }
}
</script>

<style scoped>
.Header{
  width: 100%;
  height: 15%;
  background-color: black;
  float: right;
}
</style>