package com.exam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.VO.CollistVO;
import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import com.exam.entity.Major;
import com.exam.mapper.CollegeMapper;
import com.exam.mapper.MajorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author
 * @since
 */
@RestController
@RequestMapping("/College")
public class CollegeController {

    @Autowired
    CollegeMapper collegeMapper;

    @Autowired
    MajorMapper majorMapper;

    @RequestMapping("/getCollegelist")
    public Msg getCollegelist(){
        List<CollistVO> collist = collegeMapper.getcol();
        ArrayList<Object> colArrayList = new ArrayList<>();

        for (CollistVO vo : collist) {
            vo.setMajorId(vo.getColId());
            vo.setMajorName(vo.getColName());
            QueryWrapper<Major> wrapper = new QueryWrapper<>();
            wrapper.eq("col_id",vo.getColId());
            List<Major> majors = majorMapper.selectList(wrapper);
            vo.setChildren(majors);
        }
        System.out.println(collist);
        return ResultUtil.success(collist);
    }

}

