package com.exam.mapper;

import com.exam.VO.CollistVO;
import com.exam.entity.College;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface CollegeMapper extends BaseMapper<College> {
    List<CollistVO> getcol();
}
