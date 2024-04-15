package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result;


import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "通过ShipmentID查询来货信息结果类(sku维度)",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class LogisticsInGoodsModel  implements Serializable {

    private static final long serialVersionUID = 1L;

    private String busCASIN;;
    private String busMatCode;;
    private String busSku;
     //		--,send.packCode;
    private String busLhrState;
    private String busReturnStatus;
    private String busAmaRecState;
    private String busLogTraMode2;
    private String ow_Name;
    private String busCountryCode;
    private String busShopNameSimple;
    private String busShipmentID;
    private String busPlanName;
    private String receive_Warehouse;
    private String busComWarehouseName;
    private Date busFbaTurnDate;
    private String Lhr_Code;
    private String busLhrOddNumb;
    private Date busLerSignDate;
    private String busLogTraMode1;
	private Integer busReceiveQty; //--接收数量;
    private Integer busSendQty; //--发货数量;
    private Integer busStayDeliverQty; //--物流待发;
    private Integer busIssuedQty; //--物流已发;
    private Integer busInTransitQty; //--在途（空+海+卡+铁..-接收数量）;
    private Integer busDiscrepancy; //--接收差异;
//	private 	--,CASE ISNULL(inGood.allQty,0)-ISNULL(rec.Quantity,0)  WHEN 0 THEN 'sku汇总后差异数量总数为0' ELSE '' END AS busRemark;
	private String busRemark;
	private String lpName;
}