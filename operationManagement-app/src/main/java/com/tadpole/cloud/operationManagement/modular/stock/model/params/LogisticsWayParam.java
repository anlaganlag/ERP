package com.tadpole.cloud.operationManagement.modular.stock.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 设置物流方式
 * </p>
 *
 * @author lsy
 * @since 2022-09-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("LOGISTICS_WAY")
public class LogisticsWayParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;
    private List<Integer> idList;

    /** SPU */
    @ApiModelProperty("SPU")
    private String spu;

    /** 运营大类 */
    @ApiModelProperty("PRODUCT_TYPE")
    private List<String> productTypeList;

    /** 物料编码 */
    @ExcelProperty("物料编码")
    @ApiModelProperty("MATERIAL_CODE")
    private String materialCode;
    @ApiModelProperty("MATERIAL_CODE")
    private List<String> materialCodeList;

    /** 区域 */
    @ExcelProperty("区域")
    @ApiModelProperty("AREA")
    private String area;
    @ApiModelProperty("AREA")
    private List<String> areaList;

    /** 物流方式 */
    @ExcelProperty("物流方式")
    @ApiModelProperty("WAY")
    private String way;
    @ApiModelProperty("WAY")
    private List<String> wayList;


}