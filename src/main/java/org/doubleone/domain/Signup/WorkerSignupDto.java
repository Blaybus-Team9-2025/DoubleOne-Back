package org.doubleone.domain.Signup;

import lombok.Data;
import org.doubleone.domain.worker.entity.Gender;

@Data
public class WorkerSignupDto {
    private String email;
    private String password;
    private String name;
    private Gender gender;
    private String birth;
    private String phoneNum;
    private String address;
    private String license;
}
