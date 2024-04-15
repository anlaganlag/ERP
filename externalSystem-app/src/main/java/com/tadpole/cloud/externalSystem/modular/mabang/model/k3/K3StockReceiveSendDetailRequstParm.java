package com.tadpole.cloud.externalSystem.modular.mabang.model.k3;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

import java.io.Serializable;

/**
* <p>
    * K3-WebApi-存货收发存明细（跨维度）
    * </p>
*
* @author lsy
* @since 2023-05-06
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class K3StockReceiveSendDetailRequstParm implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

// 会计期间
 private String FieldKeys;
 
// 业务日期
private String StartRow;
        
// 凭证字号
private String  Limit;
        
// 单据编号
private String  IsVerifyBaseDataField;
        
// 单据行号
 private String FSEQ;
 ;
// 事务类型
private String FBUSINESSTYPE;
        
// 单据名称
private String FBillFormName;
        
// 物料编码
private String FMATERIALID;
        
// 物料名称
private String FMATERIALNAME;
        
// BOM版本
private String FBOMNO;
        
// 辅助属性
private String FASSIPROPNAME;
        
// 规格型号
private String  FMODEL;
        
// 计划跟踪号
private String  FPLANNO;
        
// 批号
private String FLOTNO;
        
// 存货类别
private String FMATERTYPENAME;
        
// 物料分组
private String FMaterialGroup;
        
// 货主
private String FOWNERNAME;
        
// 库存组织编码
private String  FSTOCKORGNO;
        
// 库存组织名称
private String  FSTOCKORGNAME;
        
// 仓库编码
private String FSTOCKNO;
        
// 仓库名称
private String  FSTOCKNAME;
        
// 仓位名称
private String  FSTOCKPLACENAME;
        ;
// 库存状态
private String  FSTOCKSTATUSNAME;
        
// 核算范围编码
private String  FACCTGRANGENUMBER;
        
// 核算范围名称
private String  FACCTGRANGENAME;
        
// 部门
private String FdepatName;
        
// 基本单位
private String  FUNITNAME;
        
// 收入数量
private String FRECEIVEQty;

// 收入单价
private String  FRECEIVEPrice;

// 收入金额
private String FRECEIVEAmount;

// 发出数量
private String  FSENDQty;

// 发出单价
private String  FSENDPrice;

// 发出金额
private String  FSENDAmount;

// 结存数量
private String  FENDQty;

// 结存单价
private String FENDPrice;
        ;
// 结存金额
private String  FENDAmount;
        ;
// 备注
private String  FREMARK;

}
