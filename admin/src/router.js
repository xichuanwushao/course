import Vue from "vue"
import Router from "vue-router"
import Login from "./views/login.vue"
import Admin from "./views/admin.vue"
import Welcome from "./views/admin/welcome.vue"
import Course from "./views/admin/course.vue"
import Chapter from "./views/admin/chapter.vue"
import SectionLocal from "./views/admin/section-local.vue"
import SectionOss from "./views/admin/section-oss.vue"
import SectionVod from "./views/admin/section-vod.vue"
import Category from "./views/admin/category.vue"
import Teacher from "./views/admin/teacher.vue"
import File from "./views/admin/files.vue"
import User from "./views/admin/user.vue"
import Content from "./views/admin/content.vue"


Vue.use(Router);

export default new Router({
    mode: "history",
    base: process.env.BASE_URL,
    routes: [{
        path: "*",
        redirect: "/login",
    }, {
        path: "/login",
        component: Login
    }, {
        path: "/",
        name: "admin",
        component: Admin,
        children:[{
            path: "business/course",
            name: "business/course",
            component: Course,
        },{
            path: "welcome",
            name: "welcome",
            component: Welcome,
        },{
            path: "business/chapter",
            name: "business/chapter",
            component: Chapter,
        },{
            path: "business/section-local",
            name: "business/section-local",
            component: SectionLocal,
        },{
            path: "business/section-vod",
            name: "business/section-vod",
            component: SectionVod,
        },{
            path: "business/section-oss",
            name: "business/section-oss",
            component: SectionOss,
        },{
            path: "business/category",
            name: "business/category",
            component: Category,
        },{
            path: "business/teacher",
            name: "business/teacher",
            component: Teacher,
        },{
            path: "file/file",
            name: "file/file",
            component: File,
        },{
            path: "business/content",
            name: "business/content",
            component: Content,
        },{
            path: "system/user",
            name: "system/user",
            component: User,
        }]
    }
    ]
})
