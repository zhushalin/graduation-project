package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import com.exam.entity.Answersheet;
import com.exam.mapper.AnswersheetMapper;
import com.exam.service.AnswersheetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  答题卡实现类
 * </p>
 *
 * @author
 * @since
 */
@Service
public class AnswersheetServiceImpl extends ServiceImpl<AnswersheetMapper, Answersheet> implements AnswersheetService {
    @Autowired
    AnswersheetMapper answersheetMapper;

    /**
     * 查询所有答题卡
     * @return
     */
    public Msg getAnswersheet(){
        List<Answersheet> answersheets = answersheetMapper.selectList(null);
        if (answersheets.size()>=0){
            return ResultUtil.success(answersheets);
        }else{
            return ResultUtil.error(400,"没有答题卡存在");
        }
    }

    /**
     * 根据答题卡id查询
     * @param id
     * @return
     */
    public Msg getAnswersheetById(int id){
        QueryWrapper<Answersheet> wrapper = new QueryWrapper<>();
        wrapper.eq("as_id",id);
        List<Answersheet> answersheets = answersheetMapper.selectList(wrapper);
        if(answersheets.size()>=0){
            return ResultUtil.success(answersheets);
        }else{
            return ResultUtil.error(400,"该答题卡id不存在");
        }
    }

    /**
     *添加答题卡
     * @param answersheet
     * @return
     */
    public Msg addAnswersheet(Answersheet answersheet){
        int i = answersheetMapper.insert(answersheet);
        if (i>0){
            return ResultUtil.success();
        }else{
            return ResultUtil.error(400,"答题卡添加失败");
        }
    }

    /**
     * 删除答题卡
     * @param id
     * @return
     */
    public Msg delAnswersheet(int id){
        QueryWrapper<Answersheet> wrapper = new QueryWrapper<>();
        wrapper.eq("as_id",id);
        int del = answersheetMapper.delete(wrapper);
        if(del>0){
            return ResultUtil.success();
        }else{
            return ResultUtil.error(400,"答题卡删除失败");
        }
    }

    /**
     * 答题卡信息更新
     * @param answersheet
     * @return
     */
    public Msg updateAnswersheet(Answersheet answersheet){
        int update = answersheetMapper.update(answersheet, null);
        if(update>0){
            return ResultUtil.success();
        }else{
            return ResultUtil.error(400,"答题卡信息修改失败");
        }
    }
}
