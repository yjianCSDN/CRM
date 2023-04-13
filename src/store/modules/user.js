export default {

    state:{
    //全局属性
        ImgUrl:"",
        PermissionList:{}
    },
    mutations:{

        setPermissionList(state,PermissionList){
            state.PermissionList = PermissionList
            // console.log("PermissionList:",PermissionList)
        }
    },
    action:{
        /*
        如果有异步操作，网络请求某个数据后，再进行赋值
        异步：同时进行，不用等待；
         */
        setList(context,value){
            context.commit("setPermissionList",value)
        }
    },
    getters:{
        getPermissionList(state){
            return state.PermissionList
        }
    }
}