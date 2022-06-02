<template>
    <div>
        <el-row :gutter="24">
            <el-col :span="24">
                <el-card style="margin-bottom: 10px">
                    <!-- 距离考场关闭还有：<span style="color: #ff0000;">{{hour}}时{{ min }}分钟{{ sec }}秒</span>
                    <span style="margin-left: 35%">{{this.paperName}}</span> -->
                    <el-button style="float: right; margin-top: -10px" type="primary" icon="el-icon-plus" @click="handHandExam()">
                        交卷
                    </el-button>
                </el-card>
            </el-col>

            <el-col :span="6" :xs="24" style="margin-bottom: 10px">
                <el-card>
                    <p class="card-title">答题卡</p>
                    <el-row :gutter="24" class="card-line" style="padding-left: 10px">
                        <el-tag type="info" effect="dark">未作答</el-tag>
                        <el-tag type="success" effect="dark">已作答</el-tag>
                        <!-- <el-tag type="success" effect="dark">单前</el-tag> -->
                    </el-row>

                    <div v-if="paperData.radioList!==undefined && paperData.radioList.length > 0">
                        <p class="card-title">单选题</p>
                        <el-row :gutter="24" class="card-line">
                            <el-tag v-for="item in paperData.radioList" effect="dark" :type="cardItemClass(item.answered, item.testId)" @click="handSave(item)"> {{ (item.testSort)+1 }}</el-tag>
                        </el-row>
                    </div>

                    <div v-if="paperData.multiList!==undefined && paperData.multiList.length > 0">
                        <p class="card-title">多选题</p>
                        <el-row :gutter="24" class="card-line">
                            <el-tag v-for="item in paperData.multiList" effect="dark" :type="cardItemClass(item.answered, item.testId)" @click="handSave(item)">{{ (item.testSort)+1}}</el-tag>
                        </el-row>
                    </div>

                    <div v-if="paperData.judgeList!==undefined && paperData.judgeList.length > 0">
                        <p class="card-title">判断题</p>
                        <el-row :gutter="24" class="card-line">
                            <el-tag v-for="item in paperData.judgeList" effect="dark" :type="cardItemClass(item.answered, item.testId)" @click="handSave(item)">{{ (item.testSort)+1 }}</el-tag>
                        </el-row>
                    </div>
                </el-card>
            </el-col>

            <el-col :span="18" :xs="24">
                <el-card class="qu-content">
                    <p v-if="cardItem.testContent"> {{ (cardItem.testSort)+1 }}、{{ cardItem.testContent }}</p>
                    <!--单选-->
                    <div v-if="cardItem.testType === 1 || cardItem.testType === 3">
                        <el-radio-group v-model="radioValue">
                            <el-radio label="A" value="A">A、{{ cardItem.optionA }}</el-radio>
                            <el-radio label="B" value="B">B、{{ cardItem.optionB }}</el-radio>
                            <el-radio label="C" value="C" v-if="cardItem.testType === 1">C、{{ cardItem.optionC }}</el-radio>
                            <el-radio label="D" value="D" v-if="cardItem.testType === 1">D、{{ cardItem.optionD }}</el-radio>
                        </el-radio-group>
                    </div>

                    <!--多选-->
                    <div v-if="cardItem.testType === 2">
                        <el-checkbox-group v-model="multiValue" >
                            <el-checkbox label="A" value="A">A、{{ cardItem.optionA }}</el-checkbox>
                            <el-checkbox label="B" value="B">B、{{ cardItem.optionA }}</el-checkbox>
                            <el-checkbox label="C" value="C" >C、{{ cardItem.optionA }}</el-checkbox>
                            <el-checkbox label="D" value="D" >D、{{ cardItem.optionA }}</el-checkbox>
                        </el-checkbox-group>

                    </div>

                </el-card>

                <div style="margin-top: 20px">
                    <el-button v-if="showPrevious" type="primary" icon="el-icon-back" @click="handPrevious()">
                        上一题
                    </el-button>

                    <el-button v-if="showNext" type="warning" icon="el-icon-right" @click="handNext()">
                        下一题
                    </el-button>

                    <el-button v-if="!showNext" type="info" icon="el-icon-upload" disabled>
                        没有了
                    </el-button>
                </div>

            </el-col>

        </el-row>
    </div>
</template>

