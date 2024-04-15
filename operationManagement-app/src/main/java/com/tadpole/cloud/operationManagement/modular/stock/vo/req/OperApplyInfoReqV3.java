package com.tadpole.cloud.operationManagement.modular.stock.vo.req;

import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * @author: ty
 * @description: 日常备货列表请求参数VO类
 * @date: 2021/11/3
 */
@Data
@Getter
@ApiModel("日常备货列表请求参数VO类")
public class OperApplyInfoReqV3 extends BaseRequest {

    @ApiModelProperty("区域集合")
    private String area;
    @ApiModelProperty("区域集合List")
    private List<String> areaList;


    @ApiModelProperty("team集合")
    private List<String> Team;


    @ApiModelProperty("颜色集合")
    private String color;
    @ApiModelProperty("颜色集合List")
    private List<String> colorList;

    @ApiModelProperty("主材料集合")
    private String mainmaterial;
    @ApiModelProperty("主材料集合List")
    private List<String> mainmaterialList;


    @ApiModelProperty("公司品牌集合")
    private List<String> companybrand;

    @ApiModelProperty("包装数量集合")
    private String packing;
    @ApiModelProperty("包装数量集合List")
    private List<String> packingList;


    @ApiModelProperty("版本集合")
    private String version;
    @ApiModelProperty("版本集合List")
    private List<String> versionList;

    @ApiModelProperty("款式二级标签")
    private String styleSecondLabel;
    @ApiModelProperty("款式二级标签List")
    private List<String> styleSecondLabelList;

    @ApiModelProperty("尺码集合")
    private String sizes;
    @ApiModelProperty("尺码集合List")
    private List<String> sizesList;

    @ApiModelProperty("物料编码集合")
    private String materialcode;
    @ApiModelProperty("物料编码集合List")
    private List<String> materialcodeList;

    @ApiModelProperty("产品名称集合")
    private String productname;
    @ApiModelProperty("产品名称集合List")
    private List<String> productnameList;

    @ApiModelProperty("款式集合")
    private String style;
    @ApiModelProperty("款式集合List")
    private List<String> styleList;

    @ApiModelProperty("型号集合")
    private String model;
    @ApiModelProperty("型号集合List")
    private List<String> modelList;

    @ApiModelProperty("类目集合")
    private String category;
    @ApiModelProperty("类目集合List")
    private List<String> categoryList;

    @ApiModelProperty("适用品牌或对象集合")
    private String brand;
    @ApiModelProperty("适用品牌或对象集合List")
    private List<String> brandList;


    @ApiModelProperty("运营大类集合")
    private String productType;
    @ApiModelProperty("运营大类集合List")
    private List<String> productTypeList;

    @ApiModelProperty("图案集合")
    private String design;
    @ApiModelProperty("图案集合List")
    private List<String> designList;

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
    @ApiModelProperty("Team审核状态")
    private String verifStatus;
    @ApiModelProperty("备货类型--备货类型:RCBH日常备货,JJBH紧急备货,XMBH项目备货,XPBH新品备货")
    private String billType;

    @ApiModelProperty("备货类型--备货类型:RCBH日常备货,JJBH紧急备货,XMBH项目备货,XPBH新品备货")
    private List billTypes;

    @ApiModelProperty("销售需求小")
    private String salesRequireLow;

    @ApiModelProperty("销售需求大")
    private String salesRequireHigh;

    @ApiModelProperty("申请区域备货数量小")
    private String stockQtyAreaLow;

    @ApiModelProperty("申请区域备货数量大")
    private String stockQtyAreaHeight;

    @ApiModelProperty("部门")
    private String department;
}
