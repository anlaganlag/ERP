package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.k3;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * ERP---EBMS数据 BoxDet Model 箱信息 返回结果
 */
@ApiModel(value = "ERP---EBMS数据 BoxDet Model 箱信息 返回结果", description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class PackingRecordSyncBoxDetResult implements Serializable {
    private static final long serialVersionUID = 1L;

    
    /// 箱条码
    @JSONField(name = "PackDetBoxCode")
    public String packDetBoxCode ;

    
    /// 箱流水号
    @JSONField(name = "PackDetBoxNum")
    public int packDetBoxNum ;

    
    /// 箱明细集合
    @JSONField(name = "SkuDet")
    public List<PackingRecordSyncSkuDetResult> skuDet ;
}
