package com.tadpole.cloud.operationManagement.modular.shipment.model.params;


import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
* <p>
    * listing 下拉通用接口请求
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

public class ListingSelectParam extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;


        /** SKU */
        @ApiModelProperty("SKU")
        private String sku;

        /** SKU */
        @ApiModelProperty("skuList")
        private List<String> skuList;

        /** 站点 */
        @ApiModelProperty("站点")
        private String sysSite;

        /** 站点 */
        @ApiModelProperty("站点List")
        private List<String> sysSiteList;

        /** 店铺名简称 */
        @ApiModelProperty("账号-店铺名简称")
        private String sysShopsName;

        /** 店铺名简称 */
        @ApiModelProperty("账号-店铺名简称List")
        private List<String> sysShopsNameList;

        /** 物料编码 */
        @ApiModelProperty("物料编码")
        private String materialCode;

        /** 物料编码 */
        @ApiModelProperty("物料编码List")
        private List<String> materialCodeList;

        /** 部门 */
        @ApiModelProperty("部门")
        private String department;

        /** team */
        @ApiModelProperty("team")
        private String team;

        /** asin */
        @ApiModelProperty("asin")
        private String asin;

        /** asin */
        @ApiModelProperty("asin_list")
        private List<String> asinList;


        /** 区域 */
        @ApiModelProperty("区域")
        private String area;

        /** 区域 */
        @ApiModelProperty("区域List")
        private List<String> areaList;




}
