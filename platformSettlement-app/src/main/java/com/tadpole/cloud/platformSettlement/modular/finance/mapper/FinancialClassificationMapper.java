package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.Account;
import com.tadpole.cloud.platformSettlement.api.finance.entity.FinancialClassification;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.FinancialClassificationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.FinancialClassificationResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
* <p>
* 财务分类表 Mapper 接口
* </p>
*
* @author gal
* @since 2021-10-25
*/
public interface FinancialClassificationMapper extends BaseMapper<FinancialClassification> {

    Page<FinancialClassificationResult> findPageBySpec(
            @Param("page") Page page, @Param("paramCondition") FinancialClassificationParam paramCondition);


    List<FinancialClassificationResult> exportFinancialClassificationList( @Param("paramCondition") FinancialClassificationParam paramCondition);

    List<Account>  queryAccount(@Param("paramCondition") Account paramCondition);

    List<Account>  queryAccountList();

    void  changeStatus(@Param("paramCondition") FinancialClassificationParam paramCondition);

    List<FinancialClassificationResult>  getMaxWaterCode(@Param("paramCondition") FinancialClassificationParam paramCondition);

    /**
     * 获取指定财务分类、科目分类最大编码序号
     */
    @Select("select max(water_code) as water_code from cw_financial_classification where SUBJECT_CLASSIFICATION_ONE=#{subjectOne}  and  FINANCIAL_CLASSIFICATION=#{financialType} and  CLASSIFICATION_TYPE=#{classificationType}")
    String selectMaxByWaterCode(String subjectOne,String financialType,String classificationType);

    /**
     * 获取指定财务分类、科目分类编码
     */
    @Select("select t2.dict_code from CW_SYS_DICT t1 LEFT JOIN CW_SYS_DICT_DETAIL t2 ON t2.dict_id=t1.id where t1.dict_code=#{dictCode} and t2.dict_value=#{dictValue}")
    String selectMaxByDictCode(String dictCode,String dictValue);

}
