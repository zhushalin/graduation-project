package com.exam.utils;

import com.exam.DTO.MenuDTO;

import java.util.ArrayList;

public class MenuUtils {
    private static MenuDTO ExamManage = new MenuDTO("ExamManage","考试管理","el-icon-s-promotion","","",null);
    private static MenuDTO Exam = new MenuDTO("Exam","进入考试","el-icon-time","/onlineExam/Exam","onlineExam/Exam",null);

    private static MenuDTO ScoreQuery = new MenuDTO("ScoreQuery","成绩查询","el-icon-trophy","/score/query","/score/query",null);
    private static MenuDTO Score = new MenuDTO("Score","我的成绩","el-icon-trophy","/onlineExam/Score","onlineExam/Score",null);

    private static MenuDTO NoticeManage = new MenuDTO("NoticeManage","平台公告管理","el-icon-trophy","","",null);
    private static MenuDTO NoticeAdmin = new MenuDTO("NoticeAdmin","平台公告","el-icon-trophy","/notice/manage","notice/manage",null);

    private static MenuDTO Notice = new MenuDTO("Notice","平台公告","el-icon-trophy","","",null);
    private static MenuDTO NoticeQuery = new MenuDTO("Notice","查看公告","el-icon-trophy","/notice/query","notice/query",null);

    private static MenuDTO DescriptionManage = new MenuDTO("DescriptionManage","平台简介管理","el-icon-trophy","","",null);
    private static MenuDTO Description = new MenuDTO("DescriptionManage","平台简介","el-icon-trophy","/descripTion/manage","descripTion/desManage",null);

    private static MenuDTO Supervision = new MenuDTO("Supervision","监考系统","el-icon-trophy","","",null);
    private static MenuDTO Supervision1 = new MenuDTO("Supervision","监考系统","el-icon-trophy","/onlineExam/supervision","onlineExam/supervision",null);

    private static MenuDTO ScoreRelease = new MenuDTO("ScoreRelease","成绩公布","el-icon-trophy","","",null);
    private static MenuDTO ScoreRelease1 = new MenuDTO("ScoreRelease","上传成绩","el-icon-trophy","/score/release","score/release",null);

    private static MenuDTO UserInfoManage = new MenuDTO("UserInfoManage","个人信息中心","el-icon-trophy","","",null);
    private static MenuDTO UpdateUserInfo = new MenuDTO("UpdateUserInfo","修改个人信息","el-icon-trophy","/userManage/updateUserInfo","userManage/updateUserInfo",null);
    private static MenuDTO UpdatePassword = new MenuDTO("UpdatePassword","修改密码","el-icon-trophy","/userManage/updatePassword","userManage/updatePassword",null);

    private static MenuDTO StuInfoManage = new MenuDTO("StuInfoManage","个人中心","el-icon-trophy","","",null);
    private static MenuDTO UpdateStuInfo = new MenuDTO("UpdateStuInfo","修改个人信息","el-icon-trophy","/userManage/updateStuInfo","userManage/updateStuInfo",null);
    private static MenuDTO UpdateStuPassword = new MenuDTO("UpdateStuPassword","修改密码","el-icon-trophy","/userManage/updateStuPassword","userManage/updateStuPassword",null);

    private static MenuDTO TeacherInfoManage = new MenuDTO("TeacherInfoManage","个人中心","el-icon-trophy","","",null);
    private static MenuDTO UpdateTeacherInfo = new MenuDTO("UpdateTeacherInfo","修改个人信息","el-icon-trophy","/userManage/updateTeacherInfo","userManage/updateTeacherInfo",null);
    private static MenuDTO UpdateTeacherPassword = new MenuDTO("UpdateTeacherPassword","修改密码","el-icon-trophy","/userManage/updateTeacherPassword","userManage/updateTeacherPassword",null);

    private static MenuDTO ExamAdmin = new MenuDTO("ExamAdmin","考试管理","el-icon-help","","",null);
    private static MenuDTO ExamInfo = new MenuDTO("ExamInfo","考试信息","el-icon-time","/examAdmin/ExamInfo","examAdmin/ExamInfo",null);
    private static MenuDTO EATestBank = new MenuDTO("EATestBank","题库管理","el-icon-document-copy","/examAdmin/TestBank","examAdmin/TestBank",null);
    private static MenuDTO EATpList = new MenuDTO("EATpList","试卷管理","el-icon-rank","/examAdmin/TpList","examAdmin/TpList",null);
    private static MenuDTO EAScore = new MenuDTO("EAScore","成绩统计","el-icon-orange","/examAdmin/ScoreAdmin","examAdmin/ScoreAdmin",null);

