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
import welcome from "@/components/welcome";
import AllAnnouncements from "@/components/AllAnnouncements";
import Announcement from "@/components/Announcement";
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
                path:"/index/welcome",
                component:welcome,
                name:"welcome",
                meta: {
                    title: '个人中心',
                    path:"/index/welcome",
                    breadNumber: 0,
                    name:"初始页面"
                },
            },
            {
                path:"/index/PersonCenter",
                component:PersonCenter,
                name:"PersonCenter",
                meta: {
                    title: '个人中心',
                    path:"/index/PersonCenter",
                    breadNumber: 2,
                    name:"个人中心"
                },
            },
            {
                path: "/index/SaleChance",
                component: SaleChance,
                name: SaleChance,
                meta: {
                    title: '营销机会管理',
                    path:"/index/SaleChance",
                    breadNumber: 2,
                    name:"营销机会管理"
                },
            },
            {
                path:"/index/CusDevPlan",
                component: CusDevPlan,
                name:CusDevPlan,
                meta: {
                    title: '客户开发计划',
                    path:"/index/CusDevPlan",
                    breadNumber: 2,
                    name:"客户开发计划"
                },
            },
            {
                path: "/index/UserManagement",
                component: UserManagement,
                name:UserManagement,
                meta: {
                    title: '用户管理',
                    path:"/index/CusDevPlan",
                    breadNumber: 2,
                    name:"用户管理"
                },
            },
            {
                path:"/index/RoleManagement",
                component: RoleManagement,
                name:RoleManagement,
                meta: {
                    title: '角色管理',
                    path:"/index/RoleManagement",
                    breadNumber: 2,
                    name:"角色管理"
                },
            },
            {
                path: "/index/MenuManagement",
                component: MenuManagement,
                name: MenuManagement,
                meta: {
                    title: '菜单管理',
                    path: "/index/MenuManagement",
                    breadNumber: 2,
                    name:"菜单管理"
                },
            },
            {
                path: "/index/CustomerInformation",
                component: CustomerInformation,
                name: CustomerInformation,
                meta: {
                    title: '客户信息',
                    path: "/index/CustomerInformation",
                    breadNumber: 2,
                    name:"客户信息"
                },
            },
            {
                path: "/index/CustomerLoss",
                component: CustomerLoss,
                name: CustomerLoss,
                meta: {
                    title: '客户流失',
                    path: "/index/CustomerLoss",
                    breadNumber: 2,
                    name:"客户流失"
                },
            },
            {
                path: "/index/CustomerOrders",
                component: CustomerOrders,
                name: CustomerOrders,
                meta: {
                    title: '订单管理',
                    path: "/index/CustomerOrders",
                    breadNumber: 2,
                    name:"订单管理"
                },
            },
            {
                path: "/index/ServiceCreation",
                component: ServiceCreation,
                name: ServiceCreation,
                meta: {
                    title: '服务创建',
                    path: "/index/ServiceCreation",
                    breadNumber: 2,
                    name:"服务创建"
                },
            },
            {
                path: "/index/ServiceAllocation",
                component: ServiceAllocation,
                name: ServiceAllocation,
                meta: {
                    title: '服务分配',
                    path: "/index/ServiceAllocation",
                    breadNumber: 2,
                    name:"服务分配"
                },
            },
            {
                path: "/index/ServiceProcessing",
                component: ServiceProcessing,
                name: ServiceProcessing,
                meta: {
                    title: '服务处理',
                    path: "/index/ServiceProcessing",
                    breadNumber: 2,
                    name:"服务处理"
                },
            },
            {
                path: "/index/ServiceFeedback",
                component: ServiceFeedback,
                name: ServiceFeedback,
                meta: {
                    title: '服务反馈',
                    path: "/index/ServiceFeedback",
                    breadNumber: 2,
                    name:"服务反馈"
                },
            },
            {
                path: "/index/ServiceArchiving",
                component: ServiceArchiving,
                name: ServiceArchiving,
                meta: {
                    title: '服务归档',
                    path: "/index/ServiceArchiving",
                    breadNumber: 2,
                    name:"服务归档"
                },
            },
            {
                path: "/index/CustomerContribution",
                component: CustomerContribution,
                name: CustomerContribution,
                meta: {
                    title: '客户贡献',
                    path: "/index/CustomerContribution",
                    breadNumber: 2,
                    name:"客户贡献"
                },
            },
            {
                path: "/index/CustomerComposition",
                component: CustomerComposition,
                name: CustomerComposition,
                meta: {
                    title: '客户构成',
                    path: "/index/CustomerComposition",
                    breadNumber: 2,
                    name:"客户构成"
                },
            },
            {
                path: "/index/CustomerService",
                component: CustomerService,
                name: CustomerService,
                meta: {
                    title: '客户服务',
                    path: "/index/CustomerService",
                    breadNumber: 2,
                    name:"客户服务"
                },
            },
            {
                path: "/index/CustomerChurn",
                component: CustomerChurn,
                name: CustomerChurn,
                meta: {
                    title: '客户流失',
                    path: "/index/CustomerChurn",
                    breadNumber: 2,
                    name:"客户流失"
                },
            },
            {
                path: "/index/AllAnnouncements",
                component: AllAnnouncements,
                name: AllAnnouncements,
                meta: {
                    title: '所有公告',
                    path: "/index/AllAnnouncements",
                    breadNumber: 2,
                    name:"所有公告"
                },
            },
            {
                path: "/index/Announcement",
                component: Announcement,
                name: Announcement,
                meta: {
                    title: '发布公告',
                    path: "/index/Announcement",
                    breadNumber: 2,
                    name:"发布公告"
                },
            },
            {
                path: "/index/PersonalInfoManagement",
                component: PersonalInfoManagement,
                name: PersonalInfoManagement,
                meta: {
                    title: '个人中心',
                    path: "/index/PersonalInfoManagement",
                    breadNumber: 2,
                    name:"个人中心"
                },
            },
            {
                path: "/index/DictionaryManagement",
                component: DictionaryManagement,
                name: DictionaryManagement,
                meta: {
                    title: '字典管理',
                    path: "/index/DictionaryManagement",
                    breadNumber: 2,
                    name:"字典管理"
                },
            },
        ]
    }

]
const router = createRouter({
    history: createWebHistory(),
    routes
})
router.beforeEach((to,from,next)=>{
        if (Cookies.get("userIdStr")!=undefined || to.fullPath==="/"){
            next()
        }else {
            next({path:'/'})
        }
})
export {router};