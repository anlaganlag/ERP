package com.tadpole.cloud.externalSystem.api.saihu.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: ty
 * @description: 赛狐在线产品请求参数
 * @date: 2023/6/5
 */
@Data
@ApiModel(value="赛狐在线产品请求参数")
public class SaiHuProductParam extends SaiHuPageParam {

    @ApiModelProperty("创建时间开始于，yyyy-MM-dd hh:mm:ss")
    private String createTimeStart;

    @ApiModelProperty("创建时间结束于，yyyy-MM-dd hh:mm:ss")
    private String createTimeEnd;

    @ApiModelProperty("修改时间开始于，yyyy-MM-dd hh:mm:ss")
    private String modifiedTimeStart;

    @ApiModelProperty("修改时间结束于，yyyy-MM-dd hh:mm:ss")
    private String modifiedTimeEnd;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("店铺id集合")
    private List<String> shopIdList;

    @ApiModelProperty("站点ID集合")
    private List<String> marketplaceIdList;

    @ApiModelProperty("是否可售集合,可选值active(在售)，inActive(不可售),delete(删除)")
    private List<String> onlineStatusList;

    @ApiModelProperty("配送类型 (FBA:自发货,FBA:FBA)")
    private String switchFulfillmentTo;

    @ApiModelProperty("是否配对")
    private String match;

    @ApiModelProperty("搜索字段(支持搜索类型:sku,parentAsin,msku,title,fnsku,commodityName,sku)")
    private String searchType;

    @ApiModelProperty("搜索值")
    private String searchContent;

    @ApiModelProperty("fullCid")
    private String fullCid;

}
