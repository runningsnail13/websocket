<template>
    <div >
        <div style="margin: 10px 0">
            <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="username"></el-input>
            <!--            suffix-icon="el-icon-search" 是一个图标，可以去官网search; 这里v-model是一个数据绑定，它将框里面输入的内容赋值给username-->
            <el-input style="width: 200px" placeholder="请输入邮箱" suffix-icon="el-icon-message" v-model="email"></el-input>
            <el-input style="width: 200px" placeholder="请输入地址" suffix-icon="el-icon-position" v-model="address"></el-input>
            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <!--              使用@click来绑定点击事件，load是自定义的函数-->
            <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
        </div>

        <div style="margin: 10px 0">
            <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
            <el-popconfirm
                class="ml-5"
                confirm-button-text='确定'
                cancel-button-text='我再想想'
                icon="el-icon-info"
                icon-color="red"
                title="您确定删除吗？"
                @confirm="delBatch"
            >
                <template #reference>
                    <el-button type="danger" >批量删除 <i class="el-icon-remove-outline"></i></el-button>
                </template>
            </el-popconfirm>
            <el-upload action="http://localhost:9090/user/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">
                <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>
            </el-upload>
            <el-button type="primary" @click="exp" class="ml-5">导出<i class="el-icon-upload2"></i></el-button>
        </div>

        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="id" label="ID" width="80">
            </el-table-column>
            <el-table-column prop="avatarUrl" label="头像" width="140">
                <template v-slot="scope">
                    <img :src="scope.row.avatarUrl" alt=""
                         style="width: 30px; border-radius: 50%; position: relative; top: 10px; right: 5px"
                         v-if="scope.row.avatarUrl && scope.row.avatarUrl !== ''">
                    <i class="el-icon-user" v-else></i>
                </template>
            </el-table-column>

            <el-table-column prop="username" label="用户名" width="140" >
            </el-table-column>
            <el-table-column prop="nickname" label="昵称" width="120">
            </el-table-column>
            <el-table-column prop="email" label="邮箱" width="120">
            </el-table-column>
            <el-table-column prop="phone" label="电话" width="120">
            </el-table-column>
            <el-table-column prop="address" label="地址" width="120">
            </el-table-column>
            <el-table-column label="操作" >
                <template v-slot:default="scope">
                    <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i> </el-button>
                    <el-popconfirm
                        class="ml-5"
                        confirm-button-text='确定'
                        cancel-button-text='我再想想'
                        icon="el-icon-info"
                        icon-color="red"
                        title="您确定删除吗？"
                        @confirm="del(scope.row.id)"
                    >
                        <template #reference>
                            <el-button type="danger" >删除 <i class="el-icon-remove-outline"></i></el-button>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <div style="padding: 10px 0">
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="pageNum"
                :page-sizes="[2, 5, 10, 20]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
        </div>

        <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="40%">
            <el-form label-width="100px">
                <el-form-item label="用户名" size="small">
                    <el-input v-model="form.username" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="昵称" size="small">
                    <el-input v-model="form.nickname" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" size="small">
                    <el-input v-model="form.email" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="电话" size="small">
                    <el-input v-model="form.phone" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="地址" size="small">
                    <el-input v-model="form.address" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="undo">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: "User",
    data(){
        return {
            tableData: [],//存放数据库传入的表信息
            total: 0,//总条目信息
            pageNum: 1,//起始页
            pageSize: 5,//请求页长
            username:"",
            email:"",
            address:"",
            form: {},//修改/新增时用于提交的表项
            dialogFormVisible:false,//提交表项的对话框
            multipleselection:[],
            headerBg:'headerBg',
        }
    },
    created() {
        this.load();
    },
    methods:{
        load() {
            this.request.get("http://localhost:9090/user/page",{
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    username: this.username,
                    email: this.email,
                    address: this.address
                }
            }).then(res =>{
                console.log(res)

                this.tableData = res.records
                this.total = res.total
            })

        },
        //   load() {
        //       fetch("http://localhost:9090/user/page?pageNum="+this.pageNum+"&pageSize=" + this.pageSize + "&username=" + this.username+ "&email="+this.email+"&address="+this.address)
        //           .then(res => res.json()).then(res => {
        //           console.log(res)
        //           this.tableData = res.records
        //           this.total = res.total
        //       })
        //   },
        handleSizeChange(pageSize) {
            console.log(pageSize)
            this.pageSize = pageSize
            this.load()
        },
        handleCurrentChange(pageNum) {
            console.log(pageNum)
            this.pageNum = pageNum
            this.load()
        },
        reset(){//搜索框旁边的重置按钮
            this.username=""
            this.email=""
            this.address=""
            this.load()
        },
        handleSelectionChange(val){
            this.multipleselection=val//这里会将复选框的所有数据的所有信息传给multipleselection
        },
        handleAdd(){//新增按钮
            this.dialogFormVisible = true
            this.form = {}
        },
        delBatch(){//批量删除
            let ids=this.multipleselection.map(value => value.id)//由于批量删除只需要id,所以这里用map进行转换
            this.request.delete("http://localhost:9090/user/del/batch",{data: ids}).then(res => {
                if(res) {
                    this.$message.success("批量删除成功")
                    this.load()
                }
                else
                    this.$message.error("删除失败")
            })
        },
        del(id){//单项删除
            this.request.delete("http://localhost:9090/user/"+id).then(res => {
                if(res) {
                    this.$message.success("操作成功")
                    this.load()
                }
                else
                    this.$message.error("操作失败")
            })
        },
        save(){//表单提交，修改还是新增取决于form中有无id项
            this.request.post("http://localhost:9090/user",this.form).then(res => {
                if(res) {
                    this.$message.success("操作成功")
                    this.dialogFormVisible = false
                    this.load()
                }
                else
                    this.$message.error("操作失败")
            })
        },
        undo(){
            this.dialogFormVisible=false
            this.load()
        },
        handleEdit(row){//编辑按钮，这里直接将这一行赋值给form,由于这一行包括了id,所以在save提交后会是修改而非新增
            this.form=row
            this.dialogFormVisible=true;
        },
        exp(){//导出
            window.open("http://localhost:9090/user/export")
        },
        handleExcelImportSuccess() {
            this.$message.success("导入成功")
            this.load()
        }
    }

}
</script>

<style>
.headerBg{
    background: #eee!important;
}
</style>