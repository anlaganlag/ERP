package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
@ApiModel(value = "物流商发货提醒明细项",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbShipmentApplyDetModel implements Serializable {
    private static final long serialVersionUID = 1L;
    public String packCode;
    public String countryCode;
    public String shopNameSimple;
    public String shipmentID;
    public String totalNumber;
    public String totalWeight;
    public Date packDate;
}
