package com.tadpole.cloud.operationManagement.modular.stock.model.result;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jobob
 * @since 2021-06-07
 */
@Data
@ApiModel
@ExcelIgnoreUnannotated
public class SysMaterialResult implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "material_code")
  @Excel(name = "物料编码")
  @ExcelProperty(value = "物料编码")
  private String materialCode;

  @TableField("id")
  private String id;

  @TableField("logistics_mode")
  @Excel(name = "物流方式")
  @ExcelProperty(value = "物流方式")
  private String logisticsMode;

  @ApiModelProperty("platform")
  @Excel(name = "平台")
  @ExcelProperty(value = "平台")
  private String platform;

  @ApiModelProperty("area")
  @Excel(name = "区域")
  @ExcelProperty(value = "区域")
  private String area;

  @ApiModelProperty("department")
  @Excel(name = "事业部")
  @ExcelProperty(value = "事业部")
  private String department;

  @ApiModelProperty("team")
  @Excel(name = "Team")
  @ExcelProperty(value = "Team")
  private String team;

  @TableField("spu")
  @Excel(name = "SPU")
  @ExcelProperty(value = "spu")
  private Double spu;

  @TableField("forbidstatus")
  @Excel(name = "物料状态")
  @ExcelProperty(value = "物料状态")
  private String forbidstatus;

  @TableField("category")
  @Excel(name = "类目")
  @ExcelProperty(value = "类目")
  private String category;

  @TableField("product_type")
  @Excel(name = "运营大类")
  @ExcelProperty(value = "运营大类")
  private String productType;

  @TableField("product_name")
  @Excel(name = "物料产品名称")
  @ExcelProperty(value = "物料产品名称")
  private String productName;

  @TableField("style")
  @Excel(name = "物料款式")
  @ExcelProperty(value = "物料款式")
  private String style;

  @TableField("main_material")
  @Excel(name = "物料主材料")
  @ExcelProperty(value = "物料主材料")
  private String mainMaterial;

  @TableField("pattern")
  @Excel(name = "图案")
  @ExcelProperty(value = "图案")
  private String pattern;

  @TableField("company_brand")
  @Excel(name = "公司品牌")
  @ExcelProperty(value = "公司品牌")
  private String companyBrand;

  @TableField("brand")
  @Excel(name = "适用品牌")
  @ExcelProperty(value = "适用品牌")
  private String brand;

  @TableField("model")
  @Excel(name = "型号")
  @ExcelProperty(value = "型号")
  private String model;

  @TableField("color")
  @Excel(name = "颜色")
  @ExcelProperty(value = "颜色")
  private String color;

  @TableField("sizes")
  @Excel(name = "尺寸")
  @ExcelProperty(value = "尺寸")
  private String sizes;

  @TableField("packing")
  @Excel(name = "包装数量")
  @ExcelProperty(value = "包装数量")
  private String packing;

  @TableField("type")
  @Excel(name = "适用机型")
  @ExcelProperty(value = "适用机型")
  private String type;

  @TableField("version")
  @Excel(name = "版本")
  @ExcelProperty(value = "版本")
  private String version;

  @ApiModelProperty("status")
  private String status;

  @ApiModelProperty("create_date")
  private String createDate;

  @ApiModelProperty("version_des")
  private String versionDes;

  @ApiModelProperty("brand_replace")
  private String brandReplace;

  @ApiModelProperty("forbidstatus_new")
  private String forbidstatusNew;

  @ApiModelProperty("mat_edit_state")
  private String matEditState;

  @ApiModelProperty("acctg_date")
  private String acctgDate;

  @ApiModelProperty("festival_label")
  private String festivalLabel;

  @ApiModelProperty("color_code")
  private String colorCode;

  @ApiModelProperty("first_order_date")
  private String firstOrderDate;

  @ApiModelProperty("min_production_cycle")
  private Integer minProductionCycle;

  @ApiModelProperty("max_production_cycle")
  private Integer maxProductionCycle;

  @ApiModelProperty("minpoqty")
  private Integer minpoqty;

  @ApiModelProperty("minpoqty_notes")
  private String minpoqtyNotes;

  /** 拼单起订量 */
  @ApiModelProperty("SPELL_ORDERNUM")
  private Long spellOrdernum;


  /** 拼单起订量备注 */
  @TableField("SPELL_ORDERNUM_REMARK ")
  private String spellOrdernumRemark;

  @ApiModelProperty("nbdu")
  private String nbdu;

  @ApiModelProperty("season_label")
  private String seasonLabel;
}
