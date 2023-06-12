<template>
    <div>
        <div style="padding: 10px 0">
            <el-input v-model="search_kind" style="width: 200px" suffix-icon = "el-icon-search" placeholder="输入类别查询"></el-input>
            <el-input class="ml-5" v-model="search_username" style="width: 200px" suffix-icon = "el-icon-search" placeholder="输入用户名查询"></el-input>
            <el-date-picker
                    v-model="time"
                    type="datetimerange"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                    style="margin-left: 5px"
            >
            </el-date-picker>
            <el-button class="ml-5" type="primary" @click="search_by_name">搜索</el-button>
        </div>

<!--        <div class="pd-10">-->

<!--&lt;!&ndash;            <el-button type="primary" @click="show_dialog_add">新增<i class="el-icon-circle-plus-outline"></i></el-button>&ndash;&gt;-->
<!--            <el-popconfirm-->
<!--                    class="ml-5"-->
<!--                    confirm-button-text = "确定"-->
<!--                    cancel-button-text = "取消"-->
<!--                    icon="el-icon-info"-->
<!--                    icon-color="red"-->
<!--                    title="您确定要删除吗?"-->
<!--                    @confirm="delbatch_user"-->
<!--            >-->
<!--                <el-button type="danger" slot="reference">批量删除<i class="el-icon-remove-outline"></i></el-button>-->
<!--            </el-popconfirm>-->

<!--        </div>-->
        <el-table :data="tableData" border strip header-cell-class-name="headerBg">
<!--            <el-table-column type="selection" width="55">-->
<!--            </el-table-column>-->
            <el-table-column prop="id" label="ID" width="140">
            </el-table-column>
            <el-table-column prop="value" label="金额" width="120">
            </el-table-column>
            <el-table-column prop="time" label="时间" width="150">
            </el-table-column>
            <el-table-column prop="kind" label="类别" width="120">
            </el-table-column>
            <el-table-column prop="username" label="支出用户" width="120">
            </el-table-column>
            <el-table-column prop="description" label="描述" width="400">
            </el-table-column>
<!--            <el-table-column>-->
<!--                <template slot-scope="scope">-->
<!--                    <el-button type="success" @click="show_dialog_edit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>-->
<!--                    <el-popconfirm-->
<!--                            class="ml-5"-->
<!--                            confirm-button-text = "确定"-->
<!--                            cancel-button-text = "取消"-->
<!--                            icon="el-icon-info"-->
<!--                            icon-color="red"-->
<!--                            title="您确定要删除吗?"-->
<!--                            @confirm="del_user(scope.row)"-->
<!--                    >-->
<!--                        <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>-->
<!--                    </el-popconfirm>-->
<!--                </template>-->
<!--            </el-table-column>-->
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


<!--        <el-dialog title="支出信息" :visible.sync="add_dialogFormVisible" width="15%">-->
<!--            <el-form label-width="60px" size="small">-->
<!--                <el-form-item label="金额">-->
<!--                    <el-input v-model="userform.value" autocomplete="off"></el-input>-->
<!--                </el-form-item>-->
<!--                <el-form-item label="用户">-->
<!--                    <el-input v-model="userform.username" autocomplete="off"></el-input>-->
<!--                </el-form-item>-->
<!--                <el-form-item label="类别">-->
<!--                    <el-select clearable v-model="userform.kind" palceholder="请选择" style="width: 100%">-->
<!--                        <el-option v-for="item in kind" :key="item" :label="item" :value="item"></el-option>-->
<!--                    </el-select>-->
<!--                </el-form-item>-->
<!--                <el-form-item label="描述">-->
<!--                    <el-input v-model="userform.description" autocomplete="off"></el-input>-->
<!--                </el-form-item>-->
<!--                <el-form-item label="时间">-->
<!--                    <el-date-picker-->
<!--                            v-model="userform.time"-->
<!--                            type="datetime"-->
<!--                            placeholder="选择日期时间">-->
<!--                    </el-date-picker>-->
<!--                </el-form-item>-->
<!--            </el-form>-->
<!--            <div slot="footer" class="dialog-footer">-->
<!--                <el-button @click="add_dialogFormVisible = false">取 消</el-button>-->
<!--                <el-button type="primary" @click="add_report">确 定</el-button>-->
<!--            </div>-->
<!--        </el-dialog>-->
    </div>
</template>

<script>
export default {
    name: "User",
    data() {
        return {
            tableData: [],
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse:false,
            sideWidth: 200,
            logotextshow: true,
            total: 0,
            pagenumber: 1,
            pagesize: 10,
            search_username: '',
            search_kind: '',
            add_dialogFormVisible: false,
            userform: {

            },
            user: {

            },//本地记录
            multipleSelection: [],
            // kind:["投资","工资","人情","其他"]
            time: [],
        }
    },
    created() {
        this.user = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
        this.load()
    },
    methods: {
        load() {
            console.log(this.time)
            this.request.get("/incomedetail/page", {
                params: {
                    pageNum : this.pagenumber,
                    pageSize : this.pagesize,
                    kind : this.search_kind,
                    username : this.search_username,
                    begintime: this.time[0],
                    endtime: this.time[1],
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
        search_by_name() {
            this.load();
        },
        // show_dialog_add() {
        //     this.userform = {}
        //     this.add_dialogFormVisible = true
        // },
        // show_dialog_edit(row) {
        //     this.userform = JSON.parse(JSON.stringify(row));
        //     this.add_dialogFormVisible = true;
        // },
        // add_report(){
        //     this.add_dialogFormVisible = false;
        //     this.request.post("/incomedetail/save", {
        //         value : this.userform.value,
        //         time : this.userform.time,
        //         username : this.userform.username,
        //         kind : this.userform.kind,
        //         description: this.userform.description,
        //         id : this.userform.id
        //     }).then(res =>{
        //         if(res.code === 200){
        //             this.$message.success("保存成功");
        //             this.load();
        //         }else
        //         {
        //             this.$message.error(res.message);
        //         }
        //     })
        // },
        // del_user(row) {
        //     this.request.delete("/incomedetail/del", {
        //         params: {
        //             id: row.id
        //         }
        //     }).then(res => {
        //         if(res.code === 200){
        //             this.$message.success("删除成功");
        //             this.load();
        //         }else
        //         {
        //             this.$message.error("删除失败")
        //         }
        //     })
        // },
        // handleSelectionChange(val) {
        //     this.multipleSelection = val;
        // },
        // delbatch_user() {
        //     let ids = [];
        //     if(this.multipleSelection.length <= 0) {
        //         this.$message.error("未选中数据")
        //         return
        //     }
        //     ids = this.multipleSelection.map(v => v.id)
        //     this.request.delete("/spenddetail/delbatch" , {data: ids}).then(res => {
        //         if(res.code === 200){
        //             this.$message.success("删除成功");
        //             this.load();
        //         }else
        //         {
        //             this.$message.error("删除失败")
        //         }
        //     })
        // },

    }
}
</script>

<style scoped>
.headerBg {
    background: #eee!important;
}
</style>