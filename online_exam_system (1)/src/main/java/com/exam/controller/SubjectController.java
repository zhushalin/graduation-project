package com.exam.controller;


import com.exam.DTO.SubjectDTO;
import com.exam.VO.SubjectVO;
import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import com.exam.entity.Major;
import com.exam.entity.Subject;
import com.exam.mapper.SubjectMapper;
import com.exam.service.MajorService;
import com.exam.service.SubjectService;
import com.exam.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 学科
 * @author
 * @since
 */
@RestController
@RequestMapping("/Subject")
public class SubjectController {

    @Autowired
    SubjectService subjectService;
    @Autowired
    MajorService majorService;
    /**
     * 获得所有学科
     * @return
     */
    @RequestMapping("/getSubject")
    public Msg getSubject(@RequestBody SubjectDTO subjectDTO){
        System.out.println(subjectDTO);
        List<SubjectVO>  subjectlist = subjectService.getSubjectlist(subjectDTO);
        if (subjectlist.size()<1){
            return ResultUtil.error(400,"抱歉，没有找到学科信息");
        }
        Page page = new Page(subjectDTO.getSize(),subjectDTO.getCurrent());
        page.build(subjectlist);
        return ResultUtil.success(page);
    }
    //获取学院列表
    @RequestMapping("/getMajorData")
    public Msg getMajorData(){
        List<Major> majorlist = majorService.getMajorlist();
        return ResultUtil.success(majorlist);
    }

    //获取科目列表
    @RequestMapping("/getSubData")
    public Msg getSubData(){
        List<Subject> subjectlist = subjectService.getSubjectlist();
        return ResultUtil.success(subjectlist);
    }

    /**
     * 添加科目
     * @param subjectDTO
     * @return
     */
    @RequestMapping("/save")
    public Msg addTestbank(@RequestBody SubjectDTO subjectDTO){
        System.out.println("!!!!!!添加!!!!!!!");
        System.out.println(subjectDTO);
        System.out.println("!!!!!!添加!!!!!!!!");
        if (subjectDTO!=null){
            System.out.println("====================>");
            if(subjectService.checkSubName(subjectDTO)){
                return ResultUtil.error(400,"该专业已存在此科目,请重新输入");
            }
            int result = subjectService.saveSubjectDTO(subjectDTO);
            if (result>0){
                return ResultUtil.success();
            }else{
                return ResultUtil.error(400,"添加失败，请重试");
            }
        }
        return ResultUtil.error(400,"添加内容为空，添加失败");
    }

    /**
     * 根据学科id查询
     * @param subId
     * @return
     */
    @RequestMapping("/getById/{subId}")
    public Msg getSubjectBySubId(@PathVariable(name = "subId") Integer subId){
        SubjectDTO subjectDTO = new SubjectDTO();
        if (subId!=null){
            subjectDTO.setSubId(subId);
        }
        List<SubjectVO> subjectlist = subjectService.getSubjectlist(subjectDTO);
        if (subjectlist.size()>0){
            return ResultUtil.success(subjectlist);
        }
        return ResultUtil.error(400,"抱歉，所要修改的数据不存在");
    }

    /**
     *  修改学科信息
     * @param
     * @return
     */
    @RequestMapping("/update")
    public Msg updateSubject(@RequestBody SubjectDTO subjectDTO){
        System.out.println("!!!!!!修改!!!!!!!");
        System.out.println(subjectDTO);
        System.out.println("!!!!!!修改!!!!!!!!");
        if (subjectDTO!=null){
            int result = subjectService.updateSubject(subjectDTO);
            if (result>0){
                return ResultUtil.success();
            }else{
                return ResultUtil.error(400,"抱歉，修改失败，请重试");
            }
        }
        return ResultUtil.error(400,"抱歉，修改内容为空，修改失败");
    }

    /**
     * 删除学科
     * @param subIds
     * @return
     */
    @RequestMapping("/delete")
    public Msg delSubject(@RequestBody Long[] subIds){
        System.out.println("=========删除============");
        System.out.println(Arrays.asList(subIds)); //数组转list
        System.out.println("=========删除============");
        try {
            subjectService.removeByIds(Arrays.asList(subIds));
        }catch (Exception e){
            return ResultUtil.error(500,"该学科存在关联，无法删除");
        }
        return ResultUtil.success("");
    }
}
