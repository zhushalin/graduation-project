package com.exam.DTO;

import lombok.Data;

@Data
public class LoginDTO {
    private String userName;

    private String password;

    private String currentPass;

    private String checkPass;

    private String role;
 /*   private String code;

    private String Key;*/
}
