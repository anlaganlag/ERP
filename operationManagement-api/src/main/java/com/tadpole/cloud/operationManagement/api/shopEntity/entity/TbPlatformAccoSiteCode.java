package com.tadpole.cloud.operationManagement.api.shopEntity.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

 /**
 * @author : LSY
 * @date : 2023-8-3
 * @desc : 资源-平台-账号-站点-对应编码配置-资源-税号管理
 */
@TableName("Tb_Platform_Acco_Site_Code")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbPlatformAccoSiteCode implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 名称 */
    @TableField("NAME")
    @ApiModelProperty(value = "名称")
    private String name ;
    
    /** 编码值 */
    @TableField("CODE")
    @ApiModelProperty(value = "编码值")
    private String code ;
    
    /** 类型;类型:平台、账号、站点 */
    @TableField("TYPE")
    @ApiModelProperty(value = "类型")
    private String type ;
    

}