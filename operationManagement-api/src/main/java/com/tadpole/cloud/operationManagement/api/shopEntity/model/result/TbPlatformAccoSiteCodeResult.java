package com.tadpole.cloud.operationManagement.api.shopEntity.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

 /**
 * 资源-平台-账号-站点-对应编码配置;资源-税号管理
 * @author : LSY
 * @date : 2023-8-3
 */
@ApiModel(value = "资源-平台-账号-站点-对应编码配置",description = "资源-税号管理")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbPlatformAccoSiteCodeResult  implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 名称 */
    @ApiModelProperty(value = "名称")
    @ExcelProperty(value ="名称")
    private String name ;
    
    /** 编码值 */
    @ApiModelProperty(value = "编码值")
    @ExcelProperty(value ="编码值")
    private String code ;
    
    /** 类型;类型:平台、账号、站点 */
    @ApiModelProperty(value = "类型")
    @ExcelProperty(value ="类型")
    private String type ;
    

}