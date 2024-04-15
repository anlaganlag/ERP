package com.tadpole.cloud.operationManagement.modular.shipment.model.params;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
* <p>
    * 审核参数
    * </p>
*
* @author lsy
* @since 2023-02-02
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor


public class TrackParam extends BaseRequest implements Serializable, BaseValidatingParam {


        private static final long serialVersionUID = 1L;

    /** 物料编码列表 */
    @ExcelProperty("物料编码列表")
    @ApiModelProperty("物料编码列表")
    private List<String> materialCodeList;

    /** 物料编码 */
    @ExcelProperty("物料编码")
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** asin列表 */
    @ExcelProperty("asin列表")
    @ApiModelProperty("asin列表")
    private List<String> asinList;

    /** asin */
    @ExcelProperty("asin")
    @ApiModelProperty("asin")
    private String asin;


    /** 事业部列表 */
    @ExcelProperty("事业部列表")
    @ApiModelProperty("事业部列表")
    private List<String> departmentList;


    /** team列表 */
    @ExcelProperty("team列表")
    @ApiModelProperty("team列表")
    private List<String> teamList;


    /** 区域列表 */
    @ExcelProperty("区域列表")
    @ApiModelProperty("区域列表")
    private List<String> areaList;



    /** 跟踪状态 */
    @ApiModelProperty("跟踪状态")
    private String trackingStatus;

    /** 跟踪状态 */
    @ApiModelProperty("跟踪状态")
    private String trackState;




    @ApiModelProperty("调拨单号")
    private String billNo;

    @ApiModelProperty("调拨单号List")
    private List<String> billNoList;


    @ApiModelProperty("申请批次号")
    private String applyBatchNo;


    @ApiModelProperty("team")
    private String team;



    /** 运营大类列表 */
    @ApiModelProperty("运营大类列表")
    private List<String> productTypeList;


    /** 产品名称列表 */
    @ApiModelProperty("产品名称列表")
    private List<String> productNameList;

    @ApiModelProperty("申请人编号")
    private String applyPerson;

    @ApiModelProperty("审核状态0：待审核，1：已通过，2，未通过")
    private Integer checkStatus;


    @ApiModelProperty("审核状态列表")
    private List<Integer> checkStatusList;

    /** 申请日期-开始 */
    @ApiModelProperty("申请日期-开始")
    private Date applyDateStart;

    /** 申请日期-结束 */
    @ApiModelProperty("申请日期-结束")
    private Date applyDateEnd;


    /** 审核日期-开始 */
    @ApiModelProperty("审核日期-开始")
    private Date checkDateStart;

    /** 审核日期-结束 */
    @ApiModelProperty("审核日期-结束")
    private Date checkDateEnd;

    @ApiModelProperty("sku")
    private String sku;

    @ApiModelProperty("FBA编号")
    private String shipmentId;


    @ApiModelProperty("申请发货方式")
    private String transportationType;


    @ApiModelProperty("申请执行状态")
    private String applyTrackStatus;



    @ApiModelProperty("账号-店铺简称")
    private String sysShopsName;

    @ApiModelProperty("站点")
    private String sysSite;




    /** 站点列表 */
    @ApiModelProperty("站点列表")
    private List<String> sysSiteList;

    /** 账号列表 */
    @ApiModelProperty("账号列表")
    private List<String> sysShopsNameList;



    /** 运输方式列表 */
    @ApiModelProperty("运输方式列表")
    private List<String> transportationTypeList;



    /** 申请执行状态列表 */
    @ApiModelProperty("申请执行状态列表")
    private List<String> applyTrackStatusNameList;





}
