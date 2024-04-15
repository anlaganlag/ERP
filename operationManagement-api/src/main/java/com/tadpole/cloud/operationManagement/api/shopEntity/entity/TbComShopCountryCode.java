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
 * @desc : 资源-店铺站点编码-资源-店铺站点编码
 */
@TableName("Tb_Com_Shop_Country_Code")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComShopCountryCode implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 站点 */
    @TableId(value = "country_Code", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "站点")
    private String countryCode ;
    
    /** 编码 */
    @TableField("code")
    @ApiModelProperty(value = "编码")
    private String code ;
    

}