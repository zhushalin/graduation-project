package com.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.entity.Attendance;
import com.exam.entity.Favorite;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  收藏Mapper 接口
 * </p>
 *
 * @author
 * @since
 */

@Component
public interface FavoriteMapper extends BaseMapper<Favorite> {
}
