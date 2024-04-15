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
 * 亚马逊货件-明细-多少箱每箱装多少;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "亚马逊货件-明细-多少箱每箱装多少",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbLogisticsPackListDetToBoxParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** 编号 */
    @ApiModelProperty(value = "编号")
    private BigDecimal id ;
 
    /** 明细编号 */
    @ApiModelProperty(value = "明细编号")
    private Integer shipDetId ;
 
    /** 箱号 */
    @ApiModelProperty(value = "箱号")
    private String packDetBoxNum ;
 
    /** 箱号上传名称 */
    @ApiModelProperty(value = "箱号上传名称")
    private String packDetBoxNumUpload ;
 
    /** 数量 */
    @ApiModelProperty(value = "数量")
    private Integer quantity ;
 
    /** ShipmentID */
    @ApiModelProperty(value = "ShipmentID")
    private String shipmentId ;
 
    /** 系统标识字段新版导入文件 */
    @ApiModelProperty(value = "系统标识字段新版导入文件")
    private String packListCode ;


}