package com.tadpole.cloud.externalSystem.api.lingxing.model;

import lombok.Data;

@Data
public class Shop {

    private long mid;
    private int sid;
    private String name;
    private String seller_id;
    private String account_name;
    private long seller_account_id;
    private String region;
    private String country;

}
