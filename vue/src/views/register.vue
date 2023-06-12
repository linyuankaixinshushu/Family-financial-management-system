<template>
    <div class="loginbody">
        <div class="logindata">
            <div class="logintext">
                <h2>Welcome</h2>
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
                    <el-form-item prop="password2">
                        <el-input
                                prefix-icon="el-icon-lock"
                                v-model="form.password2"
                                clearable
                                placeholder="确认密码"
                                show-password
                        ></el-input>
                    </el-form-item>
                </el-form>
            </div>
            <div class="tool">
                <div>
                    <span>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</span>
                    <span class="shou" @click="$router.push('/login')">已经有帐号了?登录</span>
                </div>
            </div>
            <div class="butt">
                <el-button type="primary" @click.native.prevent="register(form)">注册</el-button>
                <el-button class="shou" @click="clearall">清空</el-button>
            </div>
        </div>
    </div>
</template>

<script>

export default {
    name: "Register",
    data() {
        var checkUsername = (rule, username, callback) => {
            this.request.post("/checkusername", {username}).then(res => {
                if(res.code != 200) {
                    return callback(new Error(res.message))
                }
                else {
                    callback()
                }
            })
        };
        var passwordagain = (rule, value, callback) => {
            if(this.form.password === this.form.password2) {
                callback()
            }
            else {
               return callback(new Error("两次输入的密码不一致"))
            }
        }
        return {
            form: {
                password: "",
                username: "",
                password2: "",
            },
            rules: {
                username: [
                    { required: true, message: "请输入用户名", trigger: "blur" },
                    { max: 32, message: "不能大于32个字符", trigger: "blur" },
                    { validator: checkUsername, trigger: 'blur'},
                    { pattern: /^[A-Za-z0-9\u4e00-\u9fa5_.]+$/, message: '不允许输入空格等特殊符号',trigger: "blur"}
                ],
                password: [
                    { required: true, message: "请输入密码", trigger: "blur" },
                    { min: 6, max: 16, message: "大于6个字符，小于16个字符,不能含有中文,除._的特殊符号", trigger: "blur" },
                    { pattern: /^[A-Za-z0-9_.]+$/, message: '不允许输入空格等特殊符号',trigger: 'blur'}
                ],
                password2: [
                    { required: true, message: "请再次输入密码", trigger: "blur" },
                    { validator: passwordagain, trigger: 'blur'}
                ],
            },
        };
    },
    methods: {
        register(form) {
            this.$refs['form'].validate((valid) => {
                if(valid) {
                    this.request.post("/register", {
                        username: this.form.username,
                        password: this.form.password,
                    }).then(res => {
                        if(res.code === 200) {
                            this.$message({type: "success",message: "注册成功,跳转到登录页面", showClose: true})
                            this.$router.push('/login')
                        }
                    })
                } else {
                    this.$message({type: "error",message: "请按照规则填写",showClose: true})
                    return false;
                }

            })
        },
        clearall() {
            this.form = {}
            this.$message.info({message:"已经为您清空选项", showClose: true})
        }
    }
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
