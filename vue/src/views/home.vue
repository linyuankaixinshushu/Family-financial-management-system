<template>
  <div>
      <el-row>
          <el-card>
              <div style="color: #409EFF;text-align: left">你好，{{user.data.username}}!欢迎使用家庭财务管理系统!</div>
          </el-card>
      </el-row>
      <el-row style="margin-top: 20px">
          <el-col :span="3">
              <el-card>
                  <div style="color: #409EFF;"><h1>今日支出：</h1></div>
                  <div style="text-align: center; padding: 10px; font-weight: normal" >￥{{todayspend}}</div>
              </el-card>

          </el-col>
          <el-col :span="3" :offset="1">
              <el-card>
                  <div style="color: #409EFF;"><h1>今日收入：</h1></div>
                  <div style="text-align: center; padding: 10px; font-weight: normal" >￥{{todayincome}}</div>
              </el-card>
          </el-col>
      </el-row>
      <el-row style="margin-top: 20px">
          <el-col :span="9">
              <el-card>
                  <div style="display: flex;margin-left: 30px">
                      <div style="font-weight: bold"><h2>支出与收入</h2></div>
                      <div style="margin-left: 50px;">
                          <el-date-picker
                          v-model="incomeandspendechartstime"
                          type="week"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="选择周" @change="selectweekchange">
                          </el-date-picker>
                      </div>
                  </div>
              <div style="width: 400px; height: 400px;margin-top: 20px" >
                    <v-chart  :options="option1">
                    </v-chart>
              </div>
              </el-card>
          </el-col>
          <el-col :span="14" :offset="1">
              <el-card>
                  <div style="display: flex;margin-left: 30px">
                  <div style="font-weight: bold"><h2>支出与收入构成</h2></div>
                  <div style="margin-left: 50px;">
                      <el-date-picker
                          v-model="time"
                          type="datetimerange"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          range-separator="至"
                          start-placeholder="开始日期"
                          end-placeholder="结束日期"
                          @change="selecttimechange"
                      >
                      </el-date-picker>
                  </div>
                  </div>
                  <div style="display: flex">
                  <div style="width: 400px; height: 400px;margin-top: 20px">
                      <v-chart  :options="option2">
                      </v-chart>
                  </div>
                      <div style="width: 400px; height: 400px; margin-left: 60px;margin-top: 20px">
                          <v-chart  :options="option3">
                          </v-chart>
                      </div>
                  </div>
              </el-card>
          </el-col>
      </el-row>
  </div>
</template>

<script>

export default {
    name: "Home.VUE",
    data() {
        return {
            incomeandspendechartstime: '',
            incomeechartsx: [],
            spendechartsx: [],
            surplusechartsx:[],
            todayspend: '',
            todayincome: '',
            nowdate:'',
            time:[],
            option2data: [],
            option3data: [],
            incomekind: ["投资","工资","人情","其他"],
        };
    },
    computed: {
        option1() {
            return {
                // title: {
                //     text: '收入和支出',
                //     top: 'bottom',
                //     left: '45%'
                // },
                xAxis: {
                    type: 'category',
                    data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
                },
                yAxis: {},
                series: [
                    {
                        type: 'line',
                        data: this.incomeechartsx,
                        itemStyle: {
                            normal: {
                                label: {
                                    show : true,
                                },
                                color: '#409EFF',
                            }
                        }
                    },
                    {
                        type: 'line',
                        data: this.spendechartsx,
                        itemStyle: {
                            normal: {
                                label: {
                                    show : true,
                                },
                                color: 'red',
                            }
                        }
                    },
                    {
                        type: 'bar',
                        data: this.surplusechartsx,
                        itemStyle: {
                            normal: {
                                label: {
                                    show : true,
                                },
                                color: 'green',
                            }
                        }
                    }
                ],
            }
        },
        option2 () {
            return {
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left'
                },
                series: [
                    {
                        name: '收入构成',
                        type: 'pie',
                        radius: '50%',
                        data: this.option2data,
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
        },
        option3 () {
            return {
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left'
                },
                series: [
                    {
                        name: '支出构成',
                        type: 'pie',
                        radius: '50%',
                        data: this.option3data,
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
        }
    },
    methods: {
        selectweekchange() {
            this.loadischartsdata(this.incomeandspendechartstime)
        },
        loadischartsdata(time) {
            this.request.get("/echarts/incomeandspend", {
                params:{
                    begintime: time,
                    username: this.user.data.username,
                }
            }).then(res => {
                this.incomeechartsx = res.data.weekincome;
                this.spendechartsx = res.data.weekspend;
                this.surplusechartsx = res.data.weeksurplus;
            })
        },
        loadtodayspend() {
            this.request.get("/spend/today",{
                params: {
                    username: this.user.data.username,
                    date: this.nowdate
                }
            }).then(res => {
                this.todayspend = res.data;
            })
        },
        loadtodayincome() {
            this.request.get("/income/today",{
                params: {
                    username: this.user.data.username,
                    date: this.nowdate
                }
            }).then(res => {
                this.todayincome = res.data;
            })
        },
        selecttimechange() {
            this.loadoption2(this.time)
            this.loadoption3(this.time)
        },
        loadoption2(time) {
            this.request.get("/echarts/income",{
                params:{
                    username: this.user.data.username,
                    begintime: time[0],
                    endtime: time[1],
                }
            }).then(res => {
                this.option2data = res.data;
            })
        },
        loadoption3(time) {
            this.request.get("/echarts/spend",{
                params:{
                    username: this.user.data.username,
                    begintime: time[0],
                    endtime: time[1],
                }
            }).then(res => {
                this.option3data = res.data;
            })
        }
    },
    created() {
        this.user = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
        this.nowdate = new Date().toLocaleDateString()
        this.loadischartsdata(this.nowdate);
        this.loadtodayspend();
        this.loadtodayincome();
        this.loadoption3(this.time);
        this.loadoption2(this.time);
    },
}
</script>

<style scoped>

</style>