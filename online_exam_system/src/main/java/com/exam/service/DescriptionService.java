package com.exam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.entity.Description;
import com.exam.entity.Notice;

import java.util.Map;

//简介服务接口
public interface DescriptionService extends IService<Description> {
    int getCount(Map<String, Object> params);
    //分页查询所有简介,可以在map中存入查询条件，实现按条件查询
    Page<Description> getAllByPage(int curr, int size, Map<String, Object> params);
    Page<Description> search(int curr, int size, Map<String, Object> params);
    int add(Description description);
    int update(Description description);
    int delete(int id);

}