    private static MenuDTO UserManager = new MenuDTO("UserManager","用户管理","el-icon-user","","",null);
    private static MenuDTO Teacher = new MenuDTO("Teacher","教师管理","el-icon-s-promotion","/userManager/Teacher","userManager/Teacher",null);
    private static MenuDTO Student = new MenuDTO("Student","学生管理","el-icon-s-promotion","/userManager/Student","userManager/Student",null);

    private static MenuDTO SysMange = new MenuDTO("SysMange","系统管理","el-icon-setting","","",null);
    private static MenuDTO SysRole = new MenuDTO("SysRole","角色管理","el-icon-s-custom","/sys/Roles","sys/Role",null);
    private static MenuDTO SysSubject = new MenuDTO("SysSubject","课程管理","el-icon-magic-stick","/sys/Subject","sys/Subject",null);
    //共享目录
    public static void CommonMenu(){
        ArrayList<MenuDTO> list1 = new ArrayList<>();
        list1.add(Exam);
        ExamManage.setChildren(list1);

        ArrayList<MenuDTO> list2 = new ArrayList<>();
        list2.add(EATestBank);
        list2.add(EATpList);
        list2.add(ExamInfo);
        ExamAdmin.setChildren(list2);

        ArrayList<MenuDTO> list3 = new ArrayList<>();
        list3.add(SysRole);
        list3.add(SysSubject);
        SysMange.setChildren(list3);

        ArrayList<MenuDTO> list4 = new ArrayList<>();
        list4.add(Teacher);
        list4.add(Student);
        UserManager.setChildren(list4);

        ArrayList<MenuDTO> scoreQueryList = new ArrayList<>();
        scoreQueryList.add(Score);
        ScoreQuery.setChildren(scoreQueryList);

        ArrayList<MenuDTO> list6 = new ArrayList<>();
        list6.add(UpdateUserInfo);
        list6.add(UpdatePassword);
        UserInfoManage.setChildren(list6);

        ArrayList<MenuDTO> list12 = new ArrayList<>();
        list12.add(UpdateStuInfo);
        list12.add(UpdateStuPassword);
        StuInfoManage.setChildren(list12);

        ArrayList<MenuDTO> list13 = new ArrayList<>();
        list13.add(UpdateTeacherInfo);
        list13.add(UpdateTeacherPassword);
        TeacherInfoManage.setChildren(list13);

        ArrayList<MenuDTO> list7 = new ArrayList<>();
        list7.add(NoticeAdmin);
        NoticeManage.setChildren(list7);

        ArrayList<MenuDTO> list8 = new ArrayList<>();
        list8.add(NoticeQuery);
        Notice.setChildren(list8);

        ArrayList<MenuDTO> list9 = new ArrayList<>();
        list9.add(Description);
        DescriptionManage.setChildren(list9);

        ArrayList<MenuDTO> list10 = new ArrayList<>();
        list10.add(Supervision1);
        Supervision.setChildren(list10);

        ArrayList<MenuDTO> list11 = new ArrayList<>();
        list11.add(ScoreRelease1);
        ScoreRelease.setChildren(list11);
    }
    //管理员目录
    public static ArrayList<MenuDTO> AdminMenu(){
        ArrayList<MenuDTO> list = new ArrayList<>();
        list.add(ExamAdmin);
        list.add(UserManager);
        list.add(SysMange);
        list.add(NoticeManage);
        list.add(DescriptionManage);
        list.add(UserInfoManage);
        return list;
    }
    //教师目录
    public static ArrayList<MenuDTO> TeacherMenu(){
        ArrayList<MenuDTO> list = new ArrayList<>();
        ExamAdmin.getChildren().get(0).setTitle("我的题库");
        ExamAdmin.getChildren().get(1).setTitle("我的试卷");
        list.add(ExamAdmin);
//        list.add(EAScore);
        list.add(Notice);
        list.add(ScoreRelease);
        list.add(Supervision);
        list.add(TeacherInfoManage);
        return list;
    }
    //学生目录
    public static ArrayList<MenuDTO> StudentMenu(){
        ArrayList<MenuDTO> list = new ArrayList<>();
        ExamManage.setTitle("在线考试");
        list.add(ExamManage);
        list.add(ScoreQuery);
        list.add(StuInfoManage);
        list.add(Notice);
        return list;
    }
}