<script>
    export default {
        name: "OnlineExam_zn",
        data: function () {
            return {
                // 全屏/不全屏
                isFullscreen: false,
                showPrevious: false,
                showNext: true,
                loading: false,
                pageLoading: false,
                // 试卷ID
                paperId: '',
                paperName: '',
                // 当前答题卡
                cardItem: {
                    itemadd: false,
                    answer: []
                },
                allItem: [],
                // 当前题目内容
                quData: {
                },
                // 试卷信息
                paperData: {
                    leftSeconds: 99999,
                    radioList: [],
                    multiList: [],
                    judgeList: []
                },
                answerSheet: {
                    tpId: null,
                    stuId: null,
                    answerList: [
                    ]
                },
                passExam: {},
                answerlist:[],
                answersheet:{},
                // 单选选定值
                radioValue: '',
                // 多选选定值
                multiValue: [],
                // 已答ID
                answeredIds: [],
                min: '00',
                sec: '00',
                hour: '00',
            }
        },
        created() {
            this.fetchData();
        },

        methods: {
            // 答题卡样式
            cardItemClass(answered, testId) {
                if (testId === this.cardItem.testId) {
                    return 'warning'
                }

                if (answered) {
                    return 'success'
                }

                if (!answered) {
                    return 'info'
                }
            },
            /**
             * 统计有多少题没答的
             * @returns {number}
             */
            countNotAnswered() {
                let notAnswered = 0

                this.paperData.radioList.forEach(function(item) {
                    if (!item.answered) {
                        notAnswered += 1
                    }
                })

                this.paperData.multiList.forEach(function(item) {
                    if (!item.answered) {
                        notAnswered += 1
                    }
                })

                this.paperData.judgeList.forEach(function(item) {
                    if (!item.answered) {
                        notAnswered += 1
                    }
                })

                return notAnswered
            },

            /**
             * 下一题
             */
            handNext() {
                const index = this.cardItem.testSort+1
                console.log("========当前试题id=======allItem【】>"+index)
                this.handSave(this.allItem[index])
            },

            /**
             * 上一题
             */
            handPrevious() {
                const index = this.cardItem.testSort-1
                console.log("========当前试题id=======>"+index)
                this.handSave(this.allItem[index])
            },
            //交卷
            doHandler() {
                this.$message({
                    message: '正在交卷，请等待...',
                    type: 'warning'
                })
                this.answersheet = this.answerSheet
                this.answersheet.answerList.forEach(function (item) {
                        if (item.testType===2){
                            item.answer.sort()
                            item.answer = item.answer.join("")
                        }
                })
                this.answersheet.tpId = this.paperId
                this.answersheet.stuId = localStorage.getItem("id")
                this.$axios.post("/Answersheet/tijiao",this.answersheet).then(res =>{
                    
                    this.$message({
                        duration: 1000,
                        message: '提交成功，得分'+res.data.data,
                        type: 'success',
                        onClose:() => {
                            sessionStorage.removeItem("answerlist");
                            this.$router.push({name: "EATestBank_CTJ"})
                        }
                    })


                })
            },

            // 交卷操作
            handHandExam() {
                const that = this

                // 交卷保存答案
                this.handSave(this.cardItem, function() {
                    const notAnswered = that.countNotAnswered()

                    if (notAnswered > 0) {
                        /*msg = '您还有' + notAnswered + '题未作答，请作答'*/
                        that.$message({
                            type: 'warning',
                            message: '您还有' + notAnswered + '题未作答，请完成作答'
                        })
                    }
                    else{
                        
                        let msg = '确认要交卷吗？'
                        that.$confirm(msg, '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then(() => {
                            that.doHandler()
                        }).catch(() => {
                            that.$message({
                                type: 'info',
                                message: '交卷已取消，您可以继续作答！'
                            })
                        })
                    }
                })
            },

            // 保存答案
            handSave(item, callback) {
                //是否有上题
                if (item.testId === this.allItem[0].testId) {
                    this.showPrevious = false
                } else {
                    this.showPrevious = true
                }

                // 最后一个索引
                const last = this.allItem.length - 1
                //是否有下题
                if (item.testId === this.allItem[last].testId) {
                    this.showNext = false
                } else {
                    this.showNext = true
                }
                if (this.multiValue!=null&& (this.cardItem.testType===2)){
                    this.answerSheet.answerList[(this.cardItem.testSort)].answer = this.multiValue
                }
                else if (this.radioValue!=null&& !(this.cardItem.testType===2)){
                    let test = this.cardItem.testSort
                    this.answerSheet.answerList[test].answer = this.radioValue/*this.cardItem.answer*/
                }
                if (!this.answerSheet.answerList[this.cardItem.testSort].testType===2){
                    if (this.answerSheet.answerList[this.cardItem.testSort].answer){
                        this.cardItem.answered = true
                        this.answerlist[this.cardItem.testSort].answered = true
                    }
                }else{
                    if (this.answerSheet.answerList[this.cardItem.testSort].answer.length>0){
                        this.cardItem.answered = true
                        this.answerlist[this.cardItem.testSort].answered = true
                    }
                }
                console.log("=======当前题内容===========")
                console.log(item)

                if (callback) {
                    callback()
                }

                this.fetchQuData(item)
            },

            // 当前试题详情
            fetchQuData(item) {


                //初始化答案
                this.radioValue=""
                this.multiValue=[]
                // 更新当前题内容
                this.cardItem = item
                //判断该题是否已添加到答题卡数组中，否则添加
                if (!this.cardItem.itemadd){
                    this.cardItem.itemadd = true
                    this.answerSheet.answerList.push(this.cardItem)
                }
                console.log("========answered=======")
                console.log(this.answerlist)
                console.log("========answered=======")
                if (this.answerlist&&this.answerlist[this.cardItem.testSort]&&this.answerlist[this.cardItem.testSort].answered&&this.cardItem.testType===2){
                    this.multiValue = this.answerlist[this.cardItem.testSort].answer
                }
                else if (this.answerlist&&this.answerlist[this.cardItem.testSort]&&this.answerlist[this.cardItem.testSort].answered){
                    this.radioValue = this.answerlist[this.cardItem.testSort].answer
                }
                else if ((this.answerSheet.answerList[this.cardItem.testSort].answered)&&(this.cardItem.testType===2)){
                    this.multiValue = this.answerSheet.answerList[this.cardItem.testSort].answer
                    this.this.answerlist[this.cardItem.testSort].answer = this.answerSheet.answerList[this.cardItem.testSort].answer
                }
                //判断是否已作答且题目不为多选题
                else if (this.answerSheet.answerList[this.cardItem.testSort].answered){
                    this.radioValue = this.answerSheet.answerList[this.cardItem.testSort].answer
                    this.answerlist[this.cardItem.testSort].answer = this.answerSheet.answerList[this.cardItem.testSort].answer
                }

            },

            // 获取整个试卷详情
            fetchData() {
                this.$axios.post("/Tplist/getTplistSuiji",{type:"重难"}).then(res =>{

                    // 获取试卷内容
                    this.paperData = res.data.data

                    // 获得第一题内容-若有单选先显示再多选最后判断
                    // if (this.paperData.radioList.length>0) {
                    //     this.cardItem = this.paperData.radioList[0]
                    // } else if (this.paperData.multiList.length>0) {
                    //     this.cardItem = this.paperData.multiList[0]
                    // } else if (this.paperData.judgeList.length>0) {
                    //     this.cardItem = this.paperData.judgeList[0]
                    // }
                    const that = this
                    //将每个试题添加到试题列表中
                    this.paperData.radioList.forEach(function(item) {
                        that.allItem.push(item)
                    })

                    this.paperData.multiList.forEach(function(item) {
                        that.allItem.push(item)
                    })

                    this.paperData.judgeList.forEach(function(item) {
                        that.allItem.push(item)
                    })
                    
                    that.cardItem = that.allItem[0];
                    that.answerlist = that.allItem
                    // 当前选定--传递当前试题
                    this.fetchQuData(this.cardItem)
                })

            }

        }
    }

</script>

<style scoped>
    .qu-content div{
        line-height: 30px;
    }

    .el-checkbox-group label,.el-radio-group label{
        width: 100%;
    }

    .card-title{
        background: #eee;
        line-height: 35px;
        text-align: center;
        font-size: 14px;
    }
    .card-line{
        padding-left: 10px
    }
    .card-line span {
        cursor: pointer;
        margin: 2px;
    }

    .el-radio, .el-checkbox{
        padding: 9px 20px 9px 10px;
        border-radius: 4px;
        border: 1px solid #dcdfe6;
        margin-bottom: 10px;
    }

    .is-checked{
        border: #409eff 1px solid;
    }

    .el-radio img, .el-checkbox img{
        max-width: 200px;
        max-height: 200px;
        border: #dcdfe6 1px dotted;
    }

    .el-checkbox__inner {
        display: none;
    }


    .el-radio__inner{
        display: none;
    }


    .el-checkbox__label{
        line-height: 30px;
    }


    .el-radio__label{
        line-height: 30px;
    }
</style>
