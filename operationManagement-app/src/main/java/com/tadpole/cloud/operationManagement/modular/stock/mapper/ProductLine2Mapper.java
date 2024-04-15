package com.tadpole.cloud.operationManagement.modular.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.modular.stock.entity.ProductLine2;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.ProductLine2Param;
import feign.Param;

/**
 * <p>
 * 产品线 Mapper 接口
 * </p>
 *
 * @author lsy
 * @since 2022-06-16
 */
public interface ProductLine2Mapper extends BaseMapper<ProductLine2> {


    /**
     * 产品线分配列表-分页查询
     * @param page
     * @param productLine2Param
     * @return
     */
    Page<ProductLine2> queryListPage(@Param("page") Page page, @Param("productLine2Param") ProductLine2Param productLine2Param);


    /**
     * 产品线分配-分析
     * @param page
     * @param paramCondition
     * @return
     */
    Page<ProductLine2> lineAnalysis(@org.apache.ibatis.annotations.Param("page") Page page, @org.apache.ibatis.annotations.Param("paramCondition") ProductLine2Param paramCondition);


}
