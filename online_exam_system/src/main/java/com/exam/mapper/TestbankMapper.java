package com.exam.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.exam.VO.TestBankVO;
import com.exam.entity.Answersheet;
import com.exam.entity.Testbank;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;


import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author
 * @since
 */

@Component
public interface TestbankMapper extends BaseMapper<Testbank> {
    List<TestBankVO> getTestBanklist(@Param(Constants.WRAPPER) Wrapper wrapper);
    List<TestBankVO> getTestBanklistCtj(@Param(Constants.WRAPPER) Wrapper wrapper);
    List<TestBankVO> getTestbankSc(@Param(Constants.WRAPPER) Wrapper wrapper);

    List<TestBankVO> getTestBanklist1(@Param(Constants.WRAPPER) Wrapper wrapper,@Param("tpId") Integer tpId);

    List<TestBankVO> getTplistTest(@Param("tpId") Integer tpId);

    List<Answersheet> getExamTest(@Param("tpId") Integer tpId, @Param("stuId") Integer stuId);

}
