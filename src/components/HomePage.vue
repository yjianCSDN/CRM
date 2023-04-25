<template>
  <div id="login">
    <div id="login-form" @keyup.enter="inputInfo">
      <h1>CRM登陆界面</h1>
      <label for="username"></label>
      <input type="text" placeholder="用户名" name="username" id="username" autocapitalize="off" v-model.trim=user.userName
             aria-autocomplete="off">
      <label for="password"></label>
      <input type="password" placeholder="密码" name="password" show-password id="password" autocapitalize="off"
             v-model.trim="user.userPwd">
      <label for="code"></label>
      <input type="text" placeholder="验证码" name="code" id="code" autocapitalize="off" v-model.trim="code">
      <tools class="tools" :identifyCode="identifyCode" @click="refreshCode"></tools>
      <div class="checkbox">
        <el-checkbox v-model="RememberMe" label="记住我" size="large"/>
      </div>
      <div>
        <el-button type="primary" v-on:click="inputInfo">登录</el-button>
        <el-button type="info" @click="open2" v-on:click="resetInfo">重置</el-button>
      </div>
    </div>
  </div>
</template>

<script>

import {ElButton, ElNotification} from 'element-plus'
import Cookies from 'js-cookie'
import {ElMessage} from "element-plus";
// eslint-disable-next-line no-unused-vars
import tools from "@/tools/tools";

export default {
  name: 'HomePage',
  props: {
    msg: String
  },
  components: {
    // eslint-disable-next-line vue/no-unused-components
    ElButton, tools
  },
  data: function () {
    return {
      user: {
        userName: '',
        userPwd: '',
      },
      RememberMe: false,
      code: "",
      identifyCodes: "1234567890abcdefjhijklinopqrsduvwxyz", //随机串内容,从这里随机抽几个显示验证码
      identifyCode: "", //验证码图片内容
    }
  },
  methods: {
    inputInfo: function () {

      if (this.code === "") {
        ElMessage({type: "info", message: "没有输入验证码"})
      } else if (this.code !== this.identifyCode) {
        ElMessage({type: "error", message: "验证码错误,请重新输入"})
      } else if (this.user.userName===""||this.user.userPwd===""){
        ElMessage({type:"info",message:"用户信息输入缺失"})
      }else {
        this.$api.login.Login("/user/login", this.user).then(res => {
          console.log(res)
          if (res.code === 200) {
            if (this.RememberMe === true) {
              Cookies.set("userName", res.result.userName, {expires: 7})
              Cookies.set("trueName", res.result.trueName, {expires: 7})
              Cookies.set("userIdStr", res.result.userIdStr, {expires: 7})
              sessionStorage.setItem("img",res.result.img)
              Cookies.set("img", res.result.img, {expires: 7})
            } else {
              Cookies.set("userName", res.result.userName)
              Cookies.set("trueName", res.result.trueName)
              Cookies.set("userIdStr", res.result.userIdStr)
              Cookies.set("img",res.result.img)
            }
            ElNotification({
              title: 'Success',
              message: '登录成功 欢迎回来',
              type: 'success',
            });
            this.$router.push("/index")
          } else {
            ElNotification({
              title: "error",
              message: '用户名或者密码错误',
              type: "error"
            })
          }
        });
      }
    },
    resetInfo: function () {
      this.user.userName = ''
      this.user.userPwd = ''
      this.identifyCode=""
    },
    open2: function () {
      if (this.user.username != '' || this.user.password != '') {
        ElNotification({
          title: 'Success',
          message: '用户名和密码将被清空',
          type: 'success',
        });
        return;
      } else {
        ElNotification({
          title: 'Error',
          message: '请不要重复此操作',
          type: 'error',
        });
      }
    },
    // 重置验证码
    refreshCode() {
      this.identifyCode = "";
      this.makeCode(this.identifyCodes, 4);
    },
    //获取验证码的值
    makeCode(o, l) {
      for (let i = 0; i < l; i++) {
        //通过循环获取字符串内随机几位
        this.identifyCode +=
            this.identifyCodes[this.randomNum(0, this.identifyCodes.length)];
      }
    },
    //随机数字：用于当角标拿字符串的值
    randomNum(min, max) {
      return Math.floor(Math.random() * (max - min) + min);
    },
  },
  mounted() {
    // 初始化验证码
    this.identifyCode = "";
    //参数：（1）随机串内容。（2）验证码显示位数
    this.makeCode(this.identifyCodes, 4);
  }
}
</script>

<style scoped>
#login {
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  position: relative;
  background-image: linear-gradient(to bottom right, #d819bf, #2683cf, #63e4b9);
  background-repeat: no-repeat;
  background-attachment: fixed;
  background-position: center;
  background-size: cover;
}

#login-form {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 50vw;
  min-width: 300px;
  max-width: 400px;
  display: flex;
  flex-direction: column;
  background-color: rgba(0, 0, 0, 0.7);
  border-radius: 15px;
  box-shadow: 0 15px 25px rgba(0, 0, 0, .5);
}

h1 {
  width: 60%;
  margin: 50px auto 20px;
  color: #c1c1c1;
  text-align: center;
}

input {
  width: 60%;
  margin: 15px auto;
  outline: none;
  border: none;
  padding: 10px;
  border-bottom: 1px solid #fff;
  background: transparent;
  color: white;
}

label {
  width: 60%;
  margin: 0 auto;
  position: relative;
  top: 30px;
  left: -15px;
}

div {
  width: 60%;
  margin: 10px auto;
  display: flex;
  justify-content: center;
  align-content: center;
}

button {
  background-color: rgba(9, 108, 144, 0.5);
  margin: 10px 25px 40px 25px;
}

p {
  width: 60%;
  margin: 8px auto;
  position: relative;
  left: -15px;
  color: #ff0000;
  font-size: 8px;
}

.checkbox {
  margin: -35px 0 20px 0;
}
input {
  -webkit-text-fill-color: #ffffff !important;
  transition: background-color 5000s ease-in-out, width 1s ease-out !important;
}
.tools{
  position: absolute;
  width: 20%;
  margin: 120px 0 0 59%;
}
</style>

