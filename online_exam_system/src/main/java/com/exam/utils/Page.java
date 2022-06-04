package com.exam.utils;

import com.exam.VO.TestTypeVO;
import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    public static final int DEFAULT_PAGE_SIZE = 10;

    /*每页显示个数*/
    private int size;

    /*当前页数*/
    private int current;

    /*总页数*/
    private int total;

    /*总数据个数*/
    private int Datatotal;

    private List<T> content;

    private List<T> dialogData;

    private TestTypeVO testTypeVO;

    public Page(){
        this.size = DEFAULT_PAGE_SIZE;
        this.current = 1;
    }

    public Page(int size, int current){
        this.size = size <= 0 ? 1:size;
        this.current = current <=0? 1:current;
    }

    public void build(List<T> content){
        this.Datatotal = content.size();
        if(this.Datatotal == 0){
            this.total = 0;
            this.current = 1;
            this.setContent(content);
            return;
        }
        this.total = this.Datatotal%this.size == 0 ? this.Datatotal/this.size : this.Datatotal/this.size+1;
        this.current = this.current > this.total ? this.total : this.current;
        this.setContent(content.subList(this.size*(this.current-1),this.current==this.total?content.size():this.size*this.current));
    }

    public void build(List<T> content,List<T> dialogData){
        this.Datatotal = content.size();
        if(this.Datatotal == 0){
            this.total = 0;
            this.current = 1;
            this.setContent(content);
            return;
        }
        this.total = this.Datatotal%this.size == 0 ? this.Datatotal/this.size : this.Datatotal/this.size+1;
        this.current = this.current > this.total ? this.total : this.current;
        this.setContent(content.subList(this.size*(this.current-1),this.current==this.total?content.size():this.size*this.current));
        this.setDialogData(dialogData);
    }
    public void build(List<T> content, TestTypeVO testTypeVO){
        this.Datatotal = content.size();
        if(this.Datatotal == 0){
            this.total = 0;
            this.current = 1;
            this.setContent(content);
            return;
        }
        this.total = this.Datatotal%this.size == 0 ? this.Datatotal/this.size : this.Datatotal/this.size+1;
        this.current = this.current > this.total ? this.total : this.current;
        this.setContent(content.subList(this.size*(this.current-1),this.current==this.total?content.size():this.size*this.current));
        this.setTestTypeVO(testTypeVO);
    }
}
