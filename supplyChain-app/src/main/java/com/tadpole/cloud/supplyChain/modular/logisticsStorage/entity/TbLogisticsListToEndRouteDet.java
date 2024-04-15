package com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity;

import java.io.Serializable;
import java.lang.*;
import lombok.*;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

 /**
 * 物流单尾程信息-明细;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_list_to_end_route_det")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsListToEndRouteDet implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    @TableId (value = "id", type = IdType.AUTO)
    @TableField("id")
    private BigDecimal id ;
 
    /** 发货批次号 */
    @ApiModelProperty(value = "发货批次号")
    @TableField("lhr_code")
    private String lhrCode ;
 
    /** 头程物流单号 */
    @ApiModelProperty(value = "头程物流单号")
    @TableField("lhr_odd_numb")
    private String lhrOddNumb ;
 
    /** 尾程物流单号 */
    @ApiModelProperty(value = "尾程物流单号")
    @TableField("ler_odd_numb")
    private String lerOddNumb ;
 
    /** 添加日期 */
    @ApiModelProperty(value = "添加日期")
    @TableField("sys_add_date")
    private Date sysAddDate ;
 
    /** 出货清单号 */
    @ApiModelProperty(value = "出货清单号")
    @TableField("pack_code")
    private String packCode ;
 
    /** 箱号 */
    @ApiModelProperty(value = "箱号")
    @TableField("pack_det_box_num")
    private Integer packDetBoxNum ;
 
    /** 箱条码 */
    @ApiModelProperty(value = "箱条码")
    @TableField("pack_det_box_code")
    private String packDetBoxCode ;
 
    /** FBA条码 */
    @ApiModelProperty(value = "FBA条码")
    @TableField("shipment_id")
    private String shipmentId ;
 
    /** 物流跟踪状态 */
    @ApiModelProperty(value = "物流跟踪状态")
    @TableField("lerd_state")
    private String lerdState ;


}