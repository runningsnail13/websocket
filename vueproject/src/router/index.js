import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";
import HomeView from '../views/Manage.vue'

Vue.use(VueRouter)

const routes = [//路由配置指的是用户选择什么路径，浏览器就会选择这个路径下的vue组件进行配置
  {
    path: '/',
    name: 'Manage',
    component: () => import('../views/Manage.vue'),//用户访问根目录时，会先渲染manage组件
    redirect:"/home",//通过redirect属性将根路径/重定向到了/home路径。这意味着当用户访问根路径时，会自动跳转到/home路径，而无需显示根路径的内容。但是这个时候Aside组件和Header组件已经在manage中被渲染了，会一直存在，而user组件由于需要特定路由而不出现
    children: [
      {path: 'user', name: '用户管理', component: () => import('../views/User.vue')},
      {path: 'home', name: '首页', component: () => import('../views/Home.vue'),},
      {path: 'person', name: '个人信息', component: () => import('../views/Person.vue'),},
      {path: 'file', name: '投稿管理', component: () => import('../views/File.vue'),},
      {path: 'userupload', name: '我的投稿', component: () => import('../views/UserUpload.vue'),},
      {path: 'im', name: '聊天', component: () => import("../views/Im.vue"),},
      {path: 'likevideo', name: '点赞视频', component: () => import("../views/LikeVideo.vue"),},
    ]
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/Register.vue')
  },
  {
    path: '/admin',
    name: 'admin',
    component: () => import('../views/admin.vue')
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  localStorage.setItem("currentPathName", to.name)  // 设置当前的路由名称，为了在Header组件中去使用
  store.commit("setPath")  // 触发store的数据更新
  next()  // 放行路由
})

export default router

/* 关于URL的知识
URL的结构：URL由多个部分组成，包括协议（protocol）、主机（host）、端口（port）、路径（path）、查询参数（query parameters）和片段标识（fragment identifier）等。例如，https://www.example.com:8080/path/to/resource?param1=value1#fragment。

1.协议（Protocol）：指定了用于访问资源的协议或规则。常见的协议包括HTTP、HTTPS、FTP、SMTP等。

2.主机（Host）：表示资源所在的服务器的域名或IP地址。例如，www.example.com是一个主机名。

3.端口（Port）：用于标识服务器上的特定服务。默认的HTTP端口是80，HTTPS端口是443，但可以在URL中指定其他端口号。

4.路径（Path）：表示资源在服务器上的位置。它是由斜杠分隔的一系列目录和文件名组成。例如，/path/to/resource是一个路径。

5.查询参数（Query Parameters）：用于向服务器传递额外的参数。它们通常以key=value的形式出现，并使用&符号分隔多个参数。例如，?param1=value1&param2=value2。

6.片段标识（Fragment Identifier）：用于指定文档内的特定位置或锚点。它以#符号开头，后面跟着一个标识符。例如，#fragment。

7.URL编码：当URL包含特殊字符或非ASCII字符时，需要进行URL编码以确保安全和正确传输。编码后的字符以百分号（%）和两位十六进制数表示。

8.绝对URL和相对URL：绝对URL包含完整的信息，可以直接访问资源。相对URL相对于当前页面或基本URL，需要结合基本URL来构建完整的URL。

9.URL重定向：服务器可以通过HTTP状态码（如302 Found）将一个URL重定向到另一个URL。这可以用于将用户导航到新的资源位置。

 */