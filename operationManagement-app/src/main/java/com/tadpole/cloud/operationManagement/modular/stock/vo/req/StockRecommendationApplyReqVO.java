package com.tadpole.cloud.operationManagement.modular.stock.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: ty
 * @description: 日常备货审核列表请求参数VO类
 * @date: 2021/11/8
 */
@Data
@ApiModel("日常备货审核列表请求参数VO类")
public class StockRecommendationApplyReqVO {
    @ApiModelProperty("当前页数")
    private Integer page;
    @ApiModelProperty("每页条数")
    private Integer pageSize;
    @ApiModelProperty("状态")
    private Integer status;
    @ApiModelProperty("请求入参条件")
    private Eform eform;

    @Data
    @ApiModel("请求入参条件")
    public class Eform {
        @ApiModelProperty("区域集合")
        private List<String> area;
        @ApiModelProperty("team集合")
        private List<String> Team;
        @ApiModelProperty("颜色集合")
        private List<String> color;
        @ApiModelProperty("主材料集合")
        private List<String> mainmaterial;
        @ApiModelProperty("公司品牌集合")
        private List<String> companybrand;
        @ApiModelProperty("包装数量集合")
        private List<String> packing;
        @ApiModelProperty("版本集合")
        private List<String> version;
        @ApiModelProperty("款式二级标签")
        private List<String> styleSecondLabel;
        @ApiModelProperty("尺码集合")
        private List<String> sizes;
        @ApiModelProperty("物料编码集合")
        private List<String> materialcode;
        @ApiModelProperty("产品名称集合")
        private List<String> productname;
        @ApiModelProperty("款式集合")
        private List<String> style;
        @ApiModelProperty("型号集合")
        private List<String> model;
        @ApiModelProperty("类目集合")
        private List<String> category;
        @ApiModelProperty("适用品牌或对象集合")
        private List<String> brand;
        @ApiModelProperty("运营大类集合")
        private List<String> productType;
        @ApiModelProperty("图案集合")
        private List<String> design;
        @ApiModelProperty("用户工号")
        private String operator;
        @ApiModelProperty("是否勾选推荐量>0")
        private Boolean isChecked;
    }
}
