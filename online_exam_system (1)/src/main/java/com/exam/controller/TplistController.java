package com.exam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.DTO.ScoreDTO;
import com.exam.DTO.TestBankDTO;
import com.exam.VO.OnlineExam.TBVO;
import com.exam.VO.OnlineExam.TPListVO;
import com.exam.VO.TestBankVO;
import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import com.exam.entity.Answersheet;
import com.exam.entity.Testpaper;
import com.exam.entity.Tplist;
import com.exam.mapper.TestbankMapper;
import com.exam.mapper.TplistMapper;
import com.exam.service.TestbankService;
import com.exam.service.TestpaperService;
import com.exam.service.TplistService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 套题
 * @author
 * @since
 */
@RestController
@RequestMapping("/Tplist")
public class TplistController {
    @Autowired
    TplistService tplistService;

    @Autowired
    TplistMapper tplistMapper;

    @Autowired
    TestbankService testbankService;

    @Autowired
    TestbankMapper testbankMapper;

    @Autowired
    TestpaperService testpaperService;

    /**
     * 根据套题id查询试卷试题内容
     * @param tpId
     * @return
     */
    @RequestMapping("/getTplistById/{tpId}")
    public Msg getTplistBySubId(@PathVariable(name = "tpId") int tpId){
        List<TestBankVO> tplistTest = testbankMapper.getTplistTest(tpId);

        TPListVO tpListVO = new TPListVO();
        ArrayList<TBVO> list1 = new ArrayList<>();
        ArrayList<TBVO> list2 = new ArrayList<>();
        ArrayList<TBVO> list3 = new ArrayList<>();
        int number = 0;
        for (TestBankVO testBankVO : tplistTest) {
            TBVO tbvo = new TBVO();
            tbvo.setAnswered(false);
            tbvo.setIsanswer(false);
            tbvo.setTestSort(number++);
            BeanUtils.copyProperties(testBankVO,tbvo);
            if (testBankVO.getTestType()==1){
                list1.add(tbvo);
            }else if(testBankVO.getTestType()==2){
                list2.add(tbvo);
            }else if(testBankVO.getTestType()==3){
                list3.add(tbvo);
            }
        }
        Testpaper testpaper = testpaperService.getById(tpId);
        tpListVO.setLeftSeconds(getTime(testpaper.getExamLength(),testpaper.getExamTime()));
        tpListVO.setRadioList(list1);
        tpListVO.setMultiList(list2);
        tpListVO.setJudgeList(list3);
        System.out.println("tplistvo===================>");
        System.out.println(tpListVO);
        return ResultUtil.success(tpListVO);
    }

    /**
     * 根据套题id查询试卷结果
     * @param scoreDTO
     * @return
     */
    @RequestMapping("/getById")
    public Msg getTplistBytpId(@RequestBody ScoreDTO scoreDTO){
        System.out.println(scoreDTO);
        //学生答题情况
        List<Answersheet> examTest = testbankMapper.getExamTest(scoreDTO.getTpId(), scoreDTO.getStuId());
        Map<Integer, Answersheet> examTestMap = examTest.stream().collect(Collectors.toMap(Answersheet::getTestId, a->a, (k1, k2) -> k1));


        //试卷试题内容
        List<TestBankVO> tplistTest = testbankMapper.getTplistTest(scoreDTO.getTpId());
        TPListVO tpListVO = new TPListVO();
        tpListVO.setExamScore(0);
        ArrayList<TBVO> list1 = new ArrayList<>();
        ArrayList<TBVO> list2 = new ArrayList<>();
        ArrayList<TBVO> list3 = new ArrayList<>();
        ArrayList<TBVO> list4 = new ArrayList<>();
        int number = 0;
        for (TestBankVO testBankVO : tplistTest) {
            TBVO tbvo = new TBVO();
            //答案默认为错误
            tbvo.setIsanswer(false);
            //设置题目分数
            tbvo.setScore(testBankVO.getScore());
            tbvo.setTestSort(number++);

            //获取学生答案
            Answersheet answersheet = examTestMap.get(testBankVO.getTestId());
            if (answersheet != null) {
                tbvo.setStuAnswer(""+ answersheet.getStuAnswer());
                if (answersheet.getStuAnswer().equals(testBankVO.getAnswer())){
                    tbvo.setIsanswer(true);
                }
            }
            BeanUtils.copyProperties(testBankVO,tbvo);
            if (testBankVO.getTestType()==1 ){
                list1.add(tbvo);
            }else if(testBankVO.getTestType()==2){
                list2.add(tbvo);
            }else if(testBankVO.getTestType()==3){
                list3.add(tbvo);
            }else if (testBankVO.getTestType()==3){
                list4.add(tbvo);
            }
        }
        for (Answersheet answersheet : examTest) {
            tpListVO.setExamScore(tpListVO.getExamScore()+answersheet.getStuScore());
        }
        tpListVO.setRadioList(list1);
        tpListVO.setMultiList(list2);
        tpListVO.setJudgeList(list3);
        tpListVO.setQuestionList(list4);
        return ResultUtil.success(tpListVO);
    }

