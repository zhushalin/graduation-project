package com.exam.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.exam.VO.ScoreVO;
import com.exam.VO.TestpaperVO;
import com.exam.entity.Testpaper;
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
public interface TestpaperMapper extends BaseMapper<Testpaper> {
        List<TestpaperVO> getTestpaper(@Param(Constants.WRAPPER) Wrapper wrapper);

        List<ScoreVO> getScore(@Param(Constants.WRAPPER) Wrapper wrapper);


}
