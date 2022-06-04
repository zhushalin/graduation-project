package com.exam.controller;

import com.exam.DTO.WrongQuestionDTO;
import com.exam.VO.WrongQuestionVO;
import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import com.exam.entity.WrongQuestion;
import com.exam.service.WrongQuestionService;
import com.exam.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  错题信息Controller
 * @author
 * @since
 */
@RestController
@RequestMapping("/wrong")
public class WrongQuestionController {

    @Autowired
    WrongQuestionService wrongQuestionService;

    /**
     * 查询所有错题
     * @param wrongQuestionDTO
     * @return
     */
    @RequestMapping("/all")
    public Msg getAll(@RequestBody WrongQuestionDTO wrongQuestionDTO){
        List<WrongQuestionVO> wrongQuestionList = wrongQuestionService.getAll(wrongQuestionDTO);
        if (wrongQuestionList.size()>0){
            //判断是否需要分页
            if (wrongQuestionDTO.isPage()){//进行分页
                Page page = new Page(wrongQuestionDTO.getSize(), wrongQuestionDTO.getCurrent());
                page.build(wrongQuestionList);
                return ResultUtil.success(page);
            }else {
                return ResultUtil.success(wrongQuestionList);
            }
        }
        return ResultUtil.error(400,"抱歉，没有找到错题信息");
    }

    /**根据错题id查询
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    public Msg getWrongQuestionById(@PathVariable(name = "id") Integer id){
        WrongQuestion wrongQuestion = wrongQuestionService.getById(id);
        if (wrongQuestion != null){
            return ResultUtil.success(wrongQuestion);
        }
        return ResultUtil.error(400,"没有该错题信息");
    }

    /**添加错题信息
     * @param wrongQuestion
     * @return
     */
    @RequestMapping({"/add"})
    public Msg add(@RequestBody WrongQuestion wrongQuestion){
        if (wrongQuestion != null){
            System.out.println(wrongQuestion);
            int result = wrongQuestionService.add(wrongQuestion);
            if (result>0){
                return ResultUtil.success();
            }else{
                return ResultUtil.error(400,"添加失败，请重试");
            }
        }
        return ResultUtil.error(400,"添加内容为空，添加失败");
    }

    /**修改错题信息
     * @param wrongQuestion
     * @return
     */
    @RequestMapping("/update")
    public Msg update(@RequestBody WrongQuestion wrongQuestion){
        if (wrongQuestion != null){
            System.out.println(wrongQuestion);
            if (wrongQuestionService.update(wrongQuestion) > 0){
                return ResultUtil.success();
            }
            return ResultUtil.error(400,"抱歉，修改失败，请重试");
        }
        return ResultUtil.error(400,"抱歉，修改内容为空，修改失败");
    }

    /**删除错题信息
     * @param wrongQuestionDTO
     * @return
     */
    @RequestMapping("/delete")
    public Msg delete(@RequestBody WrongQuestionDTO wrongQuestionDTO){
        List<Integer> ids = wrongQuestionDTO.getIds();
        System.out.println(ids);
        try {
            wrongQuestionService.removeByIds(ids);
        }catch (Exception e){
            return ResultUtil.error(500,"该错题存在关联，无法删除");
        }
        return ResultUtil.success("");
    }
}


