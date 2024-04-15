package com.tadpole.cloud.operationManagement.api.shopEntity.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComTaxNum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 资源-税号;资源-税号
 * @author : LSY
 * @date : 2023-7-28
 */
@ApiModel(value = "资源-税号",description = "资源-税号")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComTaxNumDTOResult implements Serializable{
 private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "店铺主体")
    private String shopMainBody ;

    @ApiModelProperty(value = "后台店铺名称")
    private String shopNamePlat;

   @ApiModelProperty(value = "税号信息")
   private TbComTaxNum comTaxNum;

   @ApiModelProperty(value = "店铺查账主列表")
    private List<TbComTaxAuditResult> comTaxAudit;

    @ApiModelProperty(value = "店铺查账明细列表")
    private  List<TbComTaxAuditDetResult> tbComTaxAuditDets;




 }