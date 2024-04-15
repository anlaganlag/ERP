package com.tadpole.cloud.externalSystem.api.k3.model.params.results;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.poi.hpsf.Decimal;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description:
 * @author: Amte Ma
 * @version: 1.0
 * @date: 2023/10/20 <br>
 */
@Data
public class SupplierQtyAmount implements Serializable, BaseValidatingParam {

    private String fnumber;

    private BigDecimal fqty;

    private BigDecimal famount;
}
