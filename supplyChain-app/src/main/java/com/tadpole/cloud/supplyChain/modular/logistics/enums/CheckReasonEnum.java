package com.tadpole.cloud.supplyChain.modular.logistics.enums;

import lombok.Getter;

import java.util.*;

/**
 * @author: ty
 * @description: 盘点原因
 * @date: 2023/1/31
 */
@Getter
public enum CheckReasonEnum {
    SUB_XH("sub","销毁"),
    SUB_DS("sub","丢失"),
    SUB_KCBZ("sub","库存不准"),
    ADD_KCBZ("add","库存不准"),
    ADD_DSZH("add","丢失找回");

    /**
     * 盘点类型
     */
    private String type;

    /**
     * 盘点原因
     */
    private String name;

    CheckReasonEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public static List<Map<String, String>> getCheckReason() {
        List<Map<String, String>> result = new ArrayList<>();
        CheckReasonEnum[] enumAry = CheckReasonEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            Map<String, String> confirmStatusMap = new HashMap<>();
            confirmStatusMap.put("checkReason", enumAry[i].getName());
            result.add(confirmStatusMap);
        }
        //去重
        Set reasonNameSet = new HashSet<>();
        Iterator<Map<String, String>> iterator = result.iterator();
        while(iterator.hasNext()) {
            Map<String, String> reason = iterator.next();
            String reasonName = reason.get("checkReason");
            if(reasonNameSet.contains(reasonName)){
                iterator.remove();
            }
            reasonNameSet.add(reasonName);
        }
        return result;
    }

    /**
     * 盘点盘盈原因下拉
     * @return
     */
    public static List<Map<String, String>> getAddCheckReason() {
        List<Map<String, String>> result = new ArrayList<>();
        CheckReasonEnum[] enumAry = CheckReasonEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            if("add".equals(enumAry[i].getType())){
                Map<String, String> confirmStatusMap = new HashMap<>();
                confirmStatusMap.put("checkReason", enumAry[i].getName());
                result.add(confirmStatusMap);
            }
        }
        return result;
    }

    /**
     * 盘点盘盈原因
     * @return
     */
    public static List<String> getAddCheckReasonValue() {
        List<String> result = new ArrayList<>();
        CheckReasonEnum[] enumAry = CheckReasonEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            if("add".equals(enumAry[i].getType())){
                result.add(enumAry[i].getName());
            }
        }
        return result;
    }

    /**
     * 盘点盘亏原因下拉
     * @return
     */
    public static List<Map<String, String>> getSubCheckReason() {
        List<Map<String, String>> result = new ArrayList<>();
        CheckReasonEnum[] enumAry = CheckReasonEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            if("sub".equals(enumAry[i].getType())){
                Map<String, String> confirmStatusMap = new HashMap<>();
                confirmStatusMap.put("checkReason", enumAry[i].getName());
                result.add(confirmStatusMap);
            }
        }
        return result;
    }

    /**
     * 盘点盘亏原因
     * @return
     */
    public static List<String> getSubCheckReasonValue() {
        List<String> result = new ArrayList<>();
        CheckReasonEnum[] enumAry = CheckReasonEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            if("sub".equals(enumAry[i].getType())){
                result.add(enumAry[i].getName());
            }
        }
        return result;
    }
}
