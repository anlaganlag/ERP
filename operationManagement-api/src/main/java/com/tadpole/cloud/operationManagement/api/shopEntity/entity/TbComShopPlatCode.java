package com.tadpole.cloud.operationManagement.api.shopEntity.entity;

import java.io.Serializable;
import java.lang.*;
import lombok.*;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

 /**
 * @author : LSY
 * @date : 2023-7-28
 * @desc : 资源-店铺平台编码-资源-店铺平台编码
 */
@TableName("Tb_Com_Shop_Plat_Code")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComShopPlatCode implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 电商平台 */
    @TableId(value = "ele_Platform_Name", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "电商平台")
    private String elePlatformName ;
    
    /** 编码 */
    @TableField("code")
    @ApiModelProperty(value = "编码")
    private String code ;
    

}