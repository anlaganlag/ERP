package com.tadpole.cloud.supplyChain.core.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
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
public class GeneratorSupplierNoUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    private static String fit = "0000";
    private static String CODE_HEADER = "GYS";
    private static int len = 4;

    public String getBillNo() {

        String dateStr = DateUtil.format(DateUtil.date(), "yyyy");

        String redisKey = CODE_HEADER + "-" + dateStr + "-";

        String endStr = fit;

        Integer no = (Integer) redisTemplate.boundValueOps(redisKey).get();
        if (ObjectUtil.isNull(no)) {

            redisTemplate.boundValueOps(redisKey).set(1, 0);
            endStr = endStr + 1;
            no = 1;
        } else {
            endStr = endStr + no;
        }
        String tempEndStr = endStr.substring(endStr.length() - len, endStr.length());
        no = no + 1;
        redisTemplate.boundValueOps(redisKey).set(no, 0);

        return redisKey + tempEndStr;
    }

}
