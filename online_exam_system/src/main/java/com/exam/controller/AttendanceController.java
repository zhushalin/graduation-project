package com.exam.controller;

import com.exam.DTO.AttendanceDTO;
import com.exam.VO.AttendanceVO;
import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import com.exam.entity.Attendance;
import com.exam.mapper.AttendanceMapper;
import com.exam.service.AttendanceService;
import com.exam.service.AttendanceService;
import com.exam.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *  签到信息Controller
 * @author
 * @since
 */
@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    /**
     * 查询所有签到
     * @param attendanceDTO
     * @return
     */
    @RequestMapping("/all")
    public Msg getAll(@RequestBody AttendanceDTO attendanceDTO){
        List<AttendanceVO> attendanceList = attendanceService.getAll(attendanceDTO);
        if (attendanceList.size()>0){
            //判断是否需要分页
            if (attendanceDTO.isPage()){//进行分页
                Page page = new Page(attendanceDTO.getSize(), attendanceDTO.getCurrent());
                page.build(attendanceList);
                return ResultUtil.success(page);
            }else {
                return ResultUtil.success(attendanceList);
            }
        }
        return ResultUtil.error(400,"抱歉，没有找到签到信息");
    }

    /**根据签到id查询
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    public Msg getAttendanceById(@PathVariable(name = "id") Integer id){
        Attendance attendance = attendanceService.getById(id);
        if (attendance != null){
            return ResultUtil.success(attendance);
        }
        return ResultUtil.error(400,"没有该签到信息");
    }

    /**添加签到信息
     * @param attendance
     * @return
     */
    @RequestMapping({"/add"})
    public Msg add(@RequestBody Attendance attendance){
        if (attendance != null){
            System.out.println(attendance);
            int result = attendanceService.add(attendance);
            if (result>0){
                return ResultUtil.success();
            }else{
                return ResultUtil.error(400,"添加失败，请重试");
            }
        }
        return ResultUtil.error(400,"添加内容为空，添加失败");
    }

    /**修改签到信息
     * @param attendance
     * @return
     */
    @RequestMapping("/update")
    public Msg update(@RequestBody Attendance attendance){
        if (attendance != null){
            System.out.println(attendance);
            if (attendanceService.update(attendance) > 0){
                return ResultUtil.success();
            }
            return ResultUtil.error(400,"抱歉，修改失败，请重试");
        }
        return ResultUtil.error(400,"抱歉，修改内容为空，修改失败");
    }

    /**删除签到信息
     * @param attendanceDTO
     * @return
     */
    @RequestMapping("/delete")
    public Msg delAttendance(@RequestBody AttendanceDTO attendanceDTO){
        List<Integer> ids = attendanceDTO.getIds();
        System.out.println(ids);
        try {
            attendanceService.removeByIds(ids);
        }catch (Exception e){
            return ResultUtil.error(500,"该签到存在关联，无法删除");
        }
        return ResultUtil.success("");
    }
}
