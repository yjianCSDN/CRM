<template>
  <div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item v-for="(item,index) in breadList"  :key="index" :to="item.path" @click="addBread(item)"
                            style="font-size: 15px;margin: 12px 0 0 0;font-family: 'Papyrus';"
        >&nbsp;{{item.title}}<i @click="deleteBread(item)">x</i>
        </el-breadcrumb-item>
      </el-breadcrumb>
  </div>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Bread",
  data(){
    return{
      breadList: [],
      nameList:[],
    }
  },
  mounted() {
    console.log(this.$route)
  },
  methods:{
    deleteBread(msg){
      // console.log("deleteBread",msg)
      // console.log("indexOf:     ",this.breadList.indexOf(msg))
      if (this.breadList.indexOf(msg)!==-1){
        var index = this.breadList.indexOf(msg)
        this.breadList.splice(index,1)
        // this.nameList.splice(index,1)
        let vv = this.nameList.indexOf(msg.title)
        // console.log("nameList",vv)
        if (vv!==-1){
          // this.nameList.filter(item=>item===msg.title)
          // this.nameList.splice(vv,1)
        }
        // console.log("要进行删除啦!")
      }
    },
    // eslint-disable-next-line no-unused-vars
    addBread(msg){
      // console.log(msg)
    },
    // 面包屑数据处理
    getBreadcrumb () {
      let that = this;
      // 由于本项目大部分属于‘一级’页面，所以在设置路由时候，一级页面不设置breadNumber = 1，‘二级’页面以上才设置breadNumber
      let breadNumber = typeof (this.$route.meta.breadNumber) !== 'undefined' ? this.$route.meta.breadNumber : 1;
      // 获取当前页面的名字和路由，并组合成新的对象
      let newBread = {name: this.$route.name, path: this.$route.fullPath};
      let vuexBreadList = []; // 默认初始化面包屑数据
      if (breadNumber !== 1) {
        // 当前面包屑breadNumber大于1时才会从vuex中获取值
        vuexBreadList = that.$store.state.breadListState; // 获取breadList数组
      }
      if (breadNumber < vuexBreadList.length) {
        // "回退"面包屑----判断条件：当前路由breadNumber小于vuexBreadList的长度
        vuexBreadList.splice(breadNumber - vuexBreadList.length, vuexBreadList.length - breadNumber);
      }
      if (breadNumber > vuexBreadList.length) {
        // 添加面包屑----判断条件：当前路由breadNumber大于vuexBreadList的长度
        vuexBreadList.push(newBread);
      }
      // 处理完数据后，将最终的数据更新到vuex（用于页面刷新）
      that.$store.commit('breadListMutations', vuexBreadList);
      // 处理完数据后，将最终的数据更新为新的面包屑数组
      that.breadList = vuexBreadList;
    },
    // 面包屑点击事件

  },
  watch:{
    $route:{
      // eslint-disable-next-line no-unused-vars
      handler(route){
        let List = route.matched;
        console.log("thisList:---->",List)
        if (List.length<=1){
          // this.nameList.push({})
          // console.log("List[1]",List)
          console.log("当前页是刚进入页面")
        }else {
          if (! JSON.stringify(this.nameList).includes(List[1].meta.title)){
            this.breadList.push(List[1].meta)
            this.nameList.push({name:List[1].meta.title,isFirst: false})
          }
        }
      },immediate:true
    },
  }
}
</script>

<style scoped>

</style>