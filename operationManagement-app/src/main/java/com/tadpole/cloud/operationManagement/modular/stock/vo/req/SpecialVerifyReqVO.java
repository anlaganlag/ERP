package com.tadpole.cloud.operationManagement.modular.stock.vo.req;

import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author: ty
 * @description: 特殊备货审核请求参数VO类
 * @date: 2021/11/3
 */
@Data
@ApiModel("特殊备货审核请求参数VO类")
public class SpecialVerifyReqVO extends BaseRequest {

    @ApiModelProperty("请求入参条件")
    private Eform eform;

    @Data
    @ApiModel("请求入参条件")
    @EqualsAndHashCode(callSuper = false)
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
        @ApiModelProperty("team审核编号")
        private String teamVerifNo;
        @ApiModelProperty("team审核编号集合")
        private List teamVerifNoLists;
        @ApiModelProperty("采购申请编号")
        private String purchaseApplyNo;
        @ApiModelProperty("审核状态")
        private String stockStatus;
        @ApiModelProperty("备货类型:正常备货：0，特殊紧急备货：1")
        private String billType;
    }
}
