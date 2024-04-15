package com.tadpole.cloud.externalSystem.api.lingxing.model.parm;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

/**
 * 领星物料同步;
 * @author : LSY
 * @date : 2022-12-5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "领星物料同步",description = "")
public class LxMatSyncParm extends BaseRequest implements Serializable, BaseValidatingParam {
    private static final long serialVersionUID = 1L;
    /** ID */
    @ApiModelProperty(name = "ID",notes = "")
    private String id ;
    /** 产品名称;适用具体机型-方案(产品名称+款式+主材料+图案+适用品牌或对象+型号+颜色+尺码+包装数量+版本)--非适用具体机型-方案(产品名称+款式+主材料+图案+适用品牌或对象+型号+颜色+尺码+包装数量+版本) */
    @ApiModelProperty(name = "产品名称",notes = "适用具体机型-方案(产品名称+款式+主材料+图案+适用品牌或对象+型号+颜色+尺码+包装数量+版本)--非适用具体机型-方案(产品名称+款式+主材料+图案+适用品牌或对象+型号+颜色+尺码+包装数量+版本)")
    private String productName ;
    /** 物料编码 */
    @ApiModelProperty(name = "物料编码",notes = "")
    private String matCode ;
    /** SKU */
    @ApiModelProperty(name = "SKU",notes = "")
    private String sku ;
    /** 物料状态;(领星：状态：0-停售 1-在售 2-开发中 3-清仓，EBMS:EBMS启用-->1-在售（领星） EBMS禁用-->0-停售 （领星）) */
    @ApiModelProperty(name = "物料状态",notes = "(领星：状态：0-停售 1-在售 2-开发中 3-清仓，EBMS:EBMS启用-->1-在售（领星） EBMS禁用-->0-停售 （领星）)")
    private Integer status ;
    /** 物料创建时间 */
    @ApiModelProperty(name = "物料创建时间",notes = "")
    private Date matCreateTime ;
    /** 物料最后一次更新时间 */
    @ApiModelProperty(name = "物料最后一次更新时间",notes = "")
    private Date matUpdateTime ;
    /** 同步业务类型;新增，更新,删除 */
    @ApiModelProperty(name = "同步业务类型",notes = "新增，更新,删除")
    private String bizType ;
    /** 一级目录 */
    @ApiModelProperty(name = "一级目录",notes = "")
    private String categoryOne ;
    /** 一级目录ID */
    @ApiModelProperty(name = "一级目录ID",notes = "")
    private Integer categoryOneId ;
    /** 二级目录 */
    @ApiModelProperty(name = "二级目录",notes = "")
    private String categoryTwo ;
    /** 二级目录ID */
    @ApiModelProperty(name = "二级目录ID",notes = "")
    private Integer categoryTwoId ;
    /** 三级目录 */
    @ApiModelProperty(name = "三级目录",notes = "")
    private String categoryThree ;
    /** 三级目录ID */
    @ApiModelProperty(name = "三级目录ID",notes = "")
    private Integer categoryThreeId ;
    /** 型号 */
    @ApiModelProperty(name = "型号",notes = "")
    private String model ;
    /** 品牌 */
    @ApiModelProperty(name = "品牌",notes = "")
    private String brand ;
    /** 品牌ID */
    @ApiModelProperty(name = "品牌ID",notes = "")
    private Integer brandId ;
    /** 备注 */
    @ApiModelProperty(name = "备注",notes = "")
    private String remark ;
    /** 是否删除--是否删除:正常：0，删除：1;正常：0，删除：1 */
    @ApiModelProperty(name = "是否删除--是否删除:正常：0，删除：1",notes = "正常：0，删除：1")
    private String isDelete ;

    /**同步方案信息*/
    private String syncPlanInfo ;
    /**同步方案*/
    private String syncPlan ;

    /** 同步方式--同步方式(0 ：系统同步,1：手动人工同步);0 ：系统同步,1：手动人工同步 */
    @ApiModelProperty(name = "同步方式--同步方式(0 ：系统同步,1：手动人工同步)",notes = "0 ：系统同步,1：手动人工同步")
    private String syncType ;
    /** 同步时间 */
    @ApiModelProperty(name = "同步时间",notes = "")
    private Date syncTime ;
    /** 同步状态;0 ：同步失败,1：同步成功 */
    @ApiModelProperty(name = "同步状态",notes = "0 ：同步失败,1：同步成功")
    private String syncStatus ;
    /** 同步请求参数 */
    @ApiModelProperty(name = "同步请求参数",notes = "")
    private String syncRequstPar ;
    /** 同步结果消息内容 */
    @ApiModelProperty(name = "同步结果消息内容",notes = "")
    private String syncResultMsg ;
    /** 创建时间 */
    @ApiModelProperty(name = "创建时间",notes = "")
    private Date createTime ;
    /** 更新时间 */
    @ApiModelProperty(name = "更新时间",notes = "")
    private Date updatedTime ;

    /** 同步时间查询-开始*/
    @ApiModelProperty("同步时间查询-开始")
    private Date syncStartTime;

    /** 同步时间查询-结束*/
    @ApiModelProperty("同步时间查询-结束")
    private Date syncEndTime;

    /** 物料创建时间查询-开始*/
    @ApiModelProperty("物料创建时间查询-开始")
    private Date matCreateStartTime;

    /** 物料创建时间查询-结束*/
    @ApiModelProperty("物料创建时间查询-结束")
    private Date matCreateEndTime;


    /** 物料更新时间查询-开始*/
    @ApiModelProperty("物料更新时间查询-开始")
    private Date matUpdateStartTime;

    /** 物料更新时间查询-结束*/
    @ApiModelProperty("物料更新时间查询-结束")
    private Date matUpdateEndTime;

}
