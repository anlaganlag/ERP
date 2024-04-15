package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.k3;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serializable;

/**
 * ERP---EBMS数据 SkuDet Model 箱明细 返回结果
 */
@ApiModel(value = "ERP---EBMS数据 SkuDet Model 箱明细 返回结果", description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class PackingRecordSyncSkuDetResult implements Serializable {
    private static final long serialVersionUID = 1L;


    /// SKU
       @JSONField(name = "SKU")
    public String sku ;

    /// 数量
       @JSONField(name = "Quantity")
    public int quantity ;


    /// 拣货单单号
       @JSONField(name = "PickListCode")
    public String pickListCode ;


    /// 异常信息
       @JSONField(name = "ErrorMsg")
    public String errorMsg ;
}
