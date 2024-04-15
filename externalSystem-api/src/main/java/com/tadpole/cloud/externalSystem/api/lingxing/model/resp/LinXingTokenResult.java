package com.tadpole.cloud.externalSystem.api.lingxing.model.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinXingTokenResult {
    private String msg;
    private String sellerAuthErpUrl;
    private Integer code;
    private String codeVersion;
    private Boolean firstLogin;
    private Integer update_code;
    private String envKey;
    private String token;
    private String uid;
    private Integer needReset;
    private String companyId;
    private String zid;
    private String req_time_sequence;
    private Integer tokenExpireSeconds;
    private Integer customerId;
    private Boolean isPwdNotice;

/*    {
        "msg": "操作成功",
            "sellerAuthErpUrl": "https://jcbsc.lingxing.com/api/seller/oauthRedirect",
            "code": 1,
            "codeVersion": "prod",
            "firstLogin": false,
            "update_code": 0,
            "envKey": "jcbsc",
            "token": "0da5vZFtuCLjpib4VyAQoIQUzsqbV+8teTqFGv6JWGMVB33PXvdxGhu7AfxL4vwTZkG6y0ImUSKJKbP2U1DrnRYi4tDIRUJ1qtQJpJXwttyfDH9OZ44CJuE2PgLtd4Lnza40/CllI0YCFHT4lNMvoeU",
            "uid": "10327839",
            "needReset": 0,
            "companyId": "901167908147429376",
            "zid": "1",
            "req_time_sequence": "",
            "tokenExpireSeconds": 432000,
            "customerId": 901243,
            "isPwdNotice": false
    }*/
}

