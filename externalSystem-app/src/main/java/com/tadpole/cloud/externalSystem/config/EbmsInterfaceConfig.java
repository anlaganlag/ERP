package com.tadpole.cloud.externalSystem.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ebms")
@Data
public class EbmsInterfaceConfig {


    private String server;
    private String getDeptList;
    private String category;
    private String operationCategory;
    private String saleBrand;
    private String userInfo;
    private String materiel;
    private String comOverseasWarehouse;
    private String categoryTree;

    private String comMatBrandClass;
    private String comMatModelClass;
    private String comMatColorClass;
    private String comMatSizeClass;
    private String comMatPackQtyClass;
    private String comMatCompatibleModelClass;
    private String comMatFestLabelClass;
    private String comMaterielTree;

    private String syncMateriel;
    private String syncMaterielStatus;
    private String syncMaterielOtherStatus;

    private String printSampleLabel;
    private String loginUrl;
}
