<template>
  <div>
      <div style="padding: 10px 0">
          <el-input v-model="search_username" style="width: 200px" suffix-icon = "el-icon-search" placeholder="输入用户名查询"></el-input>
          <el-input class="ml-5" v-model="search_role" style="width: 200px" suffix-icon = "el-icon-search" placeholder="输入角色名查询"></el-input>
          <el-button class="ml-5" type="primary" @click="search_by_name">搜索</el-button>
      </div>

      <div class="pd-10">

          <el-button type="primary" @click="show_dialog_add" v-if="user.data.role === '管理员'">新增<i class="el-icon-circle-plus-outline"></i></el-button>
          <el-popconfirm
                  class="ml-5"
                  confirm-button-text = "确定"
                  cancel-button-text = "取消"
                  icon="el-icon-info"
                  icon-color="red"
                  title="您确定要删除吗?"
                  @confirm="delbatch_user"
          >
              <el-button type="danger" slot="reference">批量删除<i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
<!--          <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>-->
<!--          <el-button type="primary">导出 <i class="el-icon-top"></i></el-button>-->
      </div>
      <el-table :data="tableData" border strip header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55">
          </el-table-column>
          <el-table-column prop="id" label="ID" width="140">
          </el-table-column>
          <el-table-column prop="username" label="用户名" width="120">
          </el-table-column>
          <el-table-column prop="password" label="密码" width="120" v-if="user.data.role === '管理员'">
          </el-table-column>
          <el-table-column prop="role" label="角色" width="120">
          </el-table-column>
          <el-table-column>
              <template slot-scope="scope">
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

      <el-dialog title="用户信息" :visible.sync="add_dialogFormVisible" width="10%">
          <el-form label-width="60px" size="small">
              <el-form-item label="用户名">
                  <el-input v-model="userform.username" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="密码" v-if="user.data.role === '管理员'">
                  <el-input v-model="userform.password" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="角色">
                  <el-select clearable v-model="userform.role" palceholder="请选择" style="width: 100%">
                      <el-option v-for="item in roles" :key="item.name" :label="item.name" :value="item.name"></el-option>
                  </el-select>
              </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
              <el-button @click="add_dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="add_user">确 定</el-button>
          </div>
      </el-dialog>

      <el-dialog title="用户信息" :visible.sync="edit_dialogFormVisible" width="10%">
          <el-form label-width="60px" size="small">
              <el-form-item label="用户名">
                  <el-input v-model="userform.username" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="密码" v-if="user.data.role === '管理员'">
                  <el-input v-model="userform.password" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="角色">
                  <el-select clearable v-model="userform.role" palceholder="请选择" style="width: 100%">
                      <el-option v-for="item in roles" :key="item.name" :label="item.name" :value="item.name"></el-option>
                  </el-select>
              </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
              <el-button @click="edit_dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="edit_user">确 定</el-button>
          </div>
      </el-dialog>
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
            search_role: '',
            add_dialogFormVisible: false,
            edit_dialogFormVisible: false,
            userform: {
                username: '',
                password: '',
                id: '',
                role: '',
            },
            user: {

            },//本地记录
            multipleSelection: [],
            roles: []
        }
    },
    created() {
        this.user = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
        this.load()
        this.request.get("/role/page", {
            params: {
                pageNum : 1,
                pageSize : 1000,
                currole : this.user.data.role,
            }
        }).then(res => {
            this.roles = res.data.data
        })
    },
    methods: {
        load() {
            this.request.get("/user/page", {
                params: {
                    pageNum : this.pagenumber,
                    pageSize : this.pagesize,
                    role : this.search_role,
                    username : this.search_username,
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
        search_by_name() {
            this.load();
        },
        show_dialog_add() {
            this.userform = {username: '',password: '',role: ''}
            this.add_dialogFormVisible = true
        },
        show_dialog_edit(row) {
            this.userform = JSON.parse(JSON.stringify(row));
            this.edit_dialogFormVisible = true;
        },
        add_user(){
            this.add_dialogFormVisible = false;
            this.request.post("/user/add", {
                username : this.userform.username,
                password : this.userform.password,
                role : this.userform.role,
            }).then(res =>{
                if(res.code === 200){
                    this.$message.success("保存成功");
                    this.load();
                }else
                {
                    this.$message.error("保存失败")
                }
            })
        },
        edit_user() {
            this.edit_dialogFormVisible = false;
            this.request.post("/user/edit",{
                id : this.userform.id,
                username : this.userform.username,
                password : this.userform.password,
                role : this.userform.role,
            }).then(res => {
                if(res.code === 200){
                    this.$message.success("保存成功");
                    this.load();
                }else
                {
                    this.$message.error("保存失败")
                }
            })
        },
        del_user(row) {
            this.request.delete("/user/del", {
                params: {
                    id: row.id
                }
            }).then(res => {
                if(res.code === 200){
                    this.$message.success("删除成功");
                    this.load();
                }else
                {
                    this.$message.error("删除失败")
                }
            })
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        delbatch_user() {
            let ids = [];
            if(this.multipleSelection.length <= 0) {
                this.$message.error("未选中数据")
                return
            }
            ids = this.multipleSelection.map(v => v.id)
            this.request.delete("/user/delbatch" , {data: ids}).then(res => {
                if(res.code === 200){
                    this.$message.success("删除成功");
                    this.load();
                }else
                {
                    this.$message.error("删除失败")
                }
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