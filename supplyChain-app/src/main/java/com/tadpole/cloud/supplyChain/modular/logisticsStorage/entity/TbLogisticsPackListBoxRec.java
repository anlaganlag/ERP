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
 * 出货清单和亚马逊货件关系映射表;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_pack_list_box_rec")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsPackListBoxRec implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 系统编号 */
    @ApiModelProperty(value = "系统编号")
    @TableId (value = "sys_id", type = IdType.AUTO)
    @TableField("sys_id")
    private BigDecimal sysId ;
 
    /** ShipmentID */
    @ApiModelProperty(value = "ShipmentID")
    @TableField("shipment_id")
    private String shipmentId ;
 
    /** 出货仓类型 */
    @ApiModelProperty(value = "出货仓类型")
    @TableField("com_warehouse_type")
    private String comWarehouseType ;
 
    /** 出货清单号 */
    @ApiModelProperty(value = "出货清单号")
    @TableField("pack_code")
    private String packCode ;
 
    /** 出货仓名称 */
    @ApiModelProperty(value = "出货仓名称")
    @TableField("ow_name")
    private String owName ;
 
    /** 收货仓名称 */
    @ApiModelProperty(value = "收货仓名称")
    @TableField("ship_to")
    private String shipTo ;
 
    /** 账号 */
    @ApiModelProperty(value = "账号")
    @TableField("shop_name_simple")
    private String shopNameSimple ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @TableField("country_code")
    private String countryCode ;
 
    /** 亚马逊接收状态 */
    @ApiModelProperty(value = "亚马逊接收状态")
    @TableField("ama_rec_state")
    private String amaRecState ;
 
    /** 最后更新日期 */
    @ApiModelProperty(value = "最后更新日期")
    @TableField("sys_update_date")
    private Date sysUpdateDate ;
 
    /** CreateTime */
    @ApiModelProperty(value = "CreateTime")
    @TableField("create_time")
    private Date createTime ;
 
    /** 货件实际状态 */
    @ApiModelProperty(value = "货件实际状态")
    @TableField("shipment_real_status")
    private String shipmentRealStatus ;
 
    /** 系统标识字段新版导入文件 */
    @ApiModelProperty(value = "系统标识字段新版导入文件")
    @TableField("pack_list_code")
    private String packListCode ;


}