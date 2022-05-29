package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.DTO.ScoreDTO;
import com.exam.DTO.TestpaperDTO;
import com.exam.VO.ScoreVO;
import com.exam.VO.TestpaperVO;
import com.exam.entity.Answersheet;
import com.exam.entity.Testpaper;
import com.exam.mapper.AnswersheetMapper;
import com.exam.mapper.TestpaperMapper;
import com.exam.service.AnswersheetService;
import com.exam.service.TestpaperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author
 * @since
 */
@Service
public class TestpaperServiceImpl extends ServiceImpl<TestpaperMapper, Testpaper> implements TestpaperService {

    @Autowired
    TestpaperMapper testpaperMapper;

    @Autowired
    TestpaperService testpaperService;

    @Autowired
    AnswersheetMapper answersheetMapper;

    @Autowired
    AnswersheetService answersheetService;
    //获取试卷
    @Override
    public List<TestpaperVO> getTestpaper(TestpaperDTO testpaperDTO) {
        QueryWrapper<TestpaperVO> wrapper = new QueryWrapper<>();
        if (testpaperDTO!=null){
            if (testpaperDTO.getExamState()!=null){
                wrapper.eq("exam_state",testpaperDTO.getExamState());
            }
            if (testpaperDTO.getSubId()!=null){
                wrapper.eq("s.sub_id",testpaperDTO.getSubId());
            }
            if (testpaperDTO.getTeaId()!=null){
                wrapper.eq("tea_id",testpaperDTO.getTeaId());
            }
            if (testpaperDTO.getTpName()!=null){
                wrapper.like("tp_name",testpaperDTO.getTpName());
            }
            if (testpaperDTO.getTpId()!=null){
                wrapper.eq("tp_id",testpaperDTO.getTpId());
            }
            if (testpaperDTO.getExamTime()!=null){  //查询考试开始时间之后的考试
                wrapper.apply("UNIX_TIMESTAMP(exam_time) >= UNIX_TIMESTAMP('" + testpaperDTO.getExamTime() + "')");
            }
        }
        //查询符合条件的试卷
        List<TestpaperVO> testpapers = testpaperMapper.getTestpaper(wrapper);
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("date================>");
        System.out.println(localDateTime);
        //设置试卷的考试状态
        for (TestpaperVO testpaper : testpapers) {
            int time = getTime(testpaper.getExamLength());
            testpaper.setExamLong(time);
            if (testpaper.getFiles()!=null){
                testpaper.setFileUrls(testpaper.getFiles().split("，"));
            }
            if (localDateTime.isBefore(testpaper.getExamTime())){
                testpaper.setExamState(0);
            }else if (localDateTime.isAfter(testpaper.getExamTime())&&localDateTime.isBefore(testpaper.getExamTime().plusMinutes(time))){
                testpaper.setExamState(1);
            }else{
                testpaper.setExamState(2);
            }
            Testpaper testpaper1 = new Testpaper();
            testpaper1.setTpId(testpaper.getTpId());
            testpaper1.setExamState(testpaper.getExamState());
            testpaperMapper.updateById(testpaper1);
        }
        System.out.println(testpapers);
        return testpapers;
    }

    //获取学生所有试卷考试情况
    @Override
    public List<ScoreVO> getScore(ScoreDTO scoreDTO) {
        System.out.println("======================>");
        QueryWrapper<ScoreVO> wrapper = new QueryWrapper<>();
        if (scoreDTO!=null){
            if (scoreDTO.getSubId()!=null){
                wrapper.eq("s.sub_id",scoreDTO.getSubId());
            }
            if (scoreDTO.getStuId()!=null){
                wrapper.eq("a.stu_id",scoreDTO.getStuId());
            }
            if (scoreDTO.getTpName()!=null){
                wrapper.like("tp_name",scoreDTO.getTpName());
            }
            if (scoreDTO.getTpId()!=null){
                wrapper.eq("tp_id",scoreDTO.getTpId());
            }
            if (scoreDTO.getExamTime()!=null){  //查询考试开始时间之后的考试
                wrapper.apply("UNIX_TIMESTAMP(exam_time) >= UNIX_TIMESTAMP('" + scoreDTO.getExamTime() + "')");
            }
            List<ScoreVO> scoreVOS = testpaperMapper.getScore(wrapper);
            System.out.println("=======scoreVOS===========");
            System.out.println(scoreVOS);
            System.out.println("=======scoreVOS===========");
            for (ScoreVO scoreVO : scoreVOS) {
                int time = getTime(scoreVO.getExamLength());
                scoreVO.setExamLong(time);
               /* HashMap<String, Object> map = new HashMap<>();
                map.put("tp_id",scoreVO.getTpId());
                map.put("stu_id",scoreDTO.getStuId());*/
                QueryWrapper<Answersheet> wrapper1 = new QueryWrapper<>();
                wrapper1.select("COALESCE(sum(stu_score),0) as total");
                wrapper1.eq("tp_id",scoreVO.getTpId());
                if (scoreDTO.getStuId()!=null){
                    wrapper1.eq("stu_id",scoreDTO.getStuId());
                }
                Map<String,Object> map = answersheetService.getMap(wrapper1);
                System.out.println("===================");
                System.out.println(answersheetService.getMap(wrapper1).get("total"));
                Integer total = Integer.valueOf(String.valueOf(map.get("total")));
                System.out.println("===================");
                scoreVO.setExamScore(total);
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
}
