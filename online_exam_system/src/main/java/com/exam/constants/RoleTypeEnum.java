package com.exam.constants;

public enum RoleTypeEnum {
    ADMIN("admin","管理员"),
    TEACHER("teacher","教师"),
    STUDENT("student","学生")
    ;
    private String name;
    private String description;

    RoleTypeEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
