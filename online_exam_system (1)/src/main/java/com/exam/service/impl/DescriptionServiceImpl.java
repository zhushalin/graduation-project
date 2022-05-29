package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.entity.Description;
import com.exam.mapper.AdministratorMapper;
import com.exam.mapper.DescriptionMapper;
import com.exam.service.DescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DescriptionServiceImpl extends ServiceImpl<DescriptionMapper, Description> implements DescriptionService {
    @Autowired
    AdministratorMapper adminMapper;

    @Override
    public int getCount(Map<String, Object> params) {
        return baseMapper.selectCount(new QueryWrapper<Description>().allEq(params));
    }

    //获取所有公告
    @Override
    public Page<Description> getAllByPage(int curr, int size, Map<String, Object> params) {
        Page<Description> page = new Page<>(curr,size);
        Page<Description> list = baseMapper.selectPage(page, new QueryWrapper<Description>().allEq(params));
        return list;
    }
    //通过公告名称模糊查询
    @Override
    public Page<Description> search(int curr, int size, Map<String, Object> params) {
        Page<Description> page = new Page<>(curr,size);
        QueryWrapper<Description> wrapper = new QueryWrapper<>();

        wrapper.like("title", params.get("title"));
        Page<Description> list = baseMapper.selectPage(page, wrapper);
        return list;
    }

    @Override
    public int add(Description description) {
        description.setId(0);
        return baseMapper.insert(description);
    }

    @Override
    public int update(Description description) {
        return baseMapper.updateById(description);
    }

    @Override
    public int delete(int id) {
        return baseMapper.deleteById(id);
    }
}
