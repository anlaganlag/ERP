package com.tadpole.cloud.operationManagement.api.shopEntity.model.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.*;
import java.lang.*;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

 /**
 * 资源-店铺编码;资源-店铺编码
 * @author : LSY
 * @date : 2023-7-28
 */
@ApiModel(value = "资源-店铺编码",description = "资源-店铺编码")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComShopNameCodeResult  implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 账号简称 */
    @ApiModelProperty(value = "账号简称")
    @ExcelProperty(value ="账号简称")
    private String shopNameSimple ;
    
    /** 编码 */
    @ApiModelProperty(value = "编码")
    @ExcelProperty(value ="编码")
    private String code ;
    

}