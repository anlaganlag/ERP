package com.tadpole.cloud.operationManagement.api.shopEntity.model.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.*;
import java.lang.*;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

 /**
 * 资源-店铺站点编码;资源-店铺站点编码
 * @author : LSY
 * @date : 2023-7-28
 */
@ApiModel(value = "资源-店铺站点编码",description = "资源-店铺站点编码")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComShopCountryCodeResult  implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @ExcelProperty(value ="站点")
    private String countryCode ;
    
    /** 编码 */
    @ApiModelProperty(value = "编码")
    @ExcelProperty(value ="编码")
    private String code ;
    

}