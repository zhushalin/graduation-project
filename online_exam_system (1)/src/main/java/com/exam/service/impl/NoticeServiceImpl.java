package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.exam.VO.NoticeVO;
import com.exam.entity.Administrator;
import com.exam.entity.Notice;
import com.exam.mapper.AdministratorMapper;
import com.exam.mapper.NoticeMapper;
import com.exam.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
    @Autowired
    AdministratorMapper adminMapper;

    @Override
    public int getCount(Map<String, Object> params) {
        return baseMapper.selectCount(new QueryWrapper<Notice>().allEq(params));
    }

    //获取所有公告
    @Override
    public Page<Notice> getAllByPage(int curr, int size, Map<String, Object> params) {
        Page<Notice> page = new Page<>(curr,size);
        Page<Notice> list = baseMapper.selectPage(page, new QueryWrapper<Notice>().allEq(params));
        return list;
    }
    //通过公告名称模糊查询
    @Override
    public Page<Notice> search(int curr, int size, Map<String, Object> params) {
        Page<Notice> page = new Page<>(curr,size);
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();

        wrapper.like("title", params.get("title"));
        Page<Notice> list = baseMapper.selectPage(page, wrapper);
        return list;
    }

    @Override
    public int add(Notice notice) {
        notice.setId(0);
        return baseMapper.insert(notice);
    }

    @Override
    public int update(Notice notice) {
        return baseMapper.updateById(notice);
    }

    @Override
    public int delete(int id) {
        return baseMapper.deleteById(id);
    }
}
