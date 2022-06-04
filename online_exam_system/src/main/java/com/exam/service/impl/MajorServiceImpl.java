package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import com.exam.entity.Major;
import com.exam.mapper.MajorMapper;
import com.exam.service.MajorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.sun.corba.se.spi.orbutil.threadpool.WorkQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  专业实现类
 * </p>
 *
 * @author
 * @since
 */
@Service
public class MajorServiceImpl extends ServiceImpl<MajorMapper, Major> implements MajorService {
    @Autowired
    MajorMapper majorMapper;

    /**
     * 查询所有专业
     * @return
     */
    public Msg getMajor(){
        List<Major> majors = majorMapper.selectList(null);
        if (majors.size()>=0){
            return ResultUtil.success(majors);
        }else{
            return ResultUtil.error(400,"专业数量为空");
        }
    }

    /**
     * 根据专业id查询
     * @param id
     * @return
     */
    public Msg getMajorById(int id){
        QueryWrapper<Major> wrapper = new QueryWrapper<>();
        wrapper.eq("major_id",id);
        List<Major> majors = majorMapper.selectList(wrapper);
        if(majors.size()>=0){
            return ResultUtil.success(majors);
        }else{
            return ResultUtil.error(400,"该专业id不存在");
        }
    }

    /**
     *添加专业
     * @param major
     * @return
     */
    public Msg addMajor(Major major){
        int i = majorMapper.insert(major);
        if (i>0){
            return ResultUtil.success();
        }else{
            return ResultUtil.error(400,"专业添加失败");
        }
    }

    /**
     * 删除专业
     * @param id
     * @return
     */
    public Msg delMajor(int id){
        QueryWrapper<Major> wrapper = new QueryWrapper<>();
        wrapper.eq("major_id",id);
        int del = majorMapper.delete(wrapper);
        if(del>0){
            return ResultUtil.success();
        }else{
            return ResultUtil.error(400,"专业删除失败");
        }
    }

    /**
     * 专业信息更新
     * @param major
     * @return
     */
    public Msg updateMajor(Major major){
        int update = majorMapper.update(major, null);
        if(update>0){
            return ResultUtil.success();
        }else{
            return ResultUtil.error(400,"专业信息修改失败");
        }
    }

    @Override
    public List<Major> getMajorlist() {
       return majorMapper.selectList(null);
    }
}
