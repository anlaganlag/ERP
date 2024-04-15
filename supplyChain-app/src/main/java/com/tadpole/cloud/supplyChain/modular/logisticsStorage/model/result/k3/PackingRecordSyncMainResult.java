package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.k3;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * ERP---EBMS数据 Main Model 返回结果
 */
@ApiModel(value = "ERP---EBMS数据 Main Model 返回结果", description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class PackingRecordSyncMainResult implements Serializable {
    private static final long serialVersionUID = 1L;

    /// 出货清单编码
    @JSONField(name = "PackCode")
    public String packCode ;


    /// 业务类型
    @JSONField(name = "BillType")
    public String billType ;


    /// 箱信息集合
    @JSONField(name = "BoxDet")
    public List<PackingRecordSyncBoxDetResult> boxDet ;
}
