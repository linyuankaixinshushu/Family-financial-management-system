<template>
    <div class="loginbody">
        <div class="logindata">
            <div class="logintext">
                <h2>WELCOME</h2>
            </div>
            <div class="formdata">
                <el-form ref="form" :model="form" :rules="rules">
                    <el-form-item prop="username">
                        <el-input
                                prefix-icon="el-icon-user"
                                v-model="form.username"
                                clearable
                                placeholder="请输入用户名"
                        ></el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input
                                prefix-icon="el-icon-lock"
                                v-model="form.password"
                                clearable
                                placeholder="请输入密码"
                                show-password
                        ></el-input>
                    </el-form-item>
                </el-form>
            </div>
            <div class="tool">
                <div>
                    <el-checkbox v-model="checked" @change="remenber"
                    >记住密码</el-checkbox
                    >
                </div>
                <div>
                    <span class="shou" @click="forgetpas">忘记密码？</span>
                </div>
            </div>
            <div class="butt">
                <el-button type="primary" @click.native.prevent="login()"
                >登录</el-button
                >
                <el-button class="shou" @click="register">注册</el-button>
            </div>
        </div>
    </div>
</template>

<script>
import {setRoutes} from "../router/index"
export default {
    name: "login",
    data() {
        return {
            form: {
                password: "",
                username: "",
            },
            checked: false,
            rules: {
                username: [
                    { required: true, message: "请输入用户名", trigger: "blur" },
                ],
                password: [
                    { required: true, message: "请输入密码", trigger: "blur" },
                ],
            },
        };
    },
    mounted() {
        if(localStorage.getItem("news")){
            this.form=JSON.parse(localStorage.getItem("news"))
            this.checked=true
        }
    },
    methods: {
        login() {
            this.$refs['form'].validate((valid) => {
                if(valid) {
                    this.request.post("/login", {
                        username: this.form.username,
                        password: this.form.password,
                    }).then(res => {
                        if(res.code === 200) {
                            localStorage.setItem("user", JSON.stringify(res.data));
                            setRoutes();
                            this.$message({type: "success", message: "登录成功😀", showClose: true})
                            this.$router.push('/')
                        }
                        else {
                            this.$message.error({message:res.message , showClose: true})
                        }
                    })
                } else {
                    this.$message({type: "error",message: "请填写用户名和密码",showClose: true})
                    return false;
                }

            })
        },
        remenber(data){
            this.checked=data
            if(this.checked){
                localStorage.setItem("news",JSON.stringify(this.form))
            }else{
                localStorage.removeItem("news")
            }
        },
        forgetpas() {
            this.$message({
                type:"info",
                message:"功能尚未开发额😥",
                showClose:true
            })
        },
        register() {
            this.$router.push('/register')
        }
    },
};
</script>

<style scoped>
.loginbody {
    width: 100%;
    height: 100%;
    min-width: 1000px;
    background-image: url("../assets/login_image/bg4.jpg");
    background-size: 100% 100%;
    background-position: center center;
    overflow: auto;
    background-repeat: no-repeat;
    position: fixed;
    line-height: 100%;
    padding-top: 150px;
}

.logintext {
    margin-bottom: 20px;
    line-height: 50px;
    text-align: center;
    font-size: 30px;
    font-weight: bolder;
    color: white;
    text-shadow: 2px 2px 4px #000000;
}

.logindata {
    width: 400px;
    height: 300px;
    transform: translate(-50%);
    margin-left: 50%;
}

.tool {
    display: flex;
    justify-content: space-between;
    color: #606266;
}

.butt {
    margin-top: 10px;
    text-align: center;
}

.shou {
    cursor: pointer;
    color: #606266;
}

/*ui*/
/* /deep/ .el-form-item__label {
  font-weight: bolder;
  font-size: 15px;
  text-align: left;
}

/deep/ .el-button {
  width: 100%;
  margin-bottom: 10px;

} */
</style>