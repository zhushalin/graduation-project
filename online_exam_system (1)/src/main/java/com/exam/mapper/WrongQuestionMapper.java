package com.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.entity.Favorite;
import com.exam.entity.WrongQuestion;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  错题Mapper 接口
 * </p>
 *
 * @author
 * @since
 */

@Component
public interface WrongQuestionMapper extends BaseMapper<WrongQuestion> {
}
