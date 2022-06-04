package com.exam.controller;


import com.exam.DTO.ScoreDTO;
import com.exam.VO.ScoreVO;
import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import com.exam.entity.Score;
import com.exam.mapper.AnswersheetMapper;
import com.exam.service.AnswersheetService;
import com.exam.service.ScoreService;
import com.exam.service.TestpaperService;
import com.exam.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 成绩
 * @author
 * @since
 */
@RestController
@RequestMapping("/Score")
public class ScoreController {

    @Autowired
    AnswersheetService answersheetService;

    @Autowired
    AnswersheetMapper answersheetMapper;

    @Autowired
    TestpaperService testpaperService;

    @Autowired
    ScoreService scoreService;
    /**
     * 获得所有成绩
     * @return
     */
    @RequestMapping("/getScore")
    public Msg getScore(@RequestBody ScoreDTO scoreDTO){
        System.out.println(scoreDTO);
        List<ScoreVO> scoreVOS = scoreService.getScore(scoreDTO);
        System.out.println(scoreVOS);
        if (scoreVOS.size()>0){
            Page page = new Page(scoreDTO.getSize(), scoreDTO.getCurrent());
            page.build(scoreVOS);
            return ResultUtil.success(page);
        }
        return ResultUtil.success();
    }

    /**
     * 根据学生id查询
     * @param stuId
     * @return
     */
    @RequestMapping("/getById/{stuId}")
    public Msg getScoreByStuId(@PathVariable(name = "stuId") int stuId){
        ScoreDTO scoreDTO = new ScoreDTO();
        scoreDTO.setStuId(stuId);
        scoreDTO.setState(1);
        List<ScoreVO> scoreVOS = scoreService.getScore(scoreDTO);
        System.out.println(scoreVOS);
        if (scoreVOS.size()>0){
            int size = scoreDTO.getSize()!=null?scoreDTO.getSize():10;
            int current = scoreDTO.getCurrent()!=null?scoreDTO.getCurrent():1;
            Page page = new Page(size, current);
            page.build(scoreVOS);
            return ResultUtil.success(page);
        }
        return ResultUtil.success();
    }
    /**
     * 根据学科id查询
     * @param id
     * @return
     */
    @RequestMapping("/getScoreBySubId/{id}")
    public Msg getScoreBySubId(@PathVariable(name = "id") int id){
        return null;
    }

    /**
     * 添加成绩
     * @param score
     * @return
     */
    @RequestMapping("/add")
    public Msg addScore(@RequestBody Score score){
        if (scoreService.save(score)){
            return ResultUtil.success();
        }
        return ResultUtil.error(400,"添加成绩失败");
    }

    /**
     * 删除成绩
     * @param id
     * @return
     */
    @RequestMapping("/delScore/{id}")
    public Msg delScore(@PathVariable(name = "id") int id){
        return null;
    }

    /**
     * 修改成绩信息
     * @param score
     * @return
     */
    @RequestMapping("/update")
    public Msg updateScore(@RequestBody Score score){
        if (scoreService.updateById(score)){
            return ResultUtil.success();
        }
        return ResultUtil.error(400,"修改成绩失败");
    }
}
