<template>
<div class="Announcement">
  <div class="search">
    <el-input
        v-model="noticeQuery.title"
        class="w-50 m-2"
        placeholder="标题"
        style="position: relative;width: 12%;margin: 0 0 0 0"
    />
    <el-input
        v-model="noticeQuery.publisher"
        class="w-50 m-2"
        placeholder="创建人"
        style="position: relative;width: 12%;margin: 5px"
    />
    <el-select v-model="noticeQuery.type" class="m-2" placeholder="类 型">
      <el-option label="" value=""/>
      <el-option label="平台公告" value="平台公告"/>
      <el-option label="新闻动态" value="新闻动态"/>
      <el-option label="营销机会" value="营销机会"/>
      <el-option label="行业资讯" value="行业资讯"/>
      <el-option label="其他公告" value="其他公告"/>
      <el-option label="公司通知" value="公司通知"/>
    </el-select>
    &nbsp;&nbsp;
    <el-button type="primary" style="left: 30px" @click="queryBySth">点 击 查 询</el-button>
    <el-button type="primary" style="left: 30px" @click="addNoticeInit">添 加 公 告</el-button>
  </div>
  <div>
    <el-table :data="noticeList" class="tableMenu" max-height="500"
              :default-sort="{ prop: 'createDate', order: 'descending' }"
              :header-cell-style="{ backgroundColor: '#eef5ff',   textAlign: 'center',  }">
      <el-table-column prop="id" label="编号" width="100" fixed="left" align="center"/>
      <el-table-column prop="title" label="标题" width="100" header-align="center" align="center"/>
      <el-table-column prop="type" label="公告类型" width="100" header-align="center" align="center">
        <template #defailt="scope">
          <span v-if="scope.row.type==='平台公告'" style="color: #37B328">{{scope.row.type}}</span>
          <span v-if="scope.row.type==='新闻动态'" style="color: #8c6fd0">{{scope.row.type}}</span>
          <span v-if="scope.row.type==='营销机会'" style="color: orangered">{{scope.row.type}}</span>
          <span v-if="scope.row.type==='行业资讯'" style="color: skyblue">{{scope.row.type}}</span>
          <span v-if="scope.row.type==='其他公告'" style="color: orange">{{scope.row.type}}</span>
          <span v-if="scope.row.type==='公司通知'" style="color: aqua">{{scope.row.type}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="publisher" label="发起者" width="130" header-align="center" align="center"/>
      <el-table-column  label="公告内容" width="200" header-align="center" align="center">
        <template #default="scope">
          <el-popover
              placement="right-start"
              title="公告内容"
              :width="400"
              trigger="hover"
              :content="scope.row.content"
          >
            <template #reference>
              <el-button class="m-2">公告信息</el-button>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column prop="createDate" label="发布日期" width="200" header-align="center" align="center"/>
      <el-table-column prop="updateDate" label="修改日期" width="200" header-align="center" align="center"/>
      <el-table-column fixed="right" label="操作" width="200" header-align="center" align="center">
        <template #default="scope">
          <el-button link type="primary" size="small" @click="updateInit(scope.row)"
          >修 改
          </el-button
          >
          <el-popover :visible="visible" placement="top" :width="160" trigger="hover">
            <p>确认要删除该公告吗？</p>
            <div style="text-align: right; margin: 0">
              <el-button size="small" text @click="visible = false">取消</el-button>
              <el-button size="small" type="primary" @click="deleteInit(scope.row)"
              >确认</el-button
              >
            </div>
            <template #reference>
              <el-button link size="small" type="danger" @click="visible = false">删 除</el-button>
            </template>
          </el-popover>
        </template>
      </el-table-column>
    </el-table>
  </div>
  <div class="page">
    <el-pagination
        v-model:current-page="noticeQuery.page"
        v-model:page-size="noticeQuery.limit"
        small="small"
        :disabled="false"
        :background="true"
        layout="prev,pager,next,jumper"
        :total="total"
        @current-change="handleCurrentChange"
    />
  </div>
  <!--添加公告-->
  <el-dialog
      v-model="addNoticeVisible"
      :title="topper"
      width="45%"
  >
    <el-form
        label-position="left"
        label-width="100px"
        :model="noticeInfo"
        style="max-width: 460px"
    >
      <el-form-item label="公告标题">
        <el-input v-model="noticeInfo.title" placeholder="(空)"/>
      </el-form-item>
      <el-form-item label="公告类型">
        <el-select v-model="noticeInfo.type" class="m-2" placeholder="(空)">
          <el-option label="平台公告" value="平台公告"/>
          <el-option label="新闻动态" value="新闻动态"/>
          <el-option label="营销机会" value="营销机会"/>
          <el-option label="行业资讯" value="行业资讯"/>
          <el-option label="其他公告" value="其他公告"/>
          <el-option label="公司通知" value="公司通知"/>
        </el-select>
      </el-form-item>
      <el-form-item label="公告内容">
        <el-input v-model="noticeInfo.content" type="textarea" :autosize="{ minRows: 2, maxRows: 4 }"
                  placeholder="(空)"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addNoticeVisible=false,noticeInfo={}">取 消</el-button>
        <el-button type="primary" @click="updateOrAddNoticeInfo">
          确认
        </el-button>
      </span>
    </template>
  </el-dialog>
</div>
</template>

<script>
import {reactive, ref} from "@vue/reactivity";
import {ElMessage} from "element-plus";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Announcement",
  data(){
    let noticeQuery = reactive({page:1,limit:10,type:"",title:"",publisher:""})
    let noticeInfo = reactive({publisher:"",title:"",type:"",content:""})
    let noticeList = reactive([])
    let total = ref("")
    let addNoticeVisible = ref(false)
    let topper = ref("")
    let ii = ref(false)
    return{
      noticeQuery,noticeInfo,noticeList,total,addNoticeVisible,topper,ii
    }
  },
  methods:{
    common(type,message){
      ElMessage({
        type:type,
        message:message
      })
    },
    initParams(){
      this.$api.Notice.queryNotice("/notice/lists?flag=1",this.noticeQuery).then(res=> {
        if (res.code===200){
          console.log(res)
          this.noticeList=res.result.data
          this.total=res.result.count
        }
      })
    },
    handleCurrentChange(msg){
      this.noticeQuery.page=msg
      this.$api.Notice.queryNotice("/notice/lists?flag=1",this.noticeQuery).then(res=> {
        if (res.code===200){
          this.noticeList=res.result.data
          this.total=res.result.count
        }
      })
    },
    queryBySth(){
      this.$api.Notice.queryNotice("/notice/lists?flag=1",this.noticeQuery).then(res=>{
        if (res.code===200){
          this.common("success","查找成功")
          this.noticeList=res.result.data
        }else {
          this.common("error","查找失败，请重试")
        }
      })
    },
    deleteInit(msg){
      let id = msg.id
      // console.log(id)
      this.$api.Notice.delNotice("/notice/delete",id).then(res=>{
        if (res.code===200){
          this.common("success","删除成功")
          this.initParams()
        }else {
          this.common("error","删除失败")
        }
      })
    },
    updateInit(msg){
      this.addNoticeVisible=true,
      this.noticeInfo=JSON.parse(JSON.stringify(msg))
      this.topper="公告信息修改"
      this.ii=false
    },
    updateOrAddNoticeInfo(){
      let notice = this.noticeInfo
      console.log(this.ii)
      if (this.ii){
        this.$api.Notice.addNotice("/notice/add",notice).then(res=>{
          console.log(res)
          if (res.code===200){
            this.common("success","添加成功")
            this.addNoticeVisible=false
            this.initParams()
          }else {
            this.common("error","添加失败，请重试!")
          }
        })
      }else {
        this.$api.Notice.updateNotice("/notice/update",notice).then(res=>{
          if (res.code===200){
            this.common("success","修改成功")
            this.addNoticeVisible=false
            this.noticeInfo={}
            this.initParams()
          }else {
            this.common("error","修改失败，请重试")
          }
        })
      }
    },
    addNoticeInit(){
      this.topper="添加公告信息"
      this.noticeInfo={}
      this.addNoticeVisible=true
      this.ii=true
    },
  },
  mounted() {
    this.initParams()
  }
}
</script>

<style scoped>
.Announcement{
  width: 100%;
  height: 95%;
}
.search{
  position: relative;
  width: 60%;
  height: 50px;
  display: block;
  left: 3%;
}
.tableMenu {
  header-align: center;
  left: 3%;
  position: relative;
  width: 90%;
}
.page{
  position: relative;
  margin: 1% 0 0 1%;
  width: 60%;
  color: #ffffff;
}
</style>