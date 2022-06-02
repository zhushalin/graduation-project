package com.exam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.DTO.TestBankDTO;
import com.exam.VO.TestBankVO;
import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import com.exam.entity.Favorite;
import com.exam.entity.Subject;
import com.exam.entity.Testbank;
import com.exam.service.FavoriteService;
import com.exam.service.SubjectService;
import com.exam.service.TestbankService;
import com.exam.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 试题库
 * @author
 * @since
 */
@RestController
@RequestMapping("/TestBank")
public class TestbankController {
    @Autowired
    TestbankService testbankService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    FavoriteService favoriteService;

    /**
     * 获得所有试题、按搜索框内容查询
     * @return
     */
    @RequestMapping("/getTestbank")
    public Msg getTestbank(@RequestBody TestBankDTO testBankDTO){
        System.out.println(testBankDTO);
        List<TestBankVO> testBanklist = testbankService.getTestBanklist(testBankDTO);
        if (testBanklist.size()<1){
            return ResultUtil.error(400,"抱歉，没有找到符合信息");
        }
        Page page = new Page(testBankDTO.getSize(),testBankDTO.getCurrent());
        page.build(testBanklist);
        return ResultUtil.success(page);
    }
    @RequestMapping("/getTestbank2")
    public Msg getTestbank2(@RequestBody TestBankDTO testBankDTO){
        System.out.println(testBankDTO);
        List<TestBankVO> testBanklist = testbankService.getTestBanklist(testBankDTO);
        if (testBanklist.size()<1){
            return ResultUtil.error(400,"抱歉，没有找到符合信息");
        }
        Page page = new Page(testBankDTO.getSize(),testBankDTO.getCurrent());
        page.build(testBanklist);
        return ResultUtil.success(page);
    }

    @RequestMapping("/getTestbankCtj")
    public Msg getTestbankCtj(@RequestBody TestBankDTO testBankDTO){
        System.out.println(testBankDTO);
        List<TestBankVO> testBanklist = testbankService.getTestBanklistCtj(testBankDTO);
        if (testBanklist.size()<1){
            return ResultUtil.error(400,"抱歉，没有找到符合信息");
        }
        Page page = new Page(testBankDTO.getSize(),testBankDTO.getCurrent());
        page.build(testBanklist);
        return ResultUtil.success(page);
    }

    @RequestMapping("/getTestbankSc")
    public Msg getTestbankSc(@RequestBody TestBankDTO testBankDTO){
        System.out.println(testBankDTO);
        List<TestBankVO> testBanklist = testbankService.getTestbankSc(testBankDTO);
        if (testBanklist.size()<1){
            return ResultUtil.error(400,"抱歉，没有找到符合信息");
        }
        for (TestBankVO testBankVO : testBanklist) {
            QueryWrapper<Favorite> wrapper = new QueryWrapper<>();
            wrapper.eq("student_id",testBankDTO.getStuId());
            Favorite favorite = favoriteService.getOne(wrapper);
            if(favorite!=null){
                testBankVO.setScid(favorite.getId()+"");
            }
        }
        Page page = new Page(testBankDTO.getSize(),testBankDTO.getCurrent());
        page.build(testBanklist);
        return ResultUtil.success(page);
    }

    //获取科目列表
    @RequestMapping("/getSubData")
    public Msg getSubData(){
        List<Subject> subjectlist = subjectService.getSubjectlist();
        return ResultUtil.success(subjectlist);
    }

    //根据试题id查询
    @RequestMapping("/getById/{testId}")
    public Msg getTestBankById(@PathVariable(name = "testId") Integer testId){
        TestBankDTO testBankDTO = new TestBankDTO();
        if (testId!=null){
            testBankDTO.setTestId(testId);
        }
        List<TestBankVO> testBanklist = testbankService.getTestBanklist(testBankDTO);
        if (testBanklist.size()<1){
            return ResultUtil.error(400,"抱歉，所要查询的数据不存在");
        }
        for (TestBankVO testBankVO : testBanklist) {
            QueryWrapper<Favorite> wrapper = new QueryWrapper<>();
//            wrapper.eq("student_id",testBankDTO.getStuId());
            wrapper.eq("question_id",testBankVO.getTestId());
            Favorite favorite = favoriteService.getOne(wrapper);
            if(favorite!=null){
                testBankVO.setScid(favorite.getId()+"");
            }
        }
        return ResultUtil.success(testBanklist);
    }

    /**
     * 添加试题
     * @param testBankDTO
     * @return
     */
    @RequestMapping("/save")
    public Msg addTestbank(@RequestBody TestBankDTO testBankDTO){
        System.out.println("!!!!!!添加!!!!!!!");
        System.out.println(testBankDTO);
        if (testBankDTO!=null){
            if (testBankDTO.getStringAnswer() != null){
                String str = testBankDTO.getStringAnswer();
                testBankDTO.setAnswer(str.toCharArray());
                System.out.println(testBankDTO.getAnswer());
            }
            int result = testbankService.saveTestBank(testBankDTO);

            if (result>0){
                return ResultUtil.success();
            }else{
                return ResultUtil.error(400,"添加失败，请重试");
            }
        }
        return ResultUtil.error(400,"添加内容为空，添加失败");
    }

    /**
     * 修改试题信息
     * @param testBankDTO
     * @return
     */
    @RequestMapping("/update")
    public Msg updateTestbank(@RequestBody TestBankDTO testBankDTO){
/*        System.out.println("!!!!!!修改!!!!!!!");
        System.out.println(testBankDTO);*/
        if (testBankDTO!=null){
            int result = testbankService.updateTestBank(testBankDTO);
            if (result>0){
                return ResultUtil.success();
            }else{
                return ResultUtil.error(400,"抱歉，修改失败，请重试");
            }
        }
        return ResultUtil.error(400,"抱歉，修改内容为空，修改失败");
    }

    /**
     * 删除试题
     * @param testIds
     * @return
     */
    @RequestMapping("/delete")
    public Msg delTestbank(@RequestBody Long[] testIds){
        System.out.println("======删除====");
        System.out.println(Arrays.asList(testIds));
        try {
            testbankService.removeByIds(Arrays.asList(testIds));
        }catch (Exception e){
            return ResultUtil.error(500,"该试题存在关联，无法删除");
        }

        return ResultUtil.success("");
    }


}
