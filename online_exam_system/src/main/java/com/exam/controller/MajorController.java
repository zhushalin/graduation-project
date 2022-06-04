package com.exam.controller;


import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import com.exam.entity.Major;
import com.exam.mapper.MajorMapper;
import com.exam.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 专业
 * @author
 * @since
 */
@RestController
@RequestMapping("/Major")
public class MajorController {
    @Autowired
    MajorService majorService;

    @Autowired
    MajorMapper majorMapper;

    /**
     * 获得所有专业
     * @return
     */
    @RequestMapping("/getMajor")
    public Msg getMajor(){
        List<Major> majors = majorMapper.selectList(null);
        if (majors!=null){
            return ResultUtil.success(majors);
        }
        return ResultUtil.error(400,"抱歉，没有找到专业信息");
    }

    //获取学院列表
    @RequestMapping("/getMajorData")
    public Msg getMajorData(){
        List<Major> majorlist = majorService.getMajorlist();
        return ResultUtil.success(majorlist);
    }

    /**
     * 根据专业id查询
     * @param id
     * @return
     */
    @RequestMapping("/getMajorById/{id}")
    public Msg getMajorById(@PathVariable(name = "id") int id){
        return null;
    }

    /**
     * 添加专业
     * @param major
     * @return
     */
    @RequestMapping("/addMajor")
    public Msg addMajor(@RequestBody Major major){
        return null;
    }

    /**
     * 删除专业
     * @param id
     * @return
     */
    @RequestMapping("/delMajor/{id}")
    public Msg delMajor(@PathVariable(name = "id") int id){
        return null;
    }

    /**
     * 修改专业信息
     * @param major
     * @return
     */
    @RequestMapping("/updateMajor")
    public Msg updateMajor(@RequestBody Major major){
        return null;
    }
}
