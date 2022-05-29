package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.DTO.ScoreDTO;
import com.exam.VO.ScoreVO;
import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import com.exam.entity.Answersheet;
import com.exam.entity.Score;
import com.exam.mapper.AnswersheetMapper;
import com.exam.mapper.ScoreMapper;
import com.exam.service.ScoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

/**
 * <p>
 *  学生成绩表实现类
 * </p>
 *
 * @author
 * @since
 */
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements ScoreService {
    @Autowired
    ScoreMapper scoreMapper;

    @Autowired
    AnswersheetMapper answersheetMapper;

    //获取学生所有试卷考试情况
    @Override
    public List<ScoreVO> getScore(ScoreDTO scoreDTO) {
        System.out.println("======================>");
        System.out.println(scoreDTO);
        QueryWrapper<ScoreVO> wrapper = new QueryWrapper<>();
        if (scoreDTO!=null){
            if (scoreDTO.getSubId()!=null){
                wrapper.eq("s.sub_id",scoreDTO.getSubId());
            }
            if (scoreDTO.getState() != null){
                wrapper.eq("s.state", scoreDTO.getState());
            }
            if (scoreDTO.getStuId()!=null){
                wrapper.eq("s.stu_id",scoreDTO.getStuId());
            }
            if (scoreDTO.getTpName()!=null){
                wrapper.like("tp.tp_name",scoreDTO.getTpName());
            }
            if (scoreDTO.getTpId()!=null){
                wrapper.eq("s.tp_id",scoreDTO.getTpId());
            }
            if (scoreDTO.getExamTime()!=null){  //查询考试开始时间之后的考试
                wrapper.apply("UNIX_TIMESTAMP(tp.exam_time) >= UNIX_TIMESTAMP('" + scoreDTO.getExamTime() + "')");
            }
            List<ScoreVO> scoreVOS = scoreMapper.getScore(wrapper);
            System.out.println("=======scoreVOS===========");
            System.out.println(scoreVOS);
            System.out.println("=======scoreVOS===========");
            for (ScoreVO scoreVO : scoreVOS) {
                int time = getTime(scoreVO.getExamLength());
                if (scoreVO.getFiles()!=null){
                    scoreVO.setFileUrls(scoreVO.getFiles().split("，"));
                }
                scoreVO.setExamLong(time);
//                QueryWrapper<Answersheet> wrapper1 = new QueryWrapper<>();
//                wrapper1.select("sum(stu_score)");
//                wrapper1.eq("tp_id",scoreVO.getTpId());
//                wrapper1.eq("stu_id",scoreDTO.getSubId());
//                Answersheet answersheets = answersheetMapper.selectOne(wrapper1);
//                System.out.println(answersheets);
//                scoreVO.setExamScore(answersheets.getStuScore());
            }
            System.out.println(scoreVOS);
            return scoreVOS;
        }
        return null;
    }


    int getTime(LocalTime examLength){
        String s = examLength.toString();
        String[] time = s.split(":");
        int hour = Integer.parseInt(time[0]);
        int min = Integer.parseInt(time[1]);
        return hour*60+min;
    }
    /**
     * 查询所有学生成绩
     * @return
     */
    public Msg getScore(){
        List<Score> scores = scoreMapper.selectList(null);
        if (scores.size()>=0){
            return ResultUtil.success(scores);
        }else{
            return ResultUtil.error(400,"学生成绩数量为空");
        }
    }

    /**
     * 根据学生id查询
     * @param id
     * @return
     */
    public Msg getScoreByStuId(int id){
        QueryWrapper<Score> wrapper = new QueryWrapper<>();
        wrapper.eq("stu_id",id);
        List<Score> scores = scoreMapper.selectList(wrapper);
        if(scores.size()>=0){
            return ResultUtil.success(scores);
        }else{
            return ResultUtil.error(400,"该学生id不存在");
        }
    }
    /**
     * 根据科目id查询
     * @param id
     * @return
     */
    public Msg getScoreBySubId(int id){
        QueryWrapper<Score> wrapper = new QueryWrapper<>();
        wrapper.eq("sub_id",id);
        List<Score> scores = scoreMapper.selectList(wrapper);
        if(scores.size()>=0){
            return ResultUtil.success(scores);
        }else{
            return ResultUtil.error(400,"该科目id不存在");
        }
    }

    /**
     *添加学生成绩
     * @param score
     * @return
     */
    public Msg addScore(Score score){
        int i = scoreMapper.insert(score);
        if (i>0){
            return ResultUtil.success();
        }else{
            return ResultUtil.error(400,"学生成绩添加失败");
        }
    }

    /**
     * 删除学生成绩
     * @param id
     * @return
     */
    public Msg delScore(int id){
        QueryWrapper<Score> wrapper = new QueryWrapper<>();
        wrapper.eq("stu_id",id);
        int del = scoreMapper.delete(wrapper);
        if(del>0){
            return ResultUtil.success();
        }else{
            return ResultUtil.error(400,"学生成绩删除失败");
        }
    }

    /**
     * 学生成绩信息更新
     * @param score
     * @return
     */
    public Msg updateScore(Score score){
        int update = scoreMapper.update(score, null);
        if(update>0){
            return ResultUtil.success();
        }else{
            return ResultUtil.error(400,"学生成绩信息修改失败");
        }
    }
}
