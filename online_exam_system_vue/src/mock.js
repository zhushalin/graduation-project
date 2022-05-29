// 引入mockjs
const Mock = require('mockjs')
// 获取 mock.Random 对象
// 参考：https://github.com/nuysoft/Mock/wiki/Mock.Random
const Random = Mock.Random
let Result = {
    code: 200,
    msg: '操作成功',
    data: null
}
/**
 * * Mock.mock( url, post/get , function(options))；
 * * url 表示需要拦截的 URL，
 * * post/get 需要拦截的 Ajax 请求类型
 *
 *
 * 用于生成响应数据的函数
 * */
// 获取验证码图片base64编码以及一个随机码
Mock.mock('/captcha', 'get', () => {
    Result.data = {
        token: Random.string(32), // 获取一个32位的随机字符串,
        captchaImg: Random.dataImage('120x50', 'n75s8')    //生成验证码为11111的base64图片编码
    }
    return Result
})

Mock.mock('/login', 'post', () => {
    Result.code = 200;
    Result.msg = "登录成功";
    return Result
})

Mock.mock('/userInfo', 'get', () => {
    Result.data = {
        id: "1",
        username: "李狗蛋",
        name: '李狗蛋',
        role: "崽崽"
    }
    return Result
})

Mock.mock('/logout', 'post', () => {
    return Result
})

// 获取用户菜单以及权限接口
Mock.mock('/sys/menu/nav', 'get', () => {
    //导航信息
    let nav = [
        {
            name: 'OnlineExam',
            title: '在线考试',
            icon: 'el-icon-s-promotion',
            path: '',
            component: '',
            children: [
                {
                    name: 'Exam',
                    title: '在线考试',
                    icon: 'el-icon-time',
                    path: '/onlineExam/Exam',
                    component: 'onlineExam/Exam',
                    children: []
                },
                {
                    name: 'Score',
                    title: '我的成绩',
                    icon: 'el-icon-trophy',
                    path: '/onlineExam/Score',
                    component: 'onlineExam/Score',
                    children: []
                }
            ]
        },
        {
            name: 'ExamAdmin',
            title: '考试管理',
            icon: 'el-icon-help',
            path: '',
            component: '',
            children: [
             {
                name: 'EATestBank',
                title: '题库管理',
                icon: 'el-icon-document-copy',
                path: '/examAdmin/TestBank',
                component: 'examAdmin/TestBank',
                children: []
            }, {
                name: 'EATpList',
                title: '套题管理',
                icon: 'el-icon-rank',
                path: '/examAdmin/TpList',
                component: 'examAdmin/TpList',
                children: []
            },
             {
                    name: 'EAScore',
                    title: '成绩统计',
                    icon: 'el-icon-orange',
                    path: '/examAdmin/ScoreAdmin',
                    component: 'examAdmin/ScoreAdmin',
                    children: []
             },]
        },

        {
            title: '系统管理',
            name: 'SysMange',
            icon: 'el-icon-setting',
            path: '',
            component: '',
            children: [
                {
                    name: 'SysUser',
                    title: '用户管理',
                    icon: 'el-icon-key',
                    path: '/sys/person',
                    component: 'sys/Person',
                    children: []
                },
                {
                    name: 'SysRole',
                    title: '角色管理',
                    icon: 'el-icon-s-custom',
                    path: '/sys/roles',
                    component: 'sys/Role',
                    children: []
                },
                {
                    name: 'SysSubject',
                    title: '科目管理',
                    icon: 'el-icon-magic-stick',
                    path: '/sys/menus',
                    component: 'sys/Menu',
                    children: []
                }
            ]
        },
    ]

    let authoritys = ['examAdmin:list','examAdmin:add','examAdmin:delete']

    Result.data = {
        nav: nav,
        authoritys: authoritys
    }

    return Result
})

Mock.mock(RegExp('/TestBank/*'), 'post', () => {

    return Result
})

Mock.mock(RegExp('/User/*'),'post',()=>{
    return Result
})

Mock.mock(RegExp('/TestBank/list*'), 'get', () => {
    Result.data = {
        "records": [
            {
                'teaUsername': 'Miss.Li',
                'testDate': '2021-05-03',
                'subName': '计网',
                'testType': 3,
                'testContent': '开汉是崽崽码？',
                'optionA': '是',
                'optionB': '否',
                'optionC': '',
                'optionD': '',
                'answer': 'A',
            },
            {
                'teaUsername': 'Miss.Li',
                'testDate': '2021-05-03',
                'subName': '计网',
                'testType': 2,
                'testContent': '开汉是SB码？',
                'optionA': '是',
                'optionB': '否',
                'optionC': '',
                'optionD': '',
                'answer': 'B',
            }
        ],
        'size': 10,
        'total': 2,
        'current': 1,
    }

    return Result

})

Mock.mock(RegExp('/Role/list*'), 'get', () => {
    Result.data = {
        "records": [
            {
                'roleId': 1001,

            },
            {
                'roleId': 1002,

            },{
                'roleId': 1003,

            }
        ],
        'size': 10,
        'total': 2,
        'current': 1,
    }

    return Result

})

Mock.mock(RegExp('/TestBank/info/*'), 'get', () => {

    Result.data = {
        'teaUsername': 'Miss.Li',
        'testDate': '2021-05-03',
        'subName': '计网',
        'testType': 3,
        'testContent': '开汉是SB码？',
        'optionA': '是',
        'optionB': '否',
        'optionC': '',
        'optionD': '',
        'answer': '是',
        'score': '2',
    }
    return Result
})




