package com.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.DTO.AnswersheetDTO;
import com.exam.VO.OnlineExam.TBVO;
import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import com.exam.entity.Answersheet;
import com.exam.entity.Score;
import com.exam.entity.Testbank;
import com.exam.entity.Testpaper;
import com.exam.mapper.TestbankMapper;
import com.exam.service.AnswersheetService;
import com.exam.service.ScoreService;
import com.exam.service.TestbankService;
import com.exam.service.TestpaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 答题卡
 * @author
 * @since
 */
@RestController
@RequestMapping("/Answersheet")
public class AnswersheetController {
    @Autowired
    AnswersheetService answersheetService;
    @Autowired
    ScoreService scoreService;
    @Autowired
    TestbankService testbankService;
    @Autowired
    TestpaperService testpaperService;
    @Autowired
    TestbankMapper testbankMapper;
    //查询所有答题卡
    @RequestMapping("/getAnswersheet")
    public Msg getAnswersheet(){
        return null;
    }
    //根据答题卡id查询
    @RequestMapping("/getAnswersheetById/{id}")
    public Msg getAnswersheetById(@PathVariable(name = "id") int id){
        return null;
    }
    //添加答题卡
    @RequestMapping("/save")
    public Msg addAnswersheet(@RequestBody AnswersheetDTO answersheetDTO) throws ParseException {
        SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        System.out.println(answersheetDTO);
        List<TBVO> answerList = answersheetDTO.getAnswerList();
        double sum = 0;
        for (TBVO tbvo : answerList) {
            Answersheet answersheet = new Answersheet();
            //题目类型不为问答题时，给出分数
            if (tbvo.getTestType() != 4){
                setScore(tbvo, answersheet, answersheetDTO);
                sum += answersheet.getStuScore();
            }
            answersheet.setIsAsk(0);
            answersheet.setStuAnswer(tbvo.getAnswer());
            answersheet.setTestId(tbvo.getTestId());
            answersheet.setStuId(answersheetDTO.getStuId());
            answersheet.setTpId(answersheetDTO.getTpId());
            System.out.println(answersheet);
            boolean res = answersheetService.save(answersheet);
            if (!res){
                return ResultUtil.error(400,"答题卡信息有误");
            }
        }
        //获取科目id
        Testpaper testpaper = testpaperService.getById(answersheetDTO.getTpId());
        //保存成绩
        Score score = new Score();
        score.setId(0);
        score.setStuId(answersheetDTO.getStuId());
        score.setTpId(answersheetDTO.getTpId());
        score.setSubId(testpaper.getSubId());
        score.setSubmitTime(new Date());
        score.setLeaveTimes(answersheetDTO.getLeaveTimes());
        score.setStartTime(sdf.parse(answersheetDTO.getStartTime()));
        score.setState(0);
        score.setSubScore(sum);
        scoreService.save(score);

        return ResultUtil.success();
    }

    public Void setScore(TBVO tbvo,Answersheet answersheet,AnswersheetDTO answersheetDTO){
        //将提交的答案分割成数组
        /*String[] split = tbvo.getAnswer().split("");*/
        //获取答案
        QueryWrapper<Testbank> wrapper = new QueryWrapper<>();
        wrapper.inSql("test_id","select test_id from tplist where  tp_id = "+answersheetDTO.getTpId());
        wrapper.eq("test_id",tbvo.getTestId());
        Testbank testbyId = testbankService.getOne(wrapper);
        //将提交的答案分割成数组进行排序
        /*String[] split1 = testbyId.getAnswer().split("");*/
        /*Arrays.sort(split1);*/
        System.out.println("===============");
        System.out.println("学生答案："+tbvo.getAnswer());
        System.out.println("正确答案："+testbyId.getAnswer());
        System.out.println("================");
        if (tbvo.getTestType() != 2){
            //题目类型不为多选题时
            if (tbvo.getAnswer().equals(testbyId.getAnswer())){
                answersheet.setStuScore(testbyId.getScore());
                System.out.println("对");
            }else{
                answersheet.setStuScore(0);
                System.out.println("错");
            }
        }else {
            answersheet.setIsAsk(1);
            if (isCorrect(tbvo.getStuAnswer(), testbyId.getAnswer())){
                answersheet.setStuScore(testbyId.getScore());
            }else {
                answersheet.setStuScore(0);
            }
        }
        return null;
    }

    //删除答题卡
    @RequestMapping("/delAnswersheet/{id}")
    public Msg delAnswersheet(@PathVariable(name = "id") int id){
        return null;
    }
    //修改答题卡信息
    @RequestMapping("/updateAnswersheet")
    public Msg updateAnswersheet(@RequestBody  Answersheet answersheet){
        return null;
    }

    //多选题答案判断
    public boolean isCorrect(String studentAnswer, String answer){
        //转化为字符数组
        char[] saChars = studentAnswer.toCharArray();
        char[] aChars = answer.toCharArray();
        //对字符数组排序
        Arrays.sort(saChars);
        Arrays.sort(aChars);
        //返回对比结果
        return Arrays.equals(saChars, aChars);
    }
}
