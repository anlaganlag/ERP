package com.tadpole.cloud.operationManagement.modular.shipment.model.params;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
* <p>
    * 发货追踪汇总表
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
public class ShipmentBoardParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** Team */
    @ApiModelProperty("Team")
    private String team;


    /** 物料编码 */
    @ApiModelProperty("物料编码")
    private String materialCode;


    /** 区域 */
    @ApiModelProperty("区域")
    private String area;


    /** ASIN */
    @ApiModelProperty("ASIN")
    private String asin;


    /** team列表 */
    @ExcelProperty("team列表")
    @ApiModelProperty("team列表")
    private List<String> teamList;


    /** 物料编码列表 */
    @ExcelProperty("物料编码列表")
    @ApiModelProperty("物料编码列表")
    private List<String> matCodeList;


    /** 区域列表 */
    @ExcelProperty("区域列表")
    @ApiModelProperty("区域列表")
    private List<String> areaList;



    /** asin列表 */
    @ExcelProperty("asin列表")
    @ApiModelProperty("asin列表")
    private List<String> asinList;






}
