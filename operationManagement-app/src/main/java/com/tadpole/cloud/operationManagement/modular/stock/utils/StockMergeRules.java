package com.tadpole.cloud.operationManagement.modular.stock.utils;


import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.operationManagement.modular.stock.service.IOperApplyInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static com.tadpole.cloud.operationManagement.modular.stock.constants.StockConstant.BATCH_SIZE;

/**
 * 备货维度合并规则定义
 */
@Slf4j
@Component
public class StockMergeRules {

    @Autowired
    IOperApplyInfoService operationService;


    /**
     *   2. team 审核记录 通过时 检查是否所有区域都提交了
  I  *
     */
    @DataSource(name = "stocking")
    public  ResponseData teamCreateOrderCheck(Set<String> platformMaterialCodeTeamSet) {

        if (ObjectUtil.isEmpty(platformMaterialCodeTeamSet)) {
            return ResponseData.error("没有需要检查的数据");
        }


        //查找运营申请6维度的数据还有未提交的
        List<String> setList = platformMaterialCodeTeamSet.stream().collect(Collectors.toList());
        Set<String> findResultSet = new HashSet<>();


        int applySize = setList.size();

        if (applySize < BATCH_SIZE) {
            List<String> result = operationService.getAllComintByPlatformTeamMaterialCode(setList);
            findResultSet.addAll(result);
        } else {
            int forCunt = BigDecimal.valueOf(Math.ceil(applySize / Double.valueOf(String.valueOf(BATCH_SIZE)))).intValue();
            List<String> upList = new ArrayList<>();

            for (int i = 0; i < forCunt; i++) {
                if (i == forCunt - 1) {
                    upList = setList.subList(i * BATCH_SIZE, applySize);
                } else {
                    upList = setList.subList(i * BATCH_SIZE, (i + 1) * BATCH_SIZE);
                }
                List<String> result = operationService.getAllComintByPlatformTeamMaterialCode(upList);
                findResultSet.addAll(result);
            }
        }

        if (ObjectUtil.isEmpty(findResultSet)) {
            return ResponseData.success();
        }


        //有未提交的数据

            if (platformMaterialCodeTeamSet.removeAll(findResultSet)) {
                if (platformMaterialCodeTeamSet.size()>0) {
//                    有部分已經是全部提交了;
                 return    ResponseData.error(500,"<<本次提交部分数据>>按照（物料编码+team）维度下查询,<日常备货申请>还有未提交到Team审核的数据，所以Team审核，需要等未提交的数据提交后一起审批提交",findResultSet);
                }
                return ResponseData.error(500,"<<本次提交全部数据>>按照（物料编码+team）维度下查询,<日常备货申请>还有未提交到Team审核的数据，所以Team审核，需要等未提交的数据提交后一起审批提交",findResultSet);
        }


        return ResponseData.error("team 审核记录 通过时 检查是否所有区域都提交了，查询日常申请记录维度移除数据失败");
    }


    /**
     * 将 LocalDate 转为 Date
     *
     * @param localDate
     * @return java.util.Date
     */
    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }


}
