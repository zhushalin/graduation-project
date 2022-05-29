package com.exam.service;

import com.exam.DTO.ScoreDTO;
import com.exam.DTO.TestpaperDTO;
import com.exam.VO.ScoreVO;
import com.exam.VO.TestpaperVO;
import com.exam.entity.Testpaper;
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
public interface TestpaperService extends IService<Testpaper> {
    List<TestpaperVO> getTestpaper(TestpaperDTO testpaperDTO);

    List<ScoreVO> getScore(ScoreDTO scoreDTO);



/*    int saveTestpaper(TestpaperDTO testpaperDTO);*/
}
