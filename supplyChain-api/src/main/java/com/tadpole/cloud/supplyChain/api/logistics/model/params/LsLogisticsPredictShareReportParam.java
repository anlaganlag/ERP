package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  物流投入预估分摊报表入参类
 * </p>
 *
 * @author ty
 * @since 2023-12-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LsLogisticsPredictShareReportParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("平台集合")
    private List<String> platformList;

    @ApiModelProperty("账号集合")
    private List<String> sysShopsNameList;

    @ApiModelProperty("站点集合")
    private List<String> sysSiteList;

    /** 需求部门集合 */
    @ApiModelProperty("需求部门集合")
    private List<String> departmentList;

    /** 需求Team集合 */
    @ApiModelProperty("需求Team集合")
    private List<String> teamList;

    @ApiModelProperty("发货方式1集合")
    private List<String> freightCompanyList;

    @ApiModelProperty("运输方式集合")
    private List<String> transportTypeList;

    /** 运营大类集合 */
    @ApiModelProperty("运营大类集合")
    private List<String> productTypeList;

    /** 单据编号集合 */
    @ApiModelProperty("单据编号集合")
    private List<String> orderNoList;

    /** 物流单号集合 */
    @ApiModelProperty("物流单号集合")
    private List<String> logisticsNoList;

    /** 物料编码集合 */
    @ApiModelProperty("物料编码集合")
    private List<String> materialCodeList;

    /** 调拨单号集合 */
    @ApiModelProperty("调拨单号集合")
    private List<String> packDirectCodeList;

    /** 发货开始日期 */
    @ApiModelProperty("发货开始日期")
    private String shipmentStartDate;

    /** 发货结束日期 */
    @ApiModelProperty("发货结束日期")
    private String shipmentEndDate;

    /** 数据月份 */
    @ApiModelProperty("数据月份")
    private String dataMonth;

}
