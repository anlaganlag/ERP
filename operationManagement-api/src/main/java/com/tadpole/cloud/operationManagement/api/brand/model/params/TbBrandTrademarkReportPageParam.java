package com.tadpole.cloud.operationManagement.api.brand.model.params;

import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
* <p>
* 品牌商标表
* </p>
* @author S20190161
* @since 2023-10-19
*/
@Data
public class TbBrandTrademarkReportPageParam extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("查询商标名称")
    private List<String> tradeNames;
    @ApiModelProperty("商标状态")
    private Integer trademarkStatus;
    @ApiModelProperty("注册号")
    private String registerNumber;
    @ApiModelProperty("资产编码")
    private String assetsNo;

    @ApiModelProperty("获证开始日期:yyyy-MM-dd")
    private String startCertificateDate;
    @ApiModelProperty("获证截止日期:yyyy-MM-dd")
    private String endCertificateDate;

}
