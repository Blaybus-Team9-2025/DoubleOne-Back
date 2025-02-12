package org.doubleone.domain.login;


import lombok.Data;

@Data
public class LoginDto {
    private String name;
    private String email;
    private String password;
    private String centerName;
    private String address;
    private boolean hasTruck;
    private String centerGrade;
    private String centerPeriod;
}