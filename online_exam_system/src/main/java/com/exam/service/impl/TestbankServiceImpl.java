package com.exam.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.DTO.TestBankDTO;
import com.exam.VO.TestBankVO;
import com.exam.VO.TestTypeVO;
import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import com.exam.entity.Testbank;
import com.exam.entity.Testpaper;
import com.exam.entity.Tplist;
import com.exam.mapper.TestbankMapper;
import com.exam.mapper.TestpaperMapper;
import com.exam.mapper.TplistMapper;
import com.exam.service.TestbankService;
import com.exam.utils.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  题库实现类
 * </p>
 *
 * @author
 * @since
 */
@Service
public class TestbankServiceImpl extends ServiceImpl<TestbankMapper, Testbank> implements TestbankService {
    @Autowired
    TestbankMapper testbankMapper;

    @Autowired
    TplistMapper tplistMapper;

    @Autowired
    TestpaperMapper testpaperMapper;

    //查询所有试题
    public List<TestBankVO> getTestBanklist(TestBankDTO testBankDTO){
        QueryWrapper<TestBankVO> wrapper = new QueryWrapper<>();
        if (testBankDTO!=null){
            if (testBankDTO.getSubId()!=null){
                wrapper.eq("s.sub_id",testBankDTO.getSubId());
            }
            if (testBankDTO.getTestType()!=null){
                wrapper.eq("tb.test_type",testBankDTO.getTestType());
            }
            if (testBankDTO.getTeaId()!=null){
                wrapper.eq("tb.tea_id",testBankDTO.getTeaId());
            }
            if (StringUtils.isNotBlank(testBankDTO.getTestContent())){
                wrapper.like("tb.test_content",testBankDTO.getTestContent());
            }
            //编辑时根据获取的id搜索
            if (testBankDTO.getTestId()!=null){
                wrapper.eq("tb.test_id",testBankDTO.getTestId());
            }
            if (testBankDTO.getType()!=null){
                wrapper.eq("tb.type",testBankDTO.getType());
            }
        }
        List<TestBankVO> testBanklist = testbankMapper.getTestBanklist(wrapper);
        for (TestBankVO testBankVO:testBanklist) {
            testBankVO.setStringAnswer(testBankVO.getAnswer());
            testBankVO.setAnswerChars(testBankVO.getAnswer().toCharArray());
        }
        System.out.println(testBanklist);
        return testBanklist;
    }

    @Override
    public List<TestBankVO> getTestBanklistCtj(TestBankDTO testBankDTO) {
        QueryWrapper<TestBankVO> wrapper = new QueryWrapper<>();
        if (testBankDTO!=null){
            if (testBankDTO.getSubId()!=null){
                wrapper.eq("s.sub_id",testBankDTO.getSubId());
            }
            if (testBankDTO.getTestType()!=null){
                wrapper.eq("tb.test_type",testBankDTO.getTestType());
            }
            if (testBankDTO.getTeaId()!=null){
                wrapper.eq("tb.tea_id",testBankDTO.getTeaId());
            }
            if (StringUtils.isNotBlank(testBankDTO.getTestContent())){
                wrapper.like("tb.test_content",testBankDTO.getTestContent());
            }
            //编辑时根据获取的id搜索
            if (testBankDTO.getTestId()!=null){
                wrapper.eq("tb.test_id",testBankDTO.getTestId());
            }
        }
        List<TestBankVO> testBanklist = testbankMapper.getTestBanklistCtj(wrapper);
        for (TestBankVO testBankVO:testBanklist) {
            testBankVO.setStringAnswer(testBankVO.getAnswer());
            testBankVO.setAnswerChars(testBankVO.getAnswer().toCharArray());
        }
        System.out.println(testBanklist);
        return testBanklist;
    }

    @Override
    public List<TestBankVO> getTestbankSc(TestBankDTO testBankDTO) {
        QueryWrapper<TestBankVO> wrapper = new QueryWrapper<>();
        if (testBankDTO!=null){
            if (testBankDTO.getSubId()!=null){
                wrapper.eq("s.sub_id",testBankDTO.getSubId());
            }
            if (testBankDTO.getTestType()!=null){
                wrapper.eq("tb.test_type",testBankDTO.getTestType());
            }
            if (testBankDTO.getTeaId()!=null){
                wrapper.eq("tb.tea_id",testBankDTO.getTeaId());
            }
            if (StringUtils.isNotBlank(testBankDTO.getTestContent())){
                wrapper.like("tb.test_content",testBankDTO.getTestContent());
            }
            //编辑时根据获取的id搜索
            if (testBankDTO.getTestId()!=null){
                wrapper.eq("tb.test_id",testBankDTO.getTestId());
            }
        }
        List<TestBankVO> testBanklist = testbankMapper.getTestbankSc(wrapper);
        for (TestBankVO testBankVO:testBanklist) {
            testBankVO.setStringAnswer(testBankVO.getAnswer());
            testBankVO.setAnswerChars(testBankVO.getAnswer().toCharArray());
        }
        System.out.println(testBanklist);
        return testBanklist;
    }

