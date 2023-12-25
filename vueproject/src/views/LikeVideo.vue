<template>
    <div>
        <div style="margin: 10px 0">
            <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <el-button type="warning" @click="reset">重置</el-button>
        </div>

        <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="id" label="ID" width="80"></el-table-column>
            <el-table-column prop="name" label="视频名称"></el-table-column>
            <el-table-column prop="type" label="文件类型"></el-table-column>
            <el-table-column prop="size" label="文件大小(kB)"></el-table-column>
            <el-table-column label="预览投稿">
                <template v-slot="scope">
                    <el-button type="primary" @click="preview(scope.row.url)">预览</el-button>
                </template>
            </el-table-column>
            <el-table-column label="下载">
                <template v-slot:default="scope">
                    <el-button type="primary" @click="download(scope.row.url)">下载</el-button>
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

    </div>
</template>

<script>
export default {
    name: "LikeVideo",
    data() {
        return {
            tableData: [],
            name: '',
            multipleSelection: [],
            pageNum: 1,
            pageSize: 10,
            total: 0,
            likeVideoIds: [],
            userid: 0,
            user : localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
        }
    },
    created() {
        this.request.get("http://localhost:9090/user/username/"+this.user.username).then(res =>{
            this.userid=res.data.id
            this.load()
        })
    },
    methods: {
        load() {
            this.request.get("http://localhost:9090/like/findLikesByUserId/" + this.userid).then(res => {
                this.request.get("http://localhost:9090/file/like-videos-page", {
                    params: {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                        name: this.name,
                        ids: res.join(",")
                    }
                }).then(res => {
                    this.tableData = res.data.records;
                    console.log(this.tableData);
                    this.total = res.data.total;
                }).catch(error => {
                    console.log(error);
                });
            }).catch(error => {
                console.log(error);
            });

        },
        changeEnable(row) {
            this.request.post("http://localhost:9090/file/update", row).then(res => {
                if (res.code === '200') {
                    this.$message.success("操作成功")
                }
            })
        },
        del(id) {
            this.request.delete("http://localhost:9090/file/" + id).then(res => {
                if (res.code === '200') {
                    this.$message.success("删除成功")
                    this.load()
                } else {
                    this.$message.error("删除失败")
                }
            })
        },
        handleSelectionChange(val) {
            console.log(val)
            this.multipleSelection = val
        },
        delBatch() {
            let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
            this.request.post("http://localhost:9090/file/del/batch", ids).then(res => {
                if (res.code === '200') {
                    this.$message.success("批量删除成功")
                    this.load()
                } else {
                    this.$message.error("批量删除失败")
                }
            })
        },
        reset() {
            this.name = ""
            this.load()
        },
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
        handleFileUploadSuccess(res) {
            console.log(res)
            this.load()
        },
        download(url) {
            window.open(url)
        },
        preview(url) {
            window.open('https://file.kkview.cn/onlinePreview?url=' + encodeURIComponent(window.btoa((url))))
        },
    }
}
</script>

<style scoped>

</style>
