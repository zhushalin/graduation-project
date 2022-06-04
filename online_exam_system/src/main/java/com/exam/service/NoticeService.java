package com.exam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.VO.NoticeVO;
import com.exam.entity.Notice;

import java.util.Map;

//场地服务接口
public interface NoticeService extends IService<Notice> {
    int getCount(Map<String, Object> params);
    //分页查询所有用户,可以在map中存入查询条件，实现按条件查询
    Page<Notice> getAllByPage(int curr, int size, Map<String, Object> params);
    Page<Notice> search(int curr, int size, Map<String, Object> params);
    int add(Notice notice);
    int update(Notice notice);
    int delete(int id);

}
