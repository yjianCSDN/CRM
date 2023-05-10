<template>
  <div class="welcome">
    <div class="TimeLine">
      <el-scrollbar wrapClass="scrollbar-wrap"
                    style="height: 550px"
                    ref="scrollbarContainer">
        <div>
      <el-timeline :reverse="true" style="margin: 40px 0 0 0">
        <el-timeline-item
            placement="top"
            v-for="(notice, index) in noticeList"
            :key="index"
            :color="notice.color"
            :timestamp="notice.updateDate"
        >
          <el-card>
          <h4>{{notice.title}}</h4>
            <h5>类型: &nbsp;&nbsp; {{notice.type}}</h5>
            <h5>发布人: &nbsp;&nbsp; {{notice.publisher}}</h5>
            <p> {{ notice.content }} </p>
          </el-card>
        </el-timeline-item>
      </el-timeline>
        </div>
      </el-scrollbar>
    </div>
  </div>

</template>

<script>

import {reactive, ref} from "@vue/reactivity";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "welcome",
  data() {
    let noticeQuery = reactive({page:1,limit:100,type:null,title:null,publisher:null})
    let noticeList = reactive([])
    let total = ref("")
    return {
      noticeQuery,noticeList,total,
    }
  },
  methods: {
    initParams(){
      console.log("123456789")
      console.log(this.noticeQuery,"4564sa6d54as")
      this.$api.Notice.queryNotice("/notice/lists").then(res=> {
        console.log(res)
        if (res.code===200){
          res.result.data.forEach(item=>{
            if (item.type==='平台公告'){
              item.color='#37B328'
            }else if (item.type==='新闻动态'){
              item.color='#8c6fd0'
            }else if (item.type==='营销机会'){
              item.color='orangered'
            }else if (item.type==='行业资讯'){
              item.color='skyblue'
            }else if (item.type==='其他公告'){
              item.color='orange'
            }else if (item.type==='公司通知'){
              item.color='aqua'
            }
          })
          this.noticeList=res.result.data
          this.total=res.result.count
          // console.log("noticeList:::",this.noticeList)
        }
      })
    },
  },
  created() {
    this.initParams()
  }
}
</script>

<style scoped>
.welcome{
  height: 100%;
  width: 100%;
}
.TimeLine{
  width: 99%;
  height: 100%;
  position: relative;
}


</style>