    /**
     * 添加试题到试卷
     * @param tplist
     * @return
     */
    @RequestMapping("/save")
    public Msg addTplist(@RequestBody Tplist tplist){
        System.out.println("======试题===========");
        System.out.println(tplist);
        System.out.println("======试题===========");
        if (tplist.getTpId()!=null&&tplist.getTestId()!=null){
            try {
                boolean result = tplistService.save(tplist);
                return ResultUtil.success();
            }catch (Exception e){
                return ResultUtil.error(500,"该试题已添加，请重选");
            }
        }
        return ResultUtil.error(400,"添加失败");
    }


    /**
     * 获得所有试题、按搜索框内容查询
     * @return
     */
    @RequestMapping("/getTestbank")
    public Msg getTestbank(@RequestBody TestBankDTO testBankDTO){
        System.out.println("size==============>"+testBankDTO);
        return testbankService.getTestBanklist1(testBankDTO);
    }

    /**
     * 获得所选科目试题、按搜索框内容查询
     * @return
     */
    @RequestMapping("/aboutTplist")
    public Msg aboutTplist(@RequestBody TestBankDTO testBankDTO){
        System.out.println("size==============>"+testBankDTO);
        return testbankService.aboutTplist(testBankDTO);
    }

    /**
     *  撤销试题添加
     * @param testBankDTO
     * @return
     */
    @RequestMapping("/delete")
    public Msg delTplist(@RequestBody TestBankDTO testBankDTO){
        System.out.println(testBankDTO);
        try {
            QueryWrapper<Tplist> wrapper = new QueryWrapper<>();
            wrapper.eq("tp_id",testBankDTO.getTpId());
            for (Long testId : testBankDTO.getIds()) {
                wrapper.eq("test_id",testId)
                        .or();
            }
            tplistMapper.delete(wrapper);
        }catch (Exception e){
            return ResultUtil.error(500,"该试题存在关联，无法撤销");
        }
        return ResultUtil.success();
    }

    /**
     * 修改套题信息
     * @param tplist
     * @return
     */
    @RequestMapping("/updateTplist")
    public Msg updateTplist(@RequestBody Tplist tplist){
        return null;
    }

    int getTime(LocalTime examLength, LocalDateTime examTime){
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(examTime)){
            Duration duration = Duration.between(examTime, now);
            int minute = (int) duration.toMinutes();
            int millis = (int) duration.toMillis()/1000;
            System.out.println("考试已开始："+minute+"分钟了");
            System.out.println("考试已开始："+millis+"秒了");

            String s = examLength.toString();
            String[] time = s.split(":");
            System.out.println("======time==========");
            System.out.println(time);
            System.out.println("======time==========");
            int min = Integer.parseInt(time[0])*60*60+Integer.parseInt(time[1])*60;
            System.out.println("考试时长："+min+"秒");
            System.out.println(min-millis);
            return (min-millis);
        }
        return 0;


    }
}
