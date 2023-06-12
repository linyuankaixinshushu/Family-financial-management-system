
<template>
        <el-container style="min-height: 100vh">

            <el-aside :width="sideWidth + 'px'" style="background-color: rgb(238, 241, 246);">
                <Aside  :isCollapse="isCollapse" :logotextshow="logotextshow"/>
            </el-aside>

            <el-container>

                <el-header style="border-bottom: 1px solid #ccc" >
                    <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse"/>
                </el-header>

                <el-main >
                    <router-view />
                </el-main>
            </el-container>
        </el-container>
</template>


<script>


import Aside from "@/components/Aside.vue";
import Header from "@/components/Header.vue";

export default {
    name: 'HomeView',
    data() {
        return {
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logotextshow: true,
        }
    },
    created() {
        //请求分页查询
        // this.load()
    },
    components: {
        Aside, Header
    },
    methods: {
        collapse() {
            this.isCollapse = !this.isCollapse
            if (this.isCollapse) {
                this.sideWidth = 64;
                this.collapseBtnClass = 'el-icon-s-unfold'
                this.logotextshow = false;
            } else {
                this.sideWidth = 200;
                this.collapseBtnClass = 'el-icon-s-fold'
                this.logotextshow = true;
            }
        }
    },
}
</script>

