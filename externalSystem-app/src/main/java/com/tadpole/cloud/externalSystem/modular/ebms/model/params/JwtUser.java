package com.tadpole.cloud.externalSystem.modular.ebms.model.params;

import lombok.Data;

import java.util.Date;

@Data
public class JwtUser {


    private String name;

    private String account;

    private String email;

    private String phone;

    private String sign;

}
