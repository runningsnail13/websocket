<template>
    <div style="padding: 10px; margin-bottom: 50px">
        <el-row>
            <el-col :span="8">
                <el-card style="width: 100%; min-height: 300px; color: #333">
                    <div style="padding-bottom: 10px; border-bottom: 1px solid #ccc">在线用户<span style="font-size: 12px"></span></div>
                    <div style="padding: 10px 0" >
                        <span>{{ user.username }}</span>
                        <i class="el-icon-user" style="margin-left: 10px; font-size: 16px; cursor: pointer"
                        ></i>
                        <span>（本人）</span>
                    </div>
                    <div style="margin-top: 10px; border-top: 1px solid #ccc">
                    <div style="padding: 10px 0;  " v-for="user in users" :key="user.username">
                        <span>{{ user.username }}</span>
                        <i class="el-icon-chat-dot-round" style="margin-left: 10px; font-size: 16px; cursor: pointer"
                           ></i>
                    </div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="16">
                <div style="width: 700px; margin: 0 auto; background-color: white;
                    border-radius: 5px; box-shadow: 0 0 10px #ccc">
                    <div style="text-align: center; line-height: 50px;">
                        Web聊天室
                    </div>
                    <div style="height: 350px; overflow:auto; border-top: 1px solid #ccc" v-html="content"></div>
                    <div style="height: 200px">
            <textarea v-model="text" style="height: 160px; width: 100%; padding: 20px; border: none; border-top: 1px solid #ccc;
             border-bottom: 1px solid #ccc; outline: none"></textarea>
                        <div style="text-align: right; padding-right: 10px">
                            <el-button type="primary" size="mini" @click="send">发送</el-button>
                        </div>
                    </div>
                </div>
            </el-col>
        </el-row>
    </div>
</template>
<script>
import request from "@/utils/request";
let socket;
export default {
    name: "Im",
    data() {
        return {
            circleUrl: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            isCollapse: false,
            users: [],
            chatUser: '',
            text: "",
            messages: [],
            content: ''
        }
    },
    created() {
        this.init()
    },
    methods: {
        send() {
            if (!this.text) {
                this.$message({type: 'warning', message: "请输入内容"})
            } else {
                if (typeof (WebSocket) == "undefined") {
                    console.log("您的浏览器不支持WebSocket");
                } else {
                    console.log("您的浏览器支持WebSocket");
                    // 组装待发送的消息 json
                    // {"from": "zhang", "to": "admin", "text": "聊天文本"}
                    let message = {from: this.user.username, text: this.text}
                    socket.send(JSON.stringify(message));  // 将组装好的json发送给服务端，由服务端进行转发
                    this.messages.push({user: this.user.username, text: this.text})
                    // 构建消息内容，本人消息
                    this.createContent(null, this.user.username, this.text)
                    this.text = '';
                }
            }
        },
        createContent(remoteUser, nowUser, text) {  // 这个方法是用来将 json的聊天消息数据转换成 html的。
            let html
            // 当前用户消息
            if (nowUser) { // nowUser 表示是否显示当前用户发送的聊天消息，绿色气泡
                html = "<div class=\"el-row\" style=\"padding: 5px 0\">\n" +
                    "<div class=\"el-col el-col-18\" style=\"display:flex; align-items:center; justify-content:flex-end;\">\n" +
                    "    <div class=\"tip left\">" + text + "</div>\n" +
                    "    <div style=\"padding-left:5px; padding-right:5px; margin-right:5px; border:1px solid #ccc; background-color: #f2f2f2; border-radius: 5px;\">" + nowUser + "</div>\n" +
                    " </div>\n" +
                    " <div class=\"el-col el-col-2\">\n" +
                    "   <span class=\"el-avatar el-avatar--circle\" style=\"height: 40px; width: 40px; line-height: 40px;\">\n" +
                    "      <img src=\"https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png\" style=\"object-fit: cover;\">\n" +
                    "   </span>\n" +
                    " </div>\n" +
                    "</div>";
            } else if (remoteUser) {   // remoteUser表示远程用户聊天消息，蓝色的气泡
                html = "<div class=\"el-row\" style=\"display:flex; align-items:center; padding: 5px 0;\">\n" +
                    "  <div class=\"el-col el-col-2\" style=\"text-align: right;\">\n" +
                    "  <span class=\"el-avatar el-avatar--circle\" style=\"height: 40px; width: 40px; line-height: 40px;\">\n" +
                    "    <img src=\"https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png\" style=\"object-fit: cover;\">\n" +
                    "  </span>\n" +
                    "  </div>\n" +
                    "  <div style=\"padding-left:5px; padding-right:5px; margin-left:5px; border:1px solid #ccc; background-color: #f2f2f2; border-radius: 5px;\">" + remoteUser + "</div>\n" +
                    "  <div class=\"el-col el-col-18\" style=\"text-align: left;\">\n" +
                    "    <div class=\"tip right\">" + text + "</div>\n" +
                    "  </div>\n" +
                    "</div>";
            }
            console.log(html)
            this.content += html;
        },
        init() {
            this.user = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
            console.log(this.user)
            this.circleUrl=this.user.avatarUrl;
            let username = this.user.username;
            console.log(this.user)
            console.log("hello")
            let _this = this;
            if (typeof (WebSocket) == "undefined") {
                console.log("您的浏览器不支持WebSocket");
            } else {
                console.log("您的浏览器支持WebSocket");
                let socketUrl = "ws://localhost:9090/imserver/" + username;
                if (socket != null) {
                    socket.close();
                    socket = null;
                }
                // 开启一个websocket服务
                socket = new WebSocket(socketUrl);
                //打开事件
                socket.onopen = function () {
                    console.log("websocket已打开");
                };
                //  浏览器端收消息，获得从服务端发送过来的文本消息
                socket.onmessage = function (msg) {
                    console.log("收到数据====" + msg.data)
                    let data = JSON.parse(msg.data)  // 对收到的json数据进行解析， 类似这样的： {"users": [{"username": "zhang"},{ "username": "admin"}]}
                    if (data.users) {  // 获取在线人员信息
                        _this.users = data.users.filter(user => user.username !== username)  // 获取当前连接的所有用户信息，并且排除自身，自己不会出现在自己的聊天列表里
                    } else {
                        console.log("数据是什么？"+msg.data)
                        // 如果服务器端发送过来的json数据 不包含 users 这个key，那么发送过来的就是聊天文本json数据
                        //  // {"from": "zhang", "text": "hello"}
                        _this.messages.push(data)
                        // 构建消息内容
                        _this.createContent(data.from, null, data.text)
                    }
                };
                //关闭事件
                socket.onclose = function () {
                    console.log("websocket已关闭");
                };
                //发生了错误事件
                socket.onerror = function () {
                    console.log("websocket发生了错误");
                }
            }
        }
    }
}
</script>
<style>
.tip {
    color: white;
    text-align: center;
    border-radius: 10px;
    font-family: sans-serif;
    padding: 10px;
    width:auto;
    display:inline-block !important;
    display:inline;
}
.right {
    background-color: deepskyblue;
}
.left {
    background-color: forestgreen;
}
</style>
