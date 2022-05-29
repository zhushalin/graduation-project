package com.exam.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
//公告VO
public class NoticeVO {
    private Integer id;
    private String title;
    private String content;
    //  管理员id
    private Integer adminId;
    private String adminName;
    //  发布时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
}
