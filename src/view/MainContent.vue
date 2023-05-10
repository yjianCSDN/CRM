<template>
<div class="MainContent">
  <div style="width: 100%;height: 2%">
    <bread style="height: 30px;margin: 5px 0 5px 1%"/>
  </div>
  <div class="content">
    <router-view></router-view>
  </div>
</div>
</template>

<script>
import Bread from "@/components/Bread";
export default {
  name: "MainContent",
  // eslint-disable-next-line vue/no-unused-components
  components: {Bread},
  data(){
    return{
      lists:[]
    }
  },
}
</script>

<style scoped>
.MainContent{
  position: relative;
  background-size: 100% 100%;
  background-color: seashell;
  height: 100%;
  width: 87%;
  float: right;
}
.content{
  height: 80%;
  width: 98%;
  margin: 2% 0 0 1%;
  background-color: white;
  border-radius: 5px;
  box-shadow: 0 0 10px  rgba(0,0,0,0.5);
}
</style>