package com.tadpole.cloud.operationManagement.api.shopEntity.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * 资源-店铺;资源-店铺
 * @author : LSY
 * @date : 2023-7-28
 */
@ApiModel(value = "资源-店铺",description = "资源-店铺")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComCnNameResult implements Serializable{
    private static final long serialVersionUID = 1L;


    /** 店铺公司名称（中文） */
    @ApiModelProperty(value = "店铺公司名称（中文）")
    @ExcelProperty(value ="店铺公司名称（中文）")
    private String shopComNameCn ;

    /** 店铺公司地址（中文） */
    @ApiModelProperty(value = "店铺公司地址（中文）")
    private String shopComAddrCn ;

    /** 法人（中文） */
    @ApiModelProperty(value = "法人（中文）")
    private String shopLegPersonCn ;


}