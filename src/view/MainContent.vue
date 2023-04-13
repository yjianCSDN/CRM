<template>
<div class="MainContent">
  <div style="width: 100%;height: 40px">
    <bread/>
  </div>
  <router-view></router-view>
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
  /*background-color: #d7f1ea;*/
  height: 100%;
  width: 87%;
  float: right;
}
</style>