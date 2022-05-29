package com.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.common.Msg;
import com.exam.entity.LearningFile;

/**
 * <p>
 *  学习资料服务类
 * </p>
 *
 * @author
 * @since
 */
public interface FileService extends IService<LearningFile> {
    Msg getAll();

    /**
     * 根据课程id查询
     * @param subjectId
     * @return
     */
    Msg getFilesBySubjectId(int subjectId);

    /**
     *添加
     * @param file
     * @return
     */
    Msg add(LearningFile file);

    /**
     * 删除
     * @param id
     * @return
     */
    Msg delete(int id);

    /**
     * 根据课程id删除
     * @param subjectId
     * @return
     */
    Msg deleteBySubjectId(int subjectId);

    /**
     * 更新
     * @param file
     * @return
     */
    Msg update(LearningFile file);
}
