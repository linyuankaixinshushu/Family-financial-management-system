<template>
    <el-menu :default-openeds="['1', '3']" style="min-height: 100%; overflow-x: hidden "
             background-color="rgb(48,65,86)"
             text-color="#fff"
             active-text-color="#ffd04b"
             :collapse-transition="false"
             :collapse="isCollapse"
             router
     >
        <div style="height: 60px; line-height: 60px; text-align: center">
            <img src="../assets/linyuankaixinshushu.png" alt="" style="width: 20px; position: relative; top: 5px; margin-right: 5px">
            <b style="color: #fff" v-show="logotextshow">家庭财务管理系统</b>
        </div>
        <div v-for="item in all_menus" :key="item.index">
            <div v-if="item.path && menus.includes(item.index)">
                <el-menu-item :index="item.path" >
                    <template slot="title">
                        <i :class="item.icon"></i>
                        <span slot="title">&nbsp&nbsp&nbsp&nbsp{{item.name}}</span>
                    </template>
                </el-menu-item>
            </div>
            <div v-else-if="menus.includes(item.index)">
                <el-submenu :index="item.index" >
                    <template slot="title">
                        <i :class="item.icon"></i>
                        <span slot="title">&nbsp&nbsp&nbsp&nbsp{{item.name }}</span>
                    </template>
                    <div v-for="sitem in item.children" :key="sitem.index">
                        <el-menu-item :index="sitem.path" v-if="menus.includes(sitem.index)" >
                            <i :class="sitem.icon"></i>
                            <span slot="title">{{sitem.name}}</span>
                        </el-menu-item>
                    </div>
                </el-submenu>
            </div>
        </div>

    </el-menu>
</template>

<script>
import global from "@/config/global.js";
export default {
    name: "Aside",
    props: {
        logotextshow: Boolean,
        isCollapse: Boolean,
    },
    data() {
        return {
            menus:[],
            all_menus: global.all_menus

        }
    },
    created() {
        this.users = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
        this.menus = this.users.menu;
    },
}
</script>

<style scoped>

</style>