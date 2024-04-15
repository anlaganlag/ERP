package com.tadpole.cloud.externalSystem.modular.ebms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.math.BigDecimal;
import com.tadpole.cloud.externalSystem.modular.ebms.model.result.TbDwAnalyRuleResult;
import com.tadpole.cloud.externalSystem.modular.ebms.entity.TbDwAnalyRule;

 /**
 * TbDWAnalyRule;(Tb_DW_Analy_Rule)--数据库访问层
 * @author : LSY
 * @date : 2023-8-14
 */
@Mapper
public interface TbDwAnalyRuleMapper  extends BaseMapper<TbDwAnalyRule>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbDwAnalyRuleResult> selectByPage(IPage<TbDwAnalyRuleResult> page , @Param(Constants.WRAPPER) Wrapper<TbDwAnalyRule> wrapper);
}