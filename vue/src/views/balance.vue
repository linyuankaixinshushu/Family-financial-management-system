<template>
    <div>
        <div class="pd-10">
            <el-button type="primary" @click="show_dialog_add">新增<i class="el-icon-circle-plus-outline"></i></el-button>
        </div>
        <el-table :data="tableData" border strip header-cell-class-name="headerBg">
            <el-table-column prop="fromway" label="类别" width="140">
            </el-table-column>
            <el-table-column prop="value" label="余额" width="120">
            </el-table-column>
            <el-table-column>
                <template slot-scope="scope">
                    <el-popconfirm
                            class="ml-5"
                            confirm-button-text = "确定"
                            cancel-button-text = "取消"
                            icon="el-icon-info"
                            icon-color="red"
                            title="您确定要删除吗?"
                            @confirm="del_user(scope.row)">
                        <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

        <div style="padding: 10px 0" class="footer">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :page-sizes="[5, 10, 15, 20]"
                    :page-size="pagesize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>

        <el-dialog title="用户信息" :visible.sync="add_dialogFormVisible" width="20%">
            <el-form label-width="60px" size="small">
                <el-form-item label="账户">
                    <el-input v-model="form.fromway" autoc
                              omplete="off"></el-input>
                </el-form-item>
                <el-form-item label="金额">
                    <el-input v-model="form.value" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="add_dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="add_user">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>

export default {
    name: "User",
    data() {
        return {
            expandedkeys: [1,2,3,4,5,6,7,8,9,10,11,12,13],
            checkedkeys: [],
            tableData: [],
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse:false,
            sideWidth: 200,
            logotextshow: true,
            total: 0,
            pagenumber: 1,
            pagesize: 10,
            input: null,
            add_dialogFormVisible: false,
            menuDialogVisible: false,
            form: {
            },
            user: {
            },
            multipleSelection: [],
        }
    },
    created() {
        this.user = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
        this.load()
    },
    methods: {
        load() {
            this.request.get("/balance/page", {
                params: {
                    pageNum : this.pagenumber,
                    pageSize : this.pagesize,
                }
            }).then(res => {
                this.tableData = res.data.data
                this.total = res.data.count
            })
        },
        handleSizeChange(val) {
            this.pagesize = val;
            this.load();
        },
        handleCurrentChange(val) {
            this.pagenumber = val;
            this.load();
        },
        show_dialog_add() {
            this.add_dialogFormVisible = true
            this.form = {}
        },
        add_user(){
            this.add_dialogFormVisible = false;
            this.request.post("/balance/add", {
                username : this.user.data.username,
                fromway : this.form.fromway,
                value: this.form.value
            }).then(res =>{
                if(res.code === 200){
                    this.$message.success("保存成功");
                    this.load();
                }else {
                    this.$message.error(res.message);
                }
            })
        },
        del_user(row) {
            this.request.delete("/balance/del", {
                params: {
                    username : this.user.data.username,
                    fromway: row.fromway,
                }
            }).then(res => {
                if(res.code === 200){
                    this.$message.success("删除成功");
                    this.load();
                }else
                {
                    this.$message.error(res.message)
                }
            })
        },
    }
}
</script>

<style scoped>
.headerBg {
    background: #eee!important;
}
</style>