import {createRouter,createWebHistory} from "vue-router";
import index from "@/components/Index";
import HomePage from "@/components/HomePage";
import SaleChance from "@/components/SaleChance";
import Cookies from "js-cookie";
import PersonCenter from "@/components/PersonCenter";
import CusDevPlan from "@/components/CusDevPlan";
import UserManagement from "@/components/UserManagement";
import RoleManagement from "@/components/RoleManagement";
import MenuManagement from "@/components/MenuManagement";
import CustomerInformation from "@/components/CustomerInformationManagement"
import CustomerLoss from "@/components/CustomerLoss";
import ServiceCreation from "@/components/ServiceCreation";
import ServiceAllocation from "@/components/ServiceAllocation"
import ServiceProcessing from "@/components/ServiceProcessing"
import ServiceFeedback from "@/components/ServiceFeedback"
import ServiceArchiving from "@/components/ServiceArchiving"
import CustomerContribution from "@/components/CustomerContribution";
import CustomerComposition from "@/components/CustomerComposition";
import CustomerService from "@/components/CustomerService";
import CustomerChurn from "@/components/CustomerChurn";
import CustomerOrders from "@/components/CustomerOrders";
import PersonalInfoManagement from "@/components/PersonalInfoManagement";
import DictionaryManagement from "@/components/DictionaryManagement";
const routes = [
    {
        path:"/",
        component:HomePage,
        name:"Login",
        requireAuth:false,
    },
    {
        path: "/index",
        component: index,
        name:"index",
        requireAuth: true,
        children:[
            {
                path:"/index/PersonCenter",
                component:PersonCenter,
                name:"PersonCenter",
                meta: {title: '个人中心',path:"/index/PersonCenter"},
            },
            {
                path: "/index/SaleChance",
                component: SaleChance,
                name: SaleChance,
                meta: {title: '营销机会管理',path:"/index/SaleChance"},
            },
            {
                path:"/index/CusDevPlan",
                component: CusDevPlan,
                name:CusDevPlan,
                meta: {title: '客户开发计划',path:"/index/CusDevPlan"},
            },
            {
                path: "/index/UserManagement",
                component: UserManagement,
                name:UserManagement,
                meta: {title: '用户管理',path:"/index/CusDevPlan"},
            },
            {
                path:"/index/RoleManagement",
                component: RoleManagement,
                name:RoleManagement,
                meta: {title: '角色管理',path:"/index/RoleManagement"},
            },
            {
                path: "/index/MenuManagement",
                component: MenuManagement,
                name: MenuManagement,
                meta: {title: '菜单管理',path: "/index/MenuManagement"},
            },


            // {
            //     label:"客户管理",
            //     children:[
            //         {
            //             path: "/index/CustomerInformation",
            //             component: CustomerInformation,
            //             name: CustomerInformation,
            //             meta: {title: '客户信息',path: "/index/CustomerInformation"},
            //         },
            //         {
            //             path: "/index/CustomerLoss",
            //             component: CustomerLoss,
            //             name: CustomerLoss,
            //             meta: {title: '客户流失',path: "/index/CustomerLoss"},
            //         },
            //         {
            //             path: "/index/CustomerOrders",
            //             component: CustomerOrders,
            //             name: CustomerOrders,
            //             meta: {title: '订单管理',path: "/index/CustomerOrders"},
            //         },
            //     ]
            // },

            {
                path: "/index/CustomerInformation",
                component: CustomerInformation,
                name: CustomerInformation,
                meta: {title: '客户信息',path: "/index/CustomerInformation"},
            },
            {
                path: "/index/CustomerLoss",
                component: CustomerLoss,
                name: CustomerLoss,
                meta: {title: '客户流失',path: "/index/CustomerLoss"},
            },
            {
                path: "/index/CustomerOrders",
                component: CustomerOrders,
                name: CustomerOrders,
                meta: {title: '订单管理',path: "/index/CustomerOrders"},
            },

            {
                path: "/index/ServiceCreation",
                component: ServiceCreation,
                name: ServiceCreation,
                meta: {title: '服务创建',path: "/index/ServiceCreation"},
            },
            {
                path: "/index/ServiceAllocation",
                component: ServiceAllocation,
                name: ServiceAllocation,
                meta: {title: '服务分配', path: "/index/ServiceAllocation"},
            },
            {
                path: "/index/ServiceProcessing",
                component: ServiceProcessing,
                name: ServiceProcessing,
                meta: {title: '服务处理',path: "/index/ServiceProcessing"},
            },
            {
                path: "/index/ServiceFeedback",
                component: ServiceFeedback,
                name: ServiceFeedback,
                meta: {title: '服务反馈',path: "/index/ServiceFeedback"},
            },
            {
                path: "/index/ServiceArchiving",
                component: ServiceArchiving,
                name: ServiceArchiving,
                meta: {title: '服务归档',path: "/index/ServiceArchiving"},
            },
            {
                path: "/index/CustomerContribution",
                component: CustomerContribution,
                name: CustomerContribution,
                meta: {title: '客户贡献',path: "/index/CustomerContribution"},
            },
            {
                path: "/index/CustomerComposition",
                component: CustomerComposition,
                name: CustomerComposition,
                meta: {title: '客户构成',path: "/index/CustomerComposition"},
            },
            {
                path: "/index/CustomerService",
                component: CustomerService,
                name: CustomerService,
                meta: {title: '客户服务',path: "/index/CustomerService"},
            },
            {
                path: "/index/CustomerChurn",
                component: CustomerChurn,
                name: CustomerChurn,
                meta: {title: '客户流失',path: "/index/CustomerChurn"},
            },

            {
                path: "/index/PersonalInfoManagement",
                component: PersonalInfoManagement,
                name: PersonalInfoManagement,
                meta: {title: '个人中心', path: "/index/PersonalInfoManagement"},
            },
            {
                path: "/index/DictionaryManagement",
                component: DictionaryManagement,
                name: DictionaryManagement,
                meta: {title: '字典管理', path: "/index/DictionaryManagement"},
            },
        ]
    }

]
const router = createRouter({
    history: createWebHistory(),
    routes
})
router.beforeEach((to,from,next)=>{
    // console.log("to为 ",to)
    // console.log("from为 ",from)
    // console.log("Cookie userIdStr为 ",Cookies.get("userIdStr"))
    // console.log("Cookie userName为" , Cookies.get("userName"))
    // console.log("Cookie trueName为 ",Cookies.get("trueName"))
        if (Cookies.get("userIdStr")!=undefined || to.fullPath==="/"){
            console.log(11111111111)
            next()
        }else {
            next({path:'/'})
        }
})
export {router};