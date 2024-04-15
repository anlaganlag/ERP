package com.tadpole.cloud.supplyChain.modular.logistics.utils;

/**
 * todo
 *
 * @author:idea
 * @version:2022/8/11 15:43
 */

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class EasyExcelListener<T> extends AnalysisEventListener<T> {

    /**
     * 解析的数据
     */
    List<T> list = new ArrayList<>();

    /**
     * 正文起始行
     */
    private Integer headRowNumber;
    /**
     * 合并单元格
     */
    private List<CellExtra> extraMergeInfoList = new ArrayList<>();

    public EasyExcelListener(Integer headRowNumber) {
        this.headRowNumber = headRowNumber;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        list.add(data);
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    public List<T> getData() {
        return list;
    }

    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        if (extra.getType() == CellExtraTypeEnum.MERGE) {
            if (extra.getRowIndex() >= headRowNumber) {
                extraMergeInfoList.add(extra);
            }
        }
    }

    public List<CellExtra> getExtraMergeInfoList() {
        return extraMergeInfoList;
    }


}
