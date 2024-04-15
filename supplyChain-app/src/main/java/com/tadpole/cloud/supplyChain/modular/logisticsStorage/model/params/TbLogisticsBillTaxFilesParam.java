package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import lombok.*;
import java.lang.*;
import java.math.BigDecimal;

 /**
 * 物流账单税务文件;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流账单税务文件",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbLogisticsBillTaxFilesParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** 系统编号 */
    @ApiModelProperty(value = "系统编号")
    private BigDecimal id ;
 
    /** 物流对账单号 */
    @ApiModelProperty(value = "物流对账单号")
    private String billNum ;
 
    /** 物流跟踪单号（头程物流单号） */
    @ApiModelProperty(value = "物流跟踪单号（头程物流单号）")
    private String lhrOddNum ;
 
    /** 税费单号 */
    @ApiModelProperty(value = "税费单号")
    private String taxNum ;
 
    /** 税费文件类型 */
    @ApiModelProperty(value = "税费文件类型")
    private String taxType ;
 
    /** 源文件名称 */
    @ApiModelProperty(value = "源文件名称")
    private String taxOriginFileName ;
 
    /** 税费文件名称 */
    @ApiModelProperty(value = "税费文件名称")
    private String taxFileName ;
 
    /** 添加日期 */
    @ApiModelProperty(value = "添加日期")
    private Date addDate ;
 
    /** 操作人 */
    @ApiModelProperty(value = "操作人")
    private String operator ;
 
    /** 工号 */
    @ApiModelProperty(value = "工号")
    private String operatorCode ;
 
    /** 存储路径 */
    @ApiModelProperty(value = "存储路径")
    private String filePathFull ;


}