    //对试卷进行试题添加时展示内容
    @Override
    public Msg getTestBanklist1(TestBankDTO testBankDTO) {
        List<TestBankVO> testBanklist = getTbList(testBankDTO,null); //获得所有试题
        if (testBanklist.size()<1){
            return ResultUtil.error(400,"抱歉，没有找到符合信息");
        }
        TestTypeVO testTypeVO = setTestTypeVO();    //初始化试题类型数量，总分为0

        for (TestBankVO testBankVO : testBanklist) {  //找出已添加与为添加试题进行逻辑处理
            QueryWrapper<Tplist> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("test_id",testBankVO.getTestId());
            wrapper1.eq("tp_id",testBankDTO.getTpId()); //根据试卷id对试题进行排查，选出该试卷试题为其赋予添加状态
            Tplist tplist = tplistMapper.selectOne(wrapper1);
            if (tplist!=null){
                if (testBankVO.getTestType()==1){
                    testTypeVO.setRadioCount(testTypeVO.getRadioCount()+1);
                    testTypeVO.setScoreTotal(testTypeVO.getScoreTotal()+3);
                }else if (testBankVO.getTestType()==2){
                    testTypeVO.setMultiCount(testTypeVO.getMultiCount()+1);
                    testTypeVO.setScoreTotal(testTypeVO.getScoreTotal()+5);
                }else if (testBankVO.getTestType()==3){
                    testTypeVO.setJudgeCount(testTypeVO.getJudgeCount()+1);
                    testTypeVO.setScoreTotal(testTypeVO.getScoreTotal()+2);
                }
                testBankVO.setTestState(1);
            }else{
                testBankVO.setTestState(0);
            }
        }
        setExamTotal(testBankDTO,testTypeVO); //将该试卷总分提交到数据库存储
        Page page = new Page(testBankDTO.getSize(),testBankDTO.getCurrent());
        page.build(testBanklist,testTypeVO);
        return ResultUtil.success(page);
    }

    //对试卷进行试题展示
    @Override
    public Msg aboutTplist(TestBankDTO testBankDTO) {
        List<TestBankVO> testBanklist = getTbList(testBankDTO,testBankDTO.getTpId()); //获得该试卷所有试题
        if (testBanklist.size()<1){
            return ResultUtil.error(400,"抱歉，没有找到符合信息");
        }
        TestTypeVO testTypeVO = setTestTypeVO();
        for (TestBankVO testBankVO : testBanklist) {   //遍历试卷所有试题进行计数
            if (testBankVO.getTestType()==1){
                testTypeVO.setRadioCount(testTypeVO.getRadioCount()+1);
                testTypeVO.setScoreTotal(testTypeVO.getScoreTotal()+3);
            }else if (testBankVO.getTestType()==2){
                testTypeVO.setMultiCount(testTypeVO.getMultiCount()+1);
                testTypeVO.setScoreTotal(testTypeVO.getScoreTotal()+5);
            }else if (testBankVO.getTestType()==3){
                testTypeVO.setJudgeCount(testTypeVO.getJudgeCount()+1);
                testTypeVO.setScoreTotal(testTypeVO.getScoreTotal()+2);
            }
        }
        setExamTotal(testBankDTO,testTypeVO); //将该试卷总分提交到数据库存储
        Page page = new Page(testBankDTO.getSize(),testBankDTO.getCurrent());
        page.build(testBanklist,testTypeVO);
        return ResultUtil.success(page);
    }

    //为试卷添加试题
    public int saveTestBank(TestBankDTO testBankDTO){
        Testbank testbank = getTestbank(testBankDTO);
        testbank.setStatus("待审核");
        return testbankMapper.insert(testbank);
    }

    //修改试卷信息
    public int updateTestBank(TestBankDTO testBankDTO){
        Testbank testbank = getTestbank(testBankDTO);
        QueryWrapper<Testbank> wrapper = new QueryWrapper<>();
        wrapper.eq("test_id",testBankDTO.getTestId());
        return  testbankMapper.update(testbank, wrapper);
    }

    //类型转换,赋值，testbankdto-->testbank
    public Testbank getTestbank(TestBankDTO testBankDTO){
        Testbank testbank = new Testbank();
        BeanUtils.copyProperties(testBankDTO,testbank);
        Arrays.sort(testBankDTO.getAnswer());
        if (testBankDTO.getTestType() != 4){
            testbank.setAnswer(String.valueOf(testBankDTO.getAnswer()));
        }else {
            testbank.setAnswer(testBankDTO.getStringAnswer());
        }

        if (testBankDTO.getTestType()==1){ //单选
            testbank.setScore(3);
        }else if (testBankDTO.getTestType()==2){ //多选
            testbank.setScore(5);
        }else if(testBankDTO.getTestType()==3){ //判断
            testbank.setScore(2);
        }

        return testbank;
    }

    //根据条件查询试题
    public List<TestBankVO> getTbList(TestBankDTO testBankDTO,Integer tpId){
        QueryWrapper<TestBankVO> wrapper = new QueryWrapper<>();
        if (testBankDTO!=null){
            if (testBankDTO.getTestType()!=null){
                wrapper.like("tb.test_type",testBankDTO.getTestType());
            }
            if (testBankDTO.getSubId()!=null){
                wrapper.like("tb.sub_Id",testBankDTO.getSubId());
            }
            if (testBankDTO.getTestContent()!=null){
                wrapper.like("tb.test_content",testBankDTO.getTestContent());
            }
            if (tpId!=null){
                    wrapper.inSql("test_id","select test_id from exam.tplist where tp_id = "+testBankDTO.getTpId());
            }

        }
        return testbankMapper.getTestBanklist(wrapper);
    }

    //初始化试题类型总分
    public TestTypeVO setTestTypeVO(){
        TestTypeVO testTypeVO = new TestTypeVO();
        testTypeVO.setJudgeCount(0);
        testTypeVO.setRadioCount(0);
        testTypeVO.setMultiCount(0);
        testTypeVO.setScoreTotal(0);
        return testTypeVO;
    }

    //上传总分
    public void setExamTotal(TestBankDTO testBankDTO, TestTypeVO testTypeVO){
        Testpaper testpaper = new Testpaper();
        testpaper.setTpId(testBankDTO.getTpId());
        testpaper.setExamTotal(testTypeVO.getScoreTotal());
        testpaperMapper.updateById(testpaper);
    }
}
