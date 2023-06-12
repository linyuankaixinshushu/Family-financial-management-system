<template>
    <div>
        <el-row style="margin-top: 20px">
            <el-col :span="24">
                <el-card>
                    <div style="display: flex;margin-left: 30px">
                        <div style="font-weight: bold"><h2>支出构成</h2></div>
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
                        <div style="width: 400px; height: 400px; margin-left: 25%;margin-top: 20px">
                            <v-chart  :options="option3">
                            </v-chart>
                        </div>
                    </div>
                </el-card>
            </el-col>
        </el-row>
        <el-row style="margin-top: 40px">
            <el-col :span="24">
                <el-card>
                    <div style="display: flex;margin-left: 30px">
                        <div style="font-weight: bold"><h2>支出折线图</h2></div>
                        <div style="margin-left: 50px;">
                            <el-input class="ml-5" v-model="len" style="width: 200px" placeholder="输入想显示的数据量"></el-input>
                            <el-select v-model="select" placeholder="请选择" class="ml-5" >
                                <el-option
                                        v-for="item in options"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value">
                                </el-option>
                            </el-select>
                            <el-date-picker
                                    v-model="datepicke"
                                    :type="select"
                                    value-format="yyyy-MM-dd HH:mm:ss"
                                    placeholder="请选择日期"
                                    style="margin-left: 5px">
                            </el-date-picker>
                            <el-button class="ml-5" type="primary" @click="getline">确认</el-button>
                        </div>
                    </div>
                    <div style="margin-top: 20px" >
                        <v-chart  :options="option1" style="width: 100%;height: 400px">
                        </v-chart>
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
            linex: [],
            linedata: [],
            linetime: '',
            linelen: 15 ,
            flag: 'date',
            select: '',
            datepicke: '',
            nowdate:'',
            time:[],
            len: '',
            option2data: [],
            option3data: [],
            incomekind: ["医疗","教育","食品","娱乐","投资","生活","交通","其他"],
            options: [{
                value: 'year',
                label: '按年度'
            }, {
                value: 'month',
                label: '按月度'
            }, {
                value: 'date',
                label: '按天'
            }]
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
                    data: this.linex,
                },
                yAxis: {},
                series: [
                    {
                        type: 'line',
                        data: this.linedata,
                        itemStyle: {
                            normal: {
                                label: {
                                    show : true,
                                },
                                color: '#409EFF',
                            }
                        }
                    },
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
                        name: '类别构成',
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
                        name: '用户构成',
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
        getline(){
            this.flag = this.select;
            this.linetime = this.datepicke;
            this.linelen = this.len;
            this.loadline();
        },
        selecttimechange() {
            this.loadoption2(this.time)
            this.loadoption3(this.time)
        },
        loadincomebykind(time) {
            this.request.get("/echarts/spend",{
                params:{
                    begintime: time[0],
                    endtime: time[1],
                }
            }).then(res => {
                this.option2data = res.data;
            })
        },
        loadincomebyuser(time) {
            this.request.get("/echarts/spendbyuser",{
                params:{
                    begintime: time[0],
                    endtime: time[1],
                }
            }).then(res => {
                this.option3data = res.data;
            })
        },
        loadline() {
            this.request.get("/echarts/getfamilyspendop", {
                params:{
                    time : this.linetime,
                    flag : this.flag,
                    len : this.linelen,
                }
            }).then(res => {
                this.linex = res.data.x;
                this.linedata = res.data.data;
            })
        },
    },
    created() {
        this.user = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
        this.nowdate = new Date().toLocaleDateString()
        this.loadincomebyuser(this.time);
        this.loadincomebykind(this.time);
        this.flag = 'date';
        this.linetime = this.nowdate;
        this.loadline()
    },
}
</script>

<style scoped>

</style>