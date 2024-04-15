package com.tadpole.cloud.operationManagement.api.brand.model.params;

import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.*;

/**
* <p>
* 商标证书管理表
* </p>
* @author S20190161
* @since 2023-10-24
*/
@Data
public class TbTrademarkCertificatePageParam extends BaseRequest {
   @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("商标名称")
    private List<String> tradeNames;

    @ApiModelProperty("商标类型：0.文字商标;1.图形商标")
    private BigDecimal trademarkType;

    @ApiModelProperty("注册号")
    private String registerNumber;

    @ApiModelProperty("注册国家")
    private String registerCountry;


}
