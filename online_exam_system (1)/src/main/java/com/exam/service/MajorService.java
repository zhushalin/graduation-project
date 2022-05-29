package com.exam.service;

import com.exam.entity.Major;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author
 * @since
 */
public interface MajorService extends IService<Major> {
    List<Major> getMajorlist();
}
