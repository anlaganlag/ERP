package com.tadpole.cloud.platformSettlement.core.util;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import java.util.ArrayList;
import java.util.List;

/**
 * 生成业务的编码
 *
 * @author fengshuonan
 * @Date 2019-09-25 16:37
 */
public class CodeUtil {

    /**
     * 生成业务编码
     *
     * @param prefix         前缀
     * @param separator      分隔符（可为空）
     * @param suffix         后缀
     * @param insideContents 中间内容
     * @author fengshuonan
     * @Date 2019-09-25 16:37
     */
    public static String getCode(String prefix, String separator, String suffix, List<String> insideContents) {
        StringBuilder biulder = new StringBuilder();
        biulder.append(prefix);
        if (CollectionUtil.isNotEmpty(insideContents)) {
            for (String insideContent : insideContents) {
                biulder.append(separator);
                biulder.append(insideContent);
            }
        }
        biulder.append(separator);
        biulder.append(suffix);
        return biulder.toString();
    }

    public static void main(String[] args) {
        List<String> insideContents = new ArrayList<>();
        insideContents.add(IdWorker.get32UUID().toUpperCase());
        String code = CodeUtil.getCode("TMP", "", "1", insideContents);
        System.out.println(code);
    }
}
