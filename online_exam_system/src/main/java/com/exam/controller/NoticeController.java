package com.exam.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.VO.NoticeVO;
import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import com.exam.entity.Administrator;
import com.exam.entity.Major;
import com.exam.entity.Notice;
import com.exam.mapper.AdministratorMapper;
import com.exam.mapper.NoticeMapper;
import com.exam.mapper.NoticeMapper;
import com.exam.service.AdministratorService;
import com.exam.service.MajorService;
import com.exam.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 公告
 * @author
 * @since
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    AdministratorService administratorService;
    @Autowired
    NoticeService noticeService;
    /**
     * 分页获得所有公告
     * @return
     */
    @RequestMapping("/page")
    public Msg getNotice(@RequestParam int page, @RequestParam int limit){
        Map<String, Object> params = new HashMap<>();
        Page<Notice> list = noticeService.getAllByPage(page, limit, params);
        if (list.getTotal()<1){
            return ResultUtil.error(400,"抱歉，没有找到公告信息");
        }
        return ResultUtil.success(list);
    }
    /**
     * 添加公告
     * @return
     */
    @RequestMapping("/add")
    public Msg add(@RequestBody Notice notice){
       // notice.setAdminId(adminId);
        notice.setCreateTime(new Date());
        int result = noticeService.add(notice);
        if (result>0){
            return ResultUtil.success(notice);
        }else{
            return ResultUtil.error(400,"添加失败，请重试");
        }
    }

    /**
     * 根据公告标题模糊查询
     * @return
     */
    @RequestMapping("/getByTitle")
    public Msg getNoticeByTitle(@RequestParam int page, @RequestParam int limit, @RequestParam String title){
        Map<String, Object> params = new HashMap<>();
        params.put("title", title);
        Page<Notice> list = noticeService.search(page, limit, params);
        return ResultUtil.success(list);
    }

    /**
     *  修改公告信息
     * @param
     * @return
     */
    @RequestMapping("/update")
    public Msg updateNotice(@RequestBody Notice notice){
        if (notice!=null){
            int result = noticeService.update(notice);
            if (result>0){
                return ResultUtil.success(notice);
            }else{
                return ResultUtil.error(400,"抱歉，修改失败，请重试");
            }
        }
        return ResultUtil.error(400,"抱歉，修改内容为空，修改失败");
    }

    /**
     * 删除公告
     * @param
     * @return
     */
    @RequestMapping("/delete")
    public Msg delNotice(@RequestBody int[] ids){
        System.out.println("=========删除============");
        Collection<Integer> idList = new ArrayList<>();
        for (int item : ids){
            idList.add(item);
        }
        System.out.println("=========删除============");

        try {
            noticeService.removeByIds(idList);
        }catch (Exception e){
            return ResultUtil.error(500,"该公告存在关联，无法删除");
        }
        return ResultUtil.success("");
    }
}

