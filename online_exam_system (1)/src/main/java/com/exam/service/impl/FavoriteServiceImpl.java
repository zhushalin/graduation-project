package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.DTO.FavoriteDTO;
import com.exam.VO.FavoriteVO;
import com.exam.entity.Favorite;
import com.exam.entity.Favorite;
import com.exam.mapper.FavoriteMapper;
import com.exam.mapper.FavoriteMapper;
import com.exam.service.FavoriteService;
import com.exam.service.FavoriteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  收藏Service接口实现类
 * </p>
 *
 * @author
 * @since
 */
@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements FavoriteService {

    @Override
    public List<FavoriteVO> getAll(FavoriteDTO favoriteDTO) {
        QueryWrapper wrapper = new QueryWrapper<>();
        if (favoriteDTO != null){
            if (favoriteDTO.getStudentId() != null){
                wrapper.eq("student_id", favoriteDTO.getStudentId());
            }
        }
        List<Favorite> list = baseMapper.selectList(wrapper);
        List<FavoriteVO> voList = new ArrayList<>();
        for (Favorite favorite : list){
            voList.add(newVO(favorite));
        }
        return voList;
    }

    @Override
    public int add(Favorite favorite) {
        return baseMapper.insert(favorite);
    }

    @Override
    public int delete(int id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public int deleteByStudentId(int studentId) {
        QueryWrapper queryWrapper = new QueryWrapper<Favorite>();
        queryWrapper.eq("student_id", studentId);
        return baseMapper.delete(queryWrapper);
    }

    @Override
    public int update(Favorite favorite) {
        return baseMapper.updateById(favorite);
    }
    //转化为VO对象
    private FavoriteVO newVO(Favorite favorite){
        FavoriteVO favoriteVO = new FavoriteVO();
        favoriteVO.setId(favorite.getId());
        favoriteVO.setStudentId(favorite.getStudentId());
        favoriteVO.setType(favorite.getType());
        return favoriteVO;
    }
}
