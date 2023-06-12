const all_menus =[
    {
        index: 1,
        name: "主页",
        path: "home",
        icon: "el-icon-house",
    },
    {
        index: 2,
        name: "余额信息",
        path: "balance",
        icon: "el-icon-s-custom",
    },
    {
        index: 3,
        name: "家庭管理",
        path: null,
        icon: "el-icon-s-home",
        children: [
            {
                index: 6,
                name: "成员信息",
                path: 'user',
                icon: "el-icon-s-custom",
            },
            {
                index: 7,
                name: "角色管理",
                path: 'role',
                icon: "el-icon-menu",
            }
        ]
    },
    {
        index: 4,
        name: "支出管理",
        path: null,
        icon: "el-icon-s-custom",
        children: [
            {
                index: 8,
                name: "我的支出",
                path: 'myspend',
                icon: "el-icon-s-custom",
            },
            {
                index: 9,
                name: "家庭支出",
                path: 'spenddetail',
                icon: "el-icon-s-custom",
            },
            {
                index: 10,
                name: "支出报表",
                path: 'spendreport',
                icon: "el-icon-s-custom",
            }
        ]
    },
    {
        index: 5,
        name: "收入管理",
        path: null,
        icon: "el-icon-s-custom",
        children: [
            {
                index: 11,
                name: "我的收入",
                path: 'myincome',
                icon: "el-icon-s-custom",
            },
            {
                index: 12,
                name: "家庭收入",
                path: 'incomedetail',
                icon: "el-icon-s-custom",
            },
            {
                index: 13,
                name: "收入报表",
                path: 'incomereport',
                icon: "el-icon-s-custom",
            }
        ]
    },
]


export default {
    all_menus,
}