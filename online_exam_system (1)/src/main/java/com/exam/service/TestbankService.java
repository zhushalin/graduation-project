package com.exam.service;

import com.exam.DTO.TestBankDTO;
import com.exam.VO.TestBankVO;
import com.exam.common.Msg;
import com.exam.entity.Testbank;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author
 * @since
 */
public interface TestbankService extends IService<Testbank> {

    List<TestBankVO> getTestBanklist(TestBankDTO testBankDTO);

    List<TestBankVO> getTestBanklistCtj(TestBankDTO testBankDTO);

    List<TestBankVO> getTestbankSc(TestBankDTO testBankDTO);

    Msg getTestBanklist1(TestBankDTO testBankDTO);

    Msg aboutTplist(TestBankDTO testBankDTO);

    int saveTestBank(TestBankDTO testBankDTO);

    int updateTestBank(TestBankDTO testBankDTO);

}
