package com.tadpole.cloud.operationManagement.modular.stock.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* <p>
    * 每日备货推荐表-BI
    * </p>
*
* @author cyt
* @since 2022-08-19
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("BACK_PREPARE_RECOM_BI")
public class BackPrepareRecomBiParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

   /** 事业部 */
   @ApiModelProperty("事业部集合")
   private String department;

  @ApiModelProperty("team集合")
  private List<String> team;

  @ApiModelProperty("运营大类集合")
  private List<String> productType;

   @ApiModelProperty("区域集合")
    private List<String> area;

    @ApiModelProperty("区域集合")
    private List<String> areaList;

    @ApiModelProperty("运营大类集合")
    private List<String> productTypeList;







   /** 数据为0不做备货推荐 */
   @ApiModelProperty("数据为0不做备货推荐：勾选中了传值——数据为0")
   private String dataZeroNotRecom;


   /** 数据为0不做备货推荐 */
   @ApiModelProperty("移除黑名单记录：勾选中了传值——黑名单")
   private String removeBlacklist;

    /** 淘汰物料 */
    @ApiModelProperty("淘汰物料：勾选中了传值——淘汰物料")
    private String obsoleteMat;


    /** 推荐日期-按天，最新数据为当前日期 */
    @ApiModelProperty("推荐日期")
    private Date bizdate;


    /** 平台 */
    @ApiModelProperty("平台")
    private String platform;

    /** ASIN */
    @ApiModelProperty("ASIN")
    private String asin;


    /** 操作人 */
    @ApiModelProperty("操作人")
    private String operator;

    @ApiModelProperty("team列表")
    private List<String> teamList;



}