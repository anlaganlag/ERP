package com.tadpole.cloud.externalSystem.modular.k3.mode.result;

import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;

@Data
public class K3Result implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message; //未登录或登录失效
    private String messageCode;//CheckPasswordPolicy
    private Integer loginResultType;//1成功，其他不成功
    private Object context;
    private String KDSVCSessionId;//"8233eacf-9222-4c5e-9445-3d0aba60e58a"
    private String FormId;
    private String RedirectFormParam;
    private String FormInputObject;
    private String ErrorStackTrace;
    private Integer Lcid;//0
    private String AccessToken;
    private Object CustomParam;//object
    private String KdAccessResult;
    private String IsSuccessByAPI;

}
