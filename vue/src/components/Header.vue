<template>
  <div style="font-size: 12px; line-height: 60px; display: flex">
      <div style="flex: 1; font-size: 18px">
          <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"> </span>
      </div>


      <el-dropdown style="width: 80px; cursor: pointer">
          <span>{{user.data.username}}</span><i class="el-icon-arrow-down" style="margin-right: 5px"></i>
          <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>个人信息</el-dropdown-item>
<!--              <router-link to="/login" style="text-decoration: none">-->
              <el-dropdown-item><span style="text-decoration: none" @click="logout">退出登录</span></el-dropdown-item>
<!--              </router-link>-->
          </el-dropdown-menu>
      </el-dropdown>
      <div style="margin-bottom: 30px">
      </div>
  </div>
</template>

<script>
export default {
    name: "Header",
    data() {
        return {
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
        }
    },
    props: {
        collapseBtnClass: String,
        collapse: Function,
    },
    methods: {
        logout() {
            this.$router.push("/login")
            localStorage.removeItem("user")
            this.$message.success({message: "退出成功",showClose :true})
        }
    }
}
</script>

<style scoped>

</style>