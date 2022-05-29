package com.exam.service;

import com.exam.DTO.ScoreDTO;
import com.exam.VO.ScoreVO;
import com.exam.entity.Score;
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
public interface ScoreService extends IService<Score> {
    List<ScoreVO> getScore(ScoreDTO scoreDTO);
}
