package com.exam.controller;

import com.exam.DTO.FavoriteDTO;
import com.exam.VO.FavoriteVO;
import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import com.exam.entity.Favorite;
import com.exam.service.FavoriteService;
import com.exam.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  收藏信息Controller
 * @author
 * @since
 */
@RestController
@RequestMapping("/favorite")
public class FavoriteController {

    @Autowired
    FavoriteService favoriteService;

    /**
     * 查询所有收藏
     * @param favoriteDTO
     * @return
     */
    @RequestMapping("/all")
    public Msg getAll(@RequestBody FavoriteDTO favoriteDTO){
        List<FavoriteVO> favoriteList = favoriteService.getAll(favoriteDTO);
        if (favoriteList.size()>0){
            //判断是否需要分页
            if (favoriteDTO.isPage()){//进行分页
                Page page = new Page(favoriteDTO.getSize(), favoriteDTO.getCurrent());
                page.build(favoriteList);
                return ResultUtil.success(page);
            }else {
                return ResultUtil.success(favoriteList);
            }
        }
        return ResultUtil.error(400,"抱歉，没有找到收藏信息");
    }

    /**根据收藏id查询
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    public Msg getFavoriteById(@PathVariable(name = "id") Integer id){
        Favorite favorite = favoriteService.getById(id);
        if (favorite != null){
            return ResultUtil.success(favorite);
        }
        return ResultUtil.error(400,"没有该收藏信息");
    }

    /**添加收藏信息
     * @param favorite
     * @return
     */
    @RequestMapping({"/add"})
    public Msg add(@RequestBody Favorite favorite){
        if (favorite != null){
            System.out.println(favorite);
            int result = favoriteService.add(favorite);
            if (result>0){
                return ResultUtil.success();
            }else{
                return ResultUtil.error(400,"添加失败，请重试");
            }
        }
        return ResultUtil.error(400,"添加内容为空，添加失败");
    }

    /**修改收藏信息
     * @param favorite
     * @return
     */
    @RequestMapping("/update")
    public Msg update(@RequestBody Favorite favorite){
        if (favorite != null){
            System.out.println(favorite);
            if (favoriteService.update(favorite) > 0){
                return ResultUtil.success();
            }
            return ResultUtil.error(400,"抱歉，修改失败，请重试");
        }
        return ResultUtil.error(400,"抱歉，修改内容为空，修改失败");
    }

    /**删除收藏信息
     * @param favoriteDTO
     * @return
     */
    @RequestMapping("/delete")
    public Msg delete(@RequestBody FavoriteDTO favoriteDTO){
        List<Integer> ids = favoriteDTO.getIds();
        System.out.println(ids);
        try {
            favoriteService.removeByIds(ids);
        }catch (Exception e){
            return ResultUtil.error(500,"该收藏存在关联，无法删除");
        }
        return ResultUtil.success("");
    }
}
