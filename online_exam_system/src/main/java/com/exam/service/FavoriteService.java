package com.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.DTO.FavoriteDTO;
import com.exam.VO.FavoriteVO;
import com.exam.entity.Favorite;

import java.util.List;

/**
 * <p>
 *  收藏服务类
 * </p>
 *
 * @author
 * @since
 */
public interface FavoriteService extends IService<Favorite> {

    /**
     * 根据条件查询所有
     * @param
     * @return
     */
    List<FavoriteVO> getAll(FavoriteDTO favoriteDTO);

    /**
     *添加
     * @param favorite
     * @return
     */
    int add(Favorite favorite);

    /**
     * 删除
     * @param id
     * @return
     */
    int delete(int id);

    /**
     * 根据学生id删除
     * @param studentId
     * @return
     */
    int deleteByStudentId(int studentId);

    /**
     * 更新
     * @param favorite
     * @return
     */
    int update(Favorite favorite);
}
