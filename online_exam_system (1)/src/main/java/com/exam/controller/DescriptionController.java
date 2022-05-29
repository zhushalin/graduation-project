package com.exam.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import com.exam.entity.Administrator;
import com.exam.entity.Description;
import com.exam.service.AdministratorService;
import com.exam.service.DescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 公告
 * @author
 * @since
 */
@RestController
@RequestMapping("/description")
public class DescriptionController {

    @Autowired
    AdministratorService administratorService;
    @Autowired
    DescriptionService descriptionService;
    /**
     * 分页获得所有公告
     * @return
     */
    @RequestMapping("/page")
    public Msg getDescription(@RequestParam int page, @RequestParam int limit){
        Map<String, Object> params = new HashMap<>();
        Page<Description> list = descriptionService.getAllByPage(page, limit, params);
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
    public Msg add(@RequestBody Description description, HttpSession session){
        Administrator administrator = (Administrator) session.getAttribute("admin");
        description.setId(0);
//        description.setAdminId(administrator.getAdminId());
        description.setAdminId(1001);
        int result = descriptionService.add(description);
        if (result>0){
            return ResultUtil.success(description);
        }else{
            return ResultUtil.error(400,"添加失败，请重试");
        }
    }

//    /**
//     * 根据公告标题模糊查询
//     * @return
//     */
//    @RequestMapping("/getByTitle")
//    public Msg getDescriptionByTitle(@RequestParam int page, @RequestParam int limit, @RequestParam String title){
//        Map<String, Object> params = new HashMap<>();
//        params.put("title", title);
//        Page<Description> list = descriptionService.search(page, limit, params);
//        return ResultUtil.success(list);
//    }

    /**
     *  修改公告信息
     * @param
     * @return
     */
    @RequestMapping("/update")
    public Msg updateDescription(@RequestBody Description description){
        if (description!=null){
            int result = descriptionService.update(description);
            if (result>0){
                return ResultUtil.success(description);
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
    public Msg delDescription(@RequestBody int[] subIds){
        System.out.println("=========删除============");
        Collection<Integer> idList = new ArrayList<>();
        for (int item : subIds){
            idList.add(item);
        }
        System.out.println("=========删除============");

        try {
            descriptionService.removeByIds(idList);
        }catch (Exception e){
            return ResultUtil.error(500,"该公告存在关联，无法删除");
        }
        return ResultUtil.success("");
    }
}

