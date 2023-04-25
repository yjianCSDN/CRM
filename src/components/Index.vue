<template>
  <div class="Index">
    <Header></Header>
    <SideBar></SideBar>
    <MainContent></MainContent>
  </div>
</template>

<script>
import Header from "@/view/Header"
import MainContent from "@/view/MainContent"
import SideBar from "@/view/SideBar"
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Index",
  components:{
    Header,MainContent,SideBar
  }
}
</script>

<style scoped>
.Index{
  width: 100%;
  height: 675px;
}

</style>