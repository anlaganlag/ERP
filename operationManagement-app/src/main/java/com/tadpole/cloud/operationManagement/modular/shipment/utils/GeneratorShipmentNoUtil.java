package com.tadpole.cloud.operationManagement.modular.shipment.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.tadpole.cloud.operationManagement.modular.shipment.constants.ShipmentConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: lsy
 * @description: 使用redis生成编号
 */
@Component
public class GeneratorShipmentNoUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    private static String fit = "000000000000000000";
    private static int len = 4;

    public static Map<String, String> unwTypeMap = new HashMap();

    static {
        unwTypeMap.put("小袋出货","XDS");
        unwTypeMap.put("B2B出货","BDS");
        unwTypeMap.put("亚马逊出货","ADS");
        unwTypeMap.put("亚马逊出货","ADS");
        unwTypeMap.put("批量出货","ADS");
        unwTypeMap.put("滞销销毁出货","ADS");
    }


    public String getBatchNo(String headStr) {

        String dateStr = DateUtil.format(DateUtil.date(), "yyyyMMdd");

        String redisKey = ShipmentConstants.SHIPMENT_BATCH_NO + dateStr;

        headStr = headStr + "-" + dateStr + "-";

        String endStr = fit;

        Integer no = (Integer) redisTemplate.boundValueOps(redisKey).get();
        if (ObjectUtil.isNull(no)) {

            redisTemplate.boundValueOps(redisKey).set(1, 1, TimeUnit.DAYS);
            endStr = endStr + 1;
            no = 1;
        } else {
            endStr = endStr + no;
        }
        String tempEndStr = endStr.substring(endStr.length() - len, endStr.length());
        no = no + 1;
        redisTemplate.boundValueOps(redisKey).set(no, 1, TimeUnit.DAYS);

        return headStr + tempEndStr;
    }



    public String getPrintLabelBatchNo(String headStr) {

        String dateStr = DateUtil.format(DateUtil.date(), "yyyyMMdd");

        String redisKey = ShipmentConstants.PRINT_LABEL_BATCH_NO + dateStr;

        headStr = headStr + "-" + dateStr + "-";

        String endStr = fit;

        Integer no = (Integer) redisTemplate.boundValueOps(redisKey).get();
        if (ObjectUtil.isNull(no)) {

            redisTemplate.boundValueOps(redisKey).set(1, 1, TimeUnit.DAYS);
            endStr = endStr + 1;
            no = 1;
        } else {
            endStr = endStr + no;
        }
        String tempEndStr = endStr.substring(endStr.length() - len, endStr.length());
        no = no + 1;
        redisTemplate.boundValueOps(redisKey).set(no, 1, TimeUnit.DAYS);

        return headStr + tempEndStr;
    }


    public String getBillNo(String unwType, String orgCode ) {
        String unwTypeCode = unwTypeMap.get(unwType);
        String headStr = "MCFH"; //mcms系统发货

        String dateStr = DateUtil.format(DateUtil.date(), "yyyyMMdd");

        String redisKey = ShipmentConstants.SHIPMENT_BATCH_NO + dateStr;

        headStr = headStr + "-" + unwTypeCode+"-"+orgCode+"-"+dateStr + "-";

        String endStr = fit;

        Integer no = (Integer) redisTemplate.boundValueOps(redisKey).get();
        if (ObjectUtil.isNull(no)) {

            redisTemplate.boundValueOps(redisKey).set(1, 1, TimeUnit.DAYS);
            endStr = endStr + 1;
            no = 1;
        } else {
            endStr = endStr + no;
        }
        String tempEndStr = endStr.substring(endStr.length() - len, endStr.length());
        no = no + 1;
        redisTemplate.boundValueOps(redisKey).set(no, 1, TimeUnit.DAYS);

        return headStr + tempEndStr;
    }

}
