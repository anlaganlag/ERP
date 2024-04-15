package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @author: ty
 * @description: 海外仓同步ERP异常管理结果类
 * @date: 2022/11/15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class OverseasWarehouseSyncErrorResult implements Serializable{

    private static final long serialVersionUID = 1L;

    /** 单号 */
    @ExcelProperty(value ="单号")
    @ApiModelProperty("单号")
    private String mcOrder;

    /** 操作类型 换标，盘点，乐天海外仓出库，国内仓发海外仓，亚马逊仓发海外仓，海外仓发亚马逊仓 */
    @ExcelProperty(value ="业务操作")
    @ApiModelProperty("操作类型 换标，盘点，乐天海外仓出库，国内仓发海外仓，亚马逊仓发海外仓，海外仓发亚马逊仓")
    private String operateType;

    /** 同步状态 */
    @ExcelProperty(value ="同步状态")
    @ApiModelProperty("同步状态")
    private String syncErpStatusName;
}
