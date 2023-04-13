<template>
  <div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item v-for="(item) in breadList" :to="item.path" :key="item"
                            style="font-size: 15px;margin: 12px 0 0 0;font-family: 'Papyrus';" @click="addBread(item)"
        >&nbsp; {{item.title}}<i @click="deleteBread(item)">×</i>
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
      // console.log(this.breadList.splice(this.breadList.indexOf(msg)))
      // if (this.nameList.indexOf(msg.title)!==-1){
      //   this.nameList.splice(this.nameList.indexOf(msg.title),1)
      //   this.breadList.splice(this.breadList.indexOf(msg),1)
      // }
      // console.log("delete breadList : ",this.breadList)
      // console.log("delete nameList : ",this.nameList)
    },
    // eslint-disable-next-line no-unused-vars
    addBread(msg){
      // console.log(msg)
    }
  },
  watch:{
    $route:{
      // eslint-disable-next-line no-unused-vars
      handler(route){
        let List = route.matched;
        // console.log(List)
        if (List.length<=1){
          // this.nameList.push({})
          // console.log("List[1]",List)
          // console.log("当前页是刚进入页面")
        }else {
          // for (let i = 0; i < this.nameList.length; i++) {
          //   if (this.nameList[i].name===List[i].meta.title){
          //     if (!this.nameList[i].isFirst){
          //       console.log("该项已经添加过了")
          //     }else {
          //       this.breadList.push(List[1].meta)
          //       this.nameList.push({name:List[1].meta.title,isFirst: false})
          //     }
          //   }
          // }
          if (! JSON.stringify(this.nameList).includes(List[1].meta.title)){
            this.breadList.push(List[1].meta)
            this.nameList.push({name:List[1].meta.title,isFirst: false})
          }
        }
        // console.log("watch breadList : ",this.breadList)
        // console.log("watch nameList : ",this.nameList)
      },immediate:true
    },

  }
}
</script>

<style scoped>

</style>