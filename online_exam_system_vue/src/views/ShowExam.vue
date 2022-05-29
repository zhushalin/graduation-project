<template>
    <div>
        <el-row :gutter="24">
            <el-col :span="24" >
                <el-breadcrumb separator="|" style="margin-bottom: 15px; margin-left: 5px; margin-top: 15px">
                    <el-breadcrumb-item :to="{ path: '/onlineExam/Score' }" style="margin-right: 15px; margin-left: 15px;">
                        <i class="el-icon-back"></i>
                        返回
                    </el-breadcrumb-item>
                    <el-breadcrumb-item class="testtypevo"> 试卷详情：{{this.paperName}}</el-breadcrumb-item>
                    <el-breadcrumb-item class="testtypevo">单选题分数：3分/道</el-breadcrumb-item>
                    <el-breadcrumb-item class="testtypevo">多选题分数：5分/道</el-breadcrumb-item>
                    <el-breadcrumb-item class="testtypevo">判断题分数：2分/道</el-breadcrumb-item>
                    <el-breadcrumb-item class="testtypevo"></el-breadcrumb-item>
                </el-breadcrumb>
            </el-col>

            <el-col :span="6" :xs="24" style="margin-bottom: 10px">
                <el-card>
                    <p style="color: crimson">考试得分：{{paperData.ExamScore}}</p>
                    <p class="card-title">答题卡</p>
                    <el-row :gutter="24" class="card-line" style="padding-left: 10px">
                        <el-tag type="danger" effect="dark">作答错误</el-tag>
                        <el-tag type="success" effect="dark">作答正确</el-tag>
                    </el-row>

                    <div v-if="paperData.radioList!==undefined && paperData.radioList.length > 0">
                        <p class="card-title">单选题</p>
                        <el-row :gutter="24" class="card-line">
                            <el-tag v-for="item in paperData.radioList" effect="dark" :type="cardItemClass(item.isanswer, item.testId)" @click="handSave(item)"> {{ (item.testSort)+1 }}</el-tag>
                        </el-row>
                    </div>

                    <div v-if="paperData.multiList!==undefined && paperData.multiList.length > 0">
                        <p class="card-title">多选题</p>
                        <el-row :gutter="24" class="card-line">
                            <el-tag v-for="item in paperData.multiList" effect="dark" :type="cardItemClass(item.isanswer, item.testId)" @click="handSave(item)">{{ (item.testSort)+1}}</el-tag>
                        </el-row>
                    </div>

                    <div v-if="paperData.judgeList!==undefined && paperData.judgeList.length > 0">
                        <p class="card-title">判断题</p>
                        <el-row :gutter="24" class="card-line">
                            <el-tag v-for="item in paperData.judgeList" effect="dark" :type="cardItemClass(item.isanswer, item.testId)" @click="handSave(item)">{{ (item.testSort)+1 }}</el-tag>
                        </el-row>
                    </div>
                </el-card>
            </el-col>

            <el-col :span="18" :xs="24">
                <el-card class="qu-content">
                    <p v-if="cardItem.testContent"> {{ (cardItem.testSort)+1 }}、{{ cardItem.testContent }}</p>
                    <!--单选-->
                    <div v-if="cardItem.testType === 1 || cardItem.testType === 3">

                        <el-radio-group v-model="cardItem.stuAnswer" style="width: 450px">
                            <el-radio label="A" value="A" disabled>A、{{ cardItem.optionA }}</el-radio>
                            <el-radio label="B" value="B" disabled>B、{{ cardItem.optionB }}</el-radio>
                            <el-radio label="C" value="C" disabled v-if="cardItem.testType === 1">C、{{ cardItem.optionC }}</el-radio>
                            <el-radio label="D" value="D" disabled v-if="cardItem.testType === 1">D、{{ cardItem.optionD }}</el-radio>
                        </el-radio-group>
                    </div>
                    <!--多选-->
                    <div v-if="cardItem.testType === 2">
                        <el-checkbox-group v-model="cardItem.stuAnswer" style="width: 450px">
                            <el-checkbox label="A" value="A" disabled>A、{{ cardItem.optionA }}</el-checkbox>
                            <el-checkbox label="B" value="B" disabled>B、{{ cardItem.optionA }}</el-checkbox>
                            <el-checkbox label="C" value="C" disabled>C、{{ cardItem.optionA }}</el-checkbox>
                            <el-checkbox label="D" value="D" disabled>D、{{ cardItem.optionA }}</el-checkbox>
                        </el-checkbox-group>

                    </div>
                    <template>
                       <p style="color: red">
                           正确答案：{{cardItem.answer}}
                       </p>
                    </template>
                </el-card>

                <div style="margin-top: 20px">

                    <el-button v-if="showPrevious" type="primary" icon="el-icon-back" @click="handPrevious()">
                        上一题
                    </el-button>

                    <el-button v-if="showNext" type="warning" icon="el-icon-right" @click="handNext()">
                        下一题
                    </el-button>

                    <el-button v-if="!showNext" type="success" icon="el-icon-upload" disabled>
                        已结束
                    </el-button>
                </div>

            </el-col>

        </el-row>
    </div>
</template>

<script>
    export default {
        name: "ShowExam",
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


                searchForm: {
                },
            }
        },
        created() {
            const tpId = sessionStorage.getItem("examTpId")
            const tpName = sessionStorage.getItem("examTpName")
            /*console.log("================================>")
            console.log(tpId)
            console.log("================================>")*/

            if (typeof tpId !== 'undefined') {
                this.paperId = tpId
                this.paperName = tpName
                this.fetchData(tpId)
            }
        },

        methods: {
            // 答题卡样式
            cardItemClass(isAnswer, testId) {
                if (testId === this.cardItem.testId) {
                    return 'warning'
                }

                if (isAnswer) {
                    return 'success'
                }

                if (!isAnswer) {
                    return 'danger'
                }
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

            // 保存答案
            handSave(item) {
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
                console.log("=======当前题内容===========")
                console.log(item)

                this.cardItem = item
                if (item.testType===2){
                    this.cardItem.stuAnswer = item.stuAnswer.split("")
                }
            },

            // 获取整个试卷详情
            fetchData(tpId) {
                this.searchForm.stuId = localStorage.getItem("id")
                this.searchForm.tpId = tpId
                this.$axios.post("/Tplist/getById",this.searchForm).then(res =>{
                    /*console.log("==>===========>===========>")
                    console.log(res.data.data)
                    console.log("==>===========>===========>")*/
                    // 获取试卷内容
                    this.paperData = res.data.data

                    // 获得第一题内容-若有单选先显示再多选最后判断
                    if (this.paperData.radioList.length>0) {
                        this.cardItem = this.paperData.radioList[0]
                    } else if (this.paperData.multiList.length>0) {
                        this.cardItem = this.paperData.multiList[0]
                        this.cardItem.stuAnswer = this.paperData.multiList[0].stuAnswer.split("")
                    } else if (this.paperData.judgeList.length>0) {
                        this.cardItem = this.paperData.judgeList[0]
                    }
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

                    if (this.answerlist==null){
                        this.answerlist = that.allItem
                    }
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
    .testtypevo{
        font-weight: bold
    }
</style>
