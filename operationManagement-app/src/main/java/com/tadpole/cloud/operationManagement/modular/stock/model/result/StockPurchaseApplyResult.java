package com.tadpole.cloud.operationManagement.modular.stock.model.result;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 物料采购申请表
 * </p>
 *
 * @author gal
 * @since 2021-07-23
 */
@Data
@ApiModel
public class StockPurchaseApplyResult implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("id")
  private Integer id;


  @ApiModelProperty("apply_id")
  private String applyId;



  /**
   * 单据编号
   */
  @ApiModelProperty("apply_code")
  @Excel(name = "单据编号", width = 22)
  private String applyCode;



  @ApiModelProperty("apply_date")
  @Excel(name = "申请日期", width = 13)
  private String applyDate;



  @ApiModelProperty("bill_type")
  @Excel(name = "备货类型")
  private String billType;


  @ApiModelProperty("material_code")
  @Excel(name = "物料编码")
  private String materialCode;


  @ApiModelProperty("company_brand")
  private String companyBrand;

  @ApiModelProperty("platform_name")
  private String platformName;


  @ApiModelProperty("area")
  private String area;

  @ApiModelProperty("product_type")
  @Excel(name = "运营大类", width = 15)
  private String productType;


  @ApiModelProperty("product_name")
  private String productName;

  @ApiModelProperty("brand")
  private String brand;

  @ApiModelProperty("model")
  private String model;

  @ApiModelProperty("design")
  private String design;


  @ApiModelProperty("style")
  private String style;

  @ApiModelProperty("color")
  private String color;

  @ApiModelProperty("main_material")
  private String mainMaterial;

  @ApiModelProperty("sizes")
  private String sizes;

  @ApiModelProperty("packing")
  private String packing;

  @ApiModelProperty("version")
  private String version;

  @ApiModelProperty("version_des")
  private String version_des;

  @ApiModelProperty("material_name")
  @Excel(name = "物料名称", width = 25)
  private String materialName;


  @ApiModelProperty("specification")
  @Excel(name = "规格型号", width = 80)
  private String specification;




  @ApiModelProperty("department")
  @Excel(name = "事业部")
  private String department;





  @ApiModelProperty("team")
  @Excel(name = "Team")
  private String team;

  /**
   * 需求人
   */
  @ApiModelProperty("require_by")
  private String requireBy;

  /**
   * 申请数量
   */
  @ApiModelProperty("purchase_number")
  @Excel(name = "申请数量")
  private String purchaseNumber;


  @ApiModelProperty("moq")
  @Excel(name = "MOQ")
  private String moq;




  @ApiModelProperty("moq_notes")
  @Excel(name = "MOQ备注", width = 20)
  private String moqNotes;


  @ApiModelProperty("expected_date")
  @Excel(name = "期望交期", width = 13)
  private String expectedDate;

  @ApiModelProperty("状态")
  @Excel(name = "状态")
  private String statusTxt;

  @ApiModelProperty("verify_remark")
  @Excel(name = "计划部备注")
  private String verifyRemark;


  @ApiModelProperty("verify_number")
  private String verifyNumber;


  @ApiModelProperty("refuse_remark")
  private String refuseRemark;
  /**
   * 创建日期
   */
  @ApiModelProperty("create_at")
  private String createAt;

  /**
   * 创建日期
   */
  @ApiModelProperty("last_apply_at")
  @Excel(name = "上次下单时间")
  private String lastApplyAt;

  /**
   * 同步erp状态 0 未同步、1 已同步
   */
  @ApiModelProperty("status")
  private Integer status;


  @ApiModelProperty("purchase_by_code")
  @Excel(name = "采购员工号")
  private String purchaseByCode;

  @ApiModelProperty("purchase_by")
  @Excel(name = "采购员姓名", width = 15)
  private String purchaseBy;

  @ApiModelProperty("sujust_supplier")
  @Excel(name = "建议供应商", width = 25)
  private String sujustSupplier;

  @ApiModelProperty("supplier_code")
  private String supplierCode;

  @ApiModelProperty("pmc_remark")
  @Excel(name = "PMC备注", width = 25)
  private String pmcRemark;

  @ApiModelProperty("apply_remark")
  @Excel(name = "运营备注", width = 20)
  private String applyRemark;

  @ApiModelProperty("apply_reason")
  private String applyReason;

  @ApiModelProperty("款式二级标签")
  @Excel(name = "款式二级标签", width = 20)
  private String styleSecondLabel;
}
