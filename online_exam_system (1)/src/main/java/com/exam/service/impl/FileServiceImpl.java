package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import com.exam.entity.LearningFile;
import com.exam.mapper.FileMapper;
import com.exam.service.FileService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  学习资料Service接口实现类
 * </p>
 *
 * @author
 * @since
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, LearningFile> implements FileService {

    /**
     * 查询所有学习资料
     * @return
     */
    @Override
    public Msg getAll(){
        List<LearningFile> files = baseMapper.selectList(null);
        if (files.size()>=0){
            return ResultUtil.success(files);
        }else{
            return ResultUtil.error(400,"学习资料数量为空");
        }
    }

    /**
     * 根据课程id查询
     * @param subjectId
     * @return
     */
    @Override
    public Msg getFilesBySubjectId(int subjectId){
        QueryWrapper<LearningFile> wrapper = new QueryWrapper<>();
        wrapper.eq("subject_id",subjectId);
        List<LearningFile> files = baseMapper.selectList(wrapper);
        if(files.size()>=0){
            return ResultUtil.success(files);
        }else{
            return ResultUtil.error(400,"该课程id不存在");
        }
    }

    /**
     *添加
     * @param file
     * @return
     */
    @Override
    public Msg add(LearningFile file){
        int i = baseMapper.insert(file);
        if (i>0){
            return ResultUtil.success();
        }else{
            return ResultUtil.error(400,"学习资料添加失败");
        }
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public Msg delete(int id){
        QueryWrapper<LearningFile> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        int del = baseMapper.delete(wrapper);
        if(del>0){
            return ResultUtil.success();
        }else{
            return ResultUtil.error(400,"学习资料删除失败");
        }
    }

    /**
     * 根据课程id删除
     * @param subjectId
     * @return
     */
    @Override
    public Msg deleteBySubjectId(int subjectId){
        QueryWrapper<LearningFile> wrapper = new QueryWrapper<>();
        wrapper.eq("subject_id",subjectId);
        int del = baseMapper.delete(wrapper);
        if(del>0){
            return ResultUtil.success();
        }else{
            return ResultUtil.error(400,"学习资料删除失败");
        }
    }

    /**
     * 更新
     * @param file
     * @return
     */
    @Override
    public Msg update(LearningFile file){
        int update = baseMapper.updateById(file);
        if(update>0){
            return ResultUtil.success();
        }else{
            return ResultUtil.error(400,"学习资料信息修改失败");
        }
    }
}
