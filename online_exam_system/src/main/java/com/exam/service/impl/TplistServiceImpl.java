package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import com.exam.entity.Tplist;
import com.exam.mapper.TplistMapper;
import com.exam.service.TplistService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  套题实现类
 * </p>
 *
 * @author
 * @since
 */
@Service
public class TplistServiceImpl extends ServiceImpl<TplistMapper, Tplist> implements TplistService {
    @Autowired
    TplistMapper tplistMapper;





    /**
     * 查询所有套题
     * @return
     */
    public Msg getTplist(){
        List<Tplist> tplists = tplistMapper.selectList(null);
        if (tplists.size()>=0){
            return ResultUtil.success(tplists);
        }else{
            return ResultUtil.error(400,"套题数量为空");
        }
    }

    /**
     * 根据套题id查询
     * @param id
     * @return
     */
    public Msg getTplistById(int id){
        QueryWrapper<Tplist> wrapper = new QueryWrapper<>();
        wrapper.eq("tp_id",id);
        List<Tplist> tplists = tplistMapper.selectList(wrapper);
        if(tplists!=null){
            return ResultUtil.success(tplists);
        }else{
            return ResultUtil.error(400,"该套题id不存在");
        }
    }

    /**
     *添加套题
     * @param tplist
     * @return
     */
    public Msg addTplist(Tplist tplist){
        int i = tplistMapper.insert(tplist);
        if (i>0){
            return ResultUtil.success();
        }else{
            return ResultUtil.error(400,"套题添加失败");
        }
    }

    /**
     * 删除套题
     * @param id
     * @return
     */
    public Msg delTplist(int id){
        QueryWrapper<Tplist> wrapper = new QueryWrapper<>();
        wrapper.eq("tp_id",id);
        int del = tplistMapper.delete(wrapper);
        if(del>0){
            return ResultUtil.success();
        }else{
            return ResultUtil.error(400,"套题删除失败");
        }
    }

    /**
     * 套题信息更新
     * @param tplist
     * @return
     */
    public Msg updateTplist(Tplist tplist){
        int update = tplistMapper.update(tplist, null);
        if(update>0){
            return ResultUtil.success();
        }else{
            return ResultUtil.error(400,"套题信息修改失败");
        }
    }
}
