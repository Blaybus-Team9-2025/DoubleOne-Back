package org.doubleone.domain.Signup;


import lombok.Data;

@Data
public class ManagerSignupDto {
    private String name;
    private String email;
    private String password;
    private String phoneNum;
    private String centerName;
    private String address;
    private boolean hasTruck;
    private String centerGrade;
    private String centerPeriod;
}