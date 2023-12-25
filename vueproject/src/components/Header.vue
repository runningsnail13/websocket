<template>
    <div style="line-height: 60px; display: flex">
        <div style="flex: 1; ">
            <span :class="collapseBtnClass" style="cursor: pointer; font-size: 18px" @click="collapse"></span>

            <el-breadcrumb separator="/" style="display: inline-block" class="ml-10">
                <el-breadcrumb-item :to=" '/' ">首页</el-breadcrumb-item>
                <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <el-dropdown style="width: 100px; cursor: pointer">
            <div style="display: inline-block" >
                <img :src="user.avatarUrl" alt=""
                     style="width: 30px; border-radius: 50%; position: relative; top: 10px; right: 5px">
                <span>{{ user.nickname }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
            </div>
            <el-dropdown-menu v-slot="dropdown" style="width: 100px; text-align: center" >
                <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                    <router-link to="/person" style="text-decoration: none" >个人信息</router-link>
                </el-dropdown-item>
                <el-dropdown-item style="font-size: 14px; padding: 5px 0">
<!--                    <router-link to="/login" style="text-decoration: none" >退出</router-link>-->
<!--                    使用<router-link>标签包裹一个元素时，它会自动为该元素添加点击事件，并处理导航到指定的路由路径。-->
                    <span style="text-decoration: none" @click="logout">退出</span>
                </el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </div>
</template>

<script>

export default {
    name: "Header",
    props: {
        collapseBtnClass: String,
        collapse: null,
    },
    computed: {
        user() {
            return user
        },
        currentPathName () {
            return this.$store.state.currentPathName;　　//需要监听的数据
        }
    },
    data(){
      return {
          user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}//JSON.parse的作用是将 JSON 字符串转换为 JavaScript 对象
      }
    },
    methods :{
        logout(){//退出函数
            this.$router.push("/login")//重定向路由到登录界面
            localStorage.removeItem("user")//移除表项里的内容
            this.$message.success("退出成功")//提示
        }
    },
    watch: {
        currentPathName (newVal, oldVal) {
            console.log(newVal)
        }
    },
}
</script>

<style scoped>

</style>