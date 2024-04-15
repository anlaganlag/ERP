package com.tadpole.cloud.product.core.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author: lsy
 * @description: 使用redis生成编号
 */
@Component
public class GeneratorNoUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    private static String fit = "000";
    private static String CODE_HEADER = "CPXBH";
    private static int len = 3;

    public String getBillNo() {

        String dateStr = DateUtil.format(DateUtil.date(), "yyyy");

        String redisKey = CODE_HEADER + "-" + dateStr + "-";

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

        return tempEndStr;
    }

    public String getYaBillNoExtents(String fitEx,String CODE_HEADER_Ex,int lenEx){
        String dateStr = DateUtil.format(DateUtil.date(), "yyyyMM");
        String redisKey = CODE_HEADER_Ex + "-" + dateStr + "-";
        String endStr = fitEx;

        Integer no = (Integer) redisTemplate.boundValueOps(redisKey).get();
        if (ObjectUtil.isNull(no)) {

            redisTemplate.boundValueOps(redisKey).set(1, 1, TimeUnit.DAYS);
            endStr = endStr + 1;
            no = 1;
        } else {
            endStr = endStr + no;
        }
        String tempEndStr = endStr.substring(endStr.length() - lenEx, endStr.length());
        no = no + 1;
        redisTemplate.boundValueOps(redisKey).set(no, 1, TimeUnit.DAYS);

        return CODE_HEADER_Ex + "-" + dateStr + "-" + tempEndStr;
    }

    public String getTaBillNoExtents(String fitEx,String CODE_HEADER_Ex,int lenEx){
        String dateStr = DateUtil.format(DateUtil.date(), "yyyyMM");
        String redisKey = CODE_HEADER_Ex + "-" + dateStr + "-";
        String endStr = fitEx;

        Integer no = (Integer) redisTemplate.boundValueOps(redisKey).get();
        if (ObjectUtil.isNull(no)) {

            redisTemplate.boundValueOps(redisKey).set(1, 1, TimeUnit.DAYS);
            endStr = endStr + 1;
            no = 1;
        } else {
            endStr = endStr + no;
        }
        String tempEndStr = endStr.substring(endStr.length() - lenEx, endStr.length());
        no = no + 1;
        redisTemplate.boundValueOps(redisKey).set(no, 1, TimeUnit.DAYS);

        return CODE_HEADER_Ex + "-" + dateStr + tempEndStr;
    }

    public String getSpuBillNoExtents(String fitEx,String CODE_HEADER_Ex,int lenEx){
        String dateStr = DateUtil.format(DateUtil.date(), "yyyyMM");
        String redisKey = CODE_HEADER_Ex + "-" + dateStr + "-";
        String endStr = fitEx;

        Integer no = (Integer) redisTemplate.boundValueOps(redisKey).get();
        if (ObjectUtil.isNull(no)) {

            redisTemplate.boundValueOps(redisKey).set(1, 1, TimeUnit.DAYS);
            endStr = endStr + 1;
            no = 1;
        } else {
            endStr = endStr + no;
        }
        String tempEndStr = endStr.substring(endStr.length() - lenEx, endStr.length());
        no = no + 1;
        redisTemplate.boundValueOps(redisKey).set(no, 1, TimeUnit.DAYS);

        return dateStr + tempEndStr;
    }

    public void getResetSpuBillNoExtents(String fitEx,String CODE_HEADER_Ex,int lenEx){
        String dateStr = DateUtil.format(DateUtil.date(), "yyyyMM");
        String redisKey = CODE_HEADER_Ex + "-" + dateStr + "-";
        String endStr = fitEx;

        Integer no = (Integer) redisTemplate.boundValueOps(redisKey).get();
        if (ObjectUtil.isNull(no)) {

            redisTemplate.boundValueOps(redisKey).set(1, 1, TimeUnit.DAYS);
            endStr = endStr + 1;
            no = 1;
        } else {
            endStr = endStr + no;
        }
        String tempEndStr = endStr.substring(endStr.length() - lenEx, endStr.length());
        no = no -1;
        redisTemplate.boundValueOps(redisKey).set(no, 1, TimeUnit.DAYS);

    }

}
