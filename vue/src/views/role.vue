<template>
    <div>
        <div class="pd-10">
            <el-button type="primary" @click="show_dialog_add">新增<i class="el-icon-circle-plus-outline"></i></el-button>
        </div>
        <el-table :data="tableData" border strip header-cell-class-name="headerBg" >
            <el-table-column prop="id" label="ID" width="140">
            </el-table-column>
            <el-table-column prop="name" label="角色名" width="120">
            </el-table-column>
            <el-table-column prop="description" label="描述" width="120">
            </el-table-column>
            <el-table-column>
                <template slot-scope="scope">
                    <el-button type="primary" @click="menu_select(scope.row)">权限管理<i class="el-icon-menu"></i></el-button>
                    <el-button type="success" @click="show_dialog_edit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
                    <el-popconfirm
                            class="ml-5"
                            confirm-button-text = "确定"
                            cancel-button-text = "取消"
                            icon="el-icon-info"
                            icon-color="red"
                            title="您确定要删除吗?"
                            @confirm="del_user(scope.row)"
                    >
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
                <el-form-item label="角色名">
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="form.description" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="add_dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="add_user">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="菜单分配" :visible.sync="menuDialogVisible" width="30%">
        <el-tree
            :data = "menudata"
            show-checkbox = "true"
            :default-expanded-keys="expandedkeys"
            node-key = "id"
            ref="tree"
        >
            <!--            :default-checked-keys="checkedkeys"-->
        </el-tree>
        <div slot="footer" class="dialog-footer">
            <el-button @click="menuDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="setrolemenu">确 定</el-button>
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
            roleid: 0,
            multipleSelection: [],
            menudata: [
                {
                    id: 1,
                    label: '主页',
                    children: []
                },
                {
                    id: 2,
                    label: '余额信息',
                },
                {
                    id: 3,
                    label: '家庭管理',
                    children: [
                        {
                            id: 6,
                            label: '家人管理',
                        },
                        {
                            id:7,
                            label: '角色管理',
                        }
                    ]
                },
                {
                    id: 4,
                    label: '支出管理',
                    children: [
                        {
                            id:8,
                            label: '我的支出',
                        },
                        {
                            id:9,
                            label: '家庭支出',
                        },
                        {
                            id:10,
                            label: '支出报表'
                        }
                    ]
                },
                {
                    id: 5,
                    label: '收入管理',
                    children: [
                        {
                            id:11,
                            label: '我的收入',
                        },
                        {
                            id:12,
                            label: '家庭收入',
                        },
                        {
                            id:13,
                            label: '收入报表',
                        }
                    ]
                },
            ]
        }
    },
    created() {
        this.user = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
        this.load()
    },
    methods: {
        load() {
            this.request.get("/role/page", {
                params: {
                    pageNum : this.pagenumber,
                    pageSize : this.pagesize,
                    currole : this.user.data.role,
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
        show_dialog_edit(row) {
            this.form = JSON.parse(JSON.stringify(row));
            this.add_dialogFormVisible = true;
        },
        add_user(){
            this.add_dialogFormVisible = false;
            this.request.post("/role/save", {
                name : this.form.name,
                describe : this.form.description,
                id: this.form.id
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
            this.request.delete("/role/del", {
                params: {
                    id: row.id
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
        menu_select(row) {
            this.form = row;
            this.roleid = row.id;
            this.checkedkeys = [];
            this.request.get("/role/check/" + row.id).then(res => {
                let ids = [];
                for(let i = 0; i < res.data.length; i++)
                {
                    ids.push(res.data[i].menuid);
                }
                for(let i = 0; i < ids.length; i++){
                    if(ids[i] === 3 || ids[i] === 4 || ids[i] ===5)
                    {
                        ids.splice(i,1);
                        i--;
                    }
                }
                this.$refs.tree.setCheckedKeys(ids);
            })
            this.menuDialogVisible = true;
        },
        setrolemenu(){
            const AllcheckedNodes = this.$refs.tree.getCheckedNodes(false,true);
            var menuids = [];
            for(var i = 0; i <AllcheckedNodes.length;i++) {
                menuids.push(AllcheckedNodes[i].id);
            }
            this.request.post("/role/rolemenu/" + this.roleid, menuids).then(res =>{
                if(res.code === 200){
                    this.$message.success("保存成功")
                }
                else{
                    this.$message.error("保存失败")
                }
                console.log(this.user.data.role)
                console.log(this.form.name)
                if(this.user.data.role === this.form.name)
                {
                    this.$message.success("修改了自己的角色，请重新登录！")
                    this.$router.push('/login')
                }
                this.menuDialogVisible = false;
            })
        }
    }
}
</script>

<style scoped>
.headerBg {
    background: #eee!important;
}
</style>