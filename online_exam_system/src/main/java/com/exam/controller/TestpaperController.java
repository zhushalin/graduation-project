package com.exam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.DTO.TestpaperDTO;
import com.exam.VO.ExamInfoVO;
import com.exam.VO.TestpaperVO;
import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import com.exam.entity.Answersheet;
import com.exam.entity.Testpaper;
import com.exam.mapper.AnswersheetMapper;
import com.exam.service.TestpaperService;
import com.exam.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 *
 * @author
 * @since
 */
@RestController
@RequestMapping("/TestPaper")
public class TestpaperController {

    @Autowired
    TestpaperService testpaperService;

    @Autowired
    AnswersheetMapper answersheetMapper;
    /**
     * 获取试卷信息
    * */
    @RequestMapping("/getTestPaper")
    public Msg getTestPaper(@RequestBody TestpaperDTO testpaperDTO){
        System.out.println(testpaperDTO);
        List<TestpaperVO> testpaper = testpaperService.getTestpaper(testpaperDTO);
        System.out.println("=====查询==========");
        System.out.println(testpaper);
        System.out.println("=====查询==========");
        if (testpaper.size()>0){
            Page page = new Page(testpaperDTO.getSize(), testpaperDTO.getCurrent());
            page.build(testpaper);
            return ResultUtil.success(page);
        }
        return ResultUtil.error(400,"抱歉，没有找到符合信息");
    }

    /**
     * 根据试卷id查询
    * */
    @RequestMapping("/getById/{tpId}")
    public Msg getTestBankById(@PathVariable(name = "tpId") Integer tpId){
        TestpaperDTO testpaperDTO = new TestpaperDTO();
        if (tpId!=null){
            testpaperDTO.setTpId(tpId);
        }
//        List<TestpaperVO> testpaper = testpaperService.getTestpaper(testpaperDTO);
        List<ExamInfoVO> examInfoVOList = answersheetMapper.getExamInfo(tpId);
        System.out.println("123 "+examInfoVOList);
        for (ExamInfoVO item:examInfoVOList) {
            double accuracy = Double.valueOf(item.getCorrectCount()) / Double.valueOf(item.getTotal());
            item.setAccuracy(accuracy*100);
        }

        if (examInfoVOList.size()<1){
            return ResultUtil.error(400,"抱歉，所要查询的数据不存在");
        }
        return ResultUtil.success(examInfoVOList);
    }
    @RequestMapping("/getBystuId")
    public Msg getTestBankBystuId(@RequestBody TestpaperDTO testpaperDTO){
        System.out.println("+===================+");
        System.out.println(testpaperDTO);
        QueryWrapper<Answersheet> wrapper = new QueryWrapper<>();
        wrapper.eq("tp_id",testpaperDTO.getTpId());
        wrapper.eq("stu_id",testpaperDTO.getStuId());
        List<Answersheet> answersheets = answersheetMapper.selectList(wrapper);
        if (answersheets.size()>0){
            System.out.println("7777777777777777");
            return ResultUtil.success();
        }
        List<TestpaperVO> testpaper = testpaperService.getTestpaper(testpaperDTO);
        if (testpaper.size()<1){
            return ResultUtil.error(400,"抱歉，所要查询的数据不存在");
        }
        return ResultUtil.success(testpaper);
    }

    /**
     * 添加试卷
     * @param testpaper
     * @return
     */
    @RequestMapping("/save")
    public Msg addTestbank(@RequestBody Testpaper testpaper){
        System.out.println("!!!!!!添加!!!!!!!");
        System.out.println(testpaper);
        System.out.println("!!!!!!添加!!!!!!!!");
        SetExamState(testpaper.getExamLength(),testpaper);
        if (testpaper!=null){
            boolean result = testpaperService.save(testpaper);
            if (result){
                return ResultUtil.success();
            }else{
                return ResultUtil.error(400,"添加失败，请重试");
            }
        }
        return ResultUtil.error(400,"添加内容为空，添加失败");
    }

    /**
     * 修改试卷信息
     * @param testpaper
     * @return
     */
    @RequestMapping("/update")
    public Msg updateTestbank(@RequestBody Testpaper testpaper){
        System.out.println("!!!!!!修改!!!!!!!");
        System.out.println(testpaper);
        System.out.println("!!!!!!修改!!!!!!!!");
        if (testpaper.getExamLength() != null){
            SetExamState(testpaper.getExamLength(),testpaper);
        }
        if (testpaper!=null){
            boolean result = testpaperService.updateById(testpaper);
            if (result){
                return ResultUtil.success();
            }else{
                return ResultUtil.error(400,"抱歉，修改失败，请重试");
            }
        }
        return ResultUtil.error(400,"抱歉，修改内容为空，修改失败");
    }

    /**
     * 删除试卷信息
     * @param tpIds
     * @return
     */
    @RequestMapping("/delete")
    public Msg delTplist(@RequestBody Long[] tpIds){
        try {
            testpaperService.removeByIds(Arrays.asList(tpIds));
        }catch (Exception e){
            return ResultUtil.error(500,"该试卷存在关联，无法撤销");
        }
        return ResultUtil.success();
    }

    void SetExamState(LocalTime examLength,Testpaper testpaper){
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("date================>");
        System.out.println(localDateTime);
        String s = examLength.toString();
        String[] time = s.split(":");
        int hour = Integer.parseInt(time[0]);
        int min = Integer.parseInt(time[1]);
        int mins = hour*60+min;
        if (localDateTime.isBefore(testpaper.getExamTime())){
            testpaper.setExamState(0);
        }else if (localDateTime.isAfter(testpaper.getExamTime())&&localDateTime.isBefore(testpaper.getExamTime().plusMinutes(mins))){
            testpaper.setExamState(1);
        }else{
            testpaper.setExamState(2);
        }
    }
}

