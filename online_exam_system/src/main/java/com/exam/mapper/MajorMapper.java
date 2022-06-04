package com.exam.mapper;

import com.exam.entity.Major;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author
 * @since
 */

@Component
public interface MajorMapper extends BaseMapper<Major> {
    /*List<MajorVO> getMajor();*/
}
