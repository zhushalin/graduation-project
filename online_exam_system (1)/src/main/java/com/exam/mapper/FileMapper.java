package com.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.entity.LearningFile;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  学习资料Mapper 接口
 * </p>
 *
 * @author
 * @since
 */

@Component
public interface FileMapper extends BaseMapper<LearningFile> {
